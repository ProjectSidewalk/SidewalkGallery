/**
 * Interface that enforces the data that is retrieved in a CardResponse. Backend responses from the
 * GalleryService that send back information about Cards must return their data in the shape of a
 * CardResponse.
 */
export interface CardResponse {
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
    zoom: number;

    // Custom other fields (haven't been added yet)
    name: string|undefined;
    tags: string[]|undefined;

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
      this.zoom = cardInterface.zoom;
    }
}
