import {Link, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Spinner} from "reactstrap";

function Evento(){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [evento, setEvento] = useState([]);

    const params = useParams();

    useEffect(()=>{
        axios.get("http://" + process.env.REACT_APP_API_HOST + "/api/eventos/"+params.id)
            .then(result => {
                setEvento(result.data);
                setLoading(false);
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

    return (
            <div className={"py-3 container-md"}>
                <Link to={"/"}>
                    <Button className={"my-3"} color={"primary"}>
                        Volver
                    </Button>
                </Link>

                <div className={"row"}>
                    <div className={"col-7 d-flex flex-column"}>
                        <h2>{evento.titulo}</h2>
                        <p>{evento.descripcion}</p>
                    </div>
                    <div className={"col"}>
                        <img className={"border-1 border-end img-fluid"}
                             src={'http://'+process.env.REACT_APP_API_HOST + '/api/media/' +evento.foto_perfil.nombre_archivo}/>
                    </div>
                </div>

            </div>
    );
}

export default Evento;