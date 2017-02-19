import {Component, OnInit} from '@angular/core';

import {Jaarverbruik} from './jaarverbruik';
import {JaarverbruikService} from './jaarverbruik.service';
import {Metersoorten} from './metersoorten';
import {MetersoortenService} from './metersoorten.service';

import {Settings} from './settings';

declare var Chart: any;
import '../javascript_libs/Chart.min.js';

@Component({
	moduleId: module.id,
	selector: 'my-jaarverbruikgraph',
	template: `
			<div class="row">
				<div class="col-xs-12">
					<h2>Graph jaaroverzicht</h2>
					<select name="metersoort" #ms (change)="metersoortChangeUI(ms.value)">
						<option *ngFor="let m of myMetersoorten"
							[value]="m.id"
							>{{m.metersoort}}
						</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12" id="chartParrent">
					<canvas id="myChart" width="200px" height="100px"></canvas>
				</div>
			</div>
		`
})

export class JaarverbruikGraphComponent implements OnInit{
	constructor(
		private jaarverbruikService: JaarverbruikService,
		private metersoortenService: MetersoortenService
	){};

	myDrawData: number[];
	myXdata: number[];
	myMetersoorten: Metersoorten[];
	selectedMetersoortId: number = 1;
	myChart: any;
	myOptions: any;
	myData: any;

	metersoortChangeUI(id: number): void{
		this.selectedMetersoortId = id;
		this.myDrawData = [];
		this.myXdata = [];
		this.jaarverbruikService.getJaarverbruiken()
			.then(r => {
				for (let m of r){
					if(m.id ==this.selectedMetersoortId){
						this.update(m);
					}
				}
			}
		);
	}

	update(jo: Jaarverbruik): void {
		this.myDrawData.push(jo.som);
		this.myXdata.push(jo.jaar);
		this.draw();
	}

	draw(): void{
		var datasets=[];
		datasets.push({
			data: this.myDrawData
		});

		this.myData = {
			labels: this.myXdata,
			datasets: datasets
		};

		this.myOptions = {
			legend: {
				display: false
			},
			scales: {
				yAxes: [{
					ticks: {beginAtZero:true},
					scaleLabel: {display: true, labelString:  "/jaar"}
				}]
			}
		};
	
		this.myChart.config.data = this.myData;
		this.myChart.config.options = this.myOptions;
		this.myChart.update();

	}

	getMetersoorten(): void {
		this.metersoortenService.getMetersoorten()
			.then(ms => this.myMetersoorten=ms);
	}

	ngOnInit(): void {
		this.getMetersoorten();
		this.myDrawData = [0, 0];
		this.myXdata = [0, 0];

		let datasets=[];
		datasets.push({
			//label: this.myMaandverbruik1[0].metersoort.metersoort + "-" + this.myMaandverbruik1[0].jaar,
			label: 'woot',
			data: this.myDrawData
		});

		this.myData = {
			labels: this.myXdata,
			datasets: datasets
		};

		this.myOptions = {
			legend: {
				display: false
			},
			scales: {
				yAxes: [{
					ticks: {beginAtZero:true},
					scaleLabel: {display: true, labelString:  "/jaar"}
				}]
			}
		};
		//draw chart
		var ctx = document.getElementById('myChart');
		this.myChart = new Chart(ctx, {
			type: 'bar',
			data: this.myData,
			options: this.myOptions
			}
		);
		this.metersoortChangeUI(this.selectedMetersoortId);

	}
}
