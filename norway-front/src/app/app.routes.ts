import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { PlacesComponent } from './pages/places/places.component';
import { RegisterPlaceComponent } from './pages/register-place/register-place.component';
import { HomeComponent } from './pages/home/home.component';


export const routes: Routes = [
    {
        path:'login',
        component: LoginComponent
    },
    {
        path:'',
        component: HomeComponent
    },
    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'registerplace',
        component: RegisterPlaceComponent
    },
    {
        path: 'places',
        component: PlacesComponent
    }
];
