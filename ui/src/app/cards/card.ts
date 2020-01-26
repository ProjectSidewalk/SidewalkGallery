import {CardResponse} from "../models/card-response";

export interface CardTag {
  description: string;
  tag_id: number;
}

/**
 * Front-end model representation of a Card.
 */
export class Card {
    canvasHeight: number;
    canvasWidth: number;
    canvasX: number;
    canvasY: number;
    description: string|undefined;
    heading: number;
    imageUrl: string;     // Should use a SafeURL
    gsvPanoramaId: string;
    labelId: number;
    labelTypeId: number;
    pitch: number;
    severity: number|undefined;
    tags: Map<number, string>;
    zoom: number;

  /**
   * Converts from a response to a Card object.
   * @param cardInterface
   */
  constructor (cardInterface: CardResponse) {
    this.canvasHeight = cardInterface.canvas_height;
    this.canvasWidth = cardInterface.canvas_width;
    this.canvasX = cardInterface.canvas_x;
    this.canvasY = cardInterface.canvas_y;
    this.description = cardInterface.description;
    this.heading = cardInterface.heading;
    this.imageUrl = cardInterface.image_url;
    this.gsvPanoramaId = cardInterface.gsv_panorama_id;
    this.labelId = cardInterface.label_id;
    this.labelTypeId = cardInterface.label_type_id;
    this.pitch = cardInterface.pitch;
    this.severity = cardInterface.severity;
    this.tags = new Map();
    this.zoom = cardInterface.zoom;

    let tagArray = cardInterface.tags;
    // Populates the tag map from the JSON Array values.

    tagArray.forEach(tag => {
      this.tags.set(tag.tag_id, tag.description);
    });

  }
}
