import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/index';

import { Constants } from './constants';

import { Card } from './cards/card';
import { CardResponse } from "./models/card-response";
import { TagResponse } from "./models/tag-response";
import {ValidationEvent} from "./cards/gallery-card";

/**
 * Class that handles the transfer of data between Angular and Scala.
 *
 * @class GallleryService.
 */
@Injectable()
export class GalleryService {
  // private serviceUrl = '/api/summary';
  private serviceUrl = '/api/test';
  private dataPostTestUrl = '/api/postTest';
  private dataGetTestUrl = '/api/getTest';

  private maxLabels: Number = 2;

  constructor(private http: HttpClient) { }

  /**
   * Makes a http get request to retrieve the welcome message from the backend
   * service. (Used for testing).
   */
  public getWelcomeMessage() {
    return this.http.get(this.serviceUrl).pipe(
      map(response => response)
    );
  }

  /*****************************************************************************
   * GET Utils
   ****************************************************************************/
  public labelQuery(url: String, count: Number): Observable<CardResponse[]> {
    return this.http.get<CardResponse[]>(url + count.toString(10))
  }

  /**
   * Gets curb ramp cards.
   * @param images
   */
  public getCurbRamps(): Observable<CardResponse[]> {
    return this.labelQuery(Constants.curbRampAPI, this.maxLabels);
  }

  public getMissingCurbRamps(): Observable<CardResponse[]> {
    return this.labelQuery(Constants.missingCurbRampAPI, this.maxLabels);
  }

  public getObstacles(): Observable<CardResponse[]> {
    return this.labelQuery(Constants.obstacleAPI, this.maxLabels);
  }

  public getSurfaceProblems(): Observable<CardResponse[]> {
    return this.labelQuery(Constants.surfaceProblemAPI, this.maxLabels);
  }

  public getNoSidewalk(): Observable<CardResponse[]> {
    return this.labelQuery(Constants.noSidewalkAPI, this.maxLabels);
  }

  public getTags(labelTypeId: number): Observable<TagResponse[]> {
    return this.http.get<TagResponse[]>(Constants.tagsAPI
      + labelTypeId.toString(10));
  }

  /*****************************************************************************
   * POST Utils
   ****************************************************************************/
  public submitValidationResult(result: ValidationEvent): Observable<any> {
    return this.http.post(Constants.validationPost, result);
  }
}
