import {Link, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Spinner} from "reactstrap";
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import bootstrap5Plugin from '@fullcalendar/bootstrap5';
import SesionInfo from "../components/SesionInfo";
import ErrorBoundaryHide from "../components/ErrorBoundaryHide";
import OrganizadorBanner from "../components/OrganizadorBanner";
import {useTranslation} from "react-i18next";
import CarouselEvento from "../components/CarouselEvento";

function Evento(){

    const {t} = useTranslation();


    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [evento, setEvento] = useState([]);

    const [sesionId, setSesionId] = useState(null);

    const [fullCalendarHeader, setFullCalendarHeader] = useState({
        start: 'title', // will normally be on the left. if RTL, will be on the right
        center: '',
        end: 'today prev,next' // will normally be on the right. if RTL, will be on the left
    });

    const params = useParams();

    useEffect(()=>{
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/eventos/"+params.id)
            .then(result => {
                setEvento(result.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
            });
        }, [params]);

    useEffect(()=>{
        updateCalendarToolbar();
    }, [])

    const updateCalendarToolbar = () => {
        if(window.innerWidth >= 576){
            setFullCalendarHeader({
                start: 'title', // will normally be on the left. if RTL, will be on the right
                center: '',
                end: 'today prev,next' // will normally be on the right. if RTL, will be on the left
            })
        }
        else{
            setFullCalendarHeader({
                start: 'title', // will normally be on the left. if RTL, will be on the right
                center: '',
                end: 'prev,next' // will normally be on the right. if RTL, will be on the left
            })
        }
    }

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

    const eventClick = (info) =>{
        setSesionId(info.event.extendedProps.sesion_id);
    }

    return (
            <div className={"py-3 container-md"}>
                <Link to={"/"}>
                    <Button className={"my-3"} color={"primary"}>
                        Volver
                    </Button>
                </Link>
                <section className="row py-3">
                    <div className="col-md-4 col-12">
                        <img className={"border-1 border-end img-fluid mb-3"}
                             src={process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + '/api/media/' +evento.foto_perfil.nombre_archivo}/>
                        <OrganizadorBanner className={"d-md-flex d-none"} user={evento.organizador}></OrganizadorBanner>
                    </div>
                    <div className="col-md-8 col-12 mt-3 mt-md-0">
                        <h2>{evento.titulo}</h2>
                        <p><i className="bi bi-clock" /> {evento.duracion_estandar} min.</p>
                        <p className={"mb-3 mb-md-0"}><i className="bi bi-bookmarks" /> {evento.categoria.nombre}</p>
                        <FullCalendar plugins={[dayGridPlugin, bootstrap5Plugin]}
                                      initialView="dayGridMonth"
                                      eventDisplay='block'
                                      themeSystem='bootstrap5'
                                      locale={t("lang")}
                                      events={evento.sesiones}
                                      eventClick={eventClick}
                                      headerToolbar={fullCalendarHeader}
                                      windowResize={() => {
                                          updateCalendarToolbar()
                                      }}
                                      // dayCellDidMount={function (arg){
                                      //     if(arg.date.getDate() > 15 ){
                                      //         arg.el.style.backgroundColor = "red";
                                      //
                                      //     }
                                      // }}
                        />
                    </div>
                </section>

                <ErrorBoundaryHide>
                    <SesionInfo sesionId={sesionId}></SesionInfo>
                </ErrorBoundaryHide>

                {evento.descripcion.length > 0 &&
                <section className="row py-3 border-1 border-top">
                    <div className="col-12">
                        <h3>Descripción</h3>
                        <p>{evento.descripcion}</p>
                        <OrganizadorBanner className={"d-md-none"} user={evento.organizador}></OrganizadorBanner>
                    </div>
                </section>
                }
                <section className="row py-3 border-1 border-top">
                    <div className="col-12">
                        <h3>Restricciones y normas</h3>
                        {evento.edad_minima > 0 ?
                            <p><i className="bi bi-exclamation-diamond"></i> Apto para mayores de {evento.edad_minima} años</p>
                            :
                            <p><i className="bi bi-info-circle"></i> Apto para todas las edades</p>
                        }
                        {evento.normas_evento &&
                            <a href={process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + '/api/media/' +evento.normas_evento.nombre_pdf}><i className="bi bi-book-half"/> Haz click aquí para descargar las normas del evento</a>
                        }
                    </div>
                </section>
                {evento.imagenes.length > 0 &&
                    <section className="row py-3 border-1 border-top">
                        <h3>Galeria de imágenes</h3>
                        <CarouselEvento imagenes={evento.imagenes}/>
                    </section>
                }
            </div>
    );
}

export default Evento;