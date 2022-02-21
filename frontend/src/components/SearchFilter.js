import React from "react";

function SearchFilter(props){

    const handleChange = event => props.handleChange(event.target.value)

    return(
        <input
            onChange={handleChange}
            className={"px-3 col-4 rounded-pill p-1 border-1 border-dark"}
            type="text"
            placeholder={"search..."}/>
    );
}

export default SearchFilter;