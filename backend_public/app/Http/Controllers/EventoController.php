<?php

namespace App\Http\Controllers;

use App\Http\Resources\EntradaResource;
use App\Http\Resources\EventoCollection;
use App\Http\Resources\EventoResource;
use App\Models\Entrada;
use App\Models\Evento;
use Carbon\Carbon;
use Firebase\JWT\JWT;
use Firebase\JWT\Key;
use Illuminate\Http\Request;
use phpDocumentor\Reflection\Types\False_;

//Tutorial para controlar que datos aparecen en el JSON
//https://laravel.com/docs/9.x/eloquent-resources

class EventoController extends Controller
{
    public function index(Request $request)
    {
        if (!$request->date){
            return EventoResource::collection(Evento::where('esta_oculto',false)->get());
        }

//        $requestedDate = Carbon::createFromFormat('Y-m-d',$request->date);

        return EventoResource::collection(Evento::where('esta_oculto',false)

            ->whereHas('sesiones', function ($sesiones) use ($request){
                $sesiones->whereDate('fecha_ini','=',date($request->date));
            })->get());
    }

    public function show($id)
    {
        $evento = Evento::find($id);
        if ($evento!=null){
            return new EventoResource($evento);
        }

        return $evento;
    }

    public function checkEntrada($token)
    {
        try {
            $idEntrada = JWT::decode($token,new Key(env("APP_TOKEN_SECRET"),'HS256'))->id;
        } catch (\UnexpectedValueException $e){
            return response()->json([
                'success' => false
            ]);
        }
        $entrada = Entrada::find($idEntrada);
        if ($entrada){
            $entrada->success=true;
            return new EntradaResource($entrada);
        }
        return response()->json([
            'success' => false
        ]);
    }


}
