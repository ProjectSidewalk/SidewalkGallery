import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/index';

import { Card } from './cards/card';

/**
 * Class that handles the transfer of data between Angular and Scala.
 *
 * @class GallleryService.
 */
@Injectable()
export class GalleryService {
    private serviceUrl = '/api/summary';
    private dataPostTestUrl = '/api/postTest';
    private labelMetadataUrl = '/api/labelMetadata/'

    constructor(private http: HttpClient) { }

    /**
     * Makes a http get request to retrieve the welcome message from the backend service.
     */
    public getWelcomeMessage() {
        return this.http.get(this.serviceUrl).pipe(
            map(response => response)
        );
    }

    /**
     * Makes a http post request to send some data to backend & get response.
     */
    public sendData(): Observable<any> {
        return this.http.post(this.dataPostTestUrl, {});
    }

    /**
     * Retrieves all the metadata for the images associated with some label id.˜
     * @param labelTypeId 
     */
    public getLabelMetadata(labelTypeId: number, prod: boolean): Card[] {
        let images: Card[] = [];
        if (prod) {
            // Queries endpoints to retrieve metadata for this image type.
            console.log(this.http.post(this.labelMetadataUrl, labelTypeId));
        } else {
            // Hardcoded metadata to retrieve -- use since we don't have a backend.
            images.push(this.getCard());
            images.push(this.getCard());
            images.push(this.getCard());
            images.push(this.getCard());
            images.push(this.getCard());
            images.push(this.getCard());
        }
        return images;
    }

    private getCard(): Card {
        let card: Card = new Card();
        card.name = "card6";
        card.imageUrl = "curb-ramp-example.png";
        card.severity = 2;
        card.description = "Hello, this is a very very long description for a curb ramp image.";
        return card;
    }
}
