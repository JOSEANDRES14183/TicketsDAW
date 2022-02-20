<?php

namespace App\Http\Controllers;

use App\Models\Evento;
use Illuminate\Http\Request;

//Tutorial para controlar que datos aparecen en el JSON
//https://laravel.com/docs/9.x/eloquent-resources

class EventoController extends Controller
{
    public function index()
    {
        return Evento::with('sesiones.sala.butacas')
            ->with('fotoPerfil')
            ->with('categoria')
            ->with('organizador')
            ->get();
    }

    public function show($id)
    {
        return Evento::with('fotoPerfil')->find($id);
    }


}
