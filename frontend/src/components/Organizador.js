import {useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {Spinner} from "reactstrap";

function Organizador(){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const params = useParams();

    const [organizador, setOrganizador] = useState();

    useEffect(()=>{
        setLoading(true);
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/organizador/" + params.id)
            .then(result => {
                setOrganizador(result.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            });
    },[]);

    if (isLoading) {
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

    if(error){
        return <p>{error}</p>
    }

    if (organizador===""){
       return (<p className={"mt-5 text-center text-secondary"}>No se ha encontrado ningún usuario</p>);
    }

    return (
        <section className={"container-md"}>
            <div className={"row"}>
                <div className={"col-5"}>
                    <img className={"img-fluid"}
                         src={process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + '/api/media/' +organizador.organizador_data.foto_perfil.nombre_archivo}
                         alt={"Imagen de perfil de "+organizador.nombre_usuario}/>
                </div>
                <div className={"col-7"}>
                    <h2>{organizador.organizador_data.nombre}</h2>
                    <p>{organizador.organizador_data.descripcion}</p>
                </div>
            </div>
        </section>
    );

}

export default Organizador;