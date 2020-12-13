package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.List;

@Data
public class FlowHighcharts {


    private List<Series> series;
    private String[] colors;
}
