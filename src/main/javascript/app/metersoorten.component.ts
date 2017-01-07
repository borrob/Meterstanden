import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {MetersoortenService} from './metersoorten.service'

@Component({
	moduleId: module.id,
	selector: 'my-metersoorten',
	template: `
		<h2>Metersoorten</h2>
		<table>
			<thead>
				<tr>
					<td>id</td>
					<td>metersoort</td>
					<td>unit</td>
				</tr>
			</thead>
			<tbody>
				<tr *ngFor="let ms of myMetersoorten">
					<td>{{ms.id}}</td>
					<td>{{ms.metersoort}}</td>
					<td>{{ms.unit}}</td>
				</tr>
			</tbody>
		</table>
	`
})

export class MetersoortenComponent implements OnInit{
	constructor(private metersoortenService: MetersoortenService) {};
	myMetersoorten: Metersoorten[];

	getMetersoorten(): void {
		this.metersoortenService
			.getMetersoorten()
			.then(ms => this.myMetersoorten=ms);
	}

	ngOnInit(): void {
		this.getMetersoorten();
	}
}
