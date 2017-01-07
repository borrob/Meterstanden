import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {Meterstanden} from './meterstanden';
import {MeterstandenService} from './meterstanden.service';

@Component({
	moduleId: module.id,
	selector: 'my-meterstanden',
	template: `
	<h2>Meterstanden</h2>
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
	`
})

export class MeterstandenComponent implements OnInit{
	constructor(private meterstandenService: MeterstandenService) {};
	myMeterstanden: Meterstanden[];

	getMeterstanden(): void {
		this.meterstandenService
			.getMeterstanden()
			.then(ms => this.myMeterstanden = ms);
	}

	ngOnInit(): void {
		this.getMeterstanden();
	}
}
