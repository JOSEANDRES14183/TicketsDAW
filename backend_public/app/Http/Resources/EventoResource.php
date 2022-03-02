<?php

namespace App\Http\Resources;

use App\Models\Sesion;
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
            'descripcion' => $this->descripcion,
            'edad_minima' => $this->edad_minima,
            'es_nominativo' => $this->es_nominativo,
            'duracion_estandar' => $this->duracion_estandar,
            'foto_perfil' => $this->fotoPerfil,
            'organizador' => new OrganizadorResource($this->organizador),
            'categoria' => $this->categoria,
            'latest_sesion' => new SesionResource($this->sesiones->sortBy('fecha_ini')->first()),
            'sesiones' => SesionFullCalendarResource::collection($this->sesiones),
        ];
    }
}
