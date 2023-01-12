import { Analytic, FullPageAnalytic } from "./../model/analytics.model";
import { Injectable, OnInit } from "@angular/core";
import { Observable, of, throwError } from "rxjs";
import { UUID } from "angular2-uuid";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class AnalyticsService implements OnInit {
  private BaseURI = "http://localhost:8888/ANALYTICS-SERVICE/analytics";
  constructor(private httpClient: HttpClient) {}
  ngOnInit(): void {}

  public getAnalytics(): Observable<Analytic[]> {
    return this.httpClient.get<Analytic[]>(`${this.BaseURI}`);
  }
}
