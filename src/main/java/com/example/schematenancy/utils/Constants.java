package com.example.schematenancy.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class Constants {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String SQL_SCHEMA_POPULATE = "CREATE TABLE IF NOT EXISTS {0}.to_do\n" +
            "(\n" +
            "    to_do_id uuid NOT NULL,\n" +
            "    created_at timestamp without time zone,\n" +
            "    description character varying(255) COLLATE pg_catalog.\"default\",\n" +
            "    end_at timestamp without time zone,\n" +
            "    importance integer,\n" +
            "    modified_at timestamp without time zone,\n" +
            "    start_at timestamp without time zone,\n" +
            "    CONSTRAINT to_do_pkey PRIMARY KEY (to_do_id)\n" +
            ")\n" +
            "\n" +
            "TABLESPACE pg_default;\n" +
            "\n" +
            "ALTER TABLE IF EXISTS {0}.to_do\n" +
            "    OWNER to postgres;";

}
