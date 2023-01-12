import { AnalyticsService } from "./../services/analytics.service";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Component, OnInit } from "@angular/core";
import { SmoothieChart, TimeSeries } from "smoothie";
import { SecurityService } from "../services/security.service";
import { Analytic } from "../model/analytics.model";

@Component({
  selector: "app-analytics",
  templateUrl: "./analytics.component.html",
  styleUrls: ["./analytics.component.scss"],
})
export class AnalyticsComponent implements OnInit {
  constructor(
    public authService: SecurityService,
    private analyticsService: AnalyticsService
  ) {}
  series!: TimeSeries;
  canvas: HTMLCanvasElement = document.getElementById(
    "chart"
  ) as HTMLCanvasElement;
  chart = new SmoothieChart();
  ngOnInit(): void {
    this.series = new TimeSeries();
    //this.canvas;
    this.chart.addTimeSeries(this.series, {
      strokeStyle: "rgba(0, 255, 0, 1)",
    });
    this.canvas = document.getElementById("chart") as HTMLCanvasElement;
    this.chart.streamTo(this.canvas, 500);

    const context = this;
    setInterval(function () {
      context.series.append(Date.now(), Math.random() * 10000);
      console.log("e");
    }, 500);
    this.getAnalytics();
  }

  getAnalytics(): void {
    this.analyticsService.getAnalytics().subscribe((data) => {
      console.log(data);
    });
  }
}
