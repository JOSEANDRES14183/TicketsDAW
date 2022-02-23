import EventoList from "./EventoList";

function EventoListFiltered(props){

    const applyFilters = function (evento){
        if (props.category==="All"){
            if (evento.titulo.toLowerCase().includes(props.search.toLowerCase())){
                return evento;
            }
        } else if (evento.titulo.toLowerCase().includes(props.search.toLowerCase()) && evento.categoria.nombre===props.category){
            return evento;
        }
    }

    const sortByDate = function (evento1, evento2){
        if (props.dateOrder==='noSort'){
            return evento1 === evento2;
        } else if (props.dateOrder==='asc'){
            return Date.parse(evento1.latest_sesion.fecha_ini) - Date.parse(evento2.latest_sesion.fecha_ini);
        } else {
            return Date.parse(evento2.latest_sesion.fecha_ini) - Date.parse(evento1.latest_sesion.fecha_ini);
        }
    }

    return (
        <EventoList eventos={props.eventos
            .filter(evento => applyFilters(evento))
            .sort((evento1, evento2) => sortByDate(evento1,evento2))
        }/>
    );

}

export default EventoListFiltered;