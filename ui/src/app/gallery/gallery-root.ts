import { Component } from '@angular/core';

import { Constants } from '../constants';

import {CardInterface, GalleryService, TagInterface} from '../gallery-service';
import { Card } from '../cards/card';
import {Subscription} from "rxjs";
import {Tag} from "../cards/tags";

/**
 * The top level-component for a sidewalk gallery. Holds multiple individual label galleries inside.
 */
@Component({
    selector: 'gallery-root',
    templateUrl: './gallery-root.html',
    styleUrls: ['./gallery-root.css']
})
export class GalleryRoot {
  title: string | undefined;
  maxCount: number;

  curbCards: Card[] = [];
  missingCurbCards: Card[] = [];
  surfaceProbCards: Card[] = [];
  obstacleCards: Card[] = [];
  noSidewalkCards: Card[] = [];

  curbTags: Tag[] = [];

  private curbRampSubscription: Subscription|undefined;
  private missingCurbRampSubscription: Subscription|undefined;
  private obstacleSubscription: Subscription|undefined;
  private sfcpSubscription: Subscription|undefined;
  private noSidewalkSubscription: Subscription|undefined;

  private curbTagsSubscription: Subscription|undefined;

  constructor(private galleryService: GalleryService) {
      this.maxCount = 8;
  }

  ngOnInit() {
    console.log("[gallery-root] initializing");
    this.addSubscriptions();

  }


  private jsonToCards(json: CardInterface): Card {
    return new Card(json);
  }

  private jsonToTags(json: TagInterface): Tag {
    return new Tag(json);
  }

  /**
   * Adds a subscription for each type of label.
   */
  private addSubscriptions(): void {
    this.curbRampSubscription = this.galleryService.getCurbRamps().subscribe(
      result => {
        this.curbCards = result.map(x => this.jsonToCards(x))
      });

    this.missingCurbRampSubscription = this.galleryService.getMissingCurbRamps().subscribe(
      result => {
        this.missingCurbCards = result.map(x => this.jsonToCards(x))
      });

    this.obstacleSubscription = this.galleryService.getObstacles().subscribe(
      result => {
        this.obstacleCards = result.map(x => this.jsonToCards(x))
      });

    this.sfcpSubscription = this.galleryService.getSurfaceProblems().subscribe(
      result => {
        this.surfaceProbCards = result.map(x => this.jsonToCards(x))
      });


    this.noSidewalkSubscription = this.galleryService.getNoSidewalk().subscribe(
      result => {
        this.noSidewalkCards = result.map(x => this.jsonToCards(x))
      });

    this.curbTagsSubscription = this.galleryService.getTags(1).subscribe(
      result => {
        this.curbTags = result.map(x => this.jsonToTags(x))
      });
  }
}
