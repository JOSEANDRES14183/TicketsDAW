<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Sala extends Model
{
    use HasFactory;

    protected $table = 'sala';
    protected $primaryKey = 'id';

    protected $with = 'ciudad';

    public function ciudad(){
        return $this->belongsTo(Ciudad::class,'ciudad');
    }

    public function butacas(){
        return $this->hasMany(Butaca::class,'id_sala');
    }
}
