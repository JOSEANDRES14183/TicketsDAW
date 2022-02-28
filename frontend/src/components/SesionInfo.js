import React, {useEffect} from "react";
import {Status, Wrapper} from "@googlemaps/react-wrapper";
import GoogleMaps from "./GoogleMaps";

function SesionInfo(props){

    //debugger;
    return(
        <div className={"row"}>
            <div className="col-4" style={{height: "300px"}}>
                <Wrapper apiKey={process.env.REACT_APP_API_GOOGLE_MAPS_KEY}>
                    <GoogleMaps center={{"lat": props.lat, "lng": props.lng}} zoom={9} />
                </Wrapper>
            </div>
            <div className="col-8">
                <h1>{}</h1>
            </div>
        </div>
    );

}

export default SesionInfo;