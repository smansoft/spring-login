
create user 'sl_user_1'@'localhost' identified by 'sl_password_1';
grant all privileges on spring_login_1.* to 'sl_user_1'@'localhost';

create user 'sl_user_2'@'localhost' identified by 'sl_password_2';
grant all privileges on spring_login_2.* to 'sl_user_2'@'localhost';

create user 'sl_user_3'@'localhost' identified by 'sl_password_3';
grant all privileges on spring_login_3.* to 'sl_user_3'@'localhost';

create user 'sl_user_4'@'localhost' identified by 'sl_password_4';
grant all privileges on spring_login_4.* to 'sl_user_4'@'localhost';

create user 'sl_user_5'@'localhost' identified by 'sl_password_5';
grant all privileges on spring_login_5.* to 'sl_user_5'@'localhost';

create user 'sl_user_test'@'localhost' identified by 'sl_password_test';
grant all privileges on spring_login_test.* to 'sl_user_test'@'localhost';

flush privileges;
