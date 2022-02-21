import {Link} from "react-router-dom";

function EventoCard(props){

    return(
            <article className={"col-12 col-md-6 mb-1 p-2 evento overflow-hidden"}>
                <div className={"d-flex border border-secondary rounded-25 h-100 shadow-sm position-relative"}>
                    <Link to={"/"+props.evento.id} className={"w-100 h-100 position-absolute top-0 left-0"}></Link>
                    <img className={"rounded-25 border-1 border-end img-fluid"}
                         src={'http://'+process.env.REACT_APP_API_HOST + '/api/media/' +props.evento.foto_perfil.nombre_archivo}/>
                    <div className={"d-flex flex-column p-3"}>
                        <h6>{props.evento.titulo}</h6>
                        <p className={"text-secondary fw-bold"}>{props.evento.categoria.nombre}</p>
                        <p><i className="bi bi-geo-alt-fill" /> {props.evento.sesiones[0].sala.direccion}</p>
                    </div>
                </div>
            </article>
    )
}

export default EventoCard;