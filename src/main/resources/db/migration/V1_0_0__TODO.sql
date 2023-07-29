CREATE TABLE todo (
  id                varchar(255) not null primary key,
  description       text,
  status            int,
  due_on            timestamp,
  done_on           timestamp,
  created_at        timestamp
);