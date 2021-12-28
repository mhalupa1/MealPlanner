CREATE TABLE pantry(
    ingredient_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY(ingredient_id,user_id)
);

CREATE TABLE mp_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(25) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL
);

ALTER TABLE mp_pantry
ADD CONSTRAINT userFk
FOREIGN KEY (user_id)
REFERENCES mp_user (id);