import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/index';

import { Constants } from './constants';

import { Card } from './cards/card';

export interface CardInterface {
  canvas_height: number;
  canvas_width: number;
  canvas_x: number;
  canvas_y: number;
  description: string;
  heading: number;
  image_url: string;         // Should use a SafeURL
  gsv_panorama_id: string;
  label_id: number;
  label_type_id: number;
  pitch: number;
  severity: number;
  zoom: number;

  // name: string;
  // tags: string[];
}

/**
 * Class that handles the transfer of data between Angular and Scala.
 *
 * @class GallleryService.
 */
@Injectable()
export class GalleryService {
  // TODO(@aileenzeng): move to constants
  private serviceUrl = '/api/summary';
  private dataPostTestUrl = '/api/postTest';
  private labelMetadataUrl = '/api/labelMetadata/';

  private responseCards: CardInterface[] = [];

  curbCards: Card[] = [];

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
    if (prod) {
      // Queries endpoints to retrieve metadata for this image type.
      console.log(this.http.post(this.labelMetadataUrl, labelTypeId));
    } else {
      // Hardcoded metadata to retrieve -- use since we don't have a backend.
      if (labelTypeId === Constants.curbRampId) {
          return this.getCurbRampsCards();
      } else if (labelTypeId === Constants.missingCurbRampId) {
          return this.getMissingCurbRampsCards();
      } else if (labelTypeId === Constants.noSidewalkId) {
          return this.getNoSidewalkCards();
      } else if (labelTypeId === Constants.obstacleId) {
          return this.getObstacleCards();
      } else if (labelTypeId === Constants.surfaceProblemId) {
          return this.getSurfaceProblemCards();
      }
    }
    let images: Card[] = [];
    return images;
  }

  ///////////////////////
  // Helper stub functions to generate temporary data for SidewalkGallery.
  ///////////////////////
  private labelQuery(url: String, count: Number): Observable<CardInterface[]> {
    return this.http.get<CardInterface[]>(url + count.toString(10))
  }

  /**
   * Gets curb ramp cards.
   * @param images
   */
  public getCurbRamps(): Observable<CardInterface[]> {
    return this.labelQuery(Constants.curbRampAPI, 10);
  }

  public getMissingCurbRamps(): Observable<CardInterface[]> {
    return this.labelQuery(Constants.missingCurbRampAPI, 10);
  }

  public getObstacles(): Observable<CardInterface[]> {
    return this.labelQuery(Constants.obstacleAPI, 10);
  }


  public getSurfaceProblems(): Observable<CardInterface[]> {
    return this.labelQuery(Constants.surfaceProblemAPI, 10);
  }

  public getNoSidewalk(): Observable<CardInterface[]> {
    return this.labelQuery(Constants.noSidewalkAPI, 10);
  }


  // private getCard(imageUrl: string): Card {
  //   let card: Card = new Card();
  //   card.name = "card6";
  //   card.imageUrl = imageUrl;
  //   card.severity = 2;
  //   card.description = "Hello, this is a very very long description for a curb ramp image.";
  //   card.tags = ["no friction strips", " not enough landing space"];
  //   return card;
  // }
}
