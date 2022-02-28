import React, {useEffect, useRef, useState} from "react";

function GoogleMaps({center, zoom,}){
    const ref = useRef(null);
    const [map, setMap] = useState(null);

    useEffect(() => {
        if (ref.current && !map) {
            setMap(new window.google.maps.Map(ref.current, {
                center,
                zoom,
            }));
        }
    }, [ref, map]);

    useEffect(() => {
        if(map){
            map.setCenter(center)
        }
    }, [center, zoom]);

    return(
        <div ref={ref} className={"h-100 w-100"}>
        </div>
    );

}

export default GoogleMaps;