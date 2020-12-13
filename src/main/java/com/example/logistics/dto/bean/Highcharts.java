package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.List;

@Data
public class Highcharts {

    private List<List<Series>> datas;
}
