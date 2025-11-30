import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PuntoAcopio } from '../../models/punto-acopio.model';
import { PuntoAcopioService } from '../../services/punto-acopio.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-puntos-acopio',
  templateUrl: './puntos-acopio.component.html',
  styleUrls: ['./puntos-acopio.component.css']
})
export class PuntosAcopioComponent implements OnInit {
  puntosAcopio: PuntoAcopio[] = [];
  loading = false;
  isAdmin = false;

  constructor(
    private puntoAcopioService: PuntoAcopioService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.cargarPuntosAcopio();
  }

  cargarPuntosAcopio(): void {
    this.loading = true;
    this.puntoAcopioService.listarPuntosAcopio().subscribe({
      next: (data) => {
        this.puntosAcopio = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar puntos de acopio:', error);
        this.snackBar.open('Error al cargar los puntos de acopio', 'Cerrar', {
          duration: 3000
        });
        this.loading = false;
      }
    });
  }

  abrirDialogoCrear(): void {
    this.router.navigate(['/puntos-acopio/crear']);
  }

  abrirDialogoEditar(punto: PuntoAcopio): void {
    this.router.navigate(['/puntos-acopio/editar', punto.idAcopio]);
  }

  eliminarPunto(punto: PuntoAcopio): void {
    if (confirm(`¿Estás seguro de eliminar el punto "${punto.nombre}"?`)) {
      this.puntoAcopioService.eliminarPuntoAcopio(punto.idAcopio!).subscribe({
        next: (response) => {
          this.snackBar.open('Punto de acopio eliminado exitosamente', 'Cerrar', {
            duration: 3000
          });
          this.cargarPuntosAcopio();
        },
        error: (error) => {
          console.error('Error al eliminar punto:', error);
          this.snackBar.open('Error al eliminar el punto de acopio', 'Cerrar', {
            duration: 3000
          });
        }
      });
    }
  }

  verEnMapa(ubicacion: string): void {
    const searchQuery = encodeURIComponent(ubicacion);
    window.open(`https://www.google.com/maps/search/?api=1&query=${searchQuery}`, '_blank');
  }
}
