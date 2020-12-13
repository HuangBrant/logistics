package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.List;

@Data
public class FlowHighcharts {


    private List<FlowSeries> series;
    private String[] colors = {"colors.green", "colors.blue", "colors.orange", "colors.pink"};
}
