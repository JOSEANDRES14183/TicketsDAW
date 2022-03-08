import Loading from "./Loading";
import axios from "axios";
import {useEffect, useState} from "react";

function SalasSelect(props){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [salas, setSalas] = useState([]);

    const getSalas = (idCiudad) => {
        setLoading(true);
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/salas/"+idCiudad)
            .then(result => {
                setSalas(result.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            });
    }

    useEffect(()=>{
        getSalas(props.idCiudad);
    },[props.idCiudad]);

    if (isLoading){
        return <Loading />;
    }

    if(error){
        return <p>{error}</p>;
    }

    return (
        <select>
            {salas.map(function (item,key){
                return (<option key={key} value={item.id}>{item.nombre}</option>)
            })}
        </select>
    );
}

export default SalasSelect;