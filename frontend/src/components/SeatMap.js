import React, {useEffect, useMemo, useRef, useState} from 'react';
import {useD3} from "../hooks/useD3";
import * as d3 from "d3";

const MARGIN = { top: 30, right: 30, bottom: 30, left: 30 };

//https://www.react-graph-gallery.com/scatter-plot
function SeatMap({seats, submitPurchase, refreshSesion}) {

    const [seatMap, setSeatMap] = useState(null);

    // https://stackoverflow.com/questions/57847594/react-hooks-accessing-up-to-date-state-from-within-a-callback
    // https://stackoverflow.com/questions/56782079/react-hooks-stale-state
    const seatMapRef = useRef();
    seatMapRef.current = seatMap;

    useEffect(()=>{
        setSeatMap(JSON.parse(JSON.stringify(seats)))
    }, [seats])

    const renderFn = (svg) => {
        if(!seatMap){
            return
        }

        const multiplyFactor = 48;

        //https://github.com/d3/d3-selection/blob/v3.0.0/README.md#selection_on

        //Remove all previous svg elements
        // svg
        //     .select("g.plotArea")
        //     .selectAll("circle")
        //     .remove()

        svg
            .select("g.plotArea")
            .selectAll("circle")
            .data(seatMap)
            .enter()
            .append("circle")
            .attr("r", 16)
            .attr("opacity", 1)
            .attr("stroke", "#000")
            .attr("fillOpacity", 0.7)
            .attr("strokeWidth", 1)
            .attr("fill", (d) => d.seleccionada ? "#B8FFF9" : "#9a6fb0")
            .attr("cx", (d) => d.pos_x*multiplyFactor)
            .attr("cy", (d) => d.pos_y*multiplyFactor)
            .on("click", function (event,datum) {
                seatClick(datum)
            }
            )

        svg
            .select("g.plotArea")
            .selectAll("circle")
            .data(seatMap)
            .exit()
            .remove()

        svg
            .select("g.plotArea")
            .selectAll("circle")
            .data(seatMap)
            .transition()
            .duration(100)
            .attr("fill", (d) => d.seleccionada ? "#B8FFF9" : "#9a6fb0")
            .attr("cx", (d) => d.pos_x*multiplyFactor)
            .attr("cy", (d) => d.pos_y*multiplyFactor)

        function handleZoom(e) {
            svg.select('g')
                .attr('transform', e.transform);
        }

        let zoom = d3.zoom()
            .scaleExtent([0.3,5])
            .on('zoom', handleZoom);

        svg
            .call(zoom);
    }

    const ref = useD3(renderFn, [seatMap]);

    const seatClick = (seat) => {
        let seatCopy = {...seat};
        seatCopy.seleccionada = !seatCopy.seleccionada;

        let seatsMapCopy = JSON.parse(JSON.stringify(seatMapRef.current));

        seatsMapCopy = seatsMapCopy.map((currSeat) => {
            if(currSeat.pos_x == seat.pos_x && currSeat.pos_y == seat.pos_y)
                return seatCopy;
            return currSeat;
        })

        setSeatMap(seatsMapCopy)
    }

    const purchaseClick = () => {
        let object = {};
        object.butacas = JSON.parse(JSON.stringify(seatMapRef.current));
        submitPurchase(object);
    }

    return(
        <div className={"w-100 h-100 position-relative"}>
            <button type="button" className={"uiButton position-absolute top-0 start-0"} onClick={refreshSesion}>
                <i className="bi bi-arrow-clockwise"></i>
            </button>
            <button type="button" className={"uiButton position-absolute top-0 end-0"} onClick={purchaseClick}>
                <i className="bi bi-cart3"></i>
            </button>
            <svg
                ref={ref}
                className={"seatMap"}>
                <g
                    className="plotArea"
                    transform={`translate(${[MARGIN.left, MARGIN.top].join(",")})`}
                >
                </g>
            </svg>
    </div>)
}

export default SeatMap;