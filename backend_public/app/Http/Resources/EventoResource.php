<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class EventoResource extends JsonResource
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
            'titulo' => $this->titulo,
            'duracion_estandar' => $this->duracion_estandar,
            'foto_perfil' => $this->fotoPerfil,
            'categoria' => $this->categoria,
            'sesiones' => SesionResource::collection($this->sesiones),

        ];
    }
}
