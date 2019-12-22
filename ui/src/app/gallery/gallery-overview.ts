import { Component, Input } from '@angular/core';

import { Card } from '../cards/card';
import { Tag } from "../cards/tag";
import {GalleryTag, TagEvent} from "../cards/gallery-tag";

/**
 * Angular component for the overview of one gallery.
 */
@Component({
  selector: 'gallery-overview',
  templateUrl: './gallery-overview.html',
  styleUrls: ['./gallery-overview.css']
})
export class GalleryOverview {
  // The label cards that should be displayed in this section of the gallery.
  @Input() cards: Card[] = [];

  // The title of this gallery. (Should be the name of the label that this gallery overview is
  // representing.
  @Input() title: string = '';

  // The tags that are associated with the given gallery.
  @Input() tags: Tag[] = [];

  private printTag(event: TagEvent) {
    console.log(event);
  }
}
