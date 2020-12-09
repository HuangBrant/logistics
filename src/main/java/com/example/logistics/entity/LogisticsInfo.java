package com.example.logistics.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "logistics_info")
public class LogisticsInfo {

    @Id
    private String aid;

    private Integer cid;

    private Integer uid;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Integer positionid;
}
