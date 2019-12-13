import { Component } from '@angular/core';

import { Constants } from '../constants';

import {CardInterface, GalleryService} from '../gallery-service';
import { Card } from '../cards/card';
import {Subscription} from "rxjs";

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
    postRequestResponse: string | undefined;

    curbCards: Card[] | undefined;
    missingCurbCards: Card[] | undefined;
    surfaceProbCards: Card[] | undefined;
    obstacleCards: Card[] | undefined;
    missingSidewalkCards: Card[] | undefined;

    private curbRampSubscription: Subscription|undefined;

    constructor(private galleryService: GalleryService) {
        this.maxCount = 8;
    }

    private jsonToCards(json: CardInterface): Card {
      return new Card(json);
    }

    ngOnInit() {
        console.log("[gallery-root] initializing");
        this.curbCards = this.galleryService.curbCards;
        // this.curbCards = this.galleryService.getLabelMetadata(Constants.curbRampId, this.maxCount, false);
        this.missingCurbCards = this.galleryService.getLabelMetadata(Constants.missingCurbRampId, this.maxCount, false);
        this.surfaceProbCards = this.galleryService.getLabelMetadata(Constants.surfaceProblemId, this.maxCount, false);
        this.obstacleCards = this.galleryService.getLabelMetadata(Constants.obstacleId, this.maxCount, false);
        this.missingSidewalkCards = this.galleryService.getLabelMetadata(Constants.missingCurbRampId, this.maxCount, false);


        this.curbRampSubscription = this.galleryService.labelQuery(
          Constants.curbRampAPI, this.maxCount).subscribe(
          result => {this.curbCards = result.map(x => this.jsonToCards(x))});

  }

    /**
     * This method is used to test the post request
     */
    public postData(): void {
        console.log("[gallery-root] Testing curb ramp request");
        this.galleryService.getLabelMetadata(Constants.curbRampId, 10, false);
    }
}
