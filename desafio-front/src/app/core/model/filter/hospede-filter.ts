import { PaginatorCustom } from '../commons/PaginatorCustom';

export class HospedeFilter extends PaginatorCustom {

    codigo: number;
    nome: string;
    documento: string;
    telefone: string;

    somenteCheckIn: boolean;
    somenteCheckOut: boolean;

    constructor() {
        super();
        this.somenteCheckIn = false;
        this.somenteCheckOut = false;
    }

}
