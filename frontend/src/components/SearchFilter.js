import React from "react";
import {useTranslation} from "react-i18next";

function SearchFilter(props){

    const {t} = useTranslation();

    const handleChange = event => props.handleChange(event.target.value)

    return(
        <input
            aria-label={t('search')}
            onChange={handleChange}
            className={"col px-3 py-1 rounded-pill mx-2 my-3 m-md-0 border"}
            type="text"
            placeholder={t('search')+"..."}/>
    );
}

export default SearchFilter;