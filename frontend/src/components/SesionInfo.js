import React, {useEffect, useState} from "react";
import {Status, Wrapper} from "@googlemaps/react-wrapper";
import GoogleMaps from "./GoogleMaps";
import axios from "axios";
import {Form, FormGroup, Input, Label, Spinner} from "reactstrap";
import GoogleMapsMarker from "./GoogleMapsMarker";

function SesionInfo({sesionId, visible}){
    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [sesion, setSesion] = useState(null);

    const [lat, setLat] = useState(0);
    const [lng, setLng] = useState(0);

    useEffect(()=>{
        let isMounted = true;
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/sesiones/"+sesionId)
            .then(result => {
                if(isMounted){
                    setSesion(result.data);
                    setLat(result.data.sala.latitud)
                    setLng(result.data.sala.longitud)
                    setLoading(false);
                }
            })
            .catch(error => {
                if(isMounted){
                    setError(error);
                }
            });
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

    //debugger;
    return(
        <>
            {visible &&
            <section className="py-3 border-1 border-top">
                <div className={"row"}>
                    <div className="col-4">
                        <div style={{height: "300px"}}>
                            <Wrapper apiKey={process.env.REACT_APP_API_GOOGLE_MAPS_KEY}>
                                <GoogleMaps center={{"lat": lat, "lng": lng}} zoom={15}>
                                    <GoogleMapsMarker position={{"lat": lat, "lng": lng}}></GoogleMapsMarker>
                                </GoogleMaps>
                            </Wrapper>
                        </div>
                        <p>{sesion.sala.direccion}</p>
                        <p>{"Fecha de inicio: " + sesion.fecha_ini}</p>
                    </div>
                    <div className="col-8">
                        {!sesion.isNumerada &&
                            <Form>
                                {sesion.tipos_entrada.map((tipo, i) =>
                                        <FormGroup key={i} className="d-flex justify-content-between align-items-center tipoEntrada rounded bg-light p-2">
                                            <Label for={"tipoEntrada" + i}>{tipo.nombre}</Label>
                                            <div className="d-flex flex-row align-items-center">
                                                <span className="me-2">{tipo.precio + "€"}</span>
                                                <Input id={"tipoEntrada" + i} name={tipo.id_sesion + "_" + tipo.nombre} type="number" min="0" defaultValue="0"></Input>
                                            </div>
                                        </FormGroup>
                                    )
                                }
                            </Form>
                        }
                        {sesion.isNumerada &&
                            <Form>
                                <p>Placeholder numerada</p>
                            </Form>
                        }
                    </div>
                </div>
            </section>
            }
        </>
    );

}

export default SesionInfo;