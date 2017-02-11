import {Component} from '@angular/core';

@Component({
	moduleId: module.id,
	selector: 'my-app',
	template: `
		<h1>{{title}}</h1>
		<nav>
			<a routerLink="/metersoorten">Metersoorten</a>
			<a routerLink="/meterstanden">Meterstanden</a>
			<a routerLink="/maandverbruik">Maandverbruik</a>
			<a routerLink="/jaarverbruik">Jaarverbruik</a>
			<a routerLink="/maandverbruikgraph">MaandverbruikGraph</a>
		</nav>
		<router-outlet></router-outlet>
		`
})

export class AppComponent{
	title = "meterstanden";
}
