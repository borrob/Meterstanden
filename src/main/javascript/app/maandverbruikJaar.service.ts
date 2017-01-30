import {Injectable} from '@angular/core';
import {Headers, Http, URLSearchParams} from '@angular/http';

import {MaandverbruikJaar} from './maandverbruikJaar';
import {Settings} from './settings';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class MaandverbruikJaarService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private maandverbruikURL = Settings.maandverbruikUtilURL;
	constructor(private http: Http) {}
	
	getMaandverbruikjaren(): Promise<MaandverbruikJaar[]> {
		let params = new URLSearchParams();
		params.set('q', "maandverbruikjaar");

		return this.http.get(this.maandverbruikURL, {search: params})
			.toPromise()
			.then(response => response.json() as MaandverbruikJaar[])
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		return Promise.reject(error.message || error);
	}
}
