import { Component } from '@angular/core';

import { Subscription } from "rxjs";
import { GalleryService } from '../gallery-service';
import { Card } from '../cards/card';
import { CardResponse} from "../models/card-response";
import { Tag } from "../tags/tag";
import { TagResponse } from "../models/tag-response";
import { Constants } from "../constants";

/**
 * The top level-component for a sidewalk gallery. Holds multiple individual
 * label galleries inside.
 * TODO: Rename this to something like gallery-landing-root?
 */
@Component({
    selector: 'gallery-root',
    templateUrl: './gallery-root.html',
    styleUrls: ['./gallery-root.css']
})
export class GalleryRoot {
  title: string | undefined;

  curbCards: Card[] = [];
  missingCurbCards: Card[] = [];
  surfaceProbCards: Card[] = [];
  obstacleCards: Card[] = [];
  noSidewalkCards: Card[] = [];

  curbTags: Tag[] = [];

  private curbRampSubscription: Subscription = new Subscription();
  private missingCurbRampSubscription: Subscription = new Subscription();
  private obstacleSubscription: Subscription = new Subscription();
  private sfcpSubscription: Subscription = new Subscription();
  private noSidewalkSubscription: Subscription = new Subscription();

  private curbTagsSubscription: Subscription = new Subscription();

  constructor(private galleryService: GalleryService) {
      this.addSubscriptions();
  }

  private jsonToCards(json: CardResponse): Card {
    return new Card(json);
  }

  private jsonToTags(json: TagResponse): Tag {
    return new Tag(json);
  }

  /**
   * Adds a subscription for each type of label.
   */
  private addSubscriptions(): void {
    this.curbRampSubscription = this.galleryService.getCurbRamps().subscribe(
      result => {
        this.curbCards = result.map(x => this.jsonToCards(x));
      });

    this.missingCurbRampSubscription = this.galleryService.getMissingCurbRamps().subscribe(
      result => {
        this.missingCurbCards = result.map(x => this.jsonToCards(x));
      });

    this.obstacleSubscription = this.galleryService.getObstacles().subscribe(
      result => {
        this.obstacleCards = result.map(x => this.jsonToCards(x));
      });

    this.sfcpSubscription = this.galleryService.getSurfaceProblems().subscribe(
      result => {
        this.surfaceProbCards = result.map(x => this.jsonToCards(x));
      });


    this.noSidewalkSubscription = this.galleryService.getNoSidewalk().subscribe(
      result => {
        this.noSidewalkCards = result.map(x => this.jsonToCards(x));
      });

    this.curbTagsSubscription = this.galleryService.getTags(Constants.curbRampId).subscribe(
      result => {
        this.curbTags = result.map(x => this.jsonToTags(x));
      });
  }

  private ngOnDestroy() {
    this.curbRampSubscription.unsubscribe();
    this.missingCurbRampSubscription.unsubscribe();
    this.obstacleSubscription.unsubscribe();
    this.sfcpSubscription.unsubscribe();
    this.noSidewalkSubscription.unsubscribe();
    this.curbTagsSubscription.unsubscribe();
  }
}
