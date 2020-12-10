package com.example.logistics.controller;

import com.example.logistics.dto.LoadDto;
import com.example.logistics.dto.VisitsDto;
import com.example.logistics.service.VisitsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@Slf4j
public class ShowController {

    @Autowired
    VisitsService visitsService;

    @RequestMapping(value = "/visits/getPageDate",method = RequestMethod.GET)
    @ResponseBody
    public VisitsDto getVisits(@RequestParam Date startTime,@RequestParam Date endTime){
        try {
            VisitsDto visitsDto = visitsService.getVisits(startTime, endTime);
            return visitsDto;
        }catch (Exception e){
            log.info("");
            return null;
        }
    }

    @RequestMapping(value = "/logistics/load",method = RequestMethod.GET)
    @ResponseBody
    public LoadDto getLoad(@RequestParam Date startTime,@RequestParam Date endTime){
        try {
            LoadDto load = visitsService.getLoad(startTime, endTime);
            return load;
        }catch (Exception e){
            log.info("");
            return null;
        }
    }

    @RequestMapping(value = "/flow",method = RequestMethod.GET)
    @ResponseBody
    public Object getFlow(@RequestParam Date startTime,@RequestParam Date endTime){
        try {

            return null;
        }catch (Exception e){
            log.info("");
            return null;
        }
    }
}
