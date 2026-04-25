CREATE TABLE user_authority
(
    user_id   UUID   NOT NULL,
    authority BIGINT NOT NULL,

    PRIMARY KEY (user_id, authority),

    CONSTRAINT fk_user_auth_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_user_auth_ref
        FOREIGN KEY (authority)
            REFERENCES "reference" (id)
            ON DELETE CASCADE
);