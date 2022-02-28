import React from "react";
import {
    Nav,
    Navbar,
    NavbarBrand,
    NavItem,
    NavLink,
    Button,
} from "reactstrap";
import iesmanacor_logo from "../assets/images/iesmanacor_logo.png"
import LanguagePicker from "./LanguagePicker";
import Translate from "../locales/Translate";

function Header(props){
    return(
        <header>
            <Navbar className={"border-bottom"}
                    color={"white"}
                    expand={"md"}
                    container={"md"}
                    light>

                <NavbarBrand href={"/"}>
                    <img src={iesmanacor_logo}
                         className={"img-fluid"}
                         alt="IES Manacor logo"/>
                    TicketsDAW
                </NavbarBrand>

                <button
                    className={"navbar-toggler"}
                    data-bs-toggle={"collapse"}
                    data-bs-target={"#collapseContent"}
                    aria-controls={"collapseContent"}
                    aria-expanded={"false"}>
                    <span className="navbar-toggler-icon" />
                </button>

                <div id={"collapseContent"} className={"navbar-collapse collapse"}>
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

                    <LanguagePicker changeLang = {props.changeLang}/>

                    <Button color={"primary"}>CREAR UN EVENTO</Button>
                </div>

            </Navbar>
        </header>
    );
}

export default Header;