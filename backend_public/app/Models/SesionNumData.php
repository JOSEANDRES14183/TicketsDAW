<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class SesionNumData extends Model
{
    use HasFactory;

    protected $table = 'sesion_numerada';
    protected $primaryKey = 'id';

    public $incrementing = false;
}
