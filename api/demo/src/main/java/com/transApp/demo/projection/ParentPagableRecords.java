package com.transApp.demo.projection;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParentPagableRecords {
    public List<ParentRecord> parentPagableRecords;
    public int totalItemRecords;
}
