<?php

namespace App\Http\Controllers;

use App\Http\Resources\EventoCollection;
use App\Http\Resources\EventoResource;
use App\Models\Evento;
use Carbon\Carbon;
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
        return new EventoResource(Evento::find($id));
    }


}
