import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {MetersoortenService} from './metersoorten.service'

@Component({
	moduleId: module.id,
	selector: 'my-metersoorten',
	template: `
		<div class="row">
			<div class="col-xs-12">
				<h2>Metersoorten</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>id</th>
							<th>metersoort</th>
							<th>unit</th>
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
			</div>
		</div>
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
