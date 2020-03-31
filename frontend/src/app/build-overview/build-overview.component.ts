import {Component, OnInit} from '@angular/core';
import {BuildOverviewService} from "../services/build-overview.service";

@Component({
    selector: 'app-build-overview',
    templateUrl: './build-overview.component.html',
    styleUrls: ['./build-overview.component.css']
})
export class BuildOverviewComponent implements OnInit {

    // https://github.com/g1eb/angular2-calendar-heatmap
    data = [
        {
            "date": "2020-01-28",
            "total": 1021857,
            "details": [
                {
                    "name": "vistrpptbu1069",
                    "date": "2020-01-28 04:57:32",
                    "value": 6934
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 04:57:32",
                    "value": 4712
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 07:57:20",
                    "value": 7123
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 02:30:03",
                    "value": 8840
                },
                {
                    "name": "vistrpptbu1510",
                    "date": "2020-01-28 08:30:03",
                    "value": 20832
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 02:30:03",
                    "value": 19631
                },
                {
                    "name": "z_perf_vistrpptbu0001_win7",
                    "date": "2020-01-28 08:57:40",
                    "value": 70404
                },
                {
                    "name": "z_perf_vistrpptbu0002_win7",
                    "date": "2020-01-28 05:37:48",
                    "value": 36111
                },
                {
                    "name": "z_perf_vistrpptbu0003_win7",
                    "date": "2020-01-28 07:50:57",
                    "value": 29475
                },
                {
                    "name": "z_perf_visppt7966vmh1_win7",
                    "date": "2020-01-28 03:09:49",
                    "value": 11954
                },
                {
                    "name": "z_perf_visppt7966vmh1_win7",
                    "date": "2020-01-28 06:59:09",
                    "value": 5287
                },
                {
                    "name": "z_perf_visppt7966vmh1_win7",
                    "date": "2020-01-28 06:57:41",
                    "value": 16143
                },
                {
                    "name": "z_perf_visppt7966vmh1_win7",
                    "date": "2020-01-28 12:41:53",
                    "value": 4634
                },
                {
                    "name": "z_perf_vistrpptbu0002_win7",
                    "date": "2020-01-28 08:19:03",
                    "value": 32885
                },
                {
                    "name": "vistrpptbu1510",
                    "date": "2020-01-28 08:50:07",
                    "value": 8221
                },
                {
                    "name": "vistrpptbu1510",
                    "date": "2020-01-28 12:36:36",
                    "value": 5634
                },
                {
                    "name": "vistrpptbu1043",
                    "date": "2020-01-28 01:36:33",
                    "value": 11
                },
                {
                    "name": "vistrpptbu1036",
                    "date": "2020-01-28 07:35:03",
                    "value": 14453
                },
                {
                    "name": "vistrpptbu1055",
                    "date": "2020-01-28 07:39:03",
                    "value": 20214
                },
                {
                    "name": "vistrpptbu1034",
                    "date": "2020-01-28 01:05:48",
                    "value": 29023
                },
                {
                    "name": "vistrpptbu1537",
                    "date": "2020-01-28 04:29:03",
                    "value": 898
                },
                {
                    "name": "vistrpptbu1054",
                    "date": "2020-01-28 07:59:33",
                    "value": 29052
                },
                {
                    "name": "vistrpptbu1567",
                    "date": "2020-01-28 05:04:03",
                    "value": 10977
                },
                {
                    "name": "vistrpptbu1054",
                    "date": "2020-01-28 04:11:03",
                    "value": 13703
                },
                {
                    "name": "vistrpptbu1556",
                    "date": "2020-01-28 04:37:03",
                    "value": 1886
                },
                {
                    "name": "vistrpptbu1541",
                    "date": "2020-01-28 04:14:03",
                    "value": 10569
                },
                {
                    "name": "vistrpptbu1549",
                    "date": "2020-01-28 04:09:03",
                    "value": 575
                },
                {
                    "name": "vistrpptbu1511",
                    "date": "2020-01-28 04:29:03",
                    "value": 2182
                },
                {
                    "name": "vistrpptbu1508",
                    "date": "2020-01-28 04:34:03",
                    "value": 1762
                },
                {
                    "name": "vistrpptbu1523",
                    "date": "2020-01-28 04:45:03",
                    "value": 318
                },
                {
                    "name": "vistrpptbu1516",
                    "date": "2020-01-28 04:18:03",
                    "value": 4475
                },
                {
                    "name": "vistrpptbu1019",
                    "date": "2020-01-28 02:01:14",
                    "value": 622
                },
                {
                    "name": "vistrpptbu1556",
                    "date": "2020-01-28 04:18:03",
                    "value": 265
                },
                {
                    "name": "vistrpptbu1567",
                    "date": "2020-01-28 01:09:03",
                    "value": 7740
                },
                {
                    "name": "vistrpptbu1010",
                    "date": "2020-01-28 04:44:31",
                    "value": 1013
                },
                {
                    "name": "vistrpptbu1014",
                    "date": "2020-01-28 04:22:09",
                    "value": 1478
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 03:00:03",
                    "value": 21815
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 08:13:03",
                    "value": 18749
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 07:35:08",
                    "value": 19143
                },
                {
                    "name": "vistrpptbu1060",
                    "date": "2020-01-28 01:00:03",
                    "value": 12552
                },
                {
                    "name": "vistrpptbu1060",
                    "date": "2020-01-28 01:00:03",
                    "value": 12785
                },
                {
                    "name": "vistrpptbu1050",
                    "date": "2020-01-28 01:00:03",
                    "value": 12518
                },
                {
                    "name": "vistrpptbu1060",
                    "date": "2020-01-28 01:38:03",
                    "value": 12831
                },
                {
                    "name": "vistrpptbu1050",
                    "date": "2020-01-28 02:00:03",
                    "value": 10403
                },
                {
                    "name": "vistrpptbu1060",
                    "date": "2020-01-28 07:35:04",
                    "value": 7768
                },
                {
                    "name": "vistrpptbu1060",
                    "date": "2020-01-28 12:28:03",
                    "value": 11199
                },
                {
                    "name": "vistrpptbu1050",
                    "date": "2020-01-28 08:00:03",
                    "value": 17821
                },
                {
                    "name": "vistrpptbu1060",
                    "date": "2020-01-28 08:00:03",
                    "value": 14992
                },
                {
                    "name": "vistrpptbu1020",
                    "date": "2020-01-28 07:30:03",
                    "value": 50326
                },
                {
                    "name": "vistrpptbu1070",
                    "date": "2020-01-28 06:44:57",
                    "value": 15665
                },
                {
                    "name": "vistrpptbu1070",
                    "date": "2020-01-28 06:30:03",
                    "value": 740
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 06:30:03",
                    "value": 23889
                },
                {
                    "name": "vistrpptbu1020",
                    "date": "2020-01-28 10:30:03",
                    "value": 13071
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 10:49:03",
                    "value": 11524
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 11:19:51",
                    "value": 11664
                },
                {
                    "name": "vistrpptbu1020",
                    "date": "2020-01-28 02:01:03",
                    "value": 866
                },
                {
                    "name": "vistrpptbu1544",
                    "date": "2020-01-28 11:45:03",
                    "value": 8984
                },
                {
                    "name": "vistrpptbu1525",
                    "date": "2020-01-28 11:45:03",
                    "value": 7849
                },
                {
                    "name": "vistrpptbu1070",
                    "date": "2020-01-28 07:53:03",
                    "value": 22295
                },
                {
                    "name": "vistrpptbu1070",
                    "date": "2020-01-28 07:05:03",
                    "value": 20310
                },
                {
                    "name": "vistrpptbu1020",
                    "date": "2020-01-28 09:15:03",
                    "value": 16571
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 09:15:03",
                    "value": 16251
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 07:15:03",
                    "value": 38597
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 07:00:03",
                    "value": 22701
                },
                {
                    "name": "vistrpptbu1030",
                    "date": "2020-01-28 07:33:05",
                    "value": 25536
                },
                {
                    "name": "vistrpptbu1040",
                    "date": "2020-01-28 06:45:03",
                    "value": 13876
                },
                {
                    "name": "vistrpptbu1040",
                    "date": "2020-01-28 06:45:03",
                    "value": 16851
                },
                {
                    "name": "vistrpptbu1050",
                    "date": "2020-01-28 03:30:03",
                    "value": 14349
                },
                {
                    "name": "vistrpptbu1050",
                    "date": "2020-01-28 02:11:35",
                    "value": 5011
                },
                {
                    "name": "vistrpptbu1040",
                    "date": "2020-01-28 09:30:03",
                    "value": 20663
                },
                {
                    "name": "vistrpptbu1040",
                    "date": "2020-01-28 08:30:03",
                    "value": 29231
                },
                {
                    "name": "vistrpptbu1065",
                    "date": "2020-01-28 07:19:03",
                    "value": 1538
                },
                {
                    "name": "vistrpptbu1014",
                    "date": "2020-01-28 02:46:13",
                    "value": 781
                },
                {
                    "name": "vistrpptbu1001",
                    "date": "2020-01-28 05:14:16",
                    "value": 106
                }
            ]
        }
    ]

    constructor(private buildOverviewService: BuildOverviewService) { }

    ngOnInit(): void {
        this.buildOverviewService.getBuildOverview().subscribe(data => {
            // this.data = data;
            console.log(JSON.stringify(this.data))
        })
    }

    print($event: object) {

    }
}
