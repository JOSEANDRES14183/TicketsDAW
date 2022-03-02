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

    public function tipoEntrada()
    {
        return DB::table('tipo_entrada')
            ->where('nombre','=',$this->nombre_tipo_entrada)
            ->where('id_sesion','=',$this->id_sesion)
            ->first();
    }

    public function butacas()
    {
        return $this->belongsToMany(Butaca::class,'entrada_sesion_butaca','id_sala_butaca');
//        $butacaKeys = DB::table('entrada_sesion_butaca')
//            ->where('id_entrada','=',$this->id)
//            ->get(['id_sala_butaca','pos_x_butaca','pos_y_butaca']);
//
//        return Butaca::where('id_sala','=',$butacaKeys['id_sala_butaca'])
//            ->where('pos_x','=','pos_x_butaca')
//            ->where('pos_y','=','pos_y_butaca')
//            ->first();
    }

    public function sesion()
    {
        return $this->belongsToMany(Sesion::class,"entrada_sesion_butaca",'id_sesion')
            ->first();
    }









}
