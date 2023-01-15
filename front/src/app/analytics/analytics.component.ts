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
  series: TimeSeries[] = [new TimeSeries(), new TimeSeries(), new TimeSeries()];
  canvas: HTMLCanvasElement = document.getElementById(
    "chart"
  ) as HTMLCanvasElement;
  chart = new SmoothieChart();
  randomColor(): string {
    const hex = "ABCDEF0123456789";
    let color = "#";
    for (let i = 0; i < 8; i++)
      color += hex.charAt(Math.round(Math.random() * hex.length - 1));
    return color;
  }
  colors = ["rgba(0,255,0,1)", "rgba(255,0,0,0.8)", "rgba(0,0,255,0.6)"];
  ngOnInit(): void {
    for (let i = 0; i < 3; i++) {
      //this.series.push(new TimeSeries());
      let color = this.colors[i];
      this.chart.addTimeSeries(this.series[i], {
        strokeStyle: color,
      });
      //"rgba(0, 255, 0, 1)"
    }

    this.canvas = document.getElementById("chart") as HTMLCanvasElement;
    this.chart.streamTo(this.canvas, 500);
    this.getAnalytics();
  }

  getAnalytics(): void {
    const context = this;
    this.analyticsService
      .getAnalytics()
      .addEventListener("message", (response) => {
        const data = JSON.parse(response.data);
        for (let index = 0; index < 3; index++) {
          context.series[index].append(Date.now(), data[index + 1]);
        }
      });
    // this.analyticsService.getAnalytics().subscribe((data) => {
    //   console.log("Analytics");
    //   console.log(data);
    // });
  }
}
