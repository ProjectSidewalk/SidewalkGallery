# --- !Ups
CREATE TABLE gallery_validations
(
    gallery_validation_id SERIAL NOT NULL,
    label_id INTEGER NOT NULL,
    validation_option_id INTEGER NOT NULL,
    PRIMARY KEY (gallery_validation_id)
);

# -- !Downs
DROP TABLE gallery_validations

ALTER TABLE label
    ADD user_id TEXT NOT NULL;
