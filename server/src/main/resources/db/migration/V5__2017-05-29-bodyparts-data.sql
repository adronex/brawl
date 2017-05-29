INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('1', 'HEAD', '10', '2', '2');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('2', 'HEAD', '2', '10', '2');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('3', 'HEAD', '1', '1', '10');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('4', 'LEFT_HAND', '5', '5', '5');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('5', 'RIGHT_HAND', '5', '5', '5');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('6', 'CHEST', '15', '5', '5');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('7', 'CHEST', '5', '15', '5');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('8', 'CHEST', '5', '5', '15');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('9', 'LEGS', '1', '1', '1');
INSERT INTO `bodypart` (`id`, `type`, `strength`, `agility`, `energy`) VALUES ('10', 'BACKPACK', '1', '1', '1');

INSERT INTO `equipment` (`id`, `name`, `category`, `type`, `required_strength`, `required_agility`, `required_energy`) VALUES ('1', 'Sword', 'WEAPON_CUTTING', 'ONE_HANDED_WEAPON', '1', '10', '1');
INSERT INTO `equipment` (`id`, `name`, `category`, `type`, `required_strength`, `required_agility`, `required_energy`) VALUES ('2', 'Chainsaw', 'WEAPON_CUTTING', 'TWO_HANDED_WEAPON', '1', '15', '1');
INSERT INTO `equipment` (`id`, `name`, `category`, `type`, `required_strength`, `required_agility`, `required_energy`) VALUES ('3', 'Mace', 'WEAPON_CRUSHING', 'ONE_HANDED_WEAPON', '10', '1', '1');
INSERT INTO `equipment` (`id`, `name`, `category`, `type`, `required_strength`, `required_agility`, `required_energy`) VALUES ('4', 'Lamp post', 'WEAPON_CRUSHING', 'TWO_HANDED_WEAPON', '15', '1', '1');
INSERT INTO `equipment` (`id`, `name`, `category`, `type`, `required_strength`, `required_agility`, `required_energy`) VALUES ('5', 'Riffle', 'WEAPON_FIREARM', 'ONE_HANDED_WEAPON', '1', '1', '10');
INSERT INTO `equipment` (`id`, `name`, `category`, `type`, `required_strength`, `required_agility`, `required_energy`) VALUES ('6', 'Cannon', 'WEAPON_FIREARM', 'TWO_HANDED_WEAPON', '1', '1', '15');
INSERT INTO `equipment` (`id`, `name`, `category`, `type`, `required_strength`, `required_agility`, `required_energy`) VALUES ('7', 'List of metal', 'AUXILARY_SHIELD', 'ONE_HANDED_AUXILARY', '20', '1', '1');

INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('1', '1');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('1', '4');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('1', '5');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('1', '6');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('1', '9');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('1', '10');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('2', '2');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('2', '4');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('2', '5');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('2', '7');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('2', '9');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('2', '10');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('3', '3');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('3', '4');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('3', '5');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('3', '8');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('3', '9');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('3', '10');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('4', '2');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('4', '4');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('4', '5');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('4', '6');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('4', '9');
INSERT INTO `hero_bodypart` (`hero_id`, `bodypart_id`) VALUES ('4', '10');

CREATE TABLE `hero_equipment` (
  `hero_id` VARCHAR(45) NOT NULL,
  `equipment_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`hero_id`, `equipment_id`),
  INDEX `FK_HE_EQUIPMENT_idx` (`equipment_id` ASC),
  CONSTRAINT `FK_HE_HERO`
  FOREIGN KEY (`hero_id`)
  REFERENCES `hero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_HE_EQUIPMENT`
  FOREIGN KEY (`equipment_id`)
  REFERENCES `equipment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `hero_equipment` (`hero_id`, `equipment_id`) VALUES ('1', '1');
INSERT INTO `hero_equipment` (`hero_id`, `equipment_id`) VALUES ('1', '3');
INSERT INTO `hero_equipment` (`hero_id`, `equipment_id`) VALUES ('2', '2');
INSERT INTO `hero_equipment` (`hero_id`, `equipment_id`) VALUES ('3', '6');
INSERT INTO `hero_equipment` (`hero_id`, `equipment_id`) VALUES ('4', '5');
INSERT INTO `hero_equipment` (`hero_id`, `equipment_id`) VALUES ('4', '7');