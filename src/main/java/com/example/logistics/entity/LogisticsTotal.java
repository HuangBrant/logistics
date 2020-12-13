package com.example.logistics.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "logistics_total")
public class LogisticsTotal {

    @Id
    private Integer id;

    private Integer total;
    private Integer invalid;
    private Integer loss;
    private Integer logisticsNum;
    private Integer drop;
    private Integer receiveNum;
    private Integer sentNum;
    private Date date;
    private String path;
    private Date usedTime;
}
