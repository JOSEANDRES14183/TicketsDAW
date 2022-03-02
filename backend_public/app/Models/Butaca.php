<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Butaca extends Model
{
    use HasFactory;

    protected $table = 'butaca';
    protected $primaryKey = 'id_sala';

    public function sala(){
        return $this->belongsTo(Sala::class,'id_sala');
    }

    public function entrada()
    {
        return $this->belongsToMany(Entrada::class,'entrada_sesios_butaca','id_entrada')
            ->first();
    }

    public function sesion()
    {
        return $this->belongsToMany(Sesion::class,'entrada_sesion_butaca','id_sesion')
            ->first();
    }


}
