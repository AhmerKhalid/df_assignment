package com.transApp.demo.controller;


import com.transApp.demo.projection.ParentPagableRecords;
import com.transApp.demo.projection.ParentRecord;
import com.transApp.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.data.domain.Pageable;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ParentController {

    @Autowired
    private ApiService service;

    @GetMapping("/parent/getAll")
    @ResponseBody
    public ParentPagableRecords get(Pageable pageRequest) {
        ParentPagableRecords parentPagableRecords=service.GetParentRecord(pageRequest);
        return parentPagableRecords;
    }


}
