import React, {useEffect, useState} from "react";
import axios from "axios";
import Loading from "./Loading";
import {use} from "i18next";
import SalasSelect from "./SalasSelect";

function ChainedSelect(){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [ciudades, setCiudades] = useState([])
    const [idCiudadActual, setIdCiudadActual] = useState(0);


    const getCiudades = () => {
        setLoading(true);
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/ciudades")
            .then(result => {
                setCiudades(result.data);
                setIdCiudadActual(result.data[0].id)
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            });
    }

    useEffect(()=>{
        getCiudades();
    },[]);

    if (isLoading){
        return <Loading />;
    }

    if(error){
        return <p>{error}</p>;
    }

    return (
        <>
            <select onChange={(event) => setIdCiudadActual(event.target.value)}>
                {ciudades.map(function (item,key){
                    return (<option key={key} value={item.id}>{item.nombre}</option>)
                })}
            </select>
            <SalasSelect idCiudad={idCiudadActual}/>
        </>
    );
}

export default ChainedSelect;