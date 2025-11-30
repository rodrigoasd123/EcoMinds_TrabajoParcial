import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EventoService } from '../../../services/evento.service';
import { Evento } from '../../../models/evento.model';

@Component({
  selector: 'app-form-evento',
  templateUrl: './form-evento.component.html',
  styleUrls: ['./form-evento.component.css']
})
export class FormEventoComponent implements OnInit {
  eventoForm: FormGroup;
  isEditMode: boolean = false;
  loading: boolean = false;
  eventoId?: number;

  constructor(
    private fb: FormBuilder,
    private eventoService: EventoService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.eventoForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(5)]],
      descripcion: ['', [Validators.required, Validators.minLength(20)]],
      fecha: ['', Validators.required],
      hora: ['', Validators.required],
      lugar: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  ngOnInit(): void {
    // Verificar si estamos en modo edición
    this.eventoId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.eventoId) {
      this.isEditMode = true;
      this.cargarEvento(this.eventoId);
    }
  }

  cargarEvento(id: number): void {
    this.loading = true;
    this.eventoService.getEventoById(id).subscribe({
      next: (evento) => {
        this.eventoForm.patchValue({
          ...evento,
          fecha: this.formatDateForInput(evento.fecha)
        });
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error al cargar evento:', error);
        this.snackBar.open('Error al cargar el evento', 'Cerrar', {
          duration: 3000
        });
        this.loading = false;
        this.router.navigate(['/eventos']);
      }
    });
  }

  // Convertir fecha ISO a formato yyyy-MM-dd para input date
  formatDateForInput(dateString: string): string {
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
  }

  onSubmit(): void {
    if (this.eventoForm.invalid) {
      this.snackBar.open('Por favor, completa todos los campos requeridos', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.loading = true;
    const eventoData = this.eventoForm.value;

    if (this.isEditMode && this.eventoId) {
      // Actualizar
      this.eventoService.updateEvento(this.eventoId, eventoData).subscribe({
        next: () => {
          this.snackBar.open('Evento actualizado exitosamente', 'Cerrar', {
            duration: 3000
          });
          this.router.navigate(['/eventos']);
        },
        error: (error: any) => {
          console.error('Error al actualizar:', error);
          this.snackBar.open(
            error.error?.message || 'Error al actualizar el evento. Solo puedes editar eventos que creaste.',
            'Cerrar',
            { duration: 4000 }
          );
          this.loading = false;
        }
      });
    } else {
      // Crear
      this.eventoService.createEvento(eventoData).subscribe({
        next: () => {
          this.snackBar.open('Evento creado exitosamente', 'Cerrar', {
            duration: 3000
          });
          this.router.navigate(['/eventos']);
        },
        error: (error: any) => {
          console.error('Error al crear:', error);
          this.snackBar.open('Error al crear el evento', 'Cerrar', {
            duration: 3000
          });
          this.loading = false;
        }
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/eventos']);
  }
}
