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

        $salaWithSesion = $this->sala;
        $salaWithSesion->idSesion=$this->id;

        return [
            'id' => $this->id,
            'entradas_max' => $this->entradas_max,
            'fecha_fin_venta' => $this->fecha_fin_venta,
            'fecha_ini' => $this->fecha_ini,
            'duracion' => $this->duracion,
            'isNumerada' => $this->isNumerada(),
            'sesionNumData' => $this->when($this->isNumerada(), $this->sesionNumData),
            'tipos_entrada' => $this->when(!$this->isNumerada(), $this->tiposEntrada),
            'sala' => new SalaResource($salaWithSesion)
        ];
    }
}
