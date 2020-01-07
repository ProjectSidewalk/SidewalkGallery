import { Component, Input } from '@angular/core';

import { Card } from '../cards/card';
import { Tag } from "../cards/tag";
import { TagEvent} from "../cards/gallery-tag";

/**
 * Angular component for the overview of one gallery.
 */
@Component({
  selector: 'gallery-overview',
  templateUrl: './gallery-overview.html',
  styleUrls: ['./gallery-overview.css']
})
export class GalleryOverview {
  // All possible label cards that should be displayed in this section of the gallery.
  // cards should not be modified by the client, and should only be updated upon some backend
  // request.
  @Input() cards: Card[] = [];

  // The label cards that should be displayed in this section of the gallery. Modified when the user
  // clicks some tag on the top of the gallery menu to filter out by.
  displayCards: Set<Card> = new Set<Card>();

  // The title of this gallery. (Should be the name of the label that this gallery overview is
  // representing.
  @Input() title: string = '';

  // The tags that are associated with the given gallery.
  @Input() tags: Tag[] = [];

  ngOnChanges() {
    console.log("View loaded!");
    console.log("Cards");
    console.log(this.cards);
    for (let card of this.cards) {
      this.displayCards.add(card);
    }
  }

  /**
   * Applies filters to display new cards on the gallery.
   * @param event Event that displays the status of the current cards.
   */
  private filterCards(event: TagEvent) {
    if (event.selected) {
      this.removeCards(event.tagId);
    } else {
      this.addCards(event.tagId);
    }
  }

  /**
   * Removes cards that don't have the given ID to be displayed from the GalleryOverview.
   * @param tagId
   */
  private removeCards(tagId: number) {
    console.log("removing cards");
    console.log(this.displayCards);
    for (let card of this.displayCards.values()) {
      console.log(card);
      if (!card.tags.has(tagId)) {
        this.displayCards.delete(card);
      }
    }
    console.log(this.displayCards);

  }

  private addCards(tagId: number) {
    console.log("adding cards");
    for (let card of this.cards) {
      if (!card.tags.has(tagId) && !this.displayCards.has(card)) {
        this.displayCards.add(card);
      }
    }
    console.log(this.displayCards);
  }
}
