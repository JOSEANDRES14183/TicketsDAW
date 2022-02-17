import {Card, CardBody, CardImg, CardText, CardTitle} from "reactstrap";

function EventoCard(props){

    return(
        <Card className={"col-6 col-md-3 border-0 mb-3"}>
            <div className={"row"}>
                <CardBody className={"col-7 border"}>
                    <CardTitle tag={"h6"}>
                        {props.evento.titulo}
                    </CardTitle>
                    <CardText>
                        {props.evento.descripcion}
                    </CardText>
                </CardBody>
                <CardImg className={"col-4 img-fluid"}
                         src={require(process.env.REACT_APP_IMG_DIR + props.evento.foto_perfil.nombre_archivo)}/>
            </div>
        </Card>
    )
}

export default EventoCard;