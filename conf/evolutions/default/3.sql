# --- !Ups
CREATE TABLE gallery_validation_options
(
    validation_option_id INTEGER NOT NULL,
    description TEXT NOT NULL,
    PRIMARY KEY (validation_option_id)
);

INSERT INTO gallery_validation_options VALUES (1, 'Agree');
INSERT INTO gallery_validation_options VALUES (2, 'Disagree');
INSERT INTO gallery_validation_options VALUES (3, 'Not sure');

# -- !Downs
DROP TABLE gallery_validation_options
