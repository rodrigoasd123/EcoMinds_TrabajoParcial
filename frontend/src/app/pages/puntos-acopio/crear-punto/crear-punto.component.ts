import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PuntoAcopio } from '../../../models/punto-acopio.model';
import { PuntoAcopioService } from '../../../services/punto-acopio.service';

@Component({
  selector: 'app-crear-punto',
  templateUrl: './crear-punto.component.html',
  styleUrls: ['./crear-punto.component.css']
})
export class CrearPuntoComponent implements OnInit {
  puntoForm: FormGroup;
  modoEdicion = false;
  loading = false;
  puntoId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private puntoAcopioService: PuntoAcopioService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.puntoForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      ubicacion: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  ngOnInit(): void {
    // Verificar si es modo edición
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.modoEdicion = true;
        this.puntoId = parseInt(id);
        this.cargarPunto(this.puntoId);
      }
    });
  }

  cargarPunto(id: number): void {
    this.loading = true;
    this.puntoAcopioService.obtenerPuntoAcopio(id).subscribe({
      next: (punto) => {
        this.puntoForm.patchValue({
          nombre: punto.nombre,
          ubicacion: punto.ubicacion
        });
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar punto:', error);
        this.snackBar.open('Error al cargar el punto de acopio', 'Cerrar', {
          duration: 3000
        });
        this.loading = false;
        this.router.navigate(['/puntos-acopio']);
      }
    });
  }

  guardarPunto(): void {
    if (this.puntoForm.invalid) {
      this.snackBar.open('Por favor completa todos los campos correctamente', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.loading = true;
    const puntoData: PuntoAcopio = this.puntoForm.value;

    if (this.modoEdicion && this.puntoId) {
      // Actualizar punto existente
      this.puntoAcopioService.actualizarPuntoAcopio(this.puntoId, puntoData).subscribe({
        next: (response) => {
          this.loading = false;
          this.router.navigate(['/puntos-acopio/success']);
        },
        error: (error) => {
          console.error('Error al actualizar punto:', error);
          this.snackBar.open('Error al actualizar el punto de acopio', 'Cerrar', {
            duration: 3000
          });
          this.loading = false;
        }
      });
    } else {
      // Crear nuevo punto
      this.puntoAcopioService.crearPuntoAcopio(puntoData).subscribe({
        next: (response) => {
          this.loading = false;
          this.router.navigate(['/puntos-acopio/success']);
        },
        error: (error) => {
          console.error('Error al crear punto:', error);
          this.snackBar.open('Error al crear el punto de acopio', 'Cerrar', {
            duration: 3000
          });
          this.loading = false;
        }
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/puntos-acopio']);
  }
}
