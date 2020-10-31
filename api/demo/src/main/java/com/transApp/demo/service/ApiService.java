package com.transApp.demo.service;

import com.transApp.demo.model.Child;
import com.transApp.demo.model.Parent;
import com.transApp.demo.projection.ChildRecord;
import com.transApp.demo.projection.ParentPagableRecords;
import com.transApp.demo.projection.ParentRecord;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    public ParentPagableRecords GetParentRecord(Pageable pageRequest){
        try
        {
            //This method return Parent Record according to page size and page number and also appends total no. of records.
            ParentPagableRecords parentPagableRecords=new ParentPagableRecords();
            List<ParentRecord> parentRecords = new ArrayList<>();
            parentRecords=GetParentRecords(GetParentDataFromJsonFile(),GetChildDataFromJsonFile());
            parentPagableRecords.totalItemRecords=parentRecords.size();
            parentRecords=PaginateParentRecords(pageRequest.getPageNumber(),pageRequest.getPageSize(),parentRecords);
            parentPagableRecords.parentPagableRecords=parentRecords;
            return parentPagableRecords;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    public List<ChildRecord> GetChildRecord(int parentId){
        try
        {
            //This method return Child Record.
            List<ChildRecord> childRecords = new ArrayList<>();
            List<Parent> parentList=new ArrayList<>();
            List<Child> childList=new ArrayList<>();
            parentList=GetParentDataFromJsonFile();
            childList=GetChildDataFromJsonFile();
            childRecords=GetChildRecords(parentList,childList,parentId);
            return childRecords;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    private List<ParentRecord>GetParentRecords(List<Parent> parentData,List<Child>childData)
    {
        try
        {
            List<ParentRecord> parentRecords=new ArrayList<>();
            parentData.forEach(parent->{
                ParentRecord parentRecordObj=new ParentRecord();
                parentRecordObj.id=parent.id;
                parentRecordObj.receiverName=parent.receiver;
                parentRecordObj.senderName=parent.sender;
                parentRecordObj.totalAmount=parent.totalAmount;
                parentRecordObj.totalPaidAmount=0;
                childData.forEach(child->{
                        if(child.parentId==parent.id)
                        {
                            parentRecordObj.totalPaidAmount+=child.paidAmount;
                        }
                });
                parentRecords.add(parentRecordObj);
            });
            return parentRecords;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    private List<ChildRecord>GetChildRecords(List<Parent> parentData,List<Child>childData,int parentId)
    {
        try
        {
            List<ChildRecord> childRecords=new ArrayList<>();
            parentData.forEach(parent->{
                if(parent.id==parentId)
                {
                    childData.forEach(child->{
                        if(child.parentId==parent.id)
                        {
                            ChildRecord childRecordObj=new ChildRecord();
                            childRecordObj.id=child.id;
                            childRecordObj.receiverName=parent.receiver;
                            childRecordObj.senderName=parent.sender;
                            childRecordObj.totalAmount=parent.totalAmount;
                            childRecordObj.paidAmount=child.paidAmount;
                            childRecords.add(childRecordObj);
                        }

                    });
                }


            });
            return childRecords;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    private List<Parent> GetParentDataFromJsonFile()
    {
        List<Parent> parentRecords=new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("F:\\Demo\\demo\\Parent.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray jsonarray = new JSONArray();
            jsonarray.add(obj);
            for (int i = 0; i < jsonarray.size(); i++) {
                JSONObject jsonObject = (JSONObject)jsonarray.get(i);
                JSONArray parentRecordList= (JSONArray) jsonObject.get("data");
                parentRecordList.forEach( parent ->
                {
                    parentRecords.add(parseParentObject( (JSONObject) parent ));
                } );
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parentRecords;
    }

    private Parent parseParentObject(JSONObject parent)
    {
        try
        {
            Parent parentObj=new Parent();
            int totalAmount =  ((Long)parent.get("totalAmount")).intValue();
            parentObj.totalAmount=totalAmount;

            String receiver = (String) parent.get("receiver");
            parentObj.receiver=receiver;

            String sender = (String) parent.get("sender");
            parentObj.sender=sender;

            int id = ((Long)parent.get("id")).intValue();;
            parentObj.id=id;

            return parentObj;
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    public List<Child> GetChildDataFromJsonFile()
    {
        List<Child> childRecords=new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("F:\\Demo\\demo\\Child.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray jsonarray = new JSONArray();
            jsonarray.add(obj);
            for (int i = 0; i < jsonarray.size(); i++) {
                JSONObject jsonObject = (JSONObject)jsonarray.get(i);
                JSONArray childRecordList= (JSONArray) jsonObject.get("data");
                childRecordList.forEach( child ->
                {
                    childRecords.add(parseChildObject( (JSONObject) child ));
                } );
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return childRecords;
    }

    private Child parseChildObject(JSONObject parent)
    {
        try
        {
            Child childObj=new Child();
            int parentId =  ((Long)parent.get("parentId")).intValue();
            childObj.parentId=parentId;

            int paidAmount =  ((Long)parent.get("paidAmount")).intValue();
            childObj.paidAmount=paidAmount;

            int id = ((Long)parent.get("id")).intValue();;
            childObj.id=id;

            return childObj;
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    private List<ParentRecord> PaginateParentRecords(int page, int size,List<ParentRecord> parentRecords)
    {
        try
        {
            List<ParentRecord> subParentRecords=new ArrayList<>();
            int SI=getStartingIndex(page,size,parentRecords.size());
            int EI=getEndingIndex(page,size,SI,parentRecords.size());
            subParentRecords=parentRecords.subList(SI, EI);
            return  subParentRecords;

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    private int getStartingIndex(int page, int size,int recordsSize)
    {
        try
        {
            int startingIndex=page*size;
            if (startingIndex < 0) {
                startingIndex = 0;
            }
            if(startingIndex>=recordsSize)
            {
                startingIndex = startingIndex-size;
            }
            return startingIndex;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    private int getEndingIndex(int page, int size,int startingIndex, int recordsSize)
    {
        try
        {
            int endingIndex=startingIndex+size;
            if (endingIndex < 0) {
                endingIndex = size-1;
            }
            if(endingIndex>recordsSize)
            {
                endingIndex = recordsSize;
            }
            return endingIndex;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
