import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamerDetailsComponent } from './gamer-details.component';

describe('GamerDetailsComponent', () => {
  let component: GamerDetailsComponent;
  let fixture: ComponentFixture<GamerDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamerDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
