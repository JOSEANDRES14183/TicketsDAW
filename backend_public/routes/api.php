<?php

use App\Http\Controllers\EventoController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\RecursoMediaController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('eventos', [EventoController::class,'index']);
Route::get('eventos/{id}', [EventoController::class,'show']);

Route::get('media/{filename}', [RecursoMediaController::class,'image']);

//Route::get('organizador/{id}',function ($id){
//    return new \App\Http\Resources\OrganizadorResource(\App\Models\Usuario::find($id));
//});
