CREATE TABLE promotion_user
(
    id        bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    buyer_id  bigint NOT NULL REFERENCES users (id),
    budgetDay INT,
    startDate DATE,
    endDate   DATE
);

CREATE TABLE promotion_event
(
    id        bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    event_id  bigint NOT NULL REFERENCES event (id),
    buyer_id  bigint NOT NULL REFERENCES users (id),
    budgetDay INT,
    startDate DATE,
    endDate   DATE
)