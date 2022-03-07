import {Card, CardBody, CardTitle} from "reactstrap";

function Entrada(props){
    return (
        <div className={"mb-3"}>
            <div className={"row"}>
                <div className={"border col-12 col-md py-2 mb-2 h-100"}>
                    <h5 className={"mb-2"}>
                        {props.entrada.tipo_entrada ? props.entrada.tipo_entrada.nombre : "Butaca " + props.entrada.butaca.num_butaca}
                    </h5>

                    <h6 className={"text-primary text-end pe-2"}>
                        {props.entrada.tipo_entrada ? props.entrada.tipo_entrada.precio : props.entrada.sesion_numerada.sesionNumData.precio}€
                    </h6>
                </div>
                {props.nominativo===1 &&
                    <div className={"col-12 col-md-3 ms-md-2 h-100 p-0"}>
                        <Card className={"h-100 w-100"}>
                            <CardBody className={"h-100"}>
                                <CardTitle tag={"h5"}>
                                    Asistente:
                                </CardTitle>
                                <div className={"mb-3"}>
                                    <label className={"form-label"} htmlFor={"nombre"+props.index}>Nombre</label>
                                    <input className={"form-control"} type="text" id={"nombre"+props.index} name={"nombre"}/>
                                </div>
                                <div className={"mb-3"}>
                                    <label className={"form-label"} htmlFor={"apellidos"+props.index}>Apellidos</label>
                                    <input className={"form-control"} type="text" id={"apellidos"+props.index} name={"apellidos"}/>
                                </div>
                                <div className={"mb-3"}>
                                    <label className={"form-label"} htmlFor={"email"+props.index}>Email</label>
                                    <input className={"form-control"} type="text" id={"email"+props.index} name={"email"}/>
                                </div>
                            </CardBody>
                        </Card>
                    </div>
                }
            </div>
        </div>
    );
}

export default Entrada