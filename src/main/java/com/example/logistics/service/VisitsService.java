package com.example.logistics.service;

import com.example.logistics.dto.LoadDto;
import com.example.logistics.dto.VisitsDto;

import java.util.Date;

public interface VisitsService {

    VisitsDto getVisits(Date startTime, Date endTime);

    LoadDto getLoad(Date startTime, Date endTime);
}
