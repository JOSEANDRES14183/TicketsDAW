<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class SesionResource extends JsonResource
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
            'entradas_max' => $this->entradas_max,
            'fecha_fin_venta' => $this->fecha_fin_venta,
            'fecha_ini' => $this->fecha_ini,
            'duracion' => $this->duracion,
            'tipos_entrada' => $this->tiposEntrada,
            'sala' => new SalaResource($this->sala)
        ];
    }
}
