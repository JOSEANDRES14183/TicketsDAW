<?php

namespace App\Models;

use App\Http\Resources\UsuarioResource;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Evento extends Model
{
    use HasFactory;

    protected $table = 'evento';
    protected $primaryKey = 'id';

    protected $with = ['organizador'];

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

    public function categoria()
    {
        return $this->belongsTo(Categoria::class,'id_categoria');
    }

    public function organizador()
    {
        return new UsuarioResource($this->belongsTo(Usuario::class,'id_organizador'));
    }


}
