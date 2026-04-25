CREATE TABLE person_main
(
    id         UUID PRIMARY KEY,
    username   VARCHAR(255)             NOT NULL,
    email      VARCHAR(255)             NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    birthdate  DATE,

    "created_at" TIMESTAMP WITH TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_person_user
        FOREIGN KEY (id)
            REFERENCES "user" (id)
            ON DELETE CASCADE,

    CONSTRAINT username_uniq unique (username),
    CONSTRAINT email_uniq unique (email)
);