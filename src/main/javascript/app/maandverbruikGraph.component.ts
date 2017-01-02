import {Component, OnInit} from '@angular/core';

import {Maandverbruik} from './maandverbruik';
import {MaandverbruikService} from './maandverbruik.service';
import {Metersoorten} from './metersoorten';
import {MetersoortenService} from './metersoorten.service';

declare var Chart: any;
import '../javascript_libs/Chart.min.js';

@Component({
	moduleId: module.id,
	selector: 'my-maandverbruikgraph',
	template: `
		<h2>Graph maandverbruik</h2>
		<select name="metersoort" #ms (change)="metersoortChangeUI(ms.value)">
			<option *ngFor="let m of myMetersoorten"
				[value]="m.id"
				>{{m.metersoort}}
			</option>
		</select>
		<div><canvas id="myChart" width=400 height=400></canvas></div>
		`
})

export class MaandverbruikGraphComponent implements OnInit{
	constructor(
		private maandverbruikService: MaandverbruikService,
		private metersoortenService: MetersoortenService
	){};
	myMaandverbruik: Maandverbruik[];
	myDrawData: number[];
	myMetersoorten: Metersoorten[];

	metersoortChangeUI(id: number): void{
		this.getMaandverbruik(2016,id);
	}

	getMaandverbruik(y: number, ms: number): void {
		this.maandverbruikService.getMaandverbruiken(y, ms)
			.then(mv => this.updateAndDraw(mv));
	}

	getMetersoorten(): void {
		this.metersoortenService.getMetersoorten()
			.then(ms => this.myMetersoorten=ms);
	}

	updateAndDraw(mv:Maandverbruik[]):void{
		this.myMaandverbruik=mv;
		this.myDrawData = this.maandverbruikToData(this.myMaandverbruik);

		var ctx = document.getElementById('myChart');

		var myData = {
			labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
			datasets: [{
				label: this.myMaandverbruik[0].metersoort.metersoort + "-" + this.myMaandverbruik[0].jaar,
				data: this.myDrawData,
				borderWidth: 1,
				pointRadius: 5,
				pointBackgroundColor: 'rgb(10,10,10)',
				backgroundColor: 'rgba(10,10,10,0.2)'
				}]
		};

		var myOptions = {
			scales: {
				yAxes: [{
					ticks: {beginAtZero:true},
					scaleLabel: {display: true, labelString: this.myMaandverbruik[0].metersoort.unit + "/maand"}
				}]
			}
		};

		//draw chart
		var chart = new Chart(ctx, {
			type: 'line',
			data: myData,
			options: myOptions
			}
		);
	}

	maandverbruikToData(mv:Maandverbruik[]):number[]{
		var out = [];
		for (var m in mv){
			out.push(mv[m].verbruik);
		}
		return out;
	}

	ngOnInit(): void {
		this.getMaandverbruik(2016,1);
		this.getMetersoorten();
	}
}
