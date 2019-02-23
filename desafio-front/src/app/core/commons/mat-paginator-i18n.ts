import { MatPaginatorIntl } from '@angular/material';

export class MatPaginatorI18n extends MatPaginatorIntl {

    itemsPerPageLabel = 'Registros por página';
    nextPageLabel = 'Próxima página';
    previousPageLabel = 'Página anterior';
    getRangeLabel = (page: number, pageSize: number, totalResults: number) => {
        if (!totalResults) { return 'Nenhum resultado'; }
        totalResults = Math.max(totalResults, 0);
        const startIndex = page * pageSize;
        const endIndex =
            startIndex < totalResults ?
                Math.min(startIndex + pageSize, totalResults) :
                startIndex + pageSize; return `${startIndex + 1} - ${endIndex} de ${totalResults}`
            ;
    }

}
