import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-erro-page',
  templateUrl: './erro-page.component.html',
  styleUrls: ['./erro-page.component.css']
})
export class ErroPageComponent implements OnInit {

  @Input() errorType: number;

  constructor(private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.errorType = +this.route.snapshot.paramMap.get('type');
    if (!this.errorType) {
      this.errorType = 404;
    }
  }

  public irParaHome() {
    this.router.navigate(['']);
  }

}
