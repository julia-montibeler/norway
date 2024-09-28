import { Component, inject, NgModule } from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms'
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CardModule, InputTextModule, ReactiveFormsModule, ButtonModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm: any;

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router, private route: ActivatedRoute, private primengConfig: PrimeNGConfig){
    this.loginForm = this.fb.group({
      login: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  ngOnInit() {
    this.primengConfig.ripple = true;
  }

  onLogin(){
    this.authService.login(this.loginForm.value).subscribe({
      next: (res: any) => {
        localStorage.setItem("token_angular", res.token);
        const route = this.route.snapshot.queryParamMap.get('stateUrl') || '/places';
        this.router.navigateByUrl(route);  
      },
      error: (err) => {
        console.error('Erro de login:', err);
        alert("Senha ou usuário inválidos");
      }
    });
  }  
}
