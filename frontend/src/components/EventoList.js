import EventoCard from "./EventoCard";
import React from "react";

function EventoList(props){
    return(
        <section className={"mt-4 container-md"}>
            <div className={"row"}>
                {props.eventos
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