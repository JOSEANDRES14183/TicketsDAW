<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Usuario extends Model
{
    use HasFactory;

    protected $table = 'usuario';
    protected $primaryKey = 'id';

    protected $hidden =['password_hash'];

    protected $with = ['organizadorData'];

    public function organizadorData()
    {
        return $this->hasOne(OrganizadorData::class,'id');
    }

}
