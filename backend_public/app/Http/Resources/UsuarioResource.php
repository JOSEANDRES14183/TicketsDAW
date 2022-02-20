<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class UsuarioResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array|\Illuminate\Contracts\Support\Arrayable|\JsonSerializable
     */
    public function toArray($request)
    {
        return [
            'id'=>$this->id,
            'nombre'=>$this->organizador_data->nombre,
            'descripcion'=>$this->organizador_data->descripcion,
            'foto_perfil'=>$this->organizador_data->foto_perfil,
            'email'=>$this->email
        ];
    }
}
