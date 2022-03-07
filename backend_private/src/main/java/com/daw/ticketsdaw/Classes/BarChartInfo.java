package com.daw.ticketsdaw.Classes;

import lombok.Data;

@Data
public class BarChartInfo {

    public BarChartInfo(int day, int numberOfEntradas){
        this.day = day;
        this.numberOfEntradas = numberOfEntradas;
    }

    private int day;

    private int numberOfEntradas;
}
