<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class NormasEvento extends Model
{
    use HasFactory;

    protected $table = 'normas_evento';

    protected $primaryKey = 'id';

}
