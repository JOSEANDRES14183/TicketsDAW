import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Loading from "../components/Loading";
import Entrada from "../components/Entrada";
import {Button, Card, CardBody, CardText, CardTitle} from "reactstrap";

function Compra(){

    const [isLoading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [operacionCompra, setOperacionCompra] = useState([]);
    const [sesion,setSesion] = useState([]);

    let precioTotal = 0;

    const params = useParams();

    useEffect(()=>{
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/purchase/"+params.id)
            .then(result => {
                setOperacionCompra(result.data);
                setSesion(result.data.entradas[0].sesion_numerada ? result.data.entradas[0].sesion_numerada : result.data.entradas[0].tipo_entrada.sesion)
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

    console.log(operacionCompra);

    return (
        <section className={"container-md"}>
            <p>{operacionCompra.id}</p>
            <p>{sesion.id}</p>
            {
                operacionCompra.entradas.map(function (item,key){
                    if (item.butaca==null){
                        precioTotal+=item.tipo_entrada.precio;
                    } else {
                        precioTotal+=item.sesion_numerada.sesionNumData.precio;
                    }
                return <Entrada key={key} entrada={item} index={key} nominativo={sesion.isNominativo}/>
            })}
            <div className={"d-flex justify-content-between pt-3"}>
                <h5>Precio total:</h5>
                <h5 className={"text-primary"}>{precioTotal}€</h5>
            </div>

            {!sesion.isNominativo ?
                <Card className={"col col-md-6 me-auto my-4 shadow-sm"}>
                    <CardBody>
                        <CardTitle tag={"h5"}>
                            Datos del comprador
                        </CardTitle>
                        <form action="#" method={"post"}>
                            <div className={"mb-3"}>
                                <label className={"form-label"} htmlFor={"email"}>Email</label>
                                <input className={"form-control"} type="text" id={"email"} name={"email"}/>
                            </div>
                            <input className={"btn btn-primary"} type="submit" value={"Finalizar compra"}/>
                        </form>
                    </CardBody>
                </Card> : <Button className={"mt-3"} color={"primary"}>Finalizar compra</Button>
            }

        </section>
    );
}

export default Compra;