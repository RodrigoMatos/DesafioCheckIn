import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ErroPageComponent } from './error-page/erro-page.component';
import { PagesComponent } from './pages.component';

const routes: Routes = [
    {
        path: '',
        component: PagesComponent,
        children: [
            {
                path: '',
                redirectTo: 'checkin',
                pathMatch: 'full'
            },
            {
                path: 'checkin',
                loadChildren: './components/checkin/checkin.module#CheckInModule'
            },
            {
                path: 'checkin/cadastrar',
                loadChildren: './components/checkin/checkin.module#CheckInModule'
            },
            {
                path: 'hospede',
                loadChildren: './components/hospede/hospede.module#HospedeModule'
            },
            {
                path: 'hospede/cadastrar',
                loadChildren: './components/hospede/hospede.module#HospedeModule'
            },
        ]
    },
    {
        path: '404',
        component: ErroPageComponent
    },
    {
        path: 'error/:type',
        component: ErroPageComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PagesRoutingModule {
}
