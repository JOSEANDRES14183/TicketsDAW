import Header from './Header.js';
import Footer from './Footer.js';
import {Routes, Route, Link} from "react-router-dom";
import Eventos from "./Eventos";
import Soporte from "./Soporte";
import SobreNosotros from "./SobreNosotros";

function App() {
  return (
    <>
        <Header />
        <main>
            <Routes>
                <Route exact path={"/"} element={<Eventos />} />
                <Route path={"/soporte"} element={<Soporte />}/>
                <Route path={"/about-us"} element={<SobreNosotros />}/>
            </Routes>
        </main>
        <Footer />
    </>
  );

}

export default App;
