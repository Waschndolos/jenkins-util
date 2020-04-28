import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BuildOverviewService {

  constructor(private http: HttpClient) { }

  getBuildOverview(): Observable<any[]> {
    return this.http.get<any[]>('/api/v1/jenkins/build/distribution')
  }

}
