<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Evento extends Model
{
    use HasFactory;

    protected $table = 'evento';
    protected $primaryKey = 'id';

    //It is necessary to define if the primary key is not incremental
    //https://laravel.com/docs/9.x/eloquent#introduction
    //public $incrementing = false;

    public function sesiones()
    {
        return $this->hasMany(Sesion::class,'id_evento');
    }

    public function fotoPerfil()
    {
        return $this->belongsTo(RecursoMedia::class,'foto_perfil');
    }

    public function normasEvento()
    {
        return $this->belongsTo(NormasEvento::class,'documento_normas');
    }

    public function categoria()
    {
        return $this->belongsTo(Categoria::class,'id_categoria');
    }

    public function organizador()
    {
        return $this->belongsTo(Usuario::class,'id_organizador');
    }

    public function imagenes()
    {
        return $this->hasMany(RecursoMedia::class,'id_evento_galeria_img');
    }


}
