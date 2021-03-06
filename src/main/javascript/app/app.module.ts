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
import {JaarverbruikComponent} from './jaarverbruik.component';
import {JaarverbruikService} from './jaarverbruik.service';
import {JaarverbruikGraphComponent} from './jaarverbruikGraph.component';
import {DashboardComponent} from './dashboard.component';

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
		JaarverbruikComponent,
		MaandverbruikGraphComponent,
		JaarverbruikGraphComponent,
		DashboardComponent
	],
	providers: [
		MeterstandenService,
		MetersoortenService,
		MaandverbruikService,
		MaandverbruikOverzichtService,
		MaandverbruikJaarService,
		JaarverbruikService
	],
	bootstrap: [AppComponent]
})

export class AppModule { }
