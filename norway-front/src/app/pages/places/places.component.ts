import { Component, OnInit } from '@angular/core';
import { Place } from '../../models/place';
import { PlaceService } from '../../services/place.service';
import { PlaceCardComponent } from "../../components/place-card/place-card.component";
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-places',
  standalone: true,
  imports: [PlaceCardComponent, CommonModule, ButtonModule],
  templateUrl: './places.component.html',
  styleUrl: './places.component.css'
})
export class PlacesComponent implements OnInit {

  places: Place[] = [];

  constructor(private placeService: PlaceService) {}

  ngOnInit(): void {
    this.getPlaces(); // Carrega os lugares ao iniciar o componente
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

  removePlaceFromList(id: string): void {
    this.places = this.places.filter(place => place.Id !== id); // Remove o lugar da lista
  }
}
