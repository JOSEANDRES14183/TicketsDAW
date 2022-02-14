package com.daw.ticketsdaw.DTOs;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CalendarEventDTO {
    String title;
    String start;
    String end;
    String backgroundColor;
    String borderColor;

    Map<String, String> extendedProps = new HashMap<>();
}
