import { Component, Input } from '@angular/core';

import { Card } from '../cards/card';
import {Tag} from "../cards/tag";
import {GalleryTag} from "../cards/gallery-tag";

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
  @Input() tags: Tag[] | undefined;

  private printCards(): void {
    console.log(this.cards);
    console.log(this.tags);

    this.tags!.forEach(tag => {
      console.log(tag)
    });


  }
}
