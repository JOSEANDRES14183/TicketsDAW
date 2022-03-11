import Header from './Header.js';
import Footer from './Footer.js';
import {Routes, Route} from "react-router-dom";
import Eventos from "../pages/Eventos";
import Soporte from "../pages/Soporte";
import SobreNosotros from "../pages/SobreNosotros";
import Evento from "../pages/Evento";
import Organizador from "../pages/Organizador";
import React, {useEffect, useState} from "react";
import {ThemeContext} from "../contexts/ThemeContext";
import Compra from "../pages/Compra";
import {useTranslation} from "react-i18next";

function App() {

    const [theme,setTheme] = useState("light")

    const toggleTheme = () => {
        theme==='dark' ? setTheme('light') : setTheme('dark');
    }

    const {t} = useTranslation();

    console.log(t("uwu.turbocucumber"))

    useEffect(() => {
        const linkDns = document.createElement("link");
        linkDns.setAttribute("rel", "dns-prefetch");
        linkDns.setAttribute("href", process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST)

        const linkPreconnect = document.createElement("link");
        linkPreconnect.setAttribute("rel", "preconnect");
        linkPreconnect.setAttribute("href", process.env.REACT_APP_API_PROTOCOL_PREFIX + process.env.REACT_APP_API_HOST)

        document.head.append(linkDns)
        document.head.append(linkPreconnect)
    })

  return (
    <>
        <ThemeContext.Provider value={theme}>
            <Header toggleTheme={toggleTheme}/>
        </ThemeContext.Provider>
        <main>
            <Routes>
                <Route exact path={"/"} element={<Eventos />} />
                <Route path={"/:id"} element={<Evento />} />
                <Route path={"/organizador/:id"} element={<Organizador />} />
                <Route path={"/purchase/:token"} element={<Compra />}/>
                <Route path={"/soporte"} element={<Soporte />}/>
                <Route path={"/about-us"} element={<SobreNosotros />}/>
            </Routes>
        </main>
        <Footer />
    </>
  );

}

export default App;
