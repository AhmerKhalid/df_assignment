package com.transApp.demo.controller;


import com.transApp.demo.projection.ChildRecord;
import com.transApp.demo.projection.ParentRecord;
import com.transApp.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ChildController {

    @Autowired
    private ApiService service;

    @GetMapping("/child/getAllById")
    @ResponseBody
    public List<ChildRecord> get(@RequestParam int parentId) {
        List<ChildRecord> childRecords=service.GetChildRecord(parentId);
        return childRecords;
    }


}
