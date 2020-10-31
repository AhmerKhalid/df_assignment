import { Injectable } from '@angular/core';
import {Parent} from 'src/app/models/index'
import {environment} from 'src/environments/environment'
@Injectable({
  providedIn: 'root'
})
export class ParentService {

  parents:Parent[]=new Array();
  totalItem:number;
  constructor() { }

 //Get Data From API
  GetParentData(pageNumber,pageSize){
    debugger;
    this.parents=[];
    let url=environment.apiUrl+"/parent/getAll?page="+pageNumber+'&size='+pageSize;
    return new Promise((resolve,reject)=>{
     try {
       fetch (url,{
       method:'GET',
        headers: {
          "Content-type":"application/json"
          },
        })
         .then(response =>{
           debugger;
            if(response.ok)
            {
              response.json().then(data=>
               {
                 this.parents=data.parentPagableRecords;
                 this.totalItem=data.totalItemRecords;
                 resolve(response.text);
               });
              
            }
            else{
              console.log("error");
              reject(response.text);
            }
          
         });
        }
        
        catch (e) {
         
         return e.message;
        }
    });
   
 }
}
