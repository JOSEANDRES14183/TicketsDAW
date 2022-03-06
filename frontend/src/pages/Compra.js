import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Loading from "../components/Loading";

function Compra(){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [operacionCompra, setOperacionCompra] = useState([]);
    const [sesion,setSesion] = useState([]);

    const params = useParams();

    useEffect(()=>{
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/purchase/"+params.id)
            .then(result => {
                setOperacionCompra(result.data);
                setSesion(result.data.entradas[0].sesion_numerada ? result.data.entradas[0].sesion_numerada : [])
                setLoading(false);
            })
            .catch(error => {
                setError(error);
            });
    }, [params]);

    if(error){
        return <p>{error}</p>
    }

    if (isLoading){
        return (
            <Loading />
        );
    }

    console.log(operacionCompra.entradas[0].sesion_numerada);

    return (
        <section className={"container-md"}>
            <p>{operacionCompra.id}</p>
            <p>{sesion.id}</p>
        </section>
    );
}

export default Compra;