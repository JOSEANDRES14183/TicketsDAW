<?php

namespace App\Http\Controllers;

use App\Http\Resources\OrganizadorResource;
use App\Models\Usuario;
use Illuminate\Http\Request;

class OrganizadorController extends Controller
{
    public function show($id)
    {
        $organizador = Usuario::with('eventos')->find($id);
        if ($organizador->organizadorData == null || $organizador->esta_validado == false || $organizador->esta_deshabilitado == true){
            return null;
        }
        return new OrganizadorResource($organizador);
    }

}
