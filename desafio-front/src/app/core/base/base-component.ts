import { AbstractControl, FormGroup } from '@angular/forms';
import { ToastOptions, ToastyService } from 'ng2-toasty';
import { isNullOrUndefined } from 'util';
import { MensagemProperties } from '../../../environments/mensagem-properties';
import { ConstraintsApp } from '../commons/constraints-app';
import { EventEmitterService } from './event-emitter-service';

export abstract class BaseComponent {

  constructor(protected dialogo?: ToastyService) {
  }

  protected mensagemInformacao(mensagem: string, controle?: AbstractControl) {
    if (this.dialogo) {
      this.dialogo.info(this.getMensagemConfig(mensagem, 'Info', controle));
    }
  }

  protected mensagemSucesso(mensagem: string, controle?: AbstractControl) {
    if (this.dialogo) {
      this.dialogo.success(this.getMensagemConfig(mensagem, 'Sucesso', controle));
    }
  }

  protected mensagemAguardando(mensagem: string, controle?: AbstractControl) {
    if (this.dialogo) {
      this.dialogo.wait(this.getMensagemConfig(mensagem, 'Aguarde', controle));
    }
  }

  protected mensagemError(mensagem: string, controle?: AbstractControl) {
    if (this.dialogo) {
      this.dialogo.error(this.getMensagemConfigError(mensagem, undefined, controle));
    }
  }

  protected mensagemAtencao(mensagem: string, controle?: AbstractControl) {
    if (this.dialogo) {
      this.dialogo.warning(this.getMensagemConfig(mensagem, 'Atenção', controle));
    }
  }

  private getMensagemConfig(mensagem: string, title?: string, controle?: AbstractControl): ToastOptions {
    const configuracoes: ToastOptions = {
      title: title,
      timeout: 5000,
      msg: mensagem,
      showClose: true,
      theme: 'bootstrap'
    };
    return configuracoes;
  }

  private getMensagemConfigError(mensagem: string, title?: string, controle?: AbstractControl): ToastOptions {
    const configuracoes = this.getMensagemConfig(mensagem, title, controle);
    configuracoes.timeout = 20000;
    return configuracoes;
  }

  public cleanAllAttrs(filter: any) {
    Object.keys(filter).forEach(function (key) {
      if (filter[key] === undefined || filter[key] === null) {
        return;
      }
      if (typeof filter[key] === 'object' && filter[key].jsdate instanceof Date) {
        filter[key] = null;
      } else {
        filter[key] = undefined;
      }
    });
  }

  public cloneObject(filterClone: any): any {
    if (!isNullOrUndefined(filterClone)) {
      if (typeof filterClone === 'object') {
        const clone = new filterClone.constructor();
        Object.keys(filterClone).forEach((key) => {
          if (typeof filterClone === 'object') {
            clone[key] = this.cloneObject(filterClone[key]);
          } else {
            clone[key] = filterClone[key];
          }
        });
        return clone;
      }
    }
    return filterClone;
  }

  public isEmpty(value: any): boolean {
    return isNullOrUndefined(value);
  }

  protected handlingError(error) {
    if (error.status === 400) {
      // BAD REQUEST
      this.mensagemError(this.getMensageErrorObj(error));
    } else {
      if (error.error) {
        if (error.error.message) {
          this.mensagemError(error.error.message);
        } else {
          this.mensagemError(error.error);
        }
      } else {
        this.mensagemError(MensagemProperties.app_operacao_nao_realizada);
      }
    }
  }

  private getMensageErrorObj(error): string {
    const errorString = JSON.stringify(error.error);
    let errorObject = JSON.parse(errorString);
    if (typeof errorObject === 'string') {
      errorObject = JSON.parse(errorObject);
    }
    return errorObject.message;
  }

  protected bloquearTela() {
    EventEmitterService.get(ConstraintsApp.EVENT_NAME_BLOCK).emit(true);
  }

  protected desbloquearTela() {
    EventEmitterService.get(ConstraintsApp.EVENT_NAME_BLOCK).emit(false);
  }

  protected isNullOrUndefinedOrEmpty(value: any): boolean {
    return isNullOrUndefined(value) || (value.toString() === '');
  }

  protected getFormControlGeneric(formGroup: FormGroup, name: string) {
    if (name.indexOf('.') !== -1) {
      const array = name.split('.');
      let form = formGroup;
      array.forEach(item => {
        form = this.getValueFormGroup(form, item);
      });
      return form;
    } else {
      return formGroup.get(name);
    }
  }

  private getValueFormGroup(form, name) {
    return form.get(name);
  }

  public compareById(entity: any, another: any): boolean {
    return entity && another ? entity.id === another.id : entity === another;
  }

  public compareByLogin(entity: any, another: any): boolean {
    return entity && another ? entity.login === another.login : entity === another;
  }

}
