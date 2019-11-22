# --- !Ups
CREATE TABLE label_type
(
    label_type_id INTEGER NOT NULL,
    description TEXT NOT NULL,
    PRIMARY KEY (label_type_id)
);

CREATE TABLE label
(
    label_id INTEGER NOT NULL,
    label_type_id INTEGER NOT NULL,
    user_id TEXT NOT NULL,
    gsv_panorama_id TEXT NOT NULL,
    canvas_x INT NOT NULL,
    canvas_y INT NOT NULL,
    heading DOUBLE PRECISION NOT NULL,
    pitch DOUBLE PRECISION NOT NULL,
    zoom DOUBLE PRECISION NOT NULL,
    canvas_height INT NOT NULL,
    canvas_width INT NOT NULL,
    severity INT,
    description TEXT,
    PRIMARY KEY (label_id),
    FOREIGN KEY (label_type_id) REFERENCES label_type(label_type_id)
);

CREATE TABLE tag
(
    tag_id SERIAL NOT NULL,
    label_type_id INT NOT NULL,
    tag TEXT NOT NULL,
    PRIMARY KEY (tag_id),
    FOREIGN KEY (label_type_id) REFERENCES label_type(label_type_id)
);

CREATE TABLE label_tags
(
    label_tag_id SERIAL NOT NULL,
    label_id INTEGER NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (label_id) REFERENCES label(label_id),
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);

# --- !Downs
DROP TABLE label_type;
DROP TABLE label;
DROP TABLE label_tags;
DROP TABLE tag;