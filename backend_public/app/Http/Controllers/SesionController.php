<?php

namespace App\Http\Controllers;

use App\Http\Resources\EventoCollection;
use App\Http\Resources\EventoResource;
use App\Models\Evento;
use Illuminate\Http\Request;

class SesionController extends Controller
{
    public function show($id)
    {
        return new SesionResource(Sesion::find($id));
    }


}
