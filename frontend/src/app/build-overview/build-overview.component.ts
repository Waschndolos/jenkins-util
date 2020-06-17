import {Component, ViewChild} from '@angular/core';
import {BuildOverviewService} from '../services/build-overview.service';
import {ApexChart, ApexPlotOptions, ApexTooltip, ApexXAxis} from 'ng-apexcharts';

export interface ChartOptions {
    chart: ApexChart;
    xaxis: ApexXAxis;
    plotOptions: ApexPlotOptions;
    tooltip: ApexTooltip;
    datalabels: ApexDataLabels;
}

@Component({
    selector: 'app-build-overview',
    templateUrl: './build-overview.component.html',
    styleUrls: ['./build-overview.component.css']
})
export class BuildOverviewComponent {

     public chartOptions: ChartOptions = {
        chart: {
            width: '100%',
            type: 'rangeBar'
        },
        plotOptions: {
            bar: {
                horizontal: true
            }
        },
        datalabels: {
            enabled: true
        },
        tooltip: {
            enabled: true,
            shared: true,
            custom: function ({series, seriesIndex, dataPointIndex, w}) {
                return ('<div class="arrow_box">' +
                     '<span>' + JSON.stringify(series[seriesIndex]) + '</span>' +
                    '</div>')
            }
        },
        xaxis: {
            tooltip: {
                enabled: true
            },
            labels: {
                format: 'dd.MM - HH:mm'
            },
            type: 'datetime'
        }
    };
    series: any = [];
    builtOn: string;
    jobName: string;
    lookbackDays: number = 2;

    onRefresh() {
        this.buildOverviewService.getBuildOverview(this.builtOn, this.jobName, this.lookbackDays.toString()).subscribe(data => {
            console.log(JSON.stringify(data.series))
            this.series = data.series;
        });
    }

    constructor(private buildOverviewService: BuildOverviewService) {
        this.buildOverviewService.getBuildOverview(this.builtOn, this.jobName, this.lookbackDays.toString()).subscribe(data => {
            console.log(JSON.stringify(data.series))
            this.series = data.series;
        });
    }

}
