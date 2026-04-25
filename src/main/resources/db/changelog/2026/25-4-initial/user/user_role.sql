CREATE TABLE user_role
(
    user_id UUID   NOT NULL,
    role    BIGINT NOT NULL,

    PRIMARY KEY (user_id, role),

    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_user_role_ref
        FOREIGN KEY (role)
            REFERENCES "reference" (id)
            ON DELETE CASCADE
);