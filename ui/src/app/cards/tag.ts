export interface TagResponse {
  label_type_id: number;
  tag: string;
  tag_id: number;
}

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
