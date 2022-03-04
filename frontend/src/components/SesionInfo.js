import React, {useEffect, useState} from "react";
import {Status, Wrapper} from "@googlemaps/react-wrapper";
import GoogleMaps from "./GoogleMaps";
import axios from "axios";
import {Form, FormGroup, Input, Label, Spinner} from "reactstrap";
import GoogleMapsMarker from "./GoogleMapsMarker";
import SeatMap from "./SeatMap";
import dayjs from "dayjs";
import {t} from "i18next";

function SesionInfo({sesionId}){
    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [sesion, setSesion] = useState(null);

    const [lat, setLat] = useState(0);
    const [lng, setLng] = useState(0);

    const [visible, setVisible] = useState(false);

    useEffect(()=>{
        let isMounted = true;
        if(sesionId){
            if(isMounted){
                axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/sesiones/"+sesionId)
                    .then(result => {
                        setSesion(result.data);
                        setLat(result.data.sala.latitud)
                        setLng(result.data.sala.longitud)
                        setLoading(false);
                        setVisible(true);
                    })
                    .catch(error => {
                        setError(error);
                    });
            }
        }
        else if(isMounted){
            setVisible(false);
            setLoading(false);
        }
        return () => { isMounted = false }
    },[sesionId]);

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

    if(!visible){
        return(null);
    }

    const handleFormSubmit = (e) => {
        e.preventDefault()
        console.log(e)
        console.log(e.target)
        console.log(new FormData(e.target))
    }

    //debugger;
    return(
        <section className="py-3 border-1 border-top">
            <div className={"row"}>
                <div className="col-md-4 col-12">
                    <div className={"mb-3"} style={{height: "300px"}}>
                        <Wrapper apiKey={process.env.REACT_APP_API_GOOGLE_MAPS_KEY}>
                            <GoogleMaps center={{"lat": lat, "lng": lng}} zoom={15}>
                                <GoogleMapsMarker position={{"lat": lat, "lng": lng}}></GoogleMapsMarker>
                            </GoogleMaps>
                        </Wrapper>
                    </div>
                    <p className={"mb-3"}><i className="bi bi-geo-alt"></i> {sesion.sala.direccion}</p>
                    <p className={"mb-3"}><i className="bi bi-calendar-week"></i> {dayjs(sesion.fecha_ini).format("DD-MM-YYYY HH:MM")}</p>
                </div>
                <div className="col-md-8 col-12">
                    {!sesion.isNumerada &&
                        <Form onSubmit={(e)=>{handleFormSubmit(e)}} className={"h-100 d-flex justify-content-between flex-column align-items-end"} action={process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/purchase"} method={"post"}>
                            <div className={"w-100"}>
                                <input type="hidden" name="id_sesion" value={sesion.sala.id} />
                                {sesion.tipos_entrada.map((tipo, i) =>
                                    <FormGroup key={i} className="d-flex justify-content-between align-items-center tipoEntrada rounded bg-light p-2 w-100">
                                        <Label for={"tipoEntrada" + i}>{tipo.nombre}</Label>
                                        <div className="d-flex flex-row align-items-center">
                                            <span className="me-2">{tipo.precio + "€"}</span>
                                            <Input id={"tipoEntrada" + i} name={tipo.nombre} type="number" min="0" defaultValue="0"></Input>
                                        </div>
                                    </FormGroup>
                                )
                                }
                            </div>
                            <button className={"btn btn-primary"} type="submit">{t("buy")}</button>
                        </Form>
                    }
                    {sesion.isNumerada &&
                        <Form className="h-100 rounded-3 bg-light">
                            <SeatMap seats={sesion.sala.butacas}></SeatMap>
                        </Form>
                    }
                </div>
            </div>
        </section>
    );

}

export default SesionInfo;