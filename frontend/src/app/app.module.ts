import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { GrafanaComponent } from './grafana/grafana.component';
import { BuildOverviewComponent } from './build-overview/build-overview.component';
import {CalendarHeatmap} from "angular2-calendar-heatmap";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    GrafanaComponent,
    BuildOverviewComponent,
    CalendarHeatmap,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
