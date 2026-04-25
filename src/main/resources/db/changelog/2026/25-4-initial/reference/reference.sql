CREATE TABLE "reference" (
     "id" BIGSERIAL PRIMARY KEY,
     "value" VARCHAR NOT NULL,
     "code" INTEGER NOT NULL,
     "note" VARCHAR NOT NULL UNIQUE,
     "header_id" INTEGER NOT NULL REFERENCES ref_header(id),
     "comment" VARCHAR(1024),
     "created_at" TIMESTAMP WITH TIME ZONE NOT NULL,
     "updated_at" TIMESTAMP WITH TIME ZONE
);