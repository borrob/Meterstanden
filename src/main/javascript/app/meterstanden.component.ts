import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {Meterstanden} from './meterstanden';
import {MeterstandenService} from './meterstanden.service';
import {MetersoortenService} from './metersoorten.service';

@Component({
	moduleId: module.id,
	selector: 'my-meterstanden',
	template: `
	<h2>Meterstanden</h2>
	<select name="meterstand_metersoort_select" #ms (change)="metersoortChangeUI(ms.value)">
		<option value=0>All</option>
		<option *ngFor="let m of myMetersoorten"
			[value]="m.id">{{m.metersoort}}
		</option>
	</select>
	<table>
		<thead>
			<tr>
				<td>id</td>
				<td>datum</td>
				<td>metersoort</td>
				<td>stand</td>
				<td>omschrijving</td>
			</tr>
		</thead>
		<tbody>
			<tr *ngFor="let ms of myMeterstanden">
				<td>{{ms.id}}</td>
				<td>{{ms.datum}}</td>
				<td>{{ms.metersoort.metersoort}}</td>
				<td>{{ms.waarde}}</td>
				<td>{{ms.omschrijving}}</td>
			</tr>
		</tbody>
	</table>
	<button (click)="currentPage!=0 && goBack()"><</button>
	<button (click)="myMeterstanden[19] != null && goNext()">></button>
	`
})

export class MeterstandenComponent implements OnInit{
	constructor(
		private meterstandenService: MeterstandenService,
		private metersoortenService: MetersoortenService
	) {};
	myMeterstanden: Meterstanden[];
	myMetersoorten: Metersoorten[];
	selectedMetersoort: number = 0;
	currentPage: number = 0;

	metersoortChangeUI(ms: number): void {
		this.currentPage=0;
		this.selectedMetersoort=ms;
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
	}

	goBack(): void {
		this.currentPage--;
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
	}

	goNext(): void {
		this.currentPage++;
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
	}

	getMeterstanden(ms: number, p: number): void {
		this.meterstandenService
			.getMeterstanden(ms, p)
			.then(mst => this.myMeterstanden = mst);
	}

	getMetersoorten(): void {
		this.metersoortenService
			.getMetersoorten()
			.then(ms => this.myMetersoorten = ms);
	}

	ngOnInit(): void {
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
		this.getMetersoorten();
	}
}
