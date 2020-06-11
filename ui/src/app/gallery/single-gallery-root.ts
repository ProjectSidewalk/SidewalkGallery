import { Component } from '@angular/core';
import {GalleryService} from "../gallery-service";
import {ActivatedRoute, Router} from "@angular/router";
import {Constants} from "../constants";
import {Subscription} from "rxjs";
import {Tag} from "../tags/tag";
import {Card} from "../cards/card";
import {CardResponse} from "../models/card-response";
import {TagResponse} from "../models/tag-response";

@Component({
  selector: 'gallery-individual',
  templateUrl: './single-gallery-root.html',
  styleUrls: ['./single-gallery-root.css']
})

export class SingleGalleryRoot {
  private title: string = '';
  private labelType: string = '';
  private cardQueryAPI: string = '';
  private tagQueryAPI: string = '';

  private routeParamsSubscription: Subscription = new Subscription();
  private cardSubscription: Subscription = new Subscription();
  private tagSubscription: Subscription = new Subscription();

  cards: Card[] = [];
  tags: Tag[] = [];

  constructor(private galleryService: GalleryService,
              private route: ActivatedRoute,
              private router: Router) {
    console.log("Created single gallery root");
  }

  ngOnInit(): void {
    // Gets the label type from the route parameter.
    this.routeParamsSubscription = this.route.params.subscribe(params => {
      let param: string = params['labelType'];
      if (param === 'curbramp' || param === 'mcr' || param === 'obstacle'
          || param === 'sfcp' || param === 'nosidewalk') {

        this.cards = [];
        this.tags = [];

        // TODO (aileenzeng): Cleanup the API for service calls.
        this.labelType = param;
        this.title = this.paramToTitle(param);
        this.cardQueryAPI = '/api/' + param + '/';
        this.tagQueryAPI = Constants.tagsAPI + this.labelNameToId(param);

        this.cardSubscription = this.galleryService
          .labelQuery(this.cardQueryAPI, 8)
          .subscribe(result =>{
            this.cards = result.map(this.jsonToCards);
          });

        this.tagSubscription = this.galleryService
          .getTags(this.labelNameToId(this.labelType))
          .subscribe(result => {
            this.tags = result.map(this.jsonToTags);
          });
      } else {
        // Hacky fix to redirect to the main page, if an invalid label type is
        // passed in.
        this.routeParamsSubscription.unsubscribe();
        this.router.navigate(['/gallery'], {relativeTo: this.route});
      }
    });
  }

  private ngOnDestroy() {
    this.routeParamsSubscription.unsubscribe();
  }

  /*****************************************************************************
   * Utils (TODO: move into actual util file later)
   ****************************************************************************/
  private jsonToCards(json: CardResponse): Card {
    return new Card(json);
  }

  private jsonToTags(json: TagResponse): Tag {
    return new Tag(json);
  }

  private labelNameToId(labelType: string) : number {
    if (labelType === 'curbramp') {
      return Constants.curbRampId;
    } else if (labelType === 'mcr') {
      return Constants.missingCurbRampId;
    } else if (labelType === 'nosidewalk') {
      return Constants.noSidewalkId;
    } else if (labelType === 'obstacle') {
      return Constants.obstacleId;
    } else if (labelType === 'sfcp') {
      return Constants.surfaceProblemId;
    } else {
      return 0;
    }
  }

  private paramToTitle(labelType: string) : string {
    if (labelType === 'curbramp') {
      return 'Curb Ramp';
    } else if (labelType === 'mcr') {
      return 'Missing Curb Ramp';
    } else if (labelType === 'nosidewalk') {
      return 'Missing Sidewalk';
    } else if (labelType === 'obstacle') {
      return 'Obstacle in Path';
    } else if (labelType === 'sfcp') {
      return 'Surface Problem'
    } else {
      return '';
    }
  }
}

