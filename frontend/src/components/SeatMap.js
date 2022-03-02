import React, {useEffect, useMemo, useRef} from 'react';
import {useD3} from "../hooks/useD3";
import * as d3 from "d3";

const MARGIN = { top: 30, right: 30, bottom: 30, left: 30 };

//https://www.react-graph-gallery.com/scatter-plot
function SeatMap({seats}) {
    const renderFn = (svg) => {
        const multiplyFactor = 48;

        //https://github.com/d3/d3-selection/blob/v3.0.0/README.md#selection_on

        svg
            .select("g.plotArea")
            .selectAll("circle")
            .data(seats)
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
            .on("click", function(event,datum){
                seatClick(datum)
            })
        //(d) => seatClick(d)

        svg
            .select("g.plotArea")
            .selectAll("circle")
            .data(seats)
            .exit()
            .remove()

        svg
            .select("g.plotArea")
            .selectAll("circle")
            .data(seats)
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

    const ref = useD3(renderFn, [seats]);

    function seatClick(seat){
        seat.seleccionada = true;
        renderFn(d3.select(ref.current))
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