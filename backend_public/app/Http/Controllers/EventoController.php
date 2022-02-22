<?php

namespace App\Http\Controllers;

use App\Http\Resources\EventoCollection;
use App\Http\Resources\EventoResource;
use App\Models\Evento;
use Illuminate\Http\Request;

//Tutorial para controlar que datos aparecen en el JSON
//https://laravel.com/docs/9.x/eloquent-resources

class EventoController extends Controller
{
    public function index()
    {
        return EventoResource::collection(Evento::with('organizador')->get());
    }

    public function show($id)
    {
        return new EventoResource(Evento::find($id));
    }


}
