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


}
