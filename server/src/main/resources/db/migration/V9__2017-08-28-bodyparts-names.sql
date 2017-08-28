ALTER TABLE `bodypart`
  ADD COLUMN `name` VARCHAR(45) NOT NULL DEFAULT 'lola' AFTER `energy`;

UPDATE `bodypart` SET `name`='StrongHead' WHERE `id`='1';
UPDATE `bodypart` SET `name`='DefaultBackpack' WHERE `id`='10';
UPDATE `bodypart` SET `name`='AgileHead' WHERE `id`='2';
UPDATE `bodypart` SET `name`='Energethic' WHERE `id`='3';
UPDATE `bodypart` SET `name`='Left' WHERE `id`='4';
UPDATE `bodypart` SET `name`='Right' WHERE `id`='5';
UPDATE `bodypart` SET `name`='Strong' WHERE `id`='6';
UPDATE `bodypart` SET `name`='Agile' WHERE `id`='7';
UPDATE `bodypart` SET `name`='Energethic' WHERE `id`='8';
UPDATE `bodypart` SET `name`='Leg-leg' WHERE `id`='9';

ALTER TABLE `bodypart`
  CHANGE COLUMN `name` `name` VARCHAR(45) NOT NULL DEFAULT 'lola' AFTER `id`;