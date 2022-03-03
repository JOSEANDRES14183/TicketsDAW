import React, {useEffect, useRef, useState} from "react";

function GoogleMaps({children, center, zoom,}){
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
        <>
            <div ref={ref} className={"h-100 w-100"} />
            {React.Children.map(children, (child) => {
                if (React.isValidElement(child)) {
                    // set the map prop on the child component
                    return React.cloneElement(child, { map });
                }
            })}
        </>
    );

}

export default GoogleMaps;