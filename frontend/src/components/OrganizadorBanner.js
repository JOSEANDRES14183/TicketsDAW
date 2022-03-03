import EventoCard from "./EventoCard";
import React from "react";

function OrganizadorBanner({user}){
    return(
        <a
            href={"/organizador/" + user.id}
            className="bg-light d-flex justify-content-between align-items-center shadow-sm userBanner">
            <img className={"h-100"}
                 src={process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + '/api/media/' +user.foto_perfil.nombre_archivo}/>
            <p className="me-5">Organizado por <span className="fw-bold">{user.nombre}</span></p>
        </a>
    );

}

export default OrganizadorBanner;