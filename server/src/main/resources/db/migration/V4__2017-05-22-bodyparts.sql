CREATE TABLE bodypart
(
  id       VARCHAR(45) PRIMARY KEY,
  type     VARCHAR(45) NOT NULL,
  strength INT         NOT NULL,
  agility  INT         NOT NULL,
  energy   INT         NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE equipment
(
  id                VARCHAR(45) PRIMARY KEY,
  name              VARCHAR(45) NOT NULL,
  category          VARCHAR(45) NOT NULL,
  type              VARCHAR(45) NOT NULL,
  required_strength INT         NOT NULL,
  required_agility  INT         NOT NULL,
  required_energy   INT         NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE UNIQUE INDEX equipment_name_uindex
  ON equipment (name);

CREATE TABLE `hero_bodypart` (
  `hero_id`     VARCHAR(45) NOT NULL,
  `bodypart_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`hero_id`, `bodypart_id`),
  KEY `FK_CS_BODYPART_idx` (`bodypart_id`),
  CONSTRAINT `FK_HBP_HERO` FOREIGN KEY (`hero_id`) REFERENCES `hero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_HBP_BODYPART` FOREIGN KEY (`bodypart_id`) REFERENCES `bodypart` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;