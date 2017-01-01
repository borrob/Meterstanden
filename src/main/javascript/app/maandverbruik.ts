import {Metersoorten} from './metersoorten'

export class Maandverbruik {
	id: number;
	jaar: number;
	maand: number;
	metersoort: Metersoorten;
	verbruik: number;
}
