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
import {MeterstandenService} from './meterstanden.service';
import {MetersoortenService} from './metersoorten.service';
import {MaandverbruikOverzichtService} from './maandverbruikOverzicht.service';

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
		MaandverbruikComponent
	],
	providers: [
		MeterstandenService,
		MetersoortenService,
		MaandverbruikOverzichtService
	],
	bootstrap: [AppComponent]
})

export class AppModule { }
