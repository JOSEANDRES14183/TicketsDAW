import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";

function EventoCard(props){

    const {t} = useTranslation();

    return(
            <article className={"col-12 col-xl-6 mb-1 p-2 evento overflow-hidden"}>
                <div className={"rounded-25 h-100 shadow-sm position-relative"}>
                    <Link to={"/"+props.evento.id} className={"w-100 h-100 position-absolute top-0 left-0"} />

                    <div className={"row w-100 h-100"}>
                        <div className={"col-6 col-sm-4 col-xl-5 h-100"}>
                            <img className={"w-100 rounded-25 border-1 border-end fit-cover"}
                                 src={process.env.REACT_APP_API_PROTOCOL_PREFIX+process.env.REACT_APP_API_HOST + '/api/media/' +props.evento.foto_perfil.nombre_archivo}
                                 alt={props.evento.foto_perfil.nombre_archivo}
                            />
                        </div>

                        <div className={"col d-flex flex-column align-content-start p-3 h-100"}>
                            <h6>{props.evento.titulo}</h6>
                            <p className={"text-secondary fw-bold"}>
                                {t('category.'+props.evento.categoria.nombre.toLowerCase())}
                            </p>
                            <p><i className="bi bi-geo-alt-fill" /> {props.evento.latest_sesion.sala.direccion}</p>
                            <p className={"text-end text-primary h4 pe-3"}>
                                {props.evento.latest_sesion.isNumerada ? props.evento.latest_sesion.sesionNumData.precio : props.evento.latest_sesion.tipos_entrada[0].precio}€
                            </p>
                        </div>
                    </div>



                </div>
            </article>
    )
}

export default EventoCard;