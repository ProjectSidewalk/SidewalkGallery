import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Tag} from "./tag";

export class TagEvent {
  labelTypeId: number;
  selected: boolean;
  tag: string;
  tagId: number;

  constructor (labelTypeId: number, selected: boolean, tag: string, tagId: number) {
    this.labelTypeId = labelTypeId;
    this.selected = selected;
    this.tag = tag;
    this.tagId = tagId;
  }
}

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
  @Output() tagEvent = new EventEmitter<TagEvent>();

  selected: boolean = false;

  get description(): string {
    return this.tag!.tag;
  }

  get labelTypeId(): number {
    return this.tag!.labelTypeId;
  }

  get tagId(): number {
    return this.tag!.tagId;
  }

  toggle(): void {
    this.selected = !this.selected;
    this.tagEvent.emit(new TagEvent(this.labelTypeId, this.selected, this.description, this.tagId));
    console.log("Emitting event: ");
    // console.log(this.description + ": " + this.selected);
  }
}
