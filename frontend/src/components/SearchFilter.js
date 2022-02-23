import React from "react";

function SearchFilter(props){

    const handleChange = event => props.handleChange(event.target.value)

    return(
        <input
            onChange={handleChange}
            className={"col px-3 rounded-pill p-1 border"}
            type="text"
            placeholder={"search..."}/>
    );
}

export default SearchFilter;