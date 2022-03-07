<?php

namespace App\Http\Controllers;

use App\Http\Resources\OperacionCompraResource;
use App\Models\Entrada;
use App\Models\OperacionCompra;
use App\Models\Sesion;
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
}
