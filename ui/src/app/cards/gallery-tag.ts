import { Component, Input } from '@angular/core';
import {MatButtonModule} from "@angular/material/button";
import {Tag} from "./tag";


@Component({
  selector: 'gallery-tags',
  templateUrl: './gallery-tag.html',
  styleUrls: ['./gallery-tag.css']
})
/**
 * Front-end representation of one card in the gallery.
 */
export class GalleryTag {
  // Metadata object containing information about this GalleryCard.
  @Input() tag: Tag | undefined;
  selected: boolean = false;

  constructor (tag: Tag) {
    this.tag = tag;
  }

  get description(): string {
    return this.tag!.tag;
  }

  toggle(): void {
    this.selected = !this.selected;
    console.log(this.description + ": " + this.selected);
  }
}
