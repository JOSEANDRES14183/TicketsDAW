<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class OrganizadorResource extends JsonResource
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
            'id' => $this->id,
            'nombre' => $this->organizadorData->nombre,
            'descripcion' => $this->organizadorData->descripcion,
            'email' => $this->email,
            'foto_perfil' => $this->organizadorData->fotoPerfil,
            'eventos' => EventoResource::collection($this->eventos->where('esta_oculto',false))
        ];
    }
}
