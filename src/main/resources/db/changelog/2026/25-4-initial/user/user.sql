CREATE TABLE "user"
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    password     VARCHAR(255),
    timezone     VARCHAR(100),

    "created_at" TIMESTAMP WITH TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP WITH TIME ZONE
);
