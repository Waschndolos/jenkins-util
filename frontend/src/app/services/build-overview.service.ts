import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BuildOverviewService {

  constructor(private http: HttpClient) { }

  getBuildOverview(builtOn: string, jobName: string): Observable<any> {
    if (builtOn != null && jobName == null) {
      return this.http.get<any>('/api/v1/jenkins/build/distribution?filter=builtOn=' + builtOn);
    }
    if (builtOn != null && jobName != null) {
      return this.http.get<any>('/api/v1/jenkins/build/distribution?filter=builtOn=' + builtOn + '&jobName=' + jobName);
    }
    if (jobName !=  null && builtOn == null) {
      return this.http.get<any>('/api/v1/jenkins/build/distribution?filter=jobName=' + jobName);
    }
    return this.http.get<any>('/api/v1/jenkins/build/distribution');
  }

}
