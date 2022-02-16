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
}
