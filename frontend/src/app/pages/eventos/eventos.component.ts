import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EventoService } from '../../services/evento.service';
import { AuthService } from '../../services/auth.service';
import { Evento } from '../../models/evento.model';

@Component({
  selector: 'app-eventos',
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.css']
})
export class EventosComponent implements OnInit {
  eventos: Evento[] = [];
  isOrganizador: boolean = false;
  isLoggedIn: boolean = false;
  showInscripcionModal: boolean = false;
  eventoSeleccionado: Evento | null = null;
  inscripcionEnProceso: boolean = false;

  constructor(
    private eventoService: EventoService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.isOrganizador = this.authService.isOrganizador();
    this.isLoggedIn = this.authService.isLoggedIn();

    // DEBUG: Verificar roles del usuario
    const currentUser = this.authService.currentUserValue;
    console.log('🔍 DEBUG - Usuario actual:', currentUser);
    console.log('🔍 DEBUG - Es organizador:', this.isOrganizador);
    console.log('🔍 DEBUG - Roles:', currentUser?.roles);

    this.cargarEventos();
  }

  cargarEventos(): void {
    this.eventoService.getAllEventos().subscribe({
      next: (data) => {
        this.eventos = data;
      },
      error: (error) => {
        console.error('Error al cargar eventos:', error);
        this.snackBar.open('Error al cargar los eventos', 'Cerrar', {
          duration: 3000
        });
      }
    });
  }

  // Abrir modal de inscripción
  abrirModalInscripcion(evento: Evento): void {
    if (!this.isLoggedIn) {
      this.snackBar.open('Debes iniciar sesión para inscribirte', 'Cerrar', {
        duration: 3000
      });
      this.router.navigate(['/login']);
      return;
    }

    this.eventoSeleccionado = evento;
    this.showInscripcionModal = true;
  }

  // Cerrar modal de inscripción
  cerrarModalInscripcion(): void {
    this.showInscripcionModal = false;
    this.eventoSeleccionado = null;
    this.inscripcionEnProceso = false;
  }

  // Confirmar inscripción a un evento
  confirmarInscripcion(): void {
    if (!this.eventoSeleccionado) return;

    const eventoId = this.eventoSeleccionado.idEvento;

    if (!eventoId) {
      this.snackBar.open('Error: No se pudo obtener el ID del evento', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.inscripcionEnProceso = true;

    this.eventoService.inscribirseEvento(eventoId).subscribe({
      next: (response) => {
        this.snackBar.open(
          response?.message || '¡Inscripción exitosa! Te esperamos en el evento',
          'Cerrar',
          {
            duration: 4000,
            panelClass: ['success-snackbar']
          }
        );
        this.cerrarModalInscripcion();
        this.cargarEventos();
      },
      error: (error) => {
        this.inscripcionEnProceso = false;
        const mensaje = error.error?.message || 'Error al inscribirte al evento';
        this.snackBar.open(mensaje, 'Cerrar', {
          duration: 4000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }

  // Eliminar evento (solo ORGANIZADOR)
  eliminarEvento(id: number, nombre: string): void {
    if (confirm(`¿Estás seguro de eliminar el evento "${nombre}"?`)) {
      this.eventoService.deleteEvento(id).subscribe({
        next: () => {
          this.snackBar.open('Evento eliminado correctamente', 'Cerrar', {
            duration: 3000
          });
          this.cargarEventos();
        },
        error: (error) => {
          console.error('Error al eliminar:', error);
          this.snackBar.open(
            error.error?.message || 'Error al eliminar el evento. Solo puedes eliminar eventos que creaste.',
            'Cerrar',
            { duration: 4000 }
          );
        }
      });
    }
  }

  // Navegar a crear/editar
  crearEvento(): void {
    this.router.navigate(['/eventos/crear']);
  }

  editarEvento(evento: Evento): void {
    this.router.navigate(['/eventos/editar', evento.idEvento]);
  }

  // Formatear fecha para mostrar
  formatearFecha(fecha: string): string {
    const date = new Date(fecha);
    return date.toLocaleDateString('es-ES', {
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });
  }
}
