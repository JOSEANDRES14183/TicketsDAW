<?php

namespace App\Http\Controllers;

use App\Http\Resources\OperacionCompraResource;
use App\Libraries\Redsys\RedsysAPI;
use App\Models\Entrada;
use App\Models\OperacionCompra;
use App\Models\Sesion;
use App\Models\Transaccion;
use Firebase\JWT\Key;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Arr;
use Illuminate\Support\Facades\Http;
use PhpParser\Node\Expr\Array_;
use Firebase\JWT\JWT;


class PurchaseController extends Controller
{
    public function purchase(Request $request)
    {
        $success = false;
        $data = [];

        if ($request->butacas==null)
        {
            $requestValues = $request->all();
            $tiposEntrada = Arr::except($requestValues,'id_sesion');
            $entradas = array();
            $sesion = Sesion::find($request->id_sesion);
            $operacionCompra = OperacionCompra::create([]);

            foreach ($tiposEntrada as $tipoEntrada => $quantity){
                for ($i = $quantity;$i>0;$i--){
                    $entrada = Entrada::create([
                        'id_operacion_compra' => $operacionCompra->id,
                        'id_sesion_no_numerada' => $sesion->id,
                        'nombre_tipo_entrada' => $tipoEntrada
                    ]);
                    array_push($entradas,$entrada);
                }
            }

            $success = true;
            $token = JWT::encode(["id" => $operacionCompra->id],env("APP_TOKEN_SECRET"),'HS256');
            $data = ["success" => $success, "token_operacion" => ($success ? $token : null)];
        }

        else

        {
            $butacas = $request->butacas;
            $operacionCompra = OperacionCompra::create([]);
            $entradas = array();

            foreach ($butacas as $butaca){
                if (isset($butaca['seleccionada']) && $butaca['seleccionada']==true)
                {
                    $entrada = Entrada::create([
                        'id_operacion_compra' => $operacionCompra->id,
                        'id_sala_butaca' => $butaca['id_sala'],
                        'pos_x_butaca' => $butaca['pos_x'],
                        'pos_y_butaca' => $butaca['pos_y'],
                        'id_sesion_numerada' => $request->id_sesion,
                    ]);
                    array_push($entradas, $entrada);
                }
            }

            $success = true;
            $token = JWT::encode(["id" => $operacionCompra->id],env("APP_TOKEN_SECRET"),'HS256');
            $data = ["success" => $success, "token_operacion" => ($success ? $token : null)];
        }
        return response()->json($data);
    }

    public function show($token)
    {
        $id = JWT::decode($token,new Key(env("APP_TOKEN_SECRET"),'HS256'))->id;
        return new OperacionCompraResource(OperacionCompra::find($id));
    }

    public function processDetails(Request $request){

        $token = $request->token;

        $idOperacion = JWT::decode($token,new Key(env("APP_TOKEN_SECRET"),'HS256'))->id;

        $operacionCompra = OperacionCompra::find($idOperacion);

        if($operacionCompra->esta_finalizada || isset($operacionCompra->id_transaccion))
            return;

        //Redsys initialization
        $miObj = new RedsysAPI;

        $amount=intval($operacionCompra->totalPrice()*100);
        $id=time();
        $fuc=env("REDSYS_MERCHANTCODE");
        $terminal="001";
        $moneda="978";
        $trans="0";
        $url=env("APP_REACT_HOSTNAME");
        $urlOKKO=env("APP_URL") . "/api/finalize_purchase";

        //Process additional user submission: Name, email...

        $transaccion = Transaccion::create([
            'id' => $id,
            'cantidad' => $amount/100
        ]);

        $operacionCompra->id_transaccion = $id;
        $operacionCompra->save();

        $miObj->setParameter("DS_MERCHANT_AMOUNT",$amount);
        $miObj->setParameter("DS_MERCHANT_ORDER",$id);
        $miObj->setParameter("DS_MERCHANT_MERCHANTCODE",$fuc);
        $miObj->setParameter("DS_MERCHANT_CURRENCY",$moneda);
        $miObj->setParameter("DS_MERCHANT_TRANSACTIONTYPE",$trans);
        $miObj->setParameter("DS_MERCHANT_TERMINAL",$terminal);
        $miObj->setParameter("DS_MERCHANT_MERCHANTURL",$url);
        $miObj->setParameter("DS_MERCHANT_URLOK",$urlOKKO);
        $miObj->setParameter("DS_MERCHANT_URLKO",$urlOKKO);

        $version=env("REDSYS_VERSION");
        $kc = env("REDSYS_KEY");

//        $request = "";
        $params = $miObj->createMerchantParameters();
        $signature = $miObj->createMerchantSignature($kc);

        return response()->json([
            "submitURL" => env("REDSYS_SUBMIT_URL"),
            "formElements" => [
                [
                    "name" => "Ds_MerchantParameters",
                    "value" => $params
                ],
                [
                    "name" => "Ds_SignatureVersion",
                    "value" => $version
                ],
                [
                    "name" => "Ds_Signature",
                    "value" => $signature
                ]
            ]
        ]);
    }

    public function finalize_purchase(Request $request){
        $redsys = new RedsysAPI;

        $version = $request -> Ds_SignatureVersion;
        $datos = $request -> Ds_MerchantParameters;
        $signatureRecibida = $request -> Ds_Signature;

        $decodec = $redsys->decodeMerchantParameters($datos);
        $responseObj = json_decode($decodec);
        $kc = env("REDSYS_KEY");
        $firma = $redsys->createMerchantSignatureNotif($kc,$datos);

        echo PHP_VERSION."<br/>";
        echo $firma."<br/>";
        echo $signatureRecibida."<br/>";
        if ($firma === $signatureRecibida){
            echo "FIRMA OK";
            echo "<br>";
            if($responseObj->Ds_Response >= 0 && $responseObj->Ds_Response <= 99){
                echo "RESPONSE CODE OK";
                dd($responseObj);
            }
        } else {
            echo "FIRMA KO";
        }
    }
}
