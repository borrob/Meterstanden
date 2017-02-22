import {Component, OnInit} from '@angular/core';

@Component({
	moduleId: module.id,
	selector: 'my-dash',
	template: `
		<style>
			.dash {
				border: solid blue 0.1em;
				color: blue;
				font-weight: 200;
				font-size: larger;
				padding-left: 1em;
				padding-right: 1em;
				padding-top: 2em;
				padding-bottom: 2em;
				margin: 0.5em;
				float: left;
				background-color: #d8d7d7;
			}
		</style>
		<h1>{{title}}</h1>
		<nav>
			<a class='dash' routerLink="/metersoorten">Metersoorten</a>
			<a class='dash' routerLink="/meterstanden">Meterstanden</a>
			<a class='dash' routerLink="/maandverbruik">Maandverbruik</a>
			<a class='dash' routerLink="/jaarverbruik">Jaarverbruik</a>
			<a class='dash' routerLink="/maandverbruikgraph">Grafiek Maandverbruik</a>
			<a class='dash' routerLink="/jaarverbruikgraph">Grafiek Jaarverbruik</a>
		</nav>
		`
})

export class DashboardComponent implements OnInit {
	title = "meterstanden";

	ngOnInit(): void {
		document.getElementById('maindiv').style.display='none';

		var links = document.getElementsByClassName('dash');
		var len = links.length;
		while(len--){
			var el = <HTMLElement>links[len];
			el.onclick=function(){
				document.getElementById('maindiv').style.display='inline';
			};
		}
	}
}
