import { Injectable } from '@angular/core';
import { Resolve, RouterStateSnapshot } from '@angular/router';
import { ActivatedRouteSnapshot } from '@angular/router/src/router_state';
import { Observable } from 'rxjs/Observable';
import { Hospede } from '../model/hospede';
import { HospedeService } from '../service/hospede.service';

@Injectable()
export class HospedeResolver implements Resolve<Array<Hospede>> {

    constructor(private hospedeService: HospedeService) { }

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<Array<Hospede>> {
        return this.hospedeService.findAll();
    }
}