<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Entrada extends Model
{
    use HasFactory;

    protected $table = 'entrada';
    protected $primaryKey = 'id';

    protected $guarded = [];

    public $timestamps = false;

    //Esto no devuelve una relacion de Eloquent, por lo que al llamarlo lo tienes que hacer así: $this->tipoEntrada()
    public function tipoEntrada()
    {
        return TipoEntrada::where('nombre','=',$this->nombre_tipo_entrada)
            ->where('id_sesion','=',$this->id_sesion_no_numerada)
            ->first();
    }

    //Esto no devuelve una relacion de Eloquent, por lo que al llamarlo lo tienes que hacer así: $this->butaca()
    public function butaca()
    {
        return Butaca::where('id_sala','=',$this->id_sala_butaca)
            ->where('pos_x','=',$this->pos_x_butaca)
            ->where('pos_y','=',$this->pos_y_butaca)->first();
    }

    public function sesionNumerada()
    {
        return $this->belongsTo(Sesion::class,"id_sesion_numerada");
    }

    public function operacionCompra()
    {
        return $this->belongsTo(OperacionCompra::class,"id_operacion_compra");
    }









}
