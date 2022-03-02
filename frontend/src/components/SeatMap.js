import React, {useEffect, useMemo, useRef} from 'react';
import {useD3} from "../hooks/useD3";
import * as d3 from "d3";

const MARGIN = { top: 30, right: 30, bottom: 30, left: 30 };

//https://www.react-graph-gallery.com/scatter-plot
function SeatMap({seats}) {
    const ref = useD3(
        (svg) => {
            const multiplyFactor = 48;

            svg
                .select("g.plotArea")
                .selectAll("circle")
                .remove()
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
                .attr("onclick", (d) => seatClick(d))

            function handleZoom(e) {
                svg.select('g')
                    .attr('transform', e.transform);
            }

            let zoom = d3.zoom()
                .on('zoom', handleZoom);

            svg
                .call(zoom);

            const allShapes = seats.map((d, i) => {
                return (
                    <circle
                        key={i}
                        r={16}
                        cx={d.pos_x*multiplyFactor}
                        cy={d.pos_y*multiplyFactor}
                        opacity={1}
                        stroke="#000"
                        fill={d.seleccionada ? "#B8FFF9" : "#9a6fb0"}
                        fillOpacity={0.7}
                        strokeWidth={1}
                        onClick={() => seatClick(i)}
                    />
                );
        }, [seats.length]
    )

    // const axesRef = useRef(null);
    // const boundsWidth = width - MARGIN.right - MARGIN.left;
    // const boundsHeight = height - MARGIN.top - MARGIN.bottom;

    // // Y axis
    // const yScale = useMemo(() => {
    //     const [min, max] = d3.extent(seats.map((d) => d.pos_x));
    //     return d3.scaleLinear().domain([min, max]).range([boundsHeight, 0]);
    // }, [seats, height]);
    //
    // // X axis
    // const xScale = useMemo(() => {
    //     const [min, max] = d3.extent(seats.map((d) => d.pos_y));
    //     return d3.scaleLinear().domain([min, max]).range([0, boundsWidth]);
    // }, [seats, width]);

    // Render the X and Y axis using d3.js, not react
    // useEffect(() => {
    //     const svgElement = d3.select(axesRef.current);
    //     svgElement.selectAll("*").remove();
    //     const xAxisGenerator = d3.axisBottom(xScale);
    //     svgElement
    //         .append("g")
    //         .attr("transform", "translate(0," + boundsHeight + ")")
    //         .call(xAxisGenerator);
    //
    //     const yAxisGenerator = d3.axisLeft(yScale);
    //     svgElement.append("g").call(yAxisGenerator);
    // }, [xScale, yScale, boundsHeight]);

    function seatClick(seat){
        seat.seleccionada = true;
    }

    });

    return(
        <svg
            ref={ref}
            // width={width} height={height}
            // id="d3graph"
        style={{
            height: "100%",
            width: "100%"
        }}>
            <g
                // width={boundsWidth}
                // height={boundsHeight}
                className="plotArea"
                transform={`translate(${[MARGIN.left, MARGIN.top].join(",")})`}
            >
                {/*{allShapes}*/}
            </g>
            {/* Second is for the axes */}
            {/*<g*/}
            {/*    width={boundsWidth}*/}
            {/*    height={boundsHeight}*/}
            {/*    ref={axesRef}*/}
            {/*    transform={`translate(${[MARGIN.left, MARGIN.top].join(",")})`}*/}
            {/*/>*/}
        </svg>
    )
}

export default SeatMap;