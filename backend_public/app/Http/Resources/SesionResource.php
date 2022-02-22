<?php

namespace App\Http\Resources;

use Carbon\Carbon;
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
        $date = Carbon::createFromFormat('Y-m-d H:i:s', $this->fecha_ini);
        $date->addMinutes($this->evento->duracion_estandar);

        return [
            'title' => $this->evento->duracion_estandar.' min.',
            'start' => $this->fecha_ini,
            'end' => $date->toDateTimeString(),
            'backgroundColor' => 'red',
            'borderColor' => '#E50000',
            'extendedProps'=>[
                'sesion_id' => $this->id
            ]
        ];
    }

}
