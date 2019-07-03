ALTER TABLE users DROP KEY UNQ_users_0
ALTER TABLE user_roles DROP FOREIGN KEY FK_user_roles_user_id
ALTER TABLE user_roles DROP KEY UNQ_user_roles_0
DROP INDEX users_id ON users
DROP INDEX users_user_login ON users
DROP INDEX users_email ON users
DROP TABLE users
DROP INDEX user_roles_id ON user_roles
DROP INDEX user_roles_user_id ON user_roles
DROP TABLE user_roles
