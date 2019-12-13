import {CardInterface} from "../gallery-service";

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

    constructor (cardInterface: CardInterface) {
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
