DROP TABLE students IF EXISTS;

CREATE TABLE students
(	id			IDENTITY    NOT NULL UNIQUE,	-- bigserial,	 --
	name		VARCHAR(64) NOT NULL,
	mark		INT,
	created_at	TIMESTAMP DEFAULT current_timestamp,
	updated_at	TIMESTAMP DEFAULT current_timestamp,
	PRIMARY KEY (id)
);
 INSERT INTO students (name, mark) VALUES
 ('One', 1), ('Two', 2), ('Three', 3), ('Four', 4), ('Five', 5);