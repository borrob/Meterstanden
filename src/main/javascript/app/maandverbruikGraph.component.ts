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
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
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
	selectedMetersoortId: number = 1;
	selectedY1: number = 2016;
	selectedY2: number = 0;
	selectedY3: number = 0;
	myYears: MaandverbruikJaar[];

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

		this.draw();
	}

	draw(): void{
		var ctx = document.getElementById('myChart');

		var datasets=[];
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

		var myData = {
			labels: Settings.graphXlabel,
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
	}
}
