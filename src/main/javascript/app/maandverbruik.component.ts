import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {MaandverbruikOverzicht} from './maandverbruikOverzicht';
import {MaandverbruikOverzichtService} from './maandverbruikOverzicht.service';
import {MetersoortenService} from './metersoorten.service';

@Component({
	moduleId: module.id,
	selector: 'my-maandverbruik',
	template: `
	<h2>Maandverbruik</h2>
	<table>
		<thead>
			<tr>
				<td>jaar</td>
				<td>maand</td>
				<td *ngFor="let ms of myMetersoorten">
					{{ms.metersoort}}
				</td>
			</tr>
		</thead>
		<tbody>
			<tr *ngFor="let m of myMaandverbruikOverzicht">
				<td>{{m.jaar}}</td>
				<td>{{m.maand}}</td>
				<td *ngFor="let ms of m.mv">
					{{ms.verbruik | number: '1.1-1'}} {{ms.metersoort.unit}}/maand
				</td>
			</tr>
		</tbody>
	</table>
	`
})

export class MaandverbruikComponent implements OnInit {
	constructor(
		private maandverbruikOverzichtService: MaandverbruikOverzichtService,
		private metersoortenService: MetersoortenService
	) {};

	myMaandverbruikOverzicht: MaandverbruikOverzicht[];
	myMetersoorten: Metersoorten[];

	getMaandverbruik(): void {
		this.maandverbruikOverzichtService.
			getMaandverbruiken()
			.then(ms => this.myMaandverbruikOverzicht = ms);
	}

	getMetersoorten(): void {
		this.metersoortenService.
			getMetersoorten()
			.then(ms => this.myMetersoorten = ms);
	}

	ngOnInit(): void {
		this.getMetersoorten();
		this.getMaandverbruik();
	}
}