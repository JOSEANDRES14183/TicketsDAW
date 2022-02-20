import EventoCard from "./EventoCard";
import React from "react";

function EventoList(props){

    const applyFilters = function (evento){
        if (props.category==="All"){
            if (evento.titulo.toLowerCase().includes(props.search.toLowerCase())){
                return evento;
            }
        } else if (evento.titulo.toLowerCase().includes(props.search.toLowerCase()) && evento.categoria.nombre===props.category){
            return evento;
        }
    }

    return(
        <section className={"mt-4 container-md"}>
            <div className={"row"}>
                {props.eventos
                    .filter(evento => applyFilters(evento))
                    .map(function (item,key){
                    return (
                        <EventoCard key={key} evento={item}/>
                    )

                })}
            </div>
        </section>
    );

}

export default EventoList;