import EventoCard from "./EventoCard";
import React from "react";
import {useTranslation} from "react-i18next";

function EventoList(props){

    const {t} = useTranslation();

    return(
        <div className={"row"}>
                {
                    props.eventos.length>0 ?
                        props.eventos.map(function (item,key){
                        return (
                            <EventoCard key={key} evento={item}/>
                        )}) : <p className={"text-center"}>{t('events-not-found')}</p>
                }
        </div>
    );

}

export default EventoList;