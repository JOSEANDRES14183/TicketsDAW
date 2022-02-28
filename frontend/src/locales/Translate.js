import {LocaleContext} from "../contexts/LocaleContext";

import es from "./es.json"
import ca from "./ca.json"
import en from "./en.json"

function Translate(props){

    const langs = {
        es,
        ca,
        en
    }

    return (
        <LocaleContext.Consumer>
            {value=>langs[value][props.string]}
        </LocaleContext.Consumer>
    );
}

export default Translate;