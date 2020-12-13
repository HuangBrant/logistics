package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.List;

@Data
public class FlowSeries {
    private String name;
    private String type;
    private List<String> data;
}
