<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TipoEntrada extends Model
{
    use HasFactory;

    protected $table = 'tipo_entrada';
    protected $primaryKey = 'id_sesion';

    public function sesion(){
        return $this->belongsTo(Sesion::class,'id_sesion');
    }

}
