import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { fromEvent, merge, of as observableOf, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, startWith, switchMap, tap } from 'rxjs/operators';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator'
import {MatSort} from '@angular/material/sort'
import { Child } from 'src/app/models/index';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { ChildService } from 'src/app/services';
@Component({
  selector: 'app-child',
  templateUrl: './child.component.html',
  styleUrls: ['./child.component.css']
})
export class ChildComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  @ViewChild('inputFilterText', { static: false }) inputFilterText: ElementRef;
  displayedColumns = [
    'id',
    'sender',
    'receiver',
    'totalAmount',
    'totalPaidAmount'
  ];
  dataSource: MatTableDataSource<Child> = new MatTableDataSource();

  totalRecords: number;
  pageNumber = '0';
  pageSize = '2';
  sortOrder = 'asc';
  sortColumn = 'id';
  filterValue = '';
  parentID:number=-1;

  constructor(private activatedRoute: ActivatedRoute,private childService:ChildService) {}
  ngAfterViewInit() {
    debugger;
       this.dataSource.paginator = this.paginator;
    fromEvent(this.inputFilterText.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          debugger;
          console.log(this.pageNumber);
        })
      ).subscribe();

      merge(this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          debugger;
          console.log(this.pageNumber);
          return observableOf([]);
        }
        )
      ).subscribe();
  }
  ngOnInit() {

    const from_id =this.activatedRoute.snapshot.params.parent_id;
     if(from_id!=undefined)
     {
       console.log("Form Idea")
       console.log(from_id)
       this.parentID=from_id;
     }
     this.callApi();
  }

  callApi(): void {
  
    if (this.paginator !== undefined && this.paginator.pageIndex !== undefined) {
      this.pageNumber = this.paginator.pageIndex.toString();
      this.pageSize = this.paginator.pageSize.toString();
    }
    if (this.sort !== undefined && this.paginator.pageIndex !== undefined) {
      if (this.sort.direction !== undefined) {
        this.sortOrder = this.sort.direction;
      }
      if (this.sort.active !== undefined) {
        if (this.sort.active == 'id') {
          this.sortColumn = 'id'
        }
        else {
          this.sortColumn = this.sort.active;
        }

      }
    }
    //API Call
   this.childService.GetChildData(this.parentID).then(res=>{
    this.dataSource.data=this.childService.childs; //Set data into dataSource
   }).catch(rej=>{ console.log(rej) });
  }
  
}
