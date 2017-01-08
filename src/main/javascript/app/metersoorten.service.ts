import {Injectable} from '@angular/core';
import {Headers, Http, URLSearchParams} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Metersoorten} from './metersoorten';

@Injectable()
export class MetersoortenService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private metersoortenURL = 'http://localhost:8080/meterstanden/metersoorten';
	
	constructor(private http: Http) {}
	
	getMetersoorten(): Promise<Metersoorten[]> {
		return this.http.get(this.metersoortenURL)
			.toPromise()
			.then(response => response.json() as Metersoorten[])
			.catch(this.handleError);
	}

	delete(id: number): Promise<any>{
		let params = new URLSearchParams()
		params.set('ms', id.toString());
		return this.http.delete(this.metersoortenURL, {search: params})
			.toPromise()
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		console.error('An error occurred', error); // for demo purposes only
		return Promise.reject(error.message || error);
	}
}
