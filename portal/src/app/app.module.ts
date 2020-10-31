import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ParentComponent,ChildComponent} from 'src/app/components/index'
import {LayoutComponent,HeaderComponent} from 'src/app/layout/index'
import {MaterialModule} from 'src/app/material-module'
import { FormsModule } from '@angular/forms';
@NgModule({
  declarations: [
    AppComponent,
    ParentComponent,
    ChildComponent,
    LayoutComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
