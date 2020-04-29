import {Component, OnInit, ViewChild} from '@angular/core';
import {BuildOverviewService} from '../services/build-overview.service';
import {ApexChart, ApexPlotOptions, ApexXAxis, ChartComponent} from 'ng-apexcharts';
import {ApexResponsive} from "ng-apexcharts/lib/model/apex-types";

export interface ChartOptions {
    // series: ApexAxisChartSeries;
    chart: ApexChart;
    xaxis: ApexXAxis;
    plotOptions: ApexPlotOptions;
    responsive: ApexResponsive;
}

@Component({
    selector: 'app-build-overview',
    templateUrl: './build-overview.component.html',
    styleUrls: ['./build-overview.component.css']
})
export class BuildOverviewComponent implements OnInit {

    @ViewChild('chart')
    public chart: ChartComponent;

    public chartOptions: ChartOptions = {
        chart: {
            height: '1000',
            width: '100%',
            type: 'rangeBar'
        },
        plotOptions: {
            bar: {
                horizontal: true
            }
        },
        xaxis: {
            type: 'datetime'
        },
        responsive: [
            {
                breakpoint: 1000,
                options: {
                    plotOptions: {
                        bar: {
                            horizontal: false
                        }
                    },
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        ]

    };
    series: any = [];


    constructor(private buildOverviewService: BuildOverviewService) {
        this.buildOverviewService.getBuildOverview().subscribe(data => {
            console.log(data);
            this.series = data.series;
            // @ts-ignore
            // this.chartOptions =  {
            //     chart: {
            //         height: '100%',
            //         width: '100%',
            //         type: 'rangeBar'
            //     },
            //     plotOptions: {
            //         bar: {
            //             horizontal: true
            //         }
            //     },
            //     xaxis: {
            //         type: 'datetime'
            //     },
            //     // responsive: {
            //     //     breakpoint: 1000,
            //     //     options: {
            //     //         plotOptions: {
            //     //             bar: {
            //     //                 horizontal: false
            //     //             }
            //     //         },
            //     //         legend: {
            //     //             position: 'bottom'
            //     //         }
            //     //     }
            //     // }
            // };
        });
    }

    ngOnInit(): void {

    }

}
