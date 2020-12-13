package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Series {

    private List<double[]> date;
    private String name;
    private String type;
}
