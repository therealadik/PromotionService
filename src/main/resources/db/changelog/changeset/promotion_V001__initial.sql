CREATE TABLE promotion_user
(
    id        bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    buyer_id  bigint NOT NULL REFERENCES users (id),
    budget_in_day INT,
    start_date    DATE,
    end_date      DATE
);

CREATE TABLE promotion_event
(
    id        bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    event_id  bigint NOT NULL REFERENCES event (id),
    buyer_id  bigint NOT NULL REFERENCES users (id),
    budget_in_day INT,
    start_date    DATE,
    end_date      DATE
)