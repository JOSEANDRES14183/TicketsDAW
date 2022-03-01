import React, {useEffect, useMemo, useRef} from 'react';
import {useD3} from "../hooks/useD3";
import * as d3 from "d3";

const MARGIN = { top: 30, right: 30, bottom: 30, left: 30 };

//https://www.react-graph-gallery.com/scatter-plot
function SeatMap({seats}) {
    const multiplyFactor = 48;

    const axesRef = useRef(null);
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

    const allShapes = seats.map((d, i) => {
        return (
            <circle
                key={i}
                r={16}
                cx={d.pos_x*multiplyFactor}
                cy={d.pos_y*multiplyFactor}
                opacity={1}
                stroke="#9a6fb0"
                fill="#9a6fb0"
                fillOpacity={0.7}
                strokeWidth={1}
            />
        );
    });

    function handleZoom(e) {
        d3.select('svg g')
            .attr('transform', e.transform);
    }

    let zoom = d3.zoom()
        .on('zoom', handleZoom);

    d3.select('svg')
        .call(zoom);

    return(
        <svg
            // width={width} height={height}
            id="d3graph"
        style={{
            height: "100%",
            width: "100%"
        }}>
            <g
                // width={boundsWidth}
                // height={boundsHeight}
                transform={`translate(${[MARGIN.left, MARGIN.top].join(",")})`}
            >
                {allShapes}
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