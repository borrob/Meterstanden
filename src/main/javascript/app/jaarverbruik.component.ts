import {Component, OnInit} from '@angular/core';

import {Jaarverbruik} from './jaarverbruik';
import {Metersoorten} from './metersoorten';
import {JaarverbruikService} from './jaarverbruik.service';
import {MetersoortenService} from './metersoorten.service';

@Component({
	moduleId: module.id,
	selector: 'my-jaarverbruik',
	template: `
		<div class="row">
			<div class="col-xs-12">
				<h2>Jaarverbruik</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-xs=12">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>jaar</th>
							<th *ngFor="let ms of myMetersoorten">
								{{ms.metersoort}}
							</th>
						</tr>
					</thead>
					<tbody>
						<tr *ngFor="let m of overzicht">
							<td>{{m.jaar}}</td>
							<td *ngFor="let ms of m.meter">
								{{ms.formatted}}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		`
})

export class JaarverbruikComponent implements OnInit {
	constructor(
		private jaarverbruikService: JaarverbruikService,
		private metersoortenService: MetersoortenService
	) {};

	myJaarverbruikOverzicht: Jaarverbruik[];
	overzicht: Overzicht[] = [new Overzicht(null)];
	myMetersoorten: Metersoorten[];

	getJaarverbruik(): void {
		this.jaarverbruikService
			.getJaarverbruiken()
			.then(js => {
				this.myJaarverbruikOverzicht = js;
				this.procesJaar(js);
			});
	}

	procesJaar(jvo: Jaarverbruik[]): void {
		for (let r of jvo){
			let m = new Mv(r.ms, r.som, null);
			let found = false;
			for(let o of this.overzicht){
				if(o.jaar == r.jaar){
					o.updateMeter(m);
					found = true;
					break;
				}
			}
			if (!found){
				let ov = new Overzicht(r.jaar);
				for(let msoorten of this.myMetersoorten){
					let newMv = new Mv(msoorten.metersoort, null, msoorten.unit);
					ov.meter.splice(ov.meter.length,0,newMv);
				}
				ov.updateMeter(m);
				this.overzicht.splice(0,0,ov);
			}
		}
	}

	getMetersoorten(): void {
		this.metersoortenService.
			getMetersoorten()
			.then(ms => this.myMetersoorten = ms);
	}

	ngOnInit(): void {
		this.getMetersoorten();
		this.getJaarverbruik();
	}
}

export class Overzicht {
	jaar: number;
	meter: Mv[];

	constructor(j: number){
		this.jaar = j;
		this.meter = [];
	}

	updateMeter = function(m: Mv): void{
		for (let meter of this.meter){
			if(meter.metersoort == m.metersoort){
				meter.verbruik = m.verbruik;
				meter.doFormat();
			}
		}
	}
}

export class Mv {
	metersoort: string;
	verbruik: number;
	unit: string;
	formatted: string;

	constructor(m: string, v: number, u: string){
		this.metersoort = m;
		this.verbruik = v;
		this.unit = u;
	}

	doFormat = function():void{
		this.formatted = Number(parseFloat(this.verbruik).toFixed(0)).toLocaleString() + " " + this.unit + "/jaar";
	}

}
