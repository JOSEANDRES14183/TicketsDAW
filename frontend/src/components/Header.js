import React,{useState} from "react";
import {Nav, Navbar, NavbarBrand, NavItem, NavLink, Button} from "reactstrap";
import iesmanacor_logo from "../assets/images/iesmanacor_logo.png"

function Header(){
    return(
        <header>
            <Navbar className={"border-bottom"}
                    color={"white"}
                    expand={"md"}
                    container={"md"}
                    light>
                <NavbarBrand href={"/"}>
                    <img src={iesmanacor_logo} className={"img-fluid"} alt="IES Manacor logo"/>
                    TicketsDAW
                </NavbarBrand>
                <Nav className={"mx-auto"}
                     navbar>
                    <NavItem>
                        <NavLink href={"/"}>
                            Eventos
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href={"/soporte"}>
                            Soporte
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href={"/about-us"}>
                            Sobre nosotros
                        </NavLink>
                    </NavItem>
                </Nav>
                <Button color={"primary"}>CREAR UN EVENTO</Button>
            </Navbar>
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
                    <input className={"col-3 rounded-pill p-1 border-1 border-dark"}
                           type="text"
                           placeholder={"search..."}/>
                    </div>
                </div>
        </header>
    );
}

export default Header;