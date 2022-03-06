<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class EntradaResource extends JsonResource
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
            'sesion_numerada' => $this->when($this->tipoEntrada()==null, $this->sesionNumerada),
            'precio' => $this->when($this->tipoEntrada()==null, $this->sesionNumerada->sesionNumData->precio),
            'tipo_entrada' => $this->tipoEntrada(),
            'butaca' => $this->butaca(),
        ];
    }
}
