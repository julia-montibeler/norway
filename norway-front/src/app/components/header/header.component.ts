import { Component } from '@angular/core';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MenubarModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  items: MenuItem[];

  constructor() {
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: '/'
      },
      {
        label: 'Places',
        icon: 'pi pi-map',
        routerLink: '/places'
      },
      {
        label: 'Login',
        icon: 'pi pi-sign-in',
        routerLink: '/login'
      },
      {
        label: 'Logout',
        icon: 'pi pi-sign-out',
        command: () => {
        }
      }
    ];
  }
}