import { Component, Input } from '@angular/core';
import {MatButtonModule} from "@angular/material/button";
import {Tag} from "./tags";


@Component({
  selector: 'gallery-tags',
  templateUrl: './gallery-tags.html',
  styleUrls: ['./gallery-tags.css']
})
/**
 * Front-end representation of one card in the gallery.
 */
export class GalleryTags {
  // Metadata object containing information about this GalleryCard.
  @Input() tag: Tag | undefined;
  selected: boolean = false;

  get description(): string {
    return this.tag!.tag;
  }

  toggle(): void {
    this.selected = !this.selected;
    console.log(this.description + ": " + this.selected);
  }
}
