
SET storage_engine = innodb;

--
-- userinfo service
--
CREATE TABLE portal_user
(
   id                     INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
   first_names            VARCHAR(100) NOT NULL,
   modification_time      DATETIME NOT NULL,
   pic                    VARCHAR(11) NOT NULL,
   surname                VARCHAR(100),
   creation_time          DATETIME NOT NULL,
   disabled               INT UNSIGNED NOT NULL,
   last_login_time        DATETIME,
   locked_time            DATETIME,
   notification_method    INT UNSIGNED NOT NULL,
   password               VARCHAR(40) NOT NULL,
   password_changed       DATETIME,
   salt                   VARCHAR(8) NOT NULL,
   username               VARCHAR(40) NOT NULL,
   wrong_password_count   INT UNSIGNED NOT NULL,
   CONSTRAINT UNIQUE(pic),
   CONSTRAINT UNIQUE(username)
);

CREATE TABLE portal_user_contact_info
(
   id               INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
   portal_user_id   INT UNSIGNED NOT NULL,
   email            VARCHAR(100) NOT NULL,
   phone_number     VARCHAR(25),
   city             VARCHAR(50) NOT NULL,
   country          VARCHAR(50) NOT NULL,
   postal_code      INT NOT NULL,
   street_address   VARCHAR(100) NOT NULL,
   CONSTRAINT UNIQUE(portal_user_id),
   FOREIGN KEY(portal_user_id) REFERENCES portal_user(id)
);