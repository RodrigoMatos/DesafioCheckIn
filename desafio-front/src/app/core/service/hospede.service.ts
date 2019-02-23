import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { BaseService } from '../base/base-service';
import { PageDataTableCustom } from '../model/commons/PageDataTableCustom';
import { HospedeFilter } from '../model/filter/hospede-filter';
import { Hospede } from '../model/hospede';

@Injectable()
export class HospedeService extends BaseService<Hospede> {

    constructor(protected http: HttpClient) {
        super(http);
    }

    public findById(id: number): Observable<Hospede> {
        return this.get('hospede/findById/' + id);
    }

    public findByFilterLazy(filter: HospedeFilter): Observable<PageDataTableCustom> {
        return this.getWithParam('hospede/findByFilterLazy', filter);
    }

    public save(entity: Hospede): Observable<Hospede> {
        return this.post('hospede/save', entity);
    }

    public findAll(): Observable<Array<Hospede>> {
        return this.get('hospede');
    }

}
