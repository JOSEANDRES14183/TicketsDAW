import {useEffect, useState} from "react";
import Calendar from "react-calendar";
import dayjs from "dayjs";
import {Button, Offcanvas, OffcanvasBody, OffcanvasHeader} from "reactstrap";
import {t} from "i18next";

function CalendarioSesiones(props){

    const [viewWidth,setViewWidth] = useState(window.innerWidth);
    const [isOpen,setOpen] = useState(false);
    const toggle = () => setOpen(!isOpen);

    const updatwViewWidth = () => setViewWidth(window.innerWidth);

    useEffect(() => {
        window.addEventListener("resize", updatwViewWidth)
    })

    return (
        <>
            {viewWidth<=990
                ?
                <div>
                    <Button
                        className={"my-2"}
                        onClick={toggle}
                        color={"primary"}>
                        <i className="bi bi-calendar3" />
                    </Button>
                    <Offcanvas isOpen={isOpen} toggle={toggle}>
                        <OffcanvasHeader toggle={toggle}>
                            {t('calendar')}
                        </OffcanvasHeader>
                        <OffcanvasBody>
                            <Calendar className={"border-0 shadow-sm p-2"} value={props.date}
                                      onChange={(fecha) => {
                                          props.setDate(fecha);
                                          props.getEvents("?date=" + dayjs(fecha).format('YYYY-MM-DD'))
                                      }}
                                      locale={t('lang')}
                            />
                            <Button onClick={() => {
                                props.setDate(null);
                                props.getEvents("");
                            }} className="mt-3" color={"primary"}>
                                {t('show-all-events')}
                            </Button>
                        </OffcanvasBody>
                    </Offcanvas>
                </div>

                : <div className={"col-3"}>
                    <Calendar className={"border-0 shadow-sm p-2"} value={props.date}
                          onChange={(fecha) => {
                              props.setDate(fecha);
                              props.getEvents("?date=" + dayjs(fecha).format('YYYY-MM-DD'))
                          }}
                          locale={t('lang')}
                    />
                    <Button onClick={() => {
                        props.setDate(null);
                        props.getEvents("");
                    }} className="mt-3" color={"primary"}>
                        {t('show-all-events')}
                    </Button>
                </div>
            }
        </>
    );
}

export default CalendarioSesiones;