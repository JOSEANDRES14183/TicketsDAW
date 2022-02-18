function EventoCard(props){

    return(
            <article className={"col-12 col-md-6 mb-1 p-2 evento overflow-hidden"}>
                <div className={"d-flex border border-secondary h-100"}>
                    <img className={"border-1 border-end img-fluid"}
                         src={'http://'+process.env.REACT_APP_API_HOST + '/api/media/' +props.evento.foto_perfil.nombre_archivo}/>
                    <div className={"d-flex flex-column p-3"}>
                        <h6>{props.evento.titulo}</h6>
                        <p>{props.evento.descripcion}</p>
                        <p>{props.evento.categoria.nombre}</p>
                    </div>
                </div>
            </article>
    )
}

export default EventoCard;