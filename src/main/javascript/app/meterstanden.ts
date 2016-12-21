import {Metersoorten} from './metersoorten'

export class Meterstanden {
	id: number;
	datum: string; //TODO: change to date
	waarde: number;
	omschrijving: string;
	metersoort: Metersoorten;
}
