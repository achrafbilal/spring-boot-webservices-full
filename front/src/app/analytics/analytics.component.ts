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

  viewMode: String = "top";
  series: TimeSeries[] = [new TimeSeries(), new TimeSeries(), new TimeSeries()];
  canvas: HTMLCanvasElement = document.getElementById(
    "chart"
  ) as HTMLCanvasElement;
  chart = new SmoothieChart();
  randomColor(): string {
    const hex = "ABCDEF0123456789";
    let color = "#";
    for (let i = 0; i < 6; i++)
      color += hex.charAt(Math.round(Math.random() * hex.length - 1));
    return color;
  }
  colors = [
    "rgba(0,255,0,1)",
    "rgba(255,0,0,1)",
    "rgba(0,0,255,1)",
    "rgba(140,140,140,1)",
  ];

  colorsAlpha = [
    "rgba(0,255,0,5)",
    "rgba(255,0,0,0.5)",
    "rgba(0,0,255,0.5)",
    "rgba(0,140,140,0.5)",
  ];
  ngOnInit(): void {
    this.canvas = document.getElementById("chart") as HTMLCanvasElement;
    this.chart.streamTo(this.canvas, 500);
    this.getAnalytics();
  }
  onViewModeSelectChange(mode: string): void {
    console.log(mode);
    this.series = [];
    this.viewMode = mode;
  }

  getAnalytics(): void {
    const context = this;
    this.analyticsService
      .getAnalytics()
      .addEventListener("message", (response) => {
        const data = JSON.parse(response.data);
        const keys = Object.keys(data);
        let arr = keys.sort((a, b) =>
          this.viewMode === "last"
            ? Number(a) - Number(b)
            : Number(b) - Number(a)
        );
        if (keys.length > 4) arr = keys.slice(0, 4);
        arr.forEach((key, index) => {
          if (keys.length !== context.series.length) {
            this.series.push(new TimeSeries());
            this.chart.addTimeSeries(this.series[index], {
              strokeStyle: this.colors[index],
              fillStyle: this.colorsAlpha[index],
              lineWidth: 3,
            });
          }
          console.log(key, index);
          context.series[index].append(Date.now(), data[key]);
        });
        // for (let index = 0; index < Object.keys(data).length; index++) {
        //   context.series[index].append(Date.now(), data[index + 1]);
        // }
      });
    // this.analyticsService.getAnalytics().subscribe((data) => {
    //   console.log("Analytics");
    //   console.log(data);
    // });
  }
}
