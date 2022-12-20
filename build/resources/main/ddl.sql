CREATE DATABASE cash_receipt
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

/*------------------------------------------------*/

CREATE TABLE public.product
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 ),
    description character varying(100) NOT NULL,
    price real NOT NULL,
    promotional boolean NOT NULL,
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS public.discount_card
    OWNER to postgres;

/*------------------------------------------------*/

CREATE TABLE public.discount_card
(
    "number" integer NOT NULL,
    discount integer NOT NULL,
    PRIMARY KEY ("number")
);
ALTER TABLE IF EXISTS public.discount_card
    OWNER to postgres;

/*------------------------------------------------*/

INSERT INTO public.product (
    description, price, promotional) VALUES
                                         ('milk'::character varying, '1.56'::real, true::boolean),
                                         ('bread'::character varying, '0.87'::real, false::boolean),
                                         ('loaf'::character varying, '0.90'::real, false::boolean),
                                         ('chicken'::character varying, '6.45'::real, true::boolean),
                                         ('cookies'::character varying, '11.30'::real, true::boolean),
                                         ('eggs'::character varying, '3.05'::real, false::boolean),
                                         ('cottage cheese'::character varying, '1.45'::real, false::boolean),
                                         ('marshmallows'::character varying, '3.78'::real, false::boolean),
                                         ('sour cream'::character varying, '4.15'::real, true::boolean),
                                         ('onion'::character varying, '2.00'::real, false::boolean),
                                         ('cucumbers'::character varying, '1.80'::real, true::boolean),
                                         ('tomatoes'::character varying, '2.45'::real, true::boolean),
                                         ('watermelon'::character varying, '1.78'::real, false::boolean),
                                         ('melon'::character varying, '2.56'::real, false::boolean),
                                         ('garlic'::character varying, '1.40'::real, false::boolean)
    returning id;

/*------------------------------------------------*/

INSERT INTO public.discount_card (
    number, discount) VALUES
                          ('1111'::integer, '5'::integer),
                          ('2222'::integer, '10'::integer),
                          ('3333'::integer, '15'::integer),
                          ('4444'::integer, '20'::integer),
                          ('5555'::integer, '25'::integer)
    returning number;