<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class OrganizadorData extends Model
{
    use HasFactory;
    protected $table = 'organizador';
    protected $primaryKey = 'id';
    public $incrementing = false;
}
