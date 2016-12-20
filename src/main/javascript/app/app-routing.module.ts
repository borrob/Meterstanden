import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {MetersoortenComponent} from './metersoorten.component';
import {MeterstandenComponent} from './meterstanden.component';
import {MaandverbruikComponent} from './maandverbruik.component';

const routes: Routes = [
  {path: '', redirectTo: '/meterstanden', pathMatch: 'full'},
  {path: 'metersoorten', component: MetersoortenComponent},
  {path: 'meterstanden', component: MeterstandenComponent},
  {path: 'maandverbruik', component: MaandverbruikComponent}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {}
