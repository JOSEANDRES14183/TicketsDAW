<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Ciudad extends Model
{
    use HasFactory;

    protected $table = 'ciudad';

    protected $primaryKey = 'id';

    public function salas(){
        return $this->hasMany(Sala::class);
    }
}
