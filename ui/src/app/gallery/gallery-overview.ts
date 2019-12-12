import { Component, Input } from '@angular/core';

import { Card } from '../cards/card';

/**
 * Angular component for the overview of one gallery.
 */
@Component({
  selector: 'gallery-overview',
  templateUrl: './gallery-overview.html',
  styleUrls: ['./gallery-overview.css']
})
export class GalleryOverview {
  @Input() cards: Card[] | undefined;
  @Input() title: string | undefined;
  maxItems: number;
  
  constructor() {
    this.maxItems = 16;
  }
}