import Header from './Header.js';
import Footer from './Footer.js';
import {Routes, Route} from "react-router-dom";
import Eventos from "../pages/Eventos";
import Soporte from "../pages/Soporte";
import SobreNosotros from "../pages/SobreNosotros";
import Evento from "../pages/Evento";
import Organizador from "../pages/Organizador";

function App() {
  return (
    <>
        <Header />
        <main>
            <Routes>
                <Route exact path={"/"} element={<Eventos />} />
                <Route path={"/:id"} element={<Evento />} />
                <Route path={"/organizador/:id"} element={<Organizador />} />
                <Route path={"/soporte"} element={<Soporte />}/>
                <Route path={"/about-us"} element={<SobreNosotros />}/>
            </Routes>
        </main>
        <Footer />
    </>
  );

}

export default App;
