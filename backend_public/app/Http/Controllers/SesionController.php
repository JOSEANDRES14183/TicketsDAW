<?php

namespace App\Http\Controllers;

use App\Http\Resources\EventoCollection;
use App\Http\Resources\SesionResource;
use App\Models\Sesion;

class SesionController extends Controller
{
    public function show($id)
    {
        return new SesionResource(Sesion::find($id));
    }


}
