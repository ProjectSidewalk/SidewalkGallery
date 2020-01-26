import {TagResponse} from "../models/tag-response";

export class Tag {
  labelTypeId: number;
  tag: string;
  tagId: number;

  constructor (tagInterface: TagResponse) {
    this.labelTypeId = tagInterface.label_type_id;
    this.tag = tagInterface.tag;
    this.tagId = tagInterface.tag_id;
  }
}
