import {Component, OnInit} from '@angular/core';
//TODO: omschrijvingveld opnemen

import {Metersoorten} from './metersoorten';
import {Meterstanden} from './meterstanden';
import {MeterstandenService} from './meterstanden.service';
import {MetersoortenService} from './metersoorten.service';

declare var $: any;
import '../javascript_libs/jquery-3.1.1.min.js';

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
				<button (click)="newMeterstand()" class="btn btn-primary">new meterstand</button>
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
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr *ngFor="let ms of myMeterstanden" id="row_{{ms.id}}">
							<td>{{ms.id}}</td>
							<td>{{ms.datum}}</td>
							<td>{{ms.metersoort.metersoort}}</td>
							<td>{{ms.waarde}}</td>
							<td>{{ms.omschrijving}}</td>
							<td>
								<button (click)="deleteMeterstand(ms.id)"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
								<button (click)="updateMeterstand(ms.id)"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
							</td>
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
		<!-- Delete Meterstand Modal -->
		<div class="modal fade" id="deleteMeterstandModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="deleteModalLabel">Delete Meterstand</h4>
					</div>
					<div class="modal-body">
						<p>Are you sure you want to delete the Meterstand: {{selectedMeterstand.metersoort.metersoort}} - {{selectedMeterstand.waarde}}?</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" (click)="reallyDelete(selectedMeterstand.id)">Save changes</button>
					</div>
				</div>
			</div>
		</div>

		<!-- Update Meterstand  Modal -->
		<div class="modal fade" id="updateMeterstandtModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="updateModalLabel">Change Meterstand</h4>
					</div>
					<div class="modal-body">
						<p>Change the meterstand:?</p>
						<form id="updateMetersoortForm" class="form form-horizontal">
							<div class="form-group">
								<label for="updateIDfield" class="col-sm-2 control-label">id</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" id="updateIDfield" value="{{selectedMeterstand.id}}" disabled/>
								</div>
							</div>
							<div class="form-group">
								<label for="updateDatumfield" class="col-sm-2 control-label">Date</label>
								<div class="col-sm-10">
									<input type="date" class="form-control" id="updateDatefield"/>
								</div>
							</div>
							<div class="form-group">
								<label for="updateMetersoortfield" class="col-sm-2 control-label">metersoort</label>
								<div class="col-sm-10">
									<select id="updateMetersoortfield" >
										<option *ngFor="let m of myMetersoorten"
											[value]="m.id">{{m.metersoort}}
										</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="updateOmschrijvingfield" class="col-sm-2 control-label">Description</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="updateOmschrijvingfield" placeholder="description (optional)"/>
								</div>
							</div>
							<div class="form-group">
								<label for="updateUnitfield" class="col-sm-2 control-label">waarde</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="updateWaardefield" value="{{selectedMeterstand.waarde}}"/>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" (click)="reallyUpdate()">Save changes</button>
					</div>
				</div>
			</div>
		</div>

		<!-- New Meterstand Modal -->
		<div class="modal fade" id="newMeterstandModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="newModalLabel">New Meterstand</h4>
					</div>
					<div class="modal-body">
						<p>Add a meterstand</p>
						<form id="newMeterstandForm" class="form form-horizontal">
							<div class="form-group">
								<label for="newDatumfield" class="col-sm-2 control-label">Date</label>
								<div class="col-sm-10">
									<input type="date" class="form-control" id="newDatefield"/>
								</div>
							</div>
							<div class="form-group">
								<label for="newMetersoortfield" class="col-sm-2 control-label">metersoort</label>
								<div class="col-sm-10">
									<select id="newMetersoortfield" >
										<option *ngFor="let m of myMetersoorten"
											[value]="m.id">{{m.metersoort}}
										</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="newOmschrijvingfield" class="col-sm-2 control-label">Description</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="newOmschrijvingfield" placeholder="description (optional)"/>
								</div>
							</div>
							<div class="form-group">
								<label for="newWaardefield" class="col-sm-2 control-label">Waarde</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" id="newWaardefield"/>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" (click)="reallyNew()">Save changes</button>
					</div>
				</div>
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
	selectedMeterstand: Meterstanden = new Meterstanden();
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

	deleteMeterstand(id: number): void {
		for (var i in this.myMeterstanden){
			if (id === this.myMeterstanden[i].id){
				this.selectedMeterstand = this.myMeterstanden[i];
			}
		}
		$('#deleteMeterstandModal').modal();
	}

	reallyDelete(id: number): void {
		this.meterstandenService
			.delete(id)
			.then(r => this.succesDelete());
	}

	succesDelete(): void {
		$('body').prepend('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> The meterstand is deleted.</div>');
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
	}

	updateMeterstand(id: number): void {
		for (var i in this.myMeterstanden){
			if (id === this.myMeterstanden[i].id){
				this.selectedMeterstand = this.myMeterstanden[i];
			}
		}
		$('#updateDatefield').val(new Date(this.selectedMeterstand.datum).toISOString().substr(0,10));
		$('#updateMeterstandtModal').modal();
	}

	reallyUpdate(): void {
		var m = new Meterstanden();
		m.id=$('#updateIDfield')[0].value;
		m.metersoort = new Metersoorten();
		m.metersoort.id = $('#updateMetersoortfield')[0].value;
		m.datum = $('#updateDatefield')[0].value;
		m.omschrijving = $('#updateOmschrijvingfield')[0].value;
		m.waarde = $('#updateWaardefield')[0].value;
		this.meterstandenService.update(m)
			.then(r => this.succesUpdate());
	}

	succesUpdate(): void {
		$('body').prepend('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> The meterstand is changed.</div>');
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
	}

	newMeterstand(): void {
		$('#newDatefield').val(new Date().toISOString().substr(0,10));
		$('#newMeterstandModal').modal();
	}

	reallyNew(): void {
		var m = new Meterstanden();
		m.metersoort = new Metersoorten();
		m.metersoort.id = $('#newMetersoortfield')[0].value;
		m.datum = $('#newDatefield')[0].value;
		m.waarde = $('#newWaardefield')[0].value;
		m.omschrijving = $('#newOmschrijvingfield')[0].value;
		this.meterstandenService.new(m)
			.then(r => this.succesNew());
	}

	succesNew(): void {
		$('body').prepend('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> The meterstand is added.</div>');
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
	}
	ngOnInit(): void {
		this.selectedMeterstand.metersoort = new Metersoorten();
		this.getMeterstanden(this.selectedMetersoort, this.currentPage);
		this.getMetersoorten();
	}
}
