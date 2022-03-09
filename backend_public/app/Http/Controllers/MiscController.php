<?php

namespace App\Http\Controllers;

use App\Models\Ciudad;
use App\Models\Sala;
use Illuminate\Http\Request;

class MiscController extends Controller
{
    public function ciudades()
    {
        return Ciudad::all();
    }

    public function salasByCiudad($idCiudad)
    {
        return Sala::where('ciudad',$idCiudad)->get();
    }
}
