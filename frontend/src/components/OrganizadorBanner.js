import EventoCard from "./EventoCard";
import React from "react";

function OrganizadorBanner({user, className}){
    return(
        <a
            href={"/organizador/" + user.id}
            className={"bg-light d-flex justify-content-between align-items-center shadow-sm userBanner " + className}>
            <img className={"h-100"}
                 src={process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + '/api/media/' +user.foto_perfil.nombre_archivo}/>
            <div className="d-flex justify-content-center align-content-center p-1 flex-grow-1"><p>Organizado por <span className="fw-bold">{user.nombre}</span></p></div>
        </a>
    );

}

export default OrganizadorBanner;