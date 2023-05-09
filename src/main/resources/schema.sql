CREATE TABLE "user"
(
    id        serial primary key,
    firstName varchar(40),
    lastName  varchar(40),
    email     varchar(40) UNIQUE NOT NULL,
    password  varchar(100),
    role      varchar(10),
    birthDate Date,
    country   varchar(40)
);

DROP TABLE "user";

CREATE TABLE category
(
    id   serial primary key,
    name varchar
);

CREATE TABLE article_category
(
    article_id  serial references article (id),
    category_id serial references category (id),
    UNIQUE (article_id, category_id)
);



CREATE TABLE article
(
    id    serial primary key,
    title varchar,
    text  varchar,
    slug  varchar unique
);


TRUNCATE TABLE article cascade;

CREATE OR REPLACE FUNCTION generate_random_articles(amount integer) RETURNS void AS
$$
BEGIN
    FOR i IN 1..amount
        LOOP
            INSERT INTO article
                (title, text, slug)
            VALUES ('название статьи ' || i::varchar(255),
                    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nibh arcu, fringilla eu elit vel, pulvinar ultrices neque. Morbi ante augue, semper a euismod quis, lacinia sit amet enim. Cras sed aliquam nunc. Phasellus nec sagittis diam, at fringilla mauris. Curabitur a pellentesque ante. Donec venenatis elementum risus a consectetur. Nam in augue vulputate, sodales tellus ut, rutrum justo. Mauris eget augue id diam rhoncus imperdiet. Sed sed ipsum in urna pharetra euismod ut eu nibh.'
                    , 'nazvanie_statiy' || i::varchar(255)
                    );
        END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT generate_random_articles(1000);



