import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ParentComponent,ChildComponent} from 'src/app/components/index'
import {LayoutComponent,HeaderComponent} from 'src/app/layout/index'

const routes: Routes = [
  {
    path:'',
    component:LayoutComponent,
    children:[
      {
        path:'',
        redirectTo : 'parent',
        pathMatch:'full'

      },
      {
        path:'parent',
        component:ParentComponent
      },
      {
        path:'child/:parent_id',
        component:ChildComponent
      },
    ]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
