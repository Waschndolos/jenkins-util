import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BuildOverviewService {

  constructor(private http: HttpClient) { }

  getBuildOverview(builtOn: string, jobName: string, lookbackDays: string): Observable<any> {

    let params = new HttpParams().set("builtOn", BuildOverviewService.getValue(builtOn)).set("jobName", BuildOverviewService.getValue(jobName)).set("lookbackDays", BuildOverviewService.getValue(lookbackDays))

    return this.http.get<any>('/api/v1/jenkins/build/distribution', {params: params});
  }

  private static getValue(param: string) {
    if (param) {
      return param;
    }
    return "";
  }

}
