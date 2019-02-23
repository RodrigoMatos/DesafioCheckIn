import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { BaseService } from '../base/base-service';
import { CheckIn } from '../model/checkin';
import { PageDataTableCustom } from '../model/commons/PageDataTableCustom';
import { CheckInFilter } from '../model/filter/checkin-filter';

@Injectable()
export class CheckInService extends BaseService<CheckIn> {

    constructor(protected http: HttpClient) {
        super(http);
    }

    public findById(id: number): Observable<CheckIn> {
        return this.get('checkin/findById/' + id);
    }

    public findByFilterLazy(filter: CheckInFilter): Observable<PageDataTableCustom> {
        return this.getWithParam('checkin/findByFilterLazy', filter);
    }

    public save(entity: CheckIn): Observable<CheckIn> {
        return this.post('checkin/save', entity);
    }

    public findAll(): Observable<Array<CheckIn>> {
        return this.get('checkin');
    }

}
