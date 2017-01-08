import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {Meterstanden} from './meterstanden';
import {MeterstandenService} from './meterstanden.service';
import {MetersoortenService} from './metersoorten.service';

@Component({
	moduleId: module.id,
	selector: 'my-meterstanden',
	template: `
		<div class="row">
			<div class="col-xs-12">
				<h2>Meterstanden</h2>
				<select name="meterstand_metersoort_select" #ms (change)="metersoortChangeUI(ms.value)">
					<option value=0>All</option>
					<option *ngFor="let m of myMetersoorten"
						[value]="m.id">{{m.metersoort}}
					</option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<table id="meterstanden_table" class="table table-hover">
					<thead>
						<tr>
							<th>id</th>
							<th>datum</th>
							<th>metersoort</th>
							<th>stand</th>
							<th>omschrijving</th>
						</tr>
					</thead>
					<tbody>
						<tr *ngFor="let ms of myMeterstanden" id="row_{{ms.id}}">
							<td>{{ms.id}}</td>
							<td>{{ms.datum}}</td>
							<td>{{ms.metersoort.metersoort}}</td>
							<td>{{ms.waarde}}</td>
							<td>{{ms.omschrijving}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<button (click)="currentPage!=0 && goBack()" class="btn btn-primary"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span></button>
				<button (click)="myMeterstanden[19] != null && goNext()" class="btn btn-primary"><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></button>
			</div>
		</div>
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
