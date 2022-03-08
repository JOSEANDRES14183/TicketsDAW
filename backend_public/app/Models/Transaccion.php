<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Transaccion extends Model
{
    use HasFactory;

    protected $table = 'transaccion';
    protected $primaryKey = 'id';

    public $timestamps = false;
    protected $guarded = [];

    public function operacionCompra(){
        return $this->hasOne(OperacionCompra::class, "id_transaccion");
    }
}
