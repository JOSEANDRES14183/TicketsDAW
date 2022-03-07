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

    const submitEntradas = (form) => {
        let obj = {};
        obj.token = params.token;
        obj.entradas = buildEntradas(form);
        console.log(obj);
        // axios.post(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/purchase_details", obj)
        //     .then(response => {
        //
        //     })
        //     .catch(error => {
        //         setError(error);
        //     });
    }

    const buildEntradas = (e) => {
        let entradasCopy = JSON.parse(JSON.stringify(operacionCompra.entradas));
        if (!sesion.isNominativo){
            e.preventDefault();
            let data = new FormData(e.target);
            entradasCopy.forEach((entrada)=>{
                entrada.emailAsistente=data.get('email');
            })

        } else {
            for (let i=0;i<entradasCopy.length;i++){
                let nombre= document.querySelector("#nombre"+i).value;
                let apellidos= document.querySelector("#apellidos"+i).value;
                let email= document.querySelector("#email"+i).value;
                entradasCopy[i].nombreAsistente = nombre;
                entradasCopy[i].apellidosAsistente = apellidos;
                entradasCopy[i].emailAsistente = email;
            }
        }
        return entradasCopy
    }

    useEffect(()=>{
        axios.get(process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST + "/api/purchase/"+params.token)
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
            <div className={"my-3 border-bottom"}>
                <h2>{sesion.evento.titulo}</h2>
                <p>
                    <i className="bi bi-geo-alt" /> {sesion.sala.direccion + ", " + sesion.sala.nombre + ", " + sesion.sala.ciudad.nombre}
                </p>
                <p>
                    <i className="bi bi-calendar3" /> {sesion.fecha_ini}
                </p>
            </div>

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
                        <form onSubmit={(event)=>submitEntradas(event)}>
                            <div className={"mb-3"}>
                                <label className={"form-label"} htmlFor={"email"}>Email</label>
                                <input className={"form-control"} type="text" id={"email"} name={"email"}/>
                            </div>
                            <input className={"btn btn-primary"} type="submit" value={"Finalizar compra"}/>
                        </form>
                    </CardBody>
                </Card> : <Button onClick={()=>submitEntradas()} className={"mt-3"} color={"primary"}>Finalizar compra</Button>
            }

        </section>
    );
}

export default Compra;