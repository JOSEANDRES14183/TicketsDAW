import EventoCard from "./EventoCard";
import React from "react";

function EventoList(props){
    return(
        <div className={"row"}>
                {
                    props.eventos.length>0 ?
                        props.eventos.map(function (item,key){
                        return (
                            <EventoCard key={key} evento={item}/>
                        )}) : <p className={"text-center"}>No se han encontrado eventos</p>
                }
        </div>
    );

}

export default EventoList;