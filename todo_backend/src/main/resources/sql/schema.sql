CREATE TABLE IF NOT EXISTS todo(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    contents TEXT NOT NULL,
    target_date DATE,
    CONSTRAINT chk_title CHECK (LENGTH(title) > 0),
    CONSTRAINT chk_contents CHECK (LENGTH(contents) > 0)
);