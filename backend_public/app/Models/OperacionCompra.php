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

}
