import {Component, OnInit} from '@angular/core';

import {Maandverbruik} from './maandverbruik';
import {MaandverbruikService} from './maandverbruik.service';
import {Metersoorten} from './metersoorten';
import {MetersoortenService} from './metersoorten.service';
import {MaandverbruikJaar} from './maandverbruikjaar';
import {MaandverbruikJaarService} from './maandverbruikJaar.service';

import {Settings} from './settings';

declare var Chart: any;
import '../javascript_libs/Chart.min.js';

@Component({
	moduleId: module.id,
	selector: 'my-maandverbruikgraph',
	template: `
			<div class="row">
				<div class="col-xs-12">
					<h2>Graph maandverbruik</h2>
					<select name="metersoort" #ms (change)="metersoortChangeUI(ms.value)">
						<option *ngFor="let m of myMetersoorten"
							[value]="m.id"
							>{{m.metersoort}}
						</option>
					</select>
					<select name="year1" #y1 (change)="year1Change(y1.value)">
						<option *ngFor="let myY of myYears"
							[value]="myY.jaar">{{myY.jaar}}
						</option>
					</select>
					<select name="year2" #y2 (change)="year2Change(y2.value)">
						<option value=0>--</option>
						<option *ngFor="let myY of myYears"
							[value]="myY.jaar">{{myY.jaar}}
						</option>
					</select>
					<select name="year3" #y3 (change)="year3Change(y3.value)">
						<option value=0>--</option>
						<option *ngFor="let myY of myYears"
							[value]="myY.jaar">{{myY.jaar}}
						</option>
					</select>
					<input #c (change)="averageCheckboxChange(c.checked)"
						type="checkbox"
						name="averageview"
						id="averageviewId"
						checked />
					<label for="averageview">Show average</label>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12" id="chartParrent">
					<canvas id="myChart" width="200px" height="100px"></canvas>
				</div>
			</div>
		`
})

export class MaandverbruikGraphComponent implements OnInit{
	constructor(
		private maandverbruikService: MaandverbruikService,
		private metersoortenService: MetersoortenService,
		private maandverbruikjaarService: MaandverbruikJaarService
	){};

	myMaandverbruik1: Maandverbruik[];
	myDrawData1: number[];
	myMaandverbruik2: Maandverbruik[];
	myDrawData2: number[];
	myMaandverbruik3: Maandverbruik[];
	myDrawData3: number[];
	myMetersoorten: Metersoorten[];
	myAverageData: number[];
	selectedMetersoortId: number = 1;
	selectedY1: number = 2017;
	selectedY2: number = 0;
	selectedY3: number = 0;
	showAverage: boolean = true;
	myYears: MaandverbruikJaar[];
	myXdata: string[];
	myData: any;
	myChart: any;
	myOptions: any;

	metersoortChangeUI(id: number): void{
		this.selectedMetersoortId = id;
		this.update();
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

	averageCheckboxChange(c: boolean): void {
		this.showAverage = c;
		this.draw();
	}

	update(): void {
		Promise.all([
				this.maandverbruikService.getMaandverbruiken(this.selectedY1,this.selectedMetersoortId),
				this.maandverbruikService.getMaandverbruiken(this.selectedY2,this.selectedMetersoortId),
				this.maandverbruikService.getMaandverbruiken(this.selectedY3,this.selectedMetersoortId)
			]).then(r => this.prepareAndDraw(r));
	}

	/*
	 * Convert the responses of the getMaandverbruiken requests and convert them
	 * into the drawformat that Chart uses.
	 *
	 * r: any - array with the responses of the getMaandverbruiken
	*/
	prepareAndDraw(r: any){
		this.myMaandverbruik1 = r[0];
		this.myDrawData1 = this.maandverbruikToData(r[0]);
		this.myMaandverbruik2 = r[1];
		this.myDrawData2 = this.maandverbruikToData(r[1]);
		this.myMaandverbruik3 = r[2];
		this.myDrawData3 = this.maandverbruikToData(r[2]);

		this.myAverageData = this.obtainAverage(r);

		this.draw();
	}

	draw(): void{
		let datasets=[];
		
		if (this.showAverage){
			datasets.push({
				label: 'Average',
				data: this.myAverageData,
				borderWidth: 2,
				pointRadius: 3,
				pointBackgroundColor: 'red',
				pointBorderColor: 'red',
				borderColor: 'red',
				fill: false
			})
		}
		if (this.myMaandverbruik1[0]){
			datasets.push({
				label: this.myMaandverbruik1[0].metersoort.metersoort + "-" + this.myMaandverbruik1[0].jaar,
				data: this.myDrawData1,
				borderWidth: 1,
				pointRadius: 5,
				pointBackgroundColor: Settings.color1,
				backgroundColor: Settings.color1transparent,
				pointBorderColor: Settings.color1
			});
		}
		if (this.myMaandverbruik2[0]){
			datasets.push({
				label: this.myMaandverbruik2[0].metersoort.metersoort + "-" + this.myMaandverbruik2[0].jaar,
				data: this.myDrawData2,
				borderWidth: 1,
				pointRadius: 5,
				pointBackgroundColor: Settings.color2,
				backgroundColor: Settings.color2transparent,
				pointBorderColor: Settings.color2
			});
		}
		if (this.myMaandverbruik3[0]){
			datasets.push({
				label: this.myMaandverbruik3[0].metersoort.metersoort + "-" + this.myMaandverbruik3[0].jaar,
				data: this.myDrawData3,
				borderWidth: 1,
				pointRadius: 5,
				pointBackgroundColor: Settings.color3,
				backgroundColor: Settings.color3transparent,
				pointBorderColor: Settings.color3
			});
		}

		this.myData = {
			labels: this.myXdata,
			datasets: datasets
		};

		this.myOptions = {
			scales: {
				yAxes: [{
					ticks: {beginAtZero:true},
					scaleLabel: {display: true, labelString: this.myMaandverbruik1[0].metersoort.unit + "/maand"}
				}]
			}
		};

		//draw chart
		this.myChart.config.data = this.myData;
		this.myChart.config.options = this.myOptions;
		this.myChart.update();
	}

	maandverbruikToData(mv:Maandverbruik[]):number[]{
		let out = Array.apply(null, Array(12)).map(function () {});
		for (let m in mv){
			if(mv[m]){
				out.splice(mv[m].maand-1, 0, mv[m].verbruik);
			} else {
				out.push(null);
			}
		}
		return out;
	}

	obtainAverage(mv:any):number[]{
		let out = Array.apply(null, Array(12)).map(function () {});
		for (let m in mv){
			for (let a in mv[m]){
				if (mv[m][a].maand !== null){
					if (out[mv[m][a].maand -1] == null){
						out[mv[m][a].maand-1] = mv[m][a].average;
					}
				}
			}
		}
		return out;
	}

	getMetersoorten(): void {
		this.metersoortenService.getMetersoorten()
			.then(ms => this.myMetersoorten=ms);
	}

	getJaren():void {
		this.maandverbruikjaarService.getMaandverbruikjaren()
			.then(m => this.myYears=m);
			//.then(m => console.log(m));
	}

	ngOnInit(): void {
		this.metersoortChangeUI(this.selectedMetersoortId);
		this.getJaren();
		this.getMetersoorten();
		this.myXdata = ['a', 'b'];
		this.myDrawData1 = [0,0];

		let datasets = [];
		datasets.push({
			label: 'temp',
			data: this.myDrawData1
		});

		this.myData = {
			labels: this.myXdata,
			datasets: datasets
		};

		this.myOptions = {
			scales: {
				yAxes: [{
					ticks: {beginAtZero:true},
					scaleLabel: {display: true, labelString: "/maand"}
				}]
			}
		};

		//draw chart
		var ctx = document.getElementById('myChart');
		this.myChart = new Chart(ctx, {
			type: 'line',
			data: this.myData,
			options: this.myOptions
			}
		);
		this.myXdata = Settings.graphXlabel;
		this.metersoortChangeUI(this.selectedMetersoortId);
	}
}
