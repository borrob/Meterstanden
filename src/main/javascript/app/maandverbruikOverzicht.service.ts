import {Injectable} from '@angular/core';
import {Headers, Http, URLSearchParams} from '@angular/http';
import {Settings} from './settings';

import 'rxjs/add/operator/toPromise';

import {MaandverbruikOverzicht} from './maandverbruikOverzicht';

@Injectable()
export class MaandverbruikOverzichtService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private overzichtmaandverbruikURL = Settings.overzichtmaandverbruikURL;
	
	constructor(private http: Http) {}
	
	getMaandverbruiken(p: number): Promise<MaandverbruikOverzicht[]> {
		let params = new URLSearchParams();
		params.set("p", p.toString());
		return this.http.get(this.overzichtmaandverbruikURL, {search: params})
			.toPromise()
			.then(response => response.json() as MaandverbruikOverzicht[])
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		return Promise.reject(error.message || error);
	}
}
