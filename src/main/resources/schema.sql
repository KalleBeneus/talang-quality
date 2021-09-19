--DROP TABLE IF EXISTS vehicles;
--DROP TABLE IF EXISTS customers;
--DROP TABLE IF EXISTS premiumBrands;

CREATE TABLE IF NOT EXISTS customers (
    organisationNumber VARCHAR NOT NULL,
    premiumCustomer BOOLEAN,
    PRIMARY KEY (organisationNumber)
);

CREATE TABLE IF NOT EXISTS vehicles (
    registrationNumber VARCHAR NOT NULL,
    owner VARCHAR REFERENCES customers(organisationNumber),
    brand VARCHAR,
    PRIMARY KEY (registrationNumber)
);

CREATE TABLE IF NOT EXISTS premiumBrands (
    brand VARCHAR NOT NULL,
    PRIMARY KEY (brand)
);
