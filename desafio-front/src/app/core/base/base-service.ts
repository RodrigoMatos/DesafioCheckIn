import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, throwError as observableThrowError } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { MensagemProperties } from '../../../environments/mensagem-properties';
import { DateUtil } from '../../../utils/date.utils';
import { ConstraintsApp } from '../commons/constraints-app';
import { EventEmitterService } from './event-emitter-service';
import { isNullOrUndefined } from 'util';


export abstract class BaseService<T> {

  constructor(protected http: HttpClient) {
  }

  private bloquearTela(assincrono?: boolean) {
    if (!assincrono) {
      EventEmitterService.get(ConstraintsApp.EVENT_NAME_BLOCK).emit(true);
    }
  }

  private desbloquearTela(assincrono?: boolean) {
    if (!assincrono) {
      EventEmitterService.get(ConstraintsApp.EVENT_NAME_BLOCK).emit(false);
    }
  }

  protected getWithParam<K>(endpoint: string, filter: any, assincrono?: boolean, download?: boolean): Observable<any> {
    let params = new HttpParams();
    if (filter !== undefined && filter !== null) {
      if (typeof filter === 'object') {
        Object.keys(filter).forEach(function (key) {
          if (filter[key] !== null && filter[key] !== undefined) {
            if (typeof filter[key] === 'object' && filter[key].jsdate instanceof Date) {
              params = params.append(key, DateUtil.objectToDate(filter[key].date).format(DateUtil.enFormat));
            } else {
              params = params.append(key, filter[key]);
            }
          }
        });
      } else {
        params = params.append('param', filter);
      }
    }
    return this.getApi(environment.api, endpoint, params, assincrono, download);
  }

  protected get<K>(endpoint: string, criteria: HttpParams = new HttpParams()): Observable<any> {
    return this.getApi(environment.api, endpoint, criteria);
  }

  protected getApi<K>(baseUrl: string, endpoint: string, criteria: any, assincrono?: boolean, download?: boolean): Observable<any> {
    this.bloquearTela(assincrono);
    return this.http.get(baseUrl + endpoint, download ? this.getDefaultHeaderDownload(criteria) : this.getDefaultHeader(criteria)).pipe(
      catchError((error: HttpResponse<T>) => {
        return observableThrowError(this.handlingError(error));
      }), finalize(() => {
        this.desbloquearTela(assincrono);
      }));
  }

  protected upload<K>(endpoint: string, criteria: FormData, assincrono?: boolean): Observable<any> {
    return this.uploadApi(environment.api, endpoint, criteria, assincrono);
  }

  protected uploadApi<K>(baseUrl: string, endpoint: string, criteria?: any, assincrono?: boolean): Observable<any> {
    this.bloquearTela(assincrono);
    return this.http.post(baseUrl + endpoint, criteria, this.getDefaultHeader(criteria)).pipe(
      catchError((error: HttpResponse<K>) => {
        return observableThrowError(this.handlingError(error));
      }), finalize(() => {
        this.desbloquearTela(assincrono);
      }));
  }

  protected post<K>(endpoint: string, criteria?: any, assincrono?: boolean): Observable<any> {
    return this.postApi(environment.api, endpoint, criteria, assincrono);
  }

  protected postApi<K>(baseUrl: string, endpoint: string, criteria?: any, assincrono?: boolean): Observable<any> {
    this.bloquearTela(assincrono);
    return this.http.post(baseUrl + endpoint, criteria ? JSON.stringify(criteria) : null, this.getDefaultHeader()).pipe(
      catchError((error: HttpResponse<T>) => {
        return observableThrowError(this.handlingError(error));
      }), finalize(() => {
        if (!assincrono) {
          this.desbloquearTela(assincrono);
        }
      }));
  }

  protected put<K>(endpoint: string, criteria?: any, assincrono?: boolean): Observable<any> {
    this.bloquearTela(assincrono);
    return this.http.put(environment.api + endpoint, criteria ? JSON.stringify(criteria) : null, this.getDefaultHeader()).pipe(
      catchError((error: HttpResponse<T>) => {
        return observableThrowError(this.handlingError(error));
      }), finalize(() => {
        this.desbloquearTela(assincrono);
      }));

  }

  protected delete<K>(endpoint: string): Observable<any> {
    this.bloquearTela();
    return this.http.delete(environment.api + endpoint, this.getDefaultHeader()).pipe(
      catchError((error: HttpResponse<T>) => {
        return observableThrowError(this.handlingError(error));
      }), finalize(() => {
        this.desbloquearTela();
      }));
  }

  private handlingError(error) {
    console.log(error);
    if (!error.error) {
      error.error = MensagemProperties.app_operacao_nao_realizada;
    }
    return error;
  }

  private getDefaultHeader(criteria?: any): any {
    const options = {
      headers: new HttpHeaders()
        .set('Accept', 'application/json')
        .set('Content-Type', 'application/json'),
      params: criteria,
      withCredentials: true
    };
    return options;
  }

  protected getDefaultHeaderDownload(criteria?: any): any {
    const options = {
      headers: new HttpHeaders()
        .set('Accept', 'application/json')
        .set('Content-Type', 'application/json'),
      params: criteria,
      responseType: 'blob',
      withCredentials: true
    };
    return options;
  }

}
