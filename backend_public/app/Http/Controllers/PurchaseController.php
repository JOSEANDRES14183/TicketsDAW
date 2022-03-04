<?php

namespace App\Http\Controllers;

use App\Models\Sesion;
use Illuminate\Http\Request;
use Illuminate\Support\Arr;
use PhpParser\Node\Expr\Array_;

class PurchaseController extends Controller
{
    public function purchase(Request $request)
    {
        if ($request->butacas==null)
        {
            $data = $request->all();
            $sesion = Sesion::find($request->id_sesion);
            $tiposEntrada = Arr::except($data,'id_sesion');

            dd($tiposEntrada);
        } else
        {

        }
    }
}
