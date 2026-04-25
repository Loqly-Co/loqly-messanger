CREATE TABLE "ref_header" (
      "id" SERIAL PRIMARY KEY,
      "name" VARCHAR(512) NOT NULL,
      "created_at" TIMESTAMP WITH TIME ZONE NOT NULL,
      "updated_at" TIMESTAMP WITH TIME ZONE,
      "comment" VARCHAR(1024)
);