import React from "react";

function SearchFilter(props){

    const handleChange = event => props.handleChange(event.target.value)

    return(
        <input
            onChange={handleChange}
            className={"col px-3 py-1 rounded-pill mx-2 my-3 m-md-0 border"}
            type="text"
            placeholder={"search..."}/>
    );
}

export default SearchFilter;