create extension hstore;
create schema tweets;
create table if not exists tweets."Tweet" ("tweet_id" BIGSERIAL NOT NULL PRIMARY KEY,"tweet_text" VARCHAR NOT NULL,"engagement_ratio" VARCHAR NOT NULL,"sentiment_score" VARCHAR NOT NULL);
