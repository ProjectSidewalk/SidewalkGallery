import { Component } from '@angular/core';

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
    postRequestResponse: string | undefined;

    curbCards: Card[] | undefined;
    missingCurbCards: Card[] | undefined;
    surfaceProbCards: Card[] | undefined;
    obstacleCards: Card[] | undefined;
    missingSidewalkCards: Card[] | undefined;



    // TODO: Pass in a different service (GalleryService).
    constructor(private galleryService: GalleryService) {
        console.log("This is the Gallery Component");
    }

    ngOnInit() {
        console.log("initializing");
        this.curbCards = this.galleryService.getLabelMetadata(1, false);
        this.missingCurbCards = this.galleryService.getLabelMetadata(2, false);
        this.surfaceProbCards = this.galleryService.getLabelMetadata(3, false);
        this.obstacleCards = this.galleryService.getLabelMetadata(4, false);
        this.missingSidewalkCards = this.galleryService.getLabelMetadata(7, false);
    }

    /**
     * This method is used to test the post request
     */
    public postData(): void {
        console.log("Posting data");
        this.galleryService.sendData().subscribe((data: any) => {
            this.postRequestResponse = data.content;
        });
    }
}
