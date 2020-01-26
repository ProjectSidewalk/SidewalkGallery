/**
 * Interface that enforces the data that is retrieved in a CardResponse. Backend responses from the
 * GalleryService that send back information about Cards must return their data in the shape of a
 * CardResponse.
 */
import { CardTag } from "../cards/card";

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
  tags: CardTag[];
  zoom: number;
}
