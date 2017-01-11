import {Injectable} from '@angular/core';
import {Headers, Http, URLSearchParams} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {Meterstanden} from './meterstanden';

@Injectable()
export class MeterstandenService {

	private headers = new Headers({'Content-Type': 'application/json'});
	private meterstandenURL = 'http://localhost:8080/meterstanden/meterstanden';
	
	constructor(private http: Http) {}
	
	getMeterstanden(ms: number, p: number): Promise<Meterstanden[]> {
		let params = new URLSearchParams();
		params.set("ms", ms.toString());
		params.set("p", p.toString());
		return this.http.get(this.meterstandenURL, {search: params})
			.toPromise()
			.then(response => response.json() as Meterstanden[])
			.catch(this.handleError);
	}

	delete(id: number): Promise<any> {
		let params = new URLSearchParams();
		params.set('ms', id.toString());
		return this.http.delete(this.meterstandenURL, {search: params})
			.toPromise()
			.catch(this.handleError);
	}

	update(data: Meterstanden): Promise<any>{
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		return this.http.post(this.meterstandenURL, data, {headers})
			.toPromise()
			.catch(this.handleError);
	}

	new(data: Meterstanden): Promise<any>{
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		return this.http.put(this.meterstandenURL, data, {headers})
			.toPromise()
			.catch(this.handleError);
	}
	
	private handleError(error: any): Promise<any> {
		return Promise.reject(error.message || error);
	}
}
