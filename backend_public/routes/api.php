<?php

use App\Http\Controllers\EventoController;
use App\Http\Controllers\OrganizadorController;
use App\Http\Controllers\PurchaseController;
use App\Http\Controllers\SesionController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\RecursoMediaController;
use App\Http\Controllers\MiscController;

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
Route::post('eventos/check/{token}', [EventoController::class, 'checkEntrada']);

Route::get('sesiones/{id}', [SesionController::class,'show']);

Route::get('media/{filename}', [RecursoMediaController::class,'image']);

Route::get('organizador/{id}', [OrganizadorController::class,'show']);

Route::post('purchase', [PurchaseController::class, 'purchase']);

Route::get('purchase/{token}', [PurchaseController::class, 'show']);

Route::post('purchase_details', [PurchaseController::class, 'processDetails']);
Route::get('finalize_purchase', [PurchaseController::class, 'finalize_purchase']);

Route::get('ciudades',[MiscController::class,'ciudades']);
Route::get('salas/{idCiudad}',[MiscController::class,'salasByCiudad']);

