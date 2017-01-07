import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {MaandverbruikOverzicht} from './maandverbruikOverzicht';

@Injectable()
export class MaandverbruikOverzichtService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private overzichtmaandverbruikURL = 'http://localhost:8080/meterstanden/overzichtmaandverbruik';
	
	constructor(private http: Http) {}
	
	getMaandverbruiken(): Promise<MaandverbruikOverzicht[]> {
		return this.http.get(this.overzichtmaandverbruikURL)
			.toPromise()
			.then(response => response.json() as MaandverbruikOverzicht[])
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		return Promise.reject(error.message || error);
	}
}
