CREATE ROLE pay_service WITH
    NOSUPERUSER
    NOCREATEDB
    NOCREATEROLE
    INHERIT
    LOGIN
    NOREPLICATION
    NOBYPASSRLS
    CONNECTION LIMIT -1
    PASSWORD 'emNQfh1bU1';


CREATE SCHEMA pay_service AUTHORIZATION pay_service;


-- Permissions

GRANT ALL ON SCHEMA pay_service TO pay_service;

CREATE TABLE pay_service.message (
	uuid varchar(100) NOT NULL,
	CONSTRAINT message_pkey PRIMARY KEY (uuid)
);

ALTER TABLE pay_service.message OWNER TO pay_service;
GRANT ALL ON TABLE pay_service.message TO pay_service;


CREATE TABLE pay_service.payment (
   id bigserial NOT NULL,
   payment_date timestamp(6) NOT NULL,
   amount numeric(16,2) NOT NULL DEFAULT 0,
   discount numeric(16,2) NOT NULL DEFAULT 0,
   success bool NOT NULL,
   payment_type varchar(10) NOT NULL,
   currency int4 NOT NULL,
   client_id int8 NOT NULL,
   order_id int8 NOT NULL,
   CONSTRAINT payment_pkey PRIMARY KEY (id)
);

ALTER TABLE pay_service.payment OWNER TO pay_service;
GRANT ALL ON TABLE pay_service.payment TO pay_service;
