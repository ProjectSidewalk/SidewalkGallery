import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/index';

import { Constants } from './constants';

import { Card } from './cards/card';

/**
 * Class that handles the transfer of data between Angular and Scala.
 *
 * @class GallleryService.
 */
@Injectable()
export class GalleryService {
  // TODO: move to constants
  private serviceUrl = '/api/summary';
  private dataPostTestUrl = '/api/postTest';
  private labelMetadataUrl = '/api/labelMetadata/';

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
  public sendData(card: Card): Observable<any> {
    console.log("[gallery-service] Sending post request to " + this.dataPostTestUrl);
    console.log("[gallery-service] Sending card");
    console.log(card);
    return this.http.post(this.dataPostTestUrl, {});
  }

  /**
   * Retrieves all the metadata for the images associated with some label id.˜
   * @param labelTypeId The label type to fetch metadata from
   * @param maxCards    Maximum number of card results to fetch
   * @param prod        Whether we should use production data or not.
   */
  public getLabelMetadata(labelTypeId: number, maxCards: number, prod: boolean): Card[] {
    let images: Card[] = [];
    if (prod) {
      // Queries endpoints to retrieve metadata for this image type.
      console.log(this.http.post(this.labelMetadataUrl, labelTypeId));
    } else {
      // Hardcoded metadata to retrieve -- use since we don't have a backend.
      let i = 0;
      if (labelTypeId === Constants.curbRampId) {
          this.getCurbRampsCards(images);
      } else if (labelTypeId === Constants.missingCurbRampId) {
          this.getMissingCurbRampsCards(images);
      } else if (labelTypeId === Constants.noSidewalkId) {
          this.getNoSidewalkCards(images);
      } else if (labelTypeId === Constants.obstacleId) {
          this.getObstacleCards(images);
      } else if (labelTypeId === Constants.surfaceProblemId) {
          this.getSurfaceProblemCards(images);
      }
    }
    return images;
  }

  ///////////////////////
  // Helper stub functions to generate temporary data for SidewalkGallery.
  ///////////////////////

  testHttpCurbRampRequest(): void {
    this.http.get(Constants.curbRampAPI + "10")
      .subscribe(
        results => console.log(results)
      );
  }

  /**
   * Gets curb ramp cards.
   * @param images
   */
  private getCurbRampsCards(images: Card[]): void {
    // Fake data
    let i = 0;
    while (i < Constants.maxCards) {
      images.push(this.getCard("curb-ramp-example.png"));
      i++;
    }
  }

  private getMissingCurbRampsCards(images: Card[]): void {
    let i = 0;
    while (i < Constants.maxCards) {
      images.push(this.getCard("missing-curb-ramp-example.png"));
      i++;
    }
  }

  private getObstacleCards(images: Card[]): void {
    let i = 0;
    while (i < Constants.maxCards) {
      images.push(this.getCard("obstacle-example.png"));
      i++;
    }
  }

  private getSurfaceProblemCards(images: Card[]): void {
    let i = 0;
    while (i < Constants.maxCards) {
      images.push(this.getCard("surface-problem-example.png"));
      i++;
    }
  }

  private getNoSidewalkCards(images: Card[]): void {
    let i = 0;
    while (i < Constants.maxCards) {
      images.push(this.getCard("curb-ramp-example.png"));
      i++;
    }
  }


  private getCard(imageUrl: string): Card {
    let card: Card = new Card();
    card.name = "card6";
    card.imageUrl = imageUrl;
    card.severity = 2;
    card.description = "Hello, this is a very very long description for a curb ramp image.";
    card.tags = ["no friction strips", " not enough landing space"];
    return card;
  }
}
