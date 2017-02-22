import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {MetersoortenComponent} from './metersoorten.component';
import {MeterstandenComponent} from './meterstanden.component';
import {MaandverbruikComponent} from './maandverbruik.component';
import {MaandverbruikGraphComponent} from './maandverbruikGraph.component';
import {JaarverbruikComponent} from './jaarverbruik.component';
import {JaarverbruikGraphComponent} from './jaarverbruikGraph.component';
import {DashboardComponent} from './dashboard.component';

const routes: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'metersoorten', component: MetersoortenComponent},
  {path: 'meterstanden', component: MeterstandenComponent},
  {path: 'maandverbruik', component: MaandverbruikComponent},
  {path: 'maandverbruikgraph', component: MaandverbruikGraphComponent},
  {path: 'jaarverbruik', component: JaarverbruikComponent},
  {path: 'jaarverbruikgraph', component: JaarverbruikGraphComponent},
  {path: 'dashboard', component: DashboardComponent}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {}
