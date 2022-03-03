import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Nav, NavItem, NavLink, Offcanvas, OffcanvasBody, OffcanvasHeader, Spinner} from "reactstrap";
import SearchFilter from "../components/SearchFilter";
import EventoListFiltered from "../components/EventoListFiltered";
import {useTranslation} from "react-i18next";
import Calendar from "react-calendar";
import 'react-calendar/dist/Calendar.css';
import {formatDate} from "@fullcalendar/react";
import dayjs from "dayjs";
import Loading from "../components/Loading";

function Eventos(){

    const {t} = useTranslation();

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [eventos,setEventos] = useState([]);
    const [category, setCategory] = useState("All");
    const [dateOrder, setDateOrder] = useState("noSort");
    const [search, setSearch] = useState("");

    const [date, setDate] = useState(null);

    const changeSearch = s => setSearch(s);

    const getEvents = (date) => {
        setLoading(true);
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/eventos" + date)
            .then(result => {
                setEventos(result.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            });
    }

    useEffect(()=>{
        getEvents("");
    },[]);

    if(error){
        return <p>{error}</p>
    }

    return(
        <>
            <section className={"py-3 container-md"}>
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
                            <option value="noSort">
                                {t('sort.no-sort')}
                            </option>
                            <option value="asc">{t('sort.by-date-asc')}</option>
                            <option value="desc">{t('sort.by-date-desc')}</option>
                        </select>
                    </div>
                    <SearchFilter handleChange={changeSearch}/>
                </div>
            </section>

            <section className={"container-md mt-3"}>
                <div className={"row"}>
                    <div className={" col-3"}>
                        <Calendar className={"border-0 shadow-sm p-2"} value={date}
                            onChange={(fecha) => {
                                setDate(fecha);
                                getEvents("?date=" + dayjs(fecha).format('YYYY-MM-DD'))
                            }}
                            locale={t('lang')}
                        />
                        <Button onClick={() => {
                            setDate(null);
                            getEvents("");
                        }}
                            className="mt-3" color={"primary"}>
                            {t('show-all-events')}
                        </Button>
                    </div>

                    <div className={"col"}>
                        {isLoading
                            ? <Loading />
                            : <EventoListFiltered
                                eventos={eventos}
                                dateOrder={dateOrder}
                                category={category}
                                search={search}/>
                        }

                    </div>
                </div>
            </section>


        </>
    );

}

export default Eventos;