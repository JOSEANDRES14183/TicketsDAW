<?php

namespace App\Http\Controllers;

use App\Http\Resources\OperacionCompraResource;
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
}
