import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {HttpModule} from '@angular/http';

import {AppComponent}  from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {MetersoortenComponent} from './metersoorten.component';
import {MeterstandenComponent} from './meterstanden.component';
import {MaandverbruikComponent} from './maandverbruik.component';
import {MaandverbruikGraphComponent} from './maandverbruikGraph.component';
import {MeterstandenService} from './meterstanden.service';
import {MetersoortenService} from './metersoorten.service';
import {MaandverbruikService} from './maandverbruik.service';
import {MaandverbruikOverzichtService} from './maandverbruikOverzicht.service';
import {MaandverbruikJaarService} from './maandverbruikJaar.service';

@NgModule({
	imports: [
		BrowserModule,
		FormsModule,
		HttpModule,
		AppRoutingModule
	],
	declarations: [
		AppComponent,
		MeterstandenComponent,
		MetersoortenComponent,
		MaandverbruikComponent,
		MaandverbruikGraphComponent
	],
	providers: [
		MeterstandenService,
		MetersoortenService,
		MaandverbruikService,
		MaandverbruikOverzichtService,
		MaandverbruikJaarService
	],
	bootstrap: [AppComponent]
})

export class AppModule { }
