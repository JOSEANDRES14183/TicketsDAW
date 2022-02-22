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

    return (
        <EventoList eventos={props.eventos.filter(evento => applyFilters(evento))}/>
    );

}

export default EventoListFiltered;