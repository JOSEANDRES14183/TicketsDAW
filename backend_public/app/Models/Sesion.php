<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Sesion extends Model
{
    use HasFactory;

    protected $table = 'sesion';
    protected $primaryKey = 'id';

    public function isNumerada(){
        return (bool) $this->sesionNumData;
    }

    public function sala(){
        return $this->belongsTo(Sala::class,'id_sala');
    }

    public function evento(){
        return $this->belongsTo(Evento::class,'id_evento');
    }

    public function tiposEntrada(){
        return $this->hasMany(TipoEntrada::class,'id_sesion');
    }

    public function sesionNumData(){
        return $this->hasOne(SesionNumData::class,'id');
    }

}
