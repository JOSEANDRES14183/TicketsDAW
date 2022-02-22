<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;

class RecursoMediaController extends Controller
{
    public function image($filename){
        return Response::download(env("APP_IMG_DIR").$filename);
    }
}
