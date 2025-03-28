CREATE TABLE cars (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      make VARCHAR NOT NULL,
                      model VARCHAR NOT NULL,
                      year INT CHECK (year BETWEEN 1886 AND EXTRACT(YEAR FROM CURRENT_DATE)),
                      price NUMERIC CHECK (price > 0),
                      vin CHAR(17) UNIQUE NOT NULL
);