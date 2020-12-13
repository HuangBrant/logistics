package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.List;

@Data
public class FlowHighcharts {


    private List<double[]> series;
    private String[] colors = {"green", "blue", "orange", "pink"};
}
