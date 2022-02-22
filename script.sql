--Run drop table only when table arleady exists.
--drop table public.shipment;
--drop table public.vehicle;
--drop table public.tariff;
CREATE TABLE IF NOT EXISTS public.tariff
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ) primary key,
    name character varying not null ,
    rate numeric,
    discount numeric

);
CREATE TABLE IF NOT EXISTS public.vehicle
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ) primary key,
    name character varying not null ,
    weight numeric,
    tariff_id bigint,
    CONSTRAINT fk_tariff
        FOREIGN KEY(tariff_id)
            REFERENCES tariff(id)
);
CREATE TABLE IF NOT EXISTS public.shipment
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ) primary key,
    name character varying not null ,
    weight numeric,
    vehicle_id bigint,
    tariff_id bigint,
    cost numeric,
    CONSTRAINT fk_vehicle
        FOREIGN KEY(vehicle_id)
            REFERENCES vehicle(id),
    CONSTRAINT fk_tariff
        FOREIGN KEY(tariff_id)
            REFERENCES tariff(id)
 );


ALTER TABLE IF EXISTS public.shipment
    OWNER to postgres;
ALTER TABLE IF EXISTS public.vehicle
    OWNER to postgres;
ALTER TABLE IF EXISTS public.tariff
    OWNER to postgres;


