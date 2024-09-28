import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { DropdownModule } from 'primeng/dropdown';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CardModule, InputTextModule, ReactiveFormsModule, ButtonModule, CommonModule, DropdownModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm: any;

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router, private route: ActivatedRoute, private primengConfig: PrimeNGConfig){
    this.registerForm = this.fb.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required]
    })
  }

  onSubmit(){
    this.authService.register(this.registerForm.value).subscribe({
      next: (res: any) => {
        const route = this.route.snapshot.queryParamMap.get('stateUrl') || '/login';
        this.router.navigateByUrl(route);  
        alert("Usuário cadastrado com sucesso")
      },
      error: (err) => {
        console.error('Register error:', err);
        alert("Unexpected error");
      }
    });
  }  
}
