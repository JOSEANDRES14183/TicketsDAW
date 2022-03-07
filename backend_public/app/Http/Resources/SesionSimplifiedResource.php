<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class SesionSimplifiedResource extends JsonResource
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
            'fecha_ini' => $this->fecha_ini,
            'sesionNumData' => $this->when($this->isNumerada(), $this->sesionNumData),
            'isNominativo'=>$this->evento->es_nominativo,
            'evento' => $this->evento,
            'sala' => $this->sala
        ];
    }
}
