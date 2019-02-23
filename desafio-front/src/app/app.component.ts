import { Component, OnInit } from '@angular/core';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'Desafio - 1.0.0';

  constructor(private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer) { }

  ngOnInit() {
    this.createIconsCustom();
  }

  private createIconsCustom() {
    this.matIconRegistry.addSvgIcon('add_custom', this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/add.svg'));
    this.matIconRegistry.addSvgIcon('edit_custom', this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/edit.svg'));
    this.matIconRegistry.addSvgIcon('lixeira_custom', this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/lixeira.svg'));
    this.matIconRegistry.addSvgIcon('menu_custom', this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/menu.svg'));
    this.matIconRegistry.addSvgIcon('voltar_custom', this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/voltar.svg'));
    this.matIconRegistry.addSvgIcon('search_custom', this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/search.svg'));
    this.matIconRegistry.addSvgIcon('hora_custom', this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/hora.svg'));
    this.matIconRegistry.addSvgIcon('search_branco_custom',
      this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/images/search_branco.svg'));
  }

}
