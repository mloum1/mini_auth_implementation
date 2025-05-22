CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);


INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM user u
JOIN role r ON r.name IN ('ROLE_USER', 'ROLE_DRIVER', 'ROLE_ADMIN', 'ROLE_OBSERVEUR')
WHERE u.username = 'admin';

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM user u
JOIN role r ON r.name IN ('ROLE_USER')
WHERE u.username = 'user'
