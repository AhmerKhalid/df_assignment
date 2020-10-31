import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { fromEvent, merge, of as observableOf, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, startWith, switchMap, tap } from 'rxjs/operators';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator, PageEvent} from '@angular/material/paginator'
import {MatSort} from '@angular/material/sort'
import {Parent} from 'src/app/models/index'
import {ParentService} from 'src/app/services/index'
@Component({
  selector: 'app-parent',
  templateUrl: './parent.component.html',
  styleUrls: ['./parent.component.css']
})
export class ParentComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  @ViewChild('inputFilterText', { static: false }) inputFilterText: ElementRef;
  displayedColumns = ['id', 'sender', 'receiver', 'totalAmount', 'totalPaidAmount'];
  dataSource: MatTableDataSource<Parent> = new MatTableDataSource();
  totalRecords: number;
  pageNumber = '0';
  pageSize = '2';
  sortOrder = 'asc';
  sortColumn = 'id';
  filterValue='';
  pageEvent: PageEvent;
  constructor(public parentService:ParentService) { }

  ngOnInit() {
    this.callApi();
  }

  ngAfterViewInit() {
    debugger;
    this.dataSource.paginator = this.paginator;
      merge(this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          debugger;
          this.callApi();
          return observableOf([]);
        }
        )
      ).subscribe();
  }

  callApi(): void {
  
    debugger;
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
   this.parentService.GetParentData(parseInt(this.pageNumber), parseInt(this.pageSize)).then(res=>{
      this.dataSource=new MatTableDataSource(this.parentService.parents);   
      this.totalRecords=this.parentService.totalItem;
   }).catch(rej=>{ console.log(rej) });
  }
  
}
