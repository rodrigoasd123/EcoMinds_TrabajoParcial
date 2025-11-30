import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../../services/auth.service';

interface Particle {
  x: number;
  y: number;
  vx: number;
  vy: number;
  radius: number;
  color: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('particleCanvas', { static: false }) canvasRef!: ElementRef<HTMLCanvasElement>;
  
  loginForm: FormGroup;
  loading = false;
  hidePassword = true;
  
  private canvas!: HTMLCanvasElement;
  private ctx!: CanvasRenderingContext2D;
  private particles: Particle[] = [];
  private animationId: any;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    // Inicialización del formulario
  }

  ngAfterViewInit(): void {
    this.initCanvas();
    this.createParticles();
    this.animate();
  }

  initCanvas(): void {
    this.canvas = this.canvasRef.nativeElement;
    this.ctx = this.canvas.getContext('2d')!;
    
    this.canvas.width = this.canvas.offsetWidth;
    this.canvas.height = this.canvas.offsetHeight;

    window.addEventListener('resize', () => {
      this.canvas.width = this.canvas.offsetWidth;
      this.canvas.height = this.canvas.offsetHeight;
    });
  }

  createParticles(): void {
    const colors = ['#66bb6a', '#81c784', '#a5d6a7', '#4caf50', '#8bc34a'];
    
    for (let i = 0; i < 40; i++) {
      this.particles.push({
        x: Math.random() * this.canvas.width,
        y: Math.random() * this.canvas.height,
        vx: (Math.random() - 0.5) * 2,
        vy: (Math.random() - 0.5) * 2,
        radius: Math.random() * 15 + 10,
        color: colors[Math.floor(Math.random() * colors.length)]
      });
    }
  }

  animate(): void {
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

    // Actualizar y dibujar partículas
    this.particles.forEach((particle, index) => {
      // Movimiento
      particle.x += particle.vx;
      particle.y += particle.vy;

      // Rebotar en bordes
      if (particle.x - particle.radius < 0 || particle.x + particle.radius > this.canvas.width) {
        particle.vx *= -1;
      }
      if (particle.y - particle.radius < 0 || particle.y + particle.radius > this.canvas.height) {
        particle.vy *= -1;
      }

      // Detectar colisiones con otras partículas
      for (let j = index + 1; j < this.particles.length; j++) {
        const other = this.particles[j];
        const dx = other.x - particle.x;
        const dy = other.y - particle.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < particle.radius + other.radius) {
          // Colisión detectada - dividir partículas
          if (particle.radius > 8 && this.particles.length < 80) {
            this.splitParticle(particle);
          }
          if (other.radius > 8 && this.particles.length < 80) {
            this.splitParticle(other);
          }

          // Rebotar
          const angle = Math.atan2(dy, dx);
          const sin = Math.sin(angle);
          const cos = Math.cos(angle);

          const vx1 = particle.vx * cos + particle.vy * sin;
          const vy1 = particle.vy * cos - particle.vx * sin;
          const vx2 = other.vx * cos + other.vy * sin;
          const vy2 = other.vy * cos - other.vx * sin;

          particle.vx = vx2 * cos - vy1 * sin;
          particle.vy = vy1 * cos + vx2 * sin;
          other.vx = vx1 * cos - vy2 * sin;
          other.vy = vy2 * cos + vx1 * sin;

          // Separar partículas
          const overlap = particle.radius + other.radius - distance;
          particle.x -= overlap * cos / 2;
          particle.y -= overlap * sin / 2;
          other.x += overlap * cos / 2;
          other.y += overlap * sin / 2;
        }
      }

      // Dibujar partícula
      this.ctx.beginPath();
      this.ctx.arc(particle.x, particle.y, particle.radius, 0, Math.PI * 2);
      this.ctx.fillStyle = particle.color;
      this.ctx.globalAlpha = 0.7;
      this.ctx.fill();
      this.ctx.globalAlpha = 1;
      
      // Borde brillante
      this.ctx.strokeStyle = 'rgba(255, 255, 255, 0.5)';
      this.ctx.lineWidth = 2;
      this.ctx.stroke();
    });

    this.animationId = requestAnimationFrame(() => this.animate());
  }

  splitParticle(particle: Particle): void {
    const newRadius = particle.radius * 0.7;
    
    if (newRadius > 5) {
      particle.radius = newRadius;
      
      const angle = Math.random() * Math.PI * 2;
      this.particles.push({
        x: particle.x + Math.cos(angle) * newRadius,
        y: particle.y + Math.sin(angle) * newRadius,
        vx: Math.cos(angle) * 3,
        vy: Math.sin(angle) * 3,
        radius: newRadius,
        color: particle.color
      });
    }
  }

  ngOnDestroy(): void {
    if (this.animationId) {
      cancelAnimationFrame(this.animationId);
    }
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.snackBar.open('Por favor completa todos los campos', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    this.loading = true;
    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.snackBar.open(`¡Bienvenido ${response.username}! 🎉`, 'Cerrar', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });
        this.loading = false;
        // Redirigir al home después del login exitoso
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Error al iniciar sesión:', error);
        this.snackBar.open('❌ Usuario o contraseña incorrectos', 'Cerrar', {
          duration: 4000,
          panelClass: ['error-snackbar']
        });
        this.loading = false;
      }
    });
  }
}
