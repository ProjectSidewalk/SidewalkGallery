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

  // Filters that have been applied on this gallery overview.
  filters: Set<number> = new Set<number>();

  // The title of this gallery. (Should be the name of the label that this gallery overview is
  // representing.
  @Input() title: string = '';

  // The tags that are associated with the given gallery.
  @Input() tags: Tag[] = [];

  /**
   * Angular hook that listens for changes on any of the inputs.
   * TODO(@aileenz): Might want to change this, because we only want to update displayCards if
   * the cards input is changed.
   */
  ngOnChanges() {
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
      this.filters.add(event.tagId);
      this.removeCards(event.tagId);
    } else {
      this.filters.delete(event.tagId);
      this.addCards(event.tagId);
    }
  }

  /**
   * Removes cards that don't have the given ID to be displayed from the GalleryOverview.
   * @param tagId TagID of the tag that has been selected.
   */
  private removeCards(tagId: number) {
    for (let card of this.displayCards.values()) {
      if (!card.tags.has(tagId)) {
        this.displayCards.delete(card);
      }
    }
  }

  /**
   * Adds cards to be displayed onto the gallery after applying a filter.
   * @param tagId TagID of the tag that has been unselected. Elements that do not have this tag, but
   * do have the rest of the tags that have been applied should be added back into the gallery.
   */
  private addCards(tagId: number) {
    // Check all cards to see if there are new cards that need to be added back into the
    // display after unapplying a filter.
    for (let card of this.cards) {
      // If the card is not in the display cards, but does have the tag id, then check
      // to see if we should add it back in.
      if (!card.tags.has(tagId) && !this.displayCards.has(card)) {
        let addCard: boolean = true;

        // Only add in tags that have no common filters with the current filters that have
        // been applied.
        for (let tag of this.filters) {
          if (!card.tags.has(tag)) {
            addCard = false;
          }
        }
        if (addCard) {
          this.displayCards.add(card);
        }
      }
    }
  }
}
