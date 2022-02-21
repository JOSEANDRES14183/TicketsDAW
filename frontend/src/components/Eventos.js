import React, {useEffect, useState} from "react";
import axios from "axios";
import {Nav, NavItem, NavLink, Spinner} from "reactstrap";
import EventoList from "./EventoList";
import SearchFilter from "./SearchFilter";

function Eventos(){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [eventos,setEventos] = useState([]);
    const [category, setCategory] = useState("All");
    const [search, setSearch] = useState("");

    const changeSearch = s => setSearch(s);

    useEffect(()=>{
        setLoading(true);
        axios.get(process.env.REACT_APP_API_PROTOCOL + process.env.REACT_APP_API_HOST + "/api/eventos")
            .then(result => {
                setEventos(result.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            });
    },[]);

    if (isLoading){
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
                <div className={"row"}>
                    <Nav className={"col-8"}>
                        <NavItem>
                            <NavLink role="button" onClick={() => setCategory("All")}>
                                All
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink role="button" onClick={() => setCategory("Teatros")}>
                                Teatros
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink role="button" onClick={() => setCategory("Musical")}>
                                Musical
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink role="button" onClick={() => setCategory("Cine")}>
                                Cine
                            </NavLink>
                        </NavItem>
                    </Nav>
                    <SearchFilter handleChange={changeSearch}/>
                </div>
            </div>
            <EventoList
                eventos={eventos}
                category={category}
                search={search}/>
        </>
    );

}

export default Eventos;