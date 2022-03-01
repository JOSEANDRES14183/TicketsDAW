import React, {useEffect, useState} from "react";
import {Status, Wrapper} from "@googlemaps/react-wrapper";
import GoogleMaps from "./GoogleMaps";
import axios from "axios";
import {Spinner} from "reactstrap";

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
            <div className={"row"}>
                <div className="col-4" style={{height: "300px"}}>
                    <p>{}</p>
                    <Wrapper apiKey={process.env.REACT_APP_API_GOOGLE_MAPS_KEY}>
                        <GoogleMaps center={{"lat": lat, "lng": lng}} zoom={9} />
                    </Wrapper>
                </div>
                <div className="col-8">
                    <h1>{}</h1>
                </div>
            </div>
            }
        </>
    );

}

export default SesionInfo;