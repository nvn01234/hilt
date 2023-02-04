#/bin/bash
HERE="$(dirname "${BASH_SOURCE[0]}")"

clickhouse client -n <<-EOSQL
    CREATE DATABASE IF NOT EXISTS "nyc-taxi";
    CREATE TABLE    IF NOT EXISTS "nyc-taxi"."trips" (
        trip_id                 UInt32,
        vendor_id               String,
        pickup_datetime         DateTime,
        dropoff_datetime        Nullable(DateTime),
        store_and_fwd_flag      Nullable(FixedString(1)),
        rate_code_id            Nullable(UInt8),
        pickup_longitude        Nullable(Float64),
        pickup_latitude         Nullable(Float64),
        dropoff_longitude       Nullable(Float64),
        dropoff_latitude        Nullable(Float64),
        passenger_count         Nullable(UInt8),
        trip_distance           Nullable(Float64),
        fare_amount             Nullable(Float32),
        extra                   Nullable(Float32),
        mta_tax                 Nullable(Float32),
        tip_amount              Nullable(Float32),
        tolls_amount            Nullable(Float32),
        ehail_fee               Nullable(Float32),
        improvement_surcharge   Nullable(Float32),
        total_amount            Nullable(Float32),
        payment_type            Nullable(String),
        trip_type               Nullable(UInt8),

        pickup                  Nullable(String),
        dropoff                 Nullable(String),

        cab_type                Nullable(String),

        precipitation           Nullable(Float32),
        snow_depth              Nullable(Float32),
        snowfall                Nullable(Float32),
        max_temperature         Nullable(Float32),
        min_temperature         Nullable(Float32),
        wind                    Nullable(Float32),

        pickup_nyct2010_gid     Nullable(UInt8),
        pickup_ctlabel          Nullable(String),
        pickup_borocode         Nullable(UInt8),
        pickup_boroname         Nullable(String),
        pickup_ct2010           Nullable(String),
        pickup_boroct2010       Nullable(String),
        pickup_cdeligibil       Nullable(FixedString(1)),
        pickup_ntacode          Nullable(String),
        pickup_ntaname          Nullable(String),
        pickup_puma             Nullable(String),

        dropoff_nyct2010_gid    Nullable(UInt8),
        dropoff_ctlabel         Nullable(String),
        dropoff_borocode        Nullable(UInt8),
        dropoff_boroname        Nullable(String),
        dropoff_ct2010          Nullable(String),
        dropoff_boroct2010      Nullable(String),
        dropoff_cdeligibil      Nullable(FixedString(1)),
        dropoff_ntacode         Nullable(String),
        dropoff_ntaname         Nullable(String),
        dropoff_puma            Nullable(String)
    ) ENGINE = MergeTree()
    PARTITION BY toYear(pickup_datetime)
    ORDER BY (trip_id, pickup_datetime)
    PRIMARY KEY trip_id;
EOSQL

tar -xOzf "${HERE}/nyc-taxi.tar.gz" | clickhouse-client --database="nyc-taxi" --query="INSERT INTO trips FORMAT Parquet"
