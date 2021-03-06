<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class SalaResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array|\Illuminate\Contracts\Support\Arrayable|\JsonSerializable
     */
    public function toArray($request)
    {

        //$this->idSesion
        $butacasWithIdSesion = $this->butacas;
        foreach ($butacasWithIdSesion as $butaca){
            $butaca->idSesion=$this->idSesion;
        }

        return [
            'id' => $this->id,
            'nombre' => $this->nombre,
            'direccion' => $this->direccion,
            'latitud' => $this->latitud,
            'longitud' => $this->longitud,
            'aforo_max' => $this->aforo_max,
            'butacas' => ButacaResource::collection($butacasWithIdSesion),
        ];
    }
}
