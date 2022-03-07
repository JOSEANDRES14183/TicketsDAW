<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ButacaResource extends JsonResource
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
            'ocupada' => $this->isOcupada($this->idSesion),
            'id_sala' => $this->id_sala,
            'num_butaca' => $this->num_butaca,
            'pos_x' => $this->pos_x,
            'pos_y' => $this->pos_y,
            'tipo_butaca' => $this->tipo_butaca
        ];
    }
}
