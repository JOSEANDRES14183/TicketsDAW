<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Sesion extends Model
{
    use HasFactory;

    protected $table = 'sesion';

    protected $primaryKey = 'id';

    public function sala(){
        return $this->belongsTo(Sala::class,'id_sala');
    }


}
