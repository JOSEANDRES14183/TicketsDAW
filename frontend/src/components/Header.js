import React from "react";
import {
    Nav,
    Navbar,
    NavbarBrand,
    NavItem,
    NavLink,
    Button,
} from "reactstrap";
import iesmanacor_logo from "../assets/images/iesmanacor_logo.webp"
import LanguagePicker from "./LanguagePicker";
import {useTranslation} from "react-i18next";
import {ThemeContext} from "../contexts/ThemeContext";
import ThemeToggler from "./ThemeToggler";

function Header(props){

    const {t} = useTranslation();

    return(
        <header>
            <ThemeContext.Consumer>
                {(value) => (
                    <Navbar className={"border-bottom"}
                            color={value}
                            expand={"md"}
                            container={"md"}
                            light={value === 'light'}
                            dark={value === 'dark'}>

                        <NavbarBrand href={"/"}>
                            <img src={iesmanacor_logo}
                                 className={"img-fluid"}
                                 alt="IES Manacor logo"/>
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
                                    <NavLink href={"/"} className={value==="light" ? "text-primary" : "text-white"}>
                                        {t('events')}
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink href={"/soporte"} className={value==="light" ? "text-primary" : "text-white"}>
                                        {t('support')}
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink href={"/about-us"} className={value==="light" ? "text-primary" : "text-white"}>
                                        {t('about-us')}
                                    </NavLink>
                                </NavItem>
                            </Nav>

                            <ThemeToggler toggleTheme={props.toggleTheme} />

                            <LanguagePicker theme={value}/>

                            <Button
                                onClick={() => window.location.href = process.env.REACT_APP_PRIVATE_HOST}
                                className="text-uppercase"
                                color={"primary"}>
                                {t('create-event')}
                            </Button>
                        </div>
                    </Navbar>
                )}
            </ThemeContext.Consumer>
        </header>
    );
}

export default Header;