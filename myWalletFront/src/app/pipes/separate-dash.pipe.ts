import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'separateDash',
  standalone: true
})
export class SeparateDashPipe implements PipeTransform {


  transform(value: string | undefined): string {
    
    if (typeof value === 'undefined') {
      value = "";
    }
      return value.replace(/(\d)(?=(\d{4})+(?!\d))/g, '$1-');
  }
}
