<?php

namespace App\Http\Controllers;

use App\Http\Resources\OperacionCompraResource;
use App\Libraries\Redsys\RedsysAPI;
use App\Models\Entrada;
use App\Models\OperacionCompra;
use App\Models\Sesion;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Arr;
use Illuminate\Support\Facades\Http;
use PhpParser\Node\Expr\Array_;

class PurchaseController extends Controller
{
    public function purchase(Request $request)
    {
        $success = false;

        if ($request->butacas==null)
        {
            $data = $request->all();
            $sesion = Sesion::find($request->id_sesion);
            $tiposEntrada = Arr::except($data,'id_sesion');

            $operacionCompra = OperacionCompra::create([]);

            $entradas = array();

            foreach ($tiposEntrada as $tipoEntrada => $quantity){
                for ($i = $quantity;$i>0;$i--){
                    $entrada = Entrada::create([
                        'id_operacion_compra' => $operacionCompra->id,
                        'id_sesion_numerada' => $sesion->id,
                        'nombre_tipo_entrada' => $tipoEntrada
                    ]);
                    array_push($entradas,$entrada);
                }
            }
            $success = true;


            return response()->json([
                "success" => $success,
                "id_operacion_compra" => ($success ? $operacionCompra->id : null)
            ]);

        }

        else

        {

        }
    }

    public function show($id)
    {
        return new OperacionCompraResource(OperacionCompra::find($id));
    }

    public function processDetails(Request $request){
        $miObj = new RedsysAPI;

        $amount="1513";
        $id=time();
        $fuc="999008881";
        $terminal="001";
        $moneda="978";
        $trans="0";
        $url="";
        $urlOKKO="http://laravelapi.local/api/finalize_purchase";

        $miObj->setParameter("DS_MERCHANT_AMOUNT",$amount);
        $miObj->setParameter("DS_MERCHANT_ORDER",$id);
        $miObj->setParameter("DS_MERCHANT_MERCHANTCODE",$fuc);
        $miObj->setParameter("DS_MERCHANT_CURRENCY",$moneda);
        $miObj->setParameter("DS_MERCHANT_TRANSACTIONTYPE",$trans);
        $miObj->setParameter("DS_MERCHANT_TERMINAL",$terminal);
        $miObj->setParameter("DS_MERCHANT_MERCHANTURL",$url);
        $miObj->setParameter("DS_MERCHANT_URLOK",$urlOKKO);
        $miObj->setParameter("DS_MERCHANT_URLKO",$urlOKKO);

        $version="HMAC_SHA256_V1";
        $kc = 'sq7HjrUOBfKmC576ILgskD5srU870gJ7'; //Llave de ejemplo

//        $request = "";
        $params = $miObj->createMerchantParameters();
        $signature = $miObj->createMerchantSignature($kc);

//        $merchantParametersJsonRaw = json_encode([
//                "DS_MERCHANT_AMOUNT" => "CANTIDAD",
//                "DS_MERCHANT_CURRENCY" => "978",
//                "DS_MERCHANT_MERCHANTCODE" => "CANTIDAD",
//                "DS_MERCHANT_ORDER" => "TEMP",
//                "DS_MERCHANT_TERMINAL" => "CANTIDAD",
//                "DS_MERCHANT_TRANSACTIONTYPE" => "CANTIDAD",
//            ]);

        return response()->json([
            "submitURL" => "https://sis-t.redsys.es:25443/sis/realizarPago",
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
        $kc = 'sq7HjrUOBfKmC576ILgskD5srU870gJ7'; //Llave de ejemplo
        $firma = $redsys->createMerchantSignatureNotif($kc,$datos);

        echo PHP_VERSION."<br/>";
        echo $firma."<br/>";
        echo $signatureRecibida."<br/>";
        if ($firma === $signatureRecibida){
            echo "FIRMA OK";
            dd($decodec);
        } else {
            echo "FIRMA KO";
        }
    }
}
