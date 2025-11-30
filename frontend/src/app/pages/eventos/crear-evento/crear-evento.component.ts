import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EventoService } from '../../../services/evento.service';
import { CrearEventoRequest } from '../../../models/evento.model';

@Component({
  selector: 'app-crear-evento',
  templateUrl: './crear-evento.component.html',
  styleUrls: ['./crear-evento.component.css']
})
export class CrearEventoComponent implements OnInit {
  eventoForm!: FormGroup;
  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private eventoService: EventoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.eventoForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      fecha: ['', Validators.required],
      hora: ['', Validators.required],
      lugar: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit(): void {
    if (this.eventoForm.invalid) {
      this.errorMessage = 'Por favor completa todos los campos correctamente';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const eventoData: CrearEventoRequest = {
      nombre: this.eventoForm.value.nombre,
      descripcion: this.eventoForm.value.descripcion,
      fecha: this.eventoForm.value.fecha,
      hora: this.eventoForm.value.hora,
      lugar: this.eventoForm.value.lugar
    };

    this.eventoService.createEvento(eventoData).subscribe({
      next: (response) => {
        this.loading = false;
        this.successMessage = 'Evento creado exitosamente';
        setTimeout(() => {
          this.router.navigate(['/eventos']);
        }, 1500);
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = error.error?.message || 'Error al crear el evento. Verifica tus permisos de organizador.';
        console.error('Error al crear evento:', error);
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/eventos']);
  }

  // Helpers para validación en template
  get nombre() { return this.eventoForm.get('nombre'); }
  get descripcion() { return this.eventoForm.get('descripcion'); }
  get fecha() { return this.eventoForm.get('fecha'); }
  get hora() { return this.eventoForm.get('hora'); }
  get lugar() { return this.eventoForm.get('lugar'); }
}
