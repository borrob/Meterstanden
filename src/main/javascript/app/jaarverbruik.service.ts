import {Injectable} from '@angular/core';
import {Headers, Http, URLSearchParams} from '@angular/http';
import {Settings} from './settings';

import 'rxjs/add/operator/toPromise';

import {Jaarverbruik} from './jaarverbruik';

@Injectable()
export class JaarverbruikService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private jaarverbruikURL:string = Settings.jaarverbruikURL;
	
	constructor(private http: Http) {}
	
	getJaarverbruiken(): Promise<Jaarverbruik[]> {
		return this.http.get(this.jaarverbruikURL)
			.toPromise()
			.then(response => response.json() as Jaarverbruik[])
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		return Promise.reject(error.message || error);
	}
}
