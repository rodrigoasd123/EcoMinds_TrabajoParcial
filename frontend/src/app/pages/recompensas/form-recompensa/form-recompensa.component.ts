import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RecompensaService } from '../../../services/recompensa.service';
import { Recompensa } from '../../../models/recompensa.model';

@Component({
  selector: 'app-form-recompensa',
  templateUrl: './form-recompensa.component.html',
  styleUrls: ['./form-recompensa.component.css']
})
export class FormRecompensaComponent implements OnInit {
  recompensaForm: FormGroup;
  isEditMode: boolean = false;
  loading: boolean = false;
  recompensaId?: number;

  constructor(
    private fb: FormBuilder,
    private recompensaService: RecompensaService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.recompensaForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      puntosRequeridos: [null, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    // Verificar si estamos en modo edición
    this.recompensaId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.recompensaId) {
      this.isEditMode = true;
      this.cargarRecompensa(this.recompensaId);
    }
  }

  cargarRecompensa(id: number): void {
    this.loading = true;
    this.recompensaService.getRecompensaById(id).subscribe({
      next: (recompensa) => {
        this.recompensaForm.patchValue(recompensa);
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error al cargar recompensa:', error);
        this.snackBar.open('Error al cargar la recompensa', 'Cerrar', {
          duration: 3000
        });
        this.loading = false;
        this.router.navigate(['/recompensas']);
      }
    });
  }

  onSubmit(): void {
    if (this.recompensaForm.invalid) {
      this.snackBar.open('Por favor, completa todos los campos requeridos', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.loading = true;
    const recompensaData = this.recompensaForm.value;
    console.log('Datos a enviar:', recompensaData);

    if (this.isEditMode && this.recompensaId) {
      // Actualizar
      this.recompensaService.updateRecompensa(this.recompensaId, recompensaData).subscribe({
        next: () => {
          this.snackBar.open('Recompensa actualizada exitosamente', 'Cerrar', {
            duration: 3000
          });
          this.router.navigate(['/recompensas']);
        },
        error: (error: any) => {
          console.error('Error al actualizar:', error);
          this.snackBar.open('Error al actualizar la recompensa', 'Cerrar', {
            duration: 3000
          });
          this.loading = false;
        }
      });
    } else {
      // Crear
      this.recompensaService.createRecompensa(recompensaData).subscribe({
        next: () => {
          this.snackBar.open('Recompensa creada exitosamente', 'Cerrar', {
            duration: 3000
          });
          this.router.navigate(['/recompensas']);
        },
        error: (error: any) => {
          console.error('Error al crear:', error);
          this.snackBar.open('Error al crear la recompensa', 'Cerrar', {
            duration: 3000
          });
          this.loading = false;
        }
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/recompensas']);
  }
}
