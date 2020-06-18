import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavigationComponent} from './navigation/navigation.component';
import {GrafanaComponent} from './grafana/grafana.component';
import {BuildOverviewComponent} from './build-overview/build-overview.component';
import {HttpClientModule} from '@angular/common/http';
import {NgApexchartsModule} from 'ng-apexcharts';
import {FormsModule} from "@angular/forms";
import {GoogleChartsModule} from "angular-google-charts";

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    GrafanaComponent,
    BuildOverviewComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        NgApexchartsModule,
        AppRoutingModule,
        GoogleChartsModule,
        FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
