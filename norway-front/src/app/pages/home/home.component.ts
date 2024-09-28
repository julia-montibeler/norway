import { Component } from '@angular/core';
import { Place } from '../../models/place';
import { PlaceService } from '../../services/place.service';
import { PlaceCardComponent } from "../../components/place-card/place-card.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [PlaceCardComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  places: Place[] = [];

  constructor(private placeService: PlaceService) {}

  ngOnInit(): void {
    this.getPlaces();
  }

  getPlaces(): void {
    this.placeService.getPlaces().subscribe(
      (response) => {
        this.places = response;
      },
      (error) => {
        console.error('Erro ao carregar os dados: ', error);
      }
    );
  }
}
