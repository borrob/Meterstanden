import {Component, OnInit} from '@angular/core';

import {Metersoorten} from './metersoorten';
import {MetersoortenService} from './metersoorten.service'

declare var $: any;
import '../javascript_libs/jquery-3.1.1.min.js';

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
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr *ngFor="let ms of myMetersoorten">
							<td>{{ms.id}}</td>
							<td>{{ms.metersoort}}</td>
							<td>{{ms.unit}}</td>
							<td>
								<button (click)="delete(ms.id)"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
								<button (click)="updateMetersoort(ms.id)"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<!-- Delete Meterstand Modal -->
		<div class="modal fade" id="deleteMetersoortModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">Delete Metersoort</h4>
					</div>
					<div class="modal-body">
						<p>Are you sure you want to delete the metersoort: {{selectedMetersoort.metersoort}} ?</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" (click)="reallyDelete(selectedMetersoort.id)">Save changes</button>
					</div>
				</div>
			</div>
		</div>

		<!-- Update Meterstand Modal -->
		<div class="modal fade" id="updateMetersoortModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">Change Meterstoort</h4>
					</div>
					<div class="modal-body">
						<p>Change the metersoort: {{selectedMetersoort.metersoort}} ?</p>
						<form id="updateMetersoortForm" class="form form-horizontal">
							<div class="form-group">
								<label for="updateIDfield" class="col-sm-2 control-label">id</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" id="updateIDfield" value="{{selectedMetersoort.id}}" disabled/>
								</div>
							</div>
							<div class="form-group">
								<label for="updateMetersoortfield" class="col-sm-2 control-label">metersoort</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="updateMetersoortfield" value="{{selectedMetersoort.metersoort}}"/>
								</div>
							</div>
							<div class="form-group">
								<label for="updateUnitfield" class="col-sm-2 control-label">unit</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="updateUnitfield" value="{{selectedMetersoort.unit}}"/>
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
	`
})

export class MetersoortenComponent implements OnInit{
	constructor(private metersoortenService: MetersoortenService) {};
	myMetersoorten: Metersoorten[];
	selectedMetersoort: Metersoorten = new Metersoorten();

	delete(id: number): void{
		for (var i in this.myMetersoorten){
			if (id == this.myMetersoorten[i].id){
				this.selectedMetersoort = this.myMetersoorten[i];
			}
		}
		$('#deleteMetersoortModal').modal();
	}

	reallyDelete(id: number): void {
		this.metersoortenService
			.delete(id)
			.then(r => this.succesDelete());
	}

	updateMetersoort(id: number): void {
		for (var i in this.myMetersoorten){
			if (id == this.myMetersoorten[i].id){
				this.selectedMetersoort = this.myMetersoorten[i];
			}
		}
		$('#updateMetersoortModal').modal();
	}

	reallyUpdate(): void {
		var m = new Metersoorten();
		m.id=$('#updateIDfield')[0].value;
		m.metersoort=$('#updateMetersoortfield')[0].value;
		m.unit=$('#updateUnitfield')[0].value;
		this.metersoortenService.update(m)
			.then(r => this.succuesUpdate());
	}

	succuesUpdate(): void {
		$('body').prepend('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> The metersoort is changed.</div>');
		this.getMetersoorten();
	}

	succesDelete(): void {
		$('body').prepend('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> The metersoort is deleted.</div>');
		this.getMetersoorten();
	}

	getMetersoorten(): void {
		this.metersoortenService
			.getMetersoorten()
			.then(ms => this.myMetersoorten=ms);
	}

	ngOnInit(): void {
		this.getMetersoorten();
	}
}
