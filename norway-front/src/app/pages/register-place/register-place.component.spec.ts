import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterPlaceComponent } from './register-place.component';

describe('RegisterPlaceComponent', () => {
  let component: RegisterPlaceComponent;
  let fixture: ComponentFixture<RegisterPlaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterPlaceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterPlaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
