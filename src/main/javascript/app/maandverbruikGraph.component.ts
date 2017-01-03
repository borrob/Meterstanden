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
		<select name="year1" #y1 (change)="year1Change(y1.value)">
			<option value=2016>2016</option>
			<option value=2015>2015</option>
			<option value=2014>2014</option>
		</select>
		<select name="year2" #y2 (change)="year2Change(y2.value)">
			<option value=0>--</option>
			<option value=2016>2016</option>
			<option value=2015>2015</option>
			<option value=2014>2014</option>
		</select>
		<select name="year3" #y3 (change)="year3Change(y3.value)">
			<option value=0>--</option>
			<option value=2016>2016</option>
			<option value=2015>2015</option>
			<option value=2014>2014</option>
		</select>
		<div><canvas id="myChart" width=400 height=400></canvas></div>
		`
})

export class MaandverbruikGraphComponent implements OnInit{
	constructor(
		private maandverbruikService: MaandverbruikService,
		private metersoortenService: MetersoortenService
	){};

	myMaandverbruik1: Maandverbruik[];
	myDrawData1: number[];
	myMaandverbruik2: Maandverbruik[];
	myDrawData2: number[];
	myMaandverbruik3: Maandverbruik[];
	myDrawData3: number[];
	myMetersoorten: Metersoorten[];
	selectedMetersoortId: number = 1;
	selectedY1: number = 2016;
	selectedY2: number = 0;
	selectedY3: number = 0;

	metersoortChangeUI(id: number): void{
		this.selectedMetersoortId = id;
		this.update();
	}

	update(): void {
		Promise.all([
				this.maandverbruikService.getMaandverbruiken(this.selectedY1,this.selectedMetersoortId),
				this.maandverbruikService.getMaandverbruiken(this.selectedY2,this.selectedMetersoortId),
				this.maandverbruikService.getMaandverbruiken(this.selectedY3,this.selectedMetersoortId)
			]).then(r => this.prepareAndDraw(r));
	}

	prepareAndDraw(r: any){
		this.myMaandverbruik1 = r[0];
		this.myDrawData1 = this.maandverbruikToData(r[0]);
		this.myMaandverbruik2 = r[1];
		this.myDrawData2 = this.maandverbruikToData(r[1]);
		this.myMaandverbruik3 = r[2];
		this.myDrawData3 = this.maandverbruikToData(r[2]);
	
		this.updateAndDraw();
	}

	year1Change(id: number): void {
		this.selectedY1=id;
		this.update();
	}

	year2Change(id: number): void {
		this.selectedY2=id;
		this.update();
	}

	year3Change(id: number): void {
		this.selectedY3=id;
		this.update();
	}

	getMetersoorten(): void {
		this.metersoortenService.getMetersoorten()
			.then(ms => this.myMetersoorten=ms);
	}

	updateAndDraw():void{
		var ctx = document.getElementById('myChart');

		var datasets=[];
		if (this.myMaandverbruik1[0]){
			datasets.push({
				label: this.myMaandverbruik1[0].metersoort.metersoort + "-" + this.myMaandverbruik1[0].jaar,
				data: this.myDrawData1,
				borderWidth: 1,
				pointRadius: 5,
				pointBackgroundColor: 'rgb(10,10,10)',
				backgroundColor: 'rgba(10,10,10,0.2)'
			});
		}
		if (this.myMaandverbruik2[0]){
			datasets.push({
				label: this.myMaandverbruik2[0].metersoort.metersoort + "-" + this.myMaandverbruik2[0].jaar,
				data: this.myDrawData2,
				borderWidth: 1,
				pointRadius: 5,
				pointBackgroundColor: 'rgb(0,0,200)',
				backgroundColor: 'rgba(0,0,200,0.2)'
			});
		}
		if (this.myMaandverbruik3[0]){
			datasets.push({
				label: this.myMaandverbruik3[0].metersoort.metersoort + "-" + this.myMaandverbruik3[0].jaar,
				data: this.myDrawData3,
				borderWidth: 1,
				pointRadius: 5,
				pointBackgroundColor: 'rgb(0,200,0)',
				backgroundColor: 'rgba(0,200,0,0.2)'
			});
		}

		var myData = {
			labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
			datasets: datasets
		};

		var myOptions = {
			scales: {
				yAxes: [{
					ticks: {beginAtZero:true},
					scaleLabel: {display: true, labelString: this.myMaandverbruik1[0].metersoort.unit + "/maand"}
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
		this.metersoortChangeUI(this.selectedMetersoortId);
		this.getMetersoorten();
	}
}
