import {Component, EventEmitter, Input, Output} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule, MatListItem } from '@angular/material/list';

import { Card } from './card';

export class ValidationEvent {
  label_id: number;
  validation_result: number;

  constructor (labelId: number, validationResult: number) {
    this.label_id = labelId;
    this.validation_result = validationResult;
  }
}

@Component({
    selector: 'gallery-card',
    templateUrl: './gallery-card.html',
    styleUrls: ['./gallery-card.css']
})
/**
 * Front-end representation of one card in the gallery.
 */
export class GalleryCard {
  // Metadata object containing information about this GalleryCard.
  @Input() card: Card | undefined;
  @Output() validationEvent = new EventEmitter<ValidationEvent>();

  displayPanel: boolean = false;
  validationResult: number = -1;

  get description(): string|undefined {
      return this.card!.description;
  }

  get imageUrl(): string|undefined {
      return this.card!.imageUrl;
  }

  /**
   * Gets the correct image asset for each label marker.
   */
  get labelIcon(): string {
    let labelId: number = this.card!.labelTypeId;
    let path = 'assets/cursors/Cursor_';
    if (labelId === 1) { // Curb Ramp
      path += 'CurbRamp.png';
    } else if (labelId === 2) {  // Missing Curb Ramp
      path += 'NoCurbRamp.png';
    } else if (labelId === 3) { // Obstacle in Path
      path += 'Obstacle.png';
    } else if (labelId === 4) { // Surface Problem
      path += 'SurfaceProblem.png';
    } else {  // No Sidewalk
      path += 'NoSidewalk.png';
    }

    return path;
  }

  /**
   * Calculates x coordinate by using the ratio of original user's mouse
   * location to the original panorama size.
   * */
  get labelX(): number {
    return (270 * this.card!.canvasX) / this.card!.canvasWidth - 5;
  }

  /**
   * Calculates y coordinate by using the ratio of original user's mouse
   * location to the original panorama size.
   * */
  get labelY(): number {
    return (202.5 * this.card!.canvasY) / this.card!.canvasHeight - 5
  }

  get severity(): number|undefined {
      return this.card!.severity;
  }

  get tags(): string[]|undefined {
      return Array.from(this.card!.tags.values());
  }

  togglePanel(): void {
    this.displayPanel = !this.displayPanel;
  }

  submitValidation(result: number): void {
    if (this.validationResult === result) {
      this.validationResult = -1;
    } else {
      this.validationResult = result;
      this.validationEvent.emit(new ValidationEvent(this.card!.labelId,
        this.validationResult));
    }
  }
}
