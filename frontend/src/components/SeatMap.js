import React, {useEffect, useMemo, useRef, useState} from 'react';
import {useD3} from "../hooks/useD3";
import * as d3 from "d3";

const MARGIN = { top: 30, right: 30, bottom: 30, left: 30 };

//https://www.react-graph-gallery.com/scatter-plot
function SeatMap({seats}) {

    const [seatMap, setSeatMap] = useState(null);

    // https://stackoverflow.com/questions/57847594/react-hooks-accessing-up-to-date-state-from-within-a-callback
    // https://stackoverflow.com/questions/56782079/react-hooks-stale-state
    const seatMapRef = useRef();
    seatMapRef.current = seatMap;

    useEffect(async ()=>{
        //setSeatMap(Object.assign([], seats))
        await setSeatMap(JSON.parse(JSON.stringify(seats)))
    }, [seats])

    const renderFn = (svg) => {
        console.log("-----------------------------------")
        console.log(seats)
        console.log(seatMap)

        if(!seatMap){
            console.log("Turbocucumber cancela el vuelo!")
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
                console.log("$$$$$$$$$$$$$$$$$$$$$$")
                console.log(seats)
                console.log(seatMap)
                seatClick(datum)
            }
            .bind(seatMap)
            )
        //(d) => seatClick(d)

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
            .duration(100) //TODO: Remove this duration
            .attr("fill", (d) => d.seleccionada ? "#B8FFF9" : "#9a6fb0")
            .attr("cx", (d) => d.pos_x*multiplyFactor)
            .attr("cy", (d) => d.pos_y*multiplyFactor)

        function handleZoom(e) {
            svg.select('g')
                .attr('transform', e.transform);
        }

        let zoom = d3.zoom()
            .on('zoom', handleZoom);

        svg
            .call(zoom);
    }

    const ref = useD3(renderFn, [seatMap]);

    const seatClick = async (seat) => {
        console.log("============================")
        console.log(seat)
        console.log(seats)
        console.log(seatMap)

        seat.seleccionada = true;

        let seatsMapCopy = JSON.parse(JSON.stringify(seatMapRef.current));

        seatsMapCopy = seatsMapCopy.map((currSeat) => {
            if(currSeat.pos_x == seat.pos_x && currSeat.pos_y == seat.pos_y)
                return Object.assign({}, seat)
            return currSeat;
        })

        await setSeatMap(seatsMapCopy)

        console.log("///////////////////////////")
        console.log(seat)
        console.log(seats)
        console.log(seatMap)

        //renderFn(d3.select(ref.current))
    }

    return(
        <svg
            ref={ref}
        style={{
            height: "100%",
            width: "100%"
        }}>
            <g
                className="plotArea"
                transform={`translate(${[MARGIN.left, MARGIN.top].join(",")})`}
            >
                {/*{allShapes}*/}
            </g>
        </svg>
    )
}

export default SeatMap;