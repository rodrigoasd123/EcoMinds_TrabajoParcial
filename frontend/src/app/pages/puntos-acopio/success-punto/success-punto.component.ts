import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-success-punto',
  templateUrl: './success-punto.component.html',
  styleUrls: ['./success-punto.component.css']
})
export class SuccessPuntoComponent {
  constructor(private router: Router) {}

  volver() {
    this.router.navigate(['/puntos-acopio']);
  }
}
