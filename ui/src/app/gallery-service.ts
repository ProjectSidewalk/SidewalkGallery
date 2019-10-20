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

        } else {
            // Hardcoded metadata to retrieve -- use since we don't have a backend.
            images.push(new Card("card1", "url1"));
            images.push(new Card("card2", "url2"));
            images.push(new Card("card3", "url3"));
        }
        return images;
    }
}
