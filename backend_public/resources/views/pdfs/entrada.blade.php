<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
</head>
<body>
<div>
    <h1>{{$entrada->sesionNumerada ? $entrada->sesionNumerada->evento->titulo : $entrada->tipoEntrada()->sesion->evento->titulo}}</h1>
    <h2>{{$entrada->sesionNumerada ? "Butaca " . $entrada->butaca()->num_butaca : $entrada->tipoEntrada()->nombre}}</h2>
    <h4>
        Direccion: {{
    $entrada->sesionNumerada
    ? $entrada->sesionNumerada->sala->direccion . ", " . $entrada->sesionNumerada->sala->ciudad()->first()->nombre . ', '. $entrada->sesionNumerada->sala->nombre
    : $entrada->tipoEntrada()->sesion->sala->direccion . ", " . $entrada->tipoEntrada()->sesion->sala->ciudad()->first()->nombre . ", " . $entrada->tipoEntrada()->sesion->sala->nombre}}
    </h4>
    <p>{{
    $entrada->sesionNumerada
    ? $entrada->sesionNumerada->fecha_ini
    : $entrada->tipoEntrada()->sesion->fecha_ini}}
    </p>

    @if($entrada->sesionNumerada)
        @if($entrada->sesionNumerada->evento->es_nominativo)
            <p>Nombre: {{$entrada->nombre_asistente}}</p>
            <p>Apellidos: {{$entrada->apellidos_asistente}}</p>
        @endif
    @else
        @if($entrada->tipoEntrada()->sesion->evento->es_nominativo)
            <p>Nombre: {{$entrada->nombre_asistente}}</p>
            <p>Apellidos: {{$entrada->apellidos_asistente}}</p>
        @endif
    @endif

</div>
<img style="width: 250px; height: 250px" src="{{$imgSrc}}" alt="QR Code">
</body>
</html>
