import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Meterstanden} from './meterstanden';

@Injectable()
export class MeterstandenService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private meterstandenURL = 'http://localhost:8080/meterstanden/meterstanden';
	
	constructor(private http: Http) {}
	
	getMeterstanden(): Promise<Meterstanden[]> {
		console.log("getting Meterstanden");
		return this.http.get(this.meterstandenURL)
			.toPromise()
			.then(response => response.json() as Meterstanden[])
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		console.error('An error occurred', error); // for demo purposes only
		return Promise.reject(error.message || error);
	}
}
