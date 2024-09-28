import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { PlaceService } from '../../services/place.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { CommonModule } from '@angular/common';
import { DropdownModule } from 'primeng/dropdown';

@Component({
  selector: 'app-register-place',
  standalone: true,
  imports:[FormsModule, CardModule, InputTextModule, ReactiveFormsModule, ButtonModule, CommonModule, DropdownModule],
  templateUrl: './register-place.component.html',
  styleUrl: './register-place.component.css'
})
export class RegisterPlaceComponent {
  registerForm: any;

  constructor(private placeService: PlaceService, private fb: FormBuilder, private router: Router, private route: ActivatedRoute, private primengConfig: PrimeNGConfig){
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      city: ['', Validators.required],
      image: ['', Validators.required]
    })
  }

  onSubmit(){
    this.placeService.createPlace(this.registerForm.value).subscribe({
      next: (res: any) => {
        const route = this.route.snapshot.queryParamMap.get('stateUrl') || '/places';
        this.router.navigateByUrl(route);  
        alert("Place succesfully registered");
      },
      error: (err) => {
        console.error('Register error:', err);
        alert("Unexpected error");
      }
    });
  }
}
