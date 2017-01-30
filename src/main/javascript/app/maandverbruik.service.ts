import {Injectable} from '@angular/core';
import {Headers, Http, URLSearchParams} from '@angular/http';
import {Settings} from './settings';

import 'rxjs/add/operator/toPromise';

import {Maandverbruik} from './maandverbruik';

@Injectable()
export class MaandverbruikService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private maandverbruikURL:string = Settings.maandverbruikURL;
	
	constructor(private http: Http) {}
	
	getMaandverbruiken(y: number, ms: number): Promise<Maandverbruik[]> {
		let params = new URLSearchParams();
		params.set('year', y.toString());
		params.set('ms', ms.toString());

		return this.http.get(this.maandverbruikURL, {search: params})
			.toPromise()
			.then(response => response.json() as Maandverbruik[])
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		return Promise.reject(error.message || error);
	}
}
