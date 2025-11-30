import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RecoleccionService } from '../../services/recoleccion.service';
import { PuntoAcopioService } from '../../services/punto-acopio.service';
import { MaterialReciclaje } from '../../models/recoleccion.model';
import { PuntoAcopio } from '../../models/punto-acopio.model';

@Component({
  selector: 'app-programar-recoleccion',
  templateUrl: './programar-recoleccion.component.html',
  styleUrls: ['./programar-recoleccion.component.css']
})
export class ProgramarRecoleccionComponent implements OnInit {
  recoleccionForm: FormGroup;
  loading = false;
  materiales: MaterialReciclaje[] = [];
  puntosAcopio: PuntoAcopio[] = [];

  constructor(
    private fb: FormBuilder,
    private recoleccionService: RecoleccionService,
    private puntoAcopioService: PuntoAcopioService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.recoleccionForm = this.fb.group({
      idMaterial: ['', [Validators.required]],
      idPunto: ['', [Validators.required]],
      peso: ['', [Validators.required, Validators.min(0.1)]]
    });
  }

  ngOnInit(): void {
    this.cargarMateriales();
    this.cargarPuntosAcopio();
  }

  cargarMateriales(): void {
    this.recoleccionService.obtenerMateriales().subscribe({
      next: (materiales) => {
        this.materiales = materiales;
      },
      error: (error) => {
        console.error('Error al cargar materiales:', error);
        this.snackBar.open('Error al cargar tipos de materiales', 'Cerrar', {
          duration: 3000
        });
      }
    });
  }

  cargarPuntosAcopio(): void {
    this.puntoAcopioService.listarPuntosAcopio().subscribe({
      next: (puntos) => {
        this.puntosAcopio = puntos;
      },
      error: (error) => {
        console.error('Error al cargar puntos de acopio:', error);
        this.snackBar.open('Error al cargar puntos de acopio', 'Cerrar', {
          duration: 3000
        });
      }
    });
  }

  registrarRecoleccion(): void {
    if (this.recoleccionForm.invalid) {
      this.snackBar.open('Por favor completa todos los campos correctamente', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.loading = true;
    const recoleccionData = {
      idMaterial: Number(this.recoleccionForm.value.idMaterial),
      idPunto: Number(this.recoleccionForm.value.idPunto),
      peso: Number(this.recoleccionForm.value.peso)
    };

    this.recoleccionService.registrarRecoleccion(recoleccionData).subscribe({
      next: (response) => {
        this.snackBar.open(
          `¡Recolección registrada! Ganaste ${response.puntosGanados} puntos. Total: ${response.puntosTotal}`,
          'Cerrar',
          {
            duration: 5000,
            panelClass: ['success-snackbar']
          }
        );
        this.loading = false;
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Error al registrar recolección:', error);
        const mensaje = error.error?.message || 'Error al registrar la recolección';
        this.snackBar.open(mensaje, 'Cerrar', {
          duration: 4000,
          panelClass: ['error-snackbar']
        });
        this.loading = false;
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/home']);
  }
}
