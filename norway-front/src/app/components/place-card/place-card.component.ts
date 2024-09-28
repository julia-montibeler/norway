import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { PlaceService } from '../../services/place.service';
import { Place } from '../../models/place';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-place-card',
  standalone: true,
  imports: [CardModule, ButtonModule],
  templateUrl: './place-card.component.html',
  styleUrl: './place-card.component.css'
})
export class PlaceCardComponent {

  @Input() place: Place = {
    Id: '',
    name: '',
    image: '',
    city: ''
  };


  constructor(private placeService: PlaceService, private router: Router, private route: ActivatedRoute) {}

  onDelete(id: string | undefined) {
    if (!id) {
      alert('Invalid place ID');
      return;
    }

    if (confirm('Are you sure you want to delete this place?')) {
      this.placeService.deletePlace(id).subscribe(response => {
        if (response.status === 'Ok') {
          alert('Place successfully deleted');
        } else {
          alert('Failed to delete the place');
        }
      });
    }
  }
}
