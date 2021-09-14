CREATE TABLE IF NOT EXISTS vehicles (
    registrationNumber VARCHAR NOT NULL,
    owner VARCHAR,
    brand VARCHAR,
    PRIMARY KEY (registrationNumber)
);

CREATE TABLE IF NOT EXISTS dealers (
    organisationNumber VARCHAR NOT NULL,
    premiumCustomer BOOLEAN,
    PRIMARY KEY (organisationNumber)
);

CREATE TABLE IF NOT EXISTS premiumBrands (
    brand VARCHAR NOT NULL,
    PRIMARY KEY (brand)
);
