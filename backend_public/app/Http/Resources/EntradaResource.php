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
            'success' => $this->when($this->success!=null, $this->success),
            'id' => $this->id,
            'sesion_numerada' => $this->when($this->sesionNumerada!=null, new SesionSimplifiedResource($this->sesionNumerada)),
            'tipo_entrada' => $this->when($this->tipoEntrada()!=null, new TipoEntradaResource($this->tipoEntrada())),
            'butaca' => $this->butaca(),
        ];
    }
}
