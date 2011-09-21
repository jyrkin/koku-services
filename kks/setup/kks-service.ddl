CREATE TABLE kks_tag (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  tag_id INT UNSIGNED NOT NULL,
  name VARCHAR(200) NOT NULL,
  description VARCHAR(500),
  CONSTRAINT UNIQUE (tag_id)
);

CREATE TABLE kks_collection_class (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  collection_class_id INT UNSIGNED NOT NULL,
  name VARCHAR(250) NOT NULL,
  concent_type VARCHAR(250) NOT NULL,
  description VARCHAR(500) NOT NULL,
  CONSTRAINT UNIQUE (collection_class_id)
);

CREATE TABLE kks_group (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  group_id INT UNSIGNED NOT NULL,
  sort_order INT UNSIGNED NOT NULL,
  name VARCHAR(200) NOT NULL,
  description VARCHAR(500),
  register VARCHAR(500) NOT NULL,
  parent_id INT UNSIGNED,
  collection_id INT UNSIGNED NOT NULL,
  CONSTRAINT UNIQUE (group_id),
  FOREIGN KEY (collection_id) REFERENCES kks_collection_class(collection_class_id)
);

CREATE TABLE kks_entry_class (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  entry_class_id INT UNSIGNED NOT NULL,
  sort_order INT UNSIGNED NOT NULL,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(500)	,
  multi_value BOOLEAN NOT NULL,
  data_type VARCHAR(30) NOT NULL,
  value_spaces VARCHAR(500),
  entry_group INT UNSIGNED NOT NULL,
  CONSTRAINT UNIQUE (entry_class_id),
  FOREIGN KEY (entry_group) REFERENCES kks_group(group_id)  
);

CREATE TABLE kks_entry_class_tags (
	entry_class_id INT UNSIGNED NOT NULL,
	tag_id INT UNSIGNED NOT NULL,
	FOREIGN KEY (entry_class_id) REFERENCES kks_entry_class(entry_class_id),
    FOREIGN KEY (tag_id) REFERENCES kks_tag(tag_id)  	
);

CREATE TABLE kks_collection (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(500),
  status VARCHAR(15) NOT NULL,
  created DATE NOT NULL,
  creator VARCHAR(15) NOT NULL,
  version INT NOT NULL,
  prev_version VARCHAR(50),
  next_version VARCHAR(50),
  customer VARCHAR(15) NOT NULL,  
  collection_class_id INT UNSIGNED NOT NULL,  
  FOREIGN KEY (collection_class_id) REFERENCES kks_collection_class(collection_class_id)  
);

CREATE TABLE kks_collection_tags (
	collection_id BIGINT UNSIGNED NOT NULL,
	tag_id INT UNSIGNED NOT NULL,
	FOREIGN KEY (collection_id) REFERENCES kks_collection(id),
    FOREIGN KEY (tag_id) REFERENCES kks_tag(tag_id)  	
);

CREATE TABLE kks_entry (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  modified DATE NOT NULL,
  creator VARCHAR(15) NOT NULL,
  version INT NOT NULL,
  entry_class_id INT UNSIGNED NOT NULL,
  customer VARCHAR(15) NOT NULL,
  collection_id BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY (collection_id) REFERENCES kks_collection(id),
  FOREIGN KEY (entry_class_id) REFERENCES kks_entry_class(entry_class_id)
);  
 
CREATE TABLE kks_entry_tags (
	entry_id BIGINT UNSIGNED NOT NULL,
	tag_id INT UNSIGNED NOT NULL,
	FOREIGN KEY (entry_id) REFERENCES kks_entry(id),
    FOREIGN KEY (tag_id) REFERENCES kks_tag(tag_id)  	
);

CREATE TABLE kks_value (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  value VARCHAR(500) NOT NULL,
  entry_id BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY (entry_id) REFERENCES kks_entry(id) 
);













