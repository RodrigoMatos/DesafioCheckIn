import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'dynamicPipe' })
export class DynamicPipe implements PipeTransform {

    transform(value: any, modifier: any) {
        return modifier.transform(value);
    }

}
