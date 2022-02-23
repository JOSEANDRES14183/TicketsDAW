import EventoCard from "./EventoCard";
import React from "react";

function EventoList(props){
    return(
        <section className={"mt-md-4 mt-2 container-md"}>
            <div className={"row"}>
                {
                    props.eventos.length>0 ?
                        props.eventos.map(function (item,key){
                        return (
                            <EventoCard key={key} evento={item}/>
                        )}) : <p className={"text-center"}>No se han encontrado eventos</p>
                }
            </div>
        </section>
    );

}

export default EventoList;