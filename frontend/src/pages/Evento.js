import {Link, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Spinner} from "reactstrap";
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import bootstrap5Plugin from '@fullcalendar/bootstrap5';
import SesionInfo from "../components/SesionInfo";
import ErrorBoundaryHide from "../components/ErrorBoundaryHide";

function Evento(){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [evento, setEvento] = useState([]);

    const [sesionId, setSesionId] = useState(3);
    const [showSesion, setShowSesion] = useState(false);

    const params = useParams();

    useEffect(()=>{
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/eventos/"+params.id)
            .then(result => {
                setEvento(result.data);
                setLoading(false);
                setSesionId(result.data.latest_sesion.id);
                setShowSesion(true);
            })
            .catch(error => {
                setError(error);
            });
        },[]);

    if(error){
        return <p>{error}</p>
    }

    if (isLoading){
        return (
            <div className={"container-md my-3"}>
                <Spinner
                    type={"border"}
                    color={"primary"}>
                    Loading...
                </Spinner>
            </div>
        );
    }

    if (evento.length===0){
        return (
            <div className={"py-3 container-md"}>
                <p className={"mt-5 text-center text-secondary"}>No se ha encontrado ningún evento</p>
            </div>
        );
    }

    return (
            <div className={"py-3 container-md"}>
                <Link to={"/"}>
                    <Button className={"my-3"} color={"primary"}>
                        Volver
                    </Button>
                </Link>
                <section className="row py-3">
                    <div className="col-4">
                        <img className={"border-1 border-end img-fluid"}
                             src={process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + '/api/media/' +evento.foto_perfil.nombre_archivo}/>
                        <input type="number" name="test" id="test" onChange={(e) => setSesionId(e.target.value)}/>
                        <p>{sesionId}</p>
                    </div>
                    <div className="col-8">
                        <h2>{evento.titulo}</h2>
                        <p><i className="bi bi-clock" /> {evento.duracion_estandar} min.</p>
                        <p><i className="bi bi-bookmarks" /> {evento.categoria.nombre}</p>
                        <FullCalendar plugins={[dayGridPlugin, bootstrap5Plugin]}
                                      initialView="dayGridMonth"
                                      eventDisplay='block'
                                      themeSystem='bootstrap5'
                                      events={evento.sesiones}
                        />
                    </div>
                </section>
                <section className="py-3 border-1 border-top">
                    <ErrorBoundaryHide>
                        <SesionInfo sesionId={sesionId} visible={showSesion}></SesionInfo>
                    </ErrorBoundaryHide>
                </section>
                {evento.descripcion.length > 0 &&
                <section className="row py-3 border-1 border-top">
                    <div className="col-12">
                        <h3>Descripción</h3>
                        <p>{evento.descripcion}</p>
                    </div>
                </section>
                }
                <section className="row py-3 border-1 border-top">
                    <div className="col-12">
                        <h3>Restricciones y normas</h3>
                        {evento.edad_minima > 0 &&
                            <p><i className="bi bi-exclamation-diamond"></i> Apto para mayores de {evento.edad_minima} años</p>
                        }
                        <p><i className="bi bi-book-half"/> Haz click aquí para descargar las normas del evento</p>
                    </div>
                </section>
            </div>
    );
}

export default Evento;