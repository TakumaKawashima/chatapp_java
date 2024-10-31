CREATE TABLE IF NOT EXISTS users (
  id        SERIAL          NOT NULL,
  name      VARCHAR(128)    NOT NULL,
  email     VARCHAR(128)    NOT NULL UNIQUE,
  password  VARCHAR(512)    NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rooms (
  id        SERIAL          NOT NULL,
  name      VARCHAR(128)    NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_rooms (
  id        SERIAL  NOT NULL,
  user_id   INT     NOT NULL,
  room_id   INT     NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS messages (
  id        SERIAL        NOT NULL,
  content   VARCHAR(512),
  user_id   INT           NOT NULL,
  room_id   INT           NOT NULL,
  image     VARCHAR(512),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);
