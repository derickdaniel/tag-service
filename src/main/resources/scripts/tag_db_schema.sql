CREATE TABLE tags (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  uuid CHAR(36) NOT NULL UNIQUE,  -- public reference for cross-service use
  name VARCHAR(100) NOT NULL UNIQUE,
  slug VARCHAR(120) NOT NULL UNIQUE, 
  description TEXT,
  color CHAR(7),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_tag_name (name),
  UNIQUE KEY uq_tag_slug (slug),
  INDEX idx_tag_name (name)   -- index for fast search by name
);

CREATE TABLE tag_assignments (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  tag_id BIGINT UNSIGNED NOT NULL,
  entity_type VARCHAR(50) NOT NULL,   -- e.g., 'issue', 'project'
  entity_id CHAR(36) NOT NULL,        -- UUID of the entity
  created_by int NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
  UNIQUE KEY uq_tag_entity (tag_id, entity_type, entity_id),
  INDEX idx_entity (entity_type, entity_id),
  INDEX idx_tag (tag_id)
);

Alter TABLE tag_assignments MODIFY COLUMN entity_id int NOT NULL;

