<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Laravel\Sanctum\HasApiTokens;

class OperacionCompra extends Model
{
    use HasFactory, HasApiTokens;

    protected $table = 'operacion_compra';
    protected $primaryKey = 'id';

    public $timestamps = false;
    protected $guarded = [];

    public function entradas(){
        return $this->hasMany(Entrada::class,"id_operacion_compra");
    }

    public function totalPrice(){
        $entradas = $this->entradas;

        $precio = 0.0;

        foreach ($entradas as $entrada){
            $tipoEntrada = $entrada->tipoEntrada();
            if(isset($tipoEntrada) && $tipoEntrada != null){
                $precio += $tipoEntrada->precio;
            }

            $sesionNum = $entrada->sesionNumerada;
            if(isset($sesionNum) && $sesionNum != null){
                $precio += $sesionNum->sesionNumData->precio;
            }
        }

        return $precio;
    }

}
