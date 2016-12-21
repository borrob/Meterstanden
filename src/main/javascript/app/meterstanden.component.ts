import {Component} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {Meterstanden} from './meterstanden';

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
			<tr>
				<td>{{meterstanden.id}}</td>
				<td>{{meterstanden.datum}}</td>
				<td>{{meterstanden.metersoort.metersoort}}</td>
				<td>{{meterstanden.waarde}}</td>
				<td>{{meterstanden.omschrijving}}</td>
			</tr>
		</tbody>
	</table>
	`
})

export class MeterstandenComponent{
	meterstanden: Meterstanden = {
	id : 1,
	datum : '10-12-2016',
	waarde : 45.3,
	omschrijving : 'sdfds',
	metersoort : {id: 1,metersoort: 'water', unit: 'm3'}
	}
}
