import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {GrafanaComponent} from "./grafana/grafana.component";
import {BuildOverviewComponent} from "./build-overview/build-overview.component";


const routes: Routes = [
  { path: 'grafana', component: GrafanaComponent },
  { path: 'overview', component: BuildOverviewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
