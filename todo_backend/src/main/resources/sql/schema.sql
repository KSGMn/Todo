CREATE TABLE IF NOT EXISTS todo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    contents TEXT NOT NULL,
    target_date DATE,
    recycle BOOLEAN DEFAULT FALSE,
    done BOOLEAN DEFAULT FALSE,
    CONSTRAINT chk_title CHECK (LENGTH(title) > 0),
    CONSTRAINT chk_contents CHECK (LENGTH(contents) > 0)
);


CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(255) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    signup_date DATE NOT NULL,
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS certification (
    user_id VARCHAR(255) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    certification_number VARCHAR(255) NOT NULL
);