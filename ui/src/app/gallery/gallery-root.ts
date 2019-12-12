import { Component } from '@angular/core';

import { Constants } from '../constants';

import { GalleryService } from '../gallery-service';
import { Card } from '../cards/card';

/** 
 * The top level-component for a sidewalk gallery. Holds multiple individual label galleries inside.
 */
@Component({
    selector: 'gallery-root',
    templateUrl: './gallery-root.html',
    styleUrls: ['./gallery-root.css']
})
export class GalleryRoot {
    title: string | undefined
    maxCount: number;
    postRequestResponse: string | undefined;

    curbCards: Card[] | undefined;
    missingCurbCards: Card[] | undefined;
    surfaceProbCards: Card[] | undefined;
    obstacleCards: Card[] | undefined;
    missingSidewalkCards: Card[] | undefined;

    constructor(private galleryService: GalleryService) {
        this.maxCount = 8;
    }

    ngOnInit() {
        console.log("[gallery-root] initializing");
        this.curbCards = this.galleryService.getLabelMetadata(Constants.curbRampId, this.maxCount, false);
        this.missingCurbCards = this.galleryService.getLabelMetadata(Constants.missingCurbRampId, this.maxCount, false);
        this.surfaceProbCards = this.galleryService.getLabelMetadata(Constants.surfaceProblemId, this.maxCount, false);
        this.obstacleCards = this.galleryService.getLabelMetadata(Constants.obstacleId, this.maxCount, false);
        this.missingSidewalkCards = this.galleryService.getLabelMetadata(Constants.missingCurbRampId, this.maxCount, false);
    }

    /**
     * This method is used to test the post request
     */
    public postData(): void {
        console.log("[gallery-root] Posting data");
        this.galleryService.sendData(this.curbCards![0]).subscribe((data: any) => {
            this.postRequestResponse = data.content;
        });
    }
}
