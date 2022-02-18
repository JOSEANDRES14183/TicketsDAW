import React, {useEffect, useState} from "react";
import axios from "axios";
import {Nav, NavItem, NavLink} from "reactstrap";
import EventoList from "./EventoList";
import SearchFilter from "./SearchFilter";

function Eventos(){

    const [isLoading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const [eventos,setEventos] = useState([]);
    const [category, setCategory] = useState("All");
    const [search, setSearch] = useState("");

    const changeCategory = c => setCategory(c);
    const changeSearch = s => setSearch(s);

    useEffect(()=>{
        setLoading(true);
        axios.get("http://" + process.env.REACT_APP_API_HOST + "/api/eventos")
            .then(result =>
                    setEventos(result.data),
                setLoading(false),
            )
            .catch(error =>
                    setError(error),
                setLoading(false)
            );
    },[]);

    if (isLoading){
        return <p>Loading...</p>
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
                            <NavLink>
                                Teatro
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink>
                                Musical
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink>
                                Cine
                            </NavLink>
                        </NavItem>
                    </Nav>
                    <SearchFilter handleChange={changeCategory}/>
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