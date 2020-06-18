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

    type: string = "Timeline"
    chartData: Array<Array<any>> =[
        [ 'vistrpptbu1010', '', 'Deine Mutter', new Date(1789, 3, 30), new Date(1797, 2, 4) ],
        [ 'vistrpptbu1010', '', 'Deine Mutter2', new Date(1797, 2, 4),  new Date(1801, 2, 4) ],
        [ 'vistrpptbu1011', '', 'Deine Mutter3', new Date(1801, 2, 4),  new Date(1809, 2, 4) ],
        [ 'vistrpptbu1012', '', 'Deine Mutter4', new Date(1901, 2, 4),  new Date(2018, 2, 4) ]
    ]
    colNames: Array<any> = ['Host', 'Job', {role: 'tooltip', type: 'string', p: {html: true} }, 'From', 'To']
    options: {} = {
        width: '1200',
        tooltip: { isHtml: true },
        explorer: { actions: ['dragToPan'] }
    }



    builtOn: string;
    jobName: string;
    lookbackDays: number = 2;

    onRefresh() {
        this.buildOverviewService.getBuildOverview(this.builtOn, this.jobName, this.lookbackDays.toString()).subscribe(data => {
            this.chartData = []
            for (let row of data) {
                console.log(JSON.stringify(row));
                this.chartData.push([row.host, '', row.jobName, row.from, row.to])
            }
        })
        // this.buildOverviewService.getBuildOverview(this.builtOn, this.jobName, this.lookbackDays.toString()).subscribe(data => {
        //     console.log(JSON.stringify(data.series))
        //     this.series = data.series;
        // });
    }

    constructor(private buildOverviewService: BuildOverviewService) {
        // this.buildOverviewService.getBuildOverview(this.builtOn, this.jobName, this.lookbackDays.toString()).subscribe(data => {
        //     console.log(JSON.stringify(data.series))
        //     this.series = data.series;
        // });
    }

}
