import {TagInterface} from "../gallery-service";

export class Tag {
  labelTypeId: number;
  tag: string;
  tagId: number;

  constructor (tagInterface: TagInterface) {
    this.labelTypeId = tagInterface.label_type_id;
    this.tag = tagInterface.tag;
    this.tagId = tagInterface.tag_id;
  }
}
