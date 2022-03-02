import {LocaleContext} from "../contexts/LocaleContext";
import es from "./es.json"
import ca from "./ca.json"
import en from "./en.json"
import {useContext} from "react";

function Translate(props){

    const value = useContext(LocaleContext);
    const langs = {
        es,
        ca,
        en
    }

    return (
        <>
            {langs[value][props.string]}
        </>
    );
}

export default Translate;