ALTER TABLE hero_bodypart DROP FOREIGN KEY FK_HBP_HERO;
ALTER TABLE hero_bodypart
  ADD CONSTRAINT FK_HBP_HERO
FOREIGN KEY (hero_id) REFERENCES hero (id) ON UPDATE CASCADE;
ALTER TABLE hero_bodypart DROP FOREIGN KEY FK_HBP_BODYPART;
ALTER TABLE hero_bodypart
  ADD CONSTRAINT FK_HBP_BODYPART
FOREIGN KEY (bodypart_id) REFERENCES bodypart (id) ON UPDATE CASCADE;

UPDATE bodypart SET id = 'HEAD_1' WHERE id = '1';
UPDATE bodypart SET id = 'HEAD_2' WHERE id = '2';
UPDATE bodypart SET id = 'HEAD_3' WHERE id = '3';
UPDATE bodypart SET id = 'LEFT_HAND_1' WHERE id = '4';
UPDATE bodypart SET id = 'RIGHT_HAND_1' WHERE id = '5';
UPDATE bodypart SET id = 'CHEST_1' WHERE id = '6';
UPDATE bodypart SET id = 'CHEST_2' WHERE id = '7';
UPDATE bodypart SET id = 'CHEST_3' WHERE id = '8';
UPDATE bodypart SET id = 'LEGS_1' WHERE id = '9';
UPDATE bodypart SET id = 'BACKPACK_1' WHERE id = '10';