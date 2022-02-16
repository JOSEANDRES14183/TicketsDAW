<?php

namespace App\Http\Controllers;

use App\Models\Evento;
use Illuminate\Http\Request;

class EventoController extends Controller
{
    public function index()
    {
        return Evento::with('sesiones.sala.butacas')->get();
    }

    public function show($id)
    {
        return Evento::find($id);
    }


}
