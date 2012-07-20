
SET storage_engine = innodb;

--
-- portaluser service
--
CREATE TABLE portal_user
(
   id                     INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
   customer_id            VARCHAR(11) NOT NULL,
   username               VARCHAR(40) NOT NULL,
   notification_method    INT UNSIGNED NOT NULL,
   creation_time          DATETIME NOT NULL,
   modification_time      DATETIME NOT NULL,   
   last_login_time        DATETIME,
   disabled               INT UNSIGNED NOT NULL,
   locked_time            DATETIME,   
   password               VARCHAR(40) NOT NULL,
   password_changed       DATETIME,
   salt                   VARCHAR(8) NOT NULL,   
   wrong_password_count   INT UNSIGNED NOT NULL,
   CONSTRAINT UNIQUE(username)   
);
