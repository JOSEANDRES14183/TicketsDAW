import React, {useEffect, useState} from "react";
import axios from "axios";
import {Nav, NavItem, NavLink, Spinner} from "reactstrap";
import EventoList from "../components/EventoList";
import SearchFilter from "../components/SearchFilter";
import EventoListFiltered from "../components/EventoListFiltered";
import {useTranslation} from "react-i18next";

function Eventos(){

    const {t} = useTranslation();

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [eventos,setEventos] = useState([]);
    const [category, setCategory] = useState("All");
    const [dateOrder, setDateOrder] = useState("noSort");
    const [search, setSearch] = useState("");

    const changeSearch = s => setSearch(s);

    useEffect(()=>{
        setLoading(true);
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/eventos")
            .then(result => {
                setEventos(result.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            });
    },[]);

    if (isLoading) {
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

    if(error){
        return <p>{error}</p>
    }

    return(
        <>
            <div className={"py-3 container-md"}>
                <div className={"row gap-md-3"}>
                    <div className={"col-md-3 col-6"}>
                        <select className={"form-select"}
                                onChange={(event) => setCategory(event.target.value)}>
                            <option value="All">
                                {t('category.all')}
                            </option>
                            <option value="Teatros">
                                {t('category.teatros')}
                            </option>
                            <option value="Musical">
                                {t('category.musical')}
                            </option>
                            <option value="Cine">
                                {t('category.cine')}
                            </option>
                        </select>
                    </div>
                    <div className={"col-md-3 col-6"}>
                        <select className={"form-select"}
                                onChange={(event) => setDateOrder(event.target.value)}>
                            <option value="noSort">Sin ordenar</option>
                            <option value="asc">Ordenar por fecha (Más reciente a menos)</option>
                            <option value="desc">Ordenar por fecha (Menos reciente a más)</option>
                        </select>
                    </div>
                    <SearchFilter handleChange={changeSearch}/>
                </div>
            </div>
            <EventoListFiltered
                eventos={eventos}
                dateOrder={dateOrder}
                category={category}
                search={search}/>
        </>
    );

}

export default Eventos;