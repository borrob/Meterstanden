import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {MaandverbruikOverzicht} from './maandverbruikOverzicht';
import {MaandverbruikOverzichtService} from './maandverbruikOverzicht.service';
import {MetersoortenService} from './metersoorten.service';

@Component({
	moduleId: module.id,
	selector: 'my-maandverbruik',
	template: `
		<div class="row">
			<div class="col-xs-12">
				<h2>Maandverbruik</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-xs=12">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>jaar</th>
							<th>maand</th>
							<th *ngFor="let ms of myMetersoorten">
								{{ms.metersoort}}
							</th>
						</tr>
					</thead>
					<tbody>
						<tr *ngFor="let m of myMaandverbruikOverzicht">
							<td>{{m.jaar}}</td>
							<td>{{m.maand}}</td>
							<td *ngFor="let ms of m.mv">
								<span *ngIf="ms">{{ms.verbruik | number: '1.1-1'}} {{ms.metersoort.unit}}/maand</span>
								<span *ngIf!="ms">--</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<button (click)="currentPage!=0 && goBack()" class="btn btn-primary"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span></button>
				<button (click)="myMaandverbruikOverzicht[19] != null && goNext()" class="btn btn-primary"><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></button>
			</div>
		</div>
		`
})

export class MaandverbruikComponent implements OnInit {
	constructor(
		private maandverbruikOverzichtService: MaandverbruikOverzichtService,
		private metersoortenService: MetersoortenService
	) {};

	myMaandverbruikOverzicht: MaandverbruikOverzicht[];
	myMetersoorten: Metersoorten[];
	currentPage: number=0;

	goBack(): void {
		this.currentPage--;
		this.getMaandverbruik(this.currentPage);
	}

	goNext(): void {
		this.currentPage++;
		this.getMaandverbruik(this.currentPage);
	}

	getMaandverbruik(p: number): void {
		this.maandverbruikOverzichtService.
			getMaandverbruiken(p)
			.then(ms => this.myMaandverbruikOverzicht = ms);
	}

	getMetersoorten(): void {
		this.metersoortenService.
			getMetersoorten()
			.then(ms => this.myMetersoorten = ms);
	}

	ngOnInit(): void {
		this.getMetersoorten();
		this.getMaandverbruik(this.currentPage);
	}
}
