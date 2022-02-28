<?php

namespace App\Http\Resources;

use Carbon\Carbon;
use Illuminate\Http\Resources\Json\JsonResource;

class SesionFullCalendarResource extends JsonResource
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
        $date->addMinutes($this->duracion);

        return [
            'title' => $this->duracion.' min.',
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
