<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Butaca extends Model
{
    use HasFactory;

    protected $table = 'butaca';
    protected $primaryKey = 'id_sala';

    public function sala()
    {
        return $this->belongsTo(Sala::class,'id_sala');
    }

    public function isOcupada($sesion) : bool{
        return (bool) DB::table("entrada")->
        where('id_sesion_numerada','=',$sesion->id)
            ->where('id_sala_butaca','=',$this->id_sala)
            ->where('pos_x_butaca','=',$this->pos_x)
            ->where('pos_y_butaca','=',$this->pos_y)->first();
    }

}
