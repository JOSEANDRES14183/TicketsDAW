import {Spinner} from "reactstrap";
import React from "react";

function Loading(){
    return (
        <div className={"container-md my-3"}>
            <Spinner
                type={"border"}
                color={"primary"}>
                Loading...
            </Spinner>
        </div>
    );
}

export default Loading;