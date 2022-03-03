<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ImgCarouselResource extends JsonResource
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
            "original" => env('APP_URL').'/api/media/'.$this->nombre_archivo,
            "thumbnail" => env('APP_URL').'/api/media/'.$this->nombre_archivo,
        ];
    }
}
