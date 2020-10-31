import { Injectable } from '@angular/core';
import {Child} from 'src/app/models/index'
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChildService {

  childs:Child[]=new Array();
  constructor() { }

  GetChildData(parentId){
    debugger;
    this.childs=[];
    let url=environment.apiUrl+"/child/getAllById?parentId="+parentId;
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
                 console.log("Child Record");
                 console.log(data);
                 this.childs=data;
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
