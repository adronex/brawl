INSERT INTO `brawl`.`squad` (`id`, `name`, `owner_id`) VALUES ('3', 'Zui', '2');

INSERT INTO `brawl`.`hero` (`id`, `owner_id`, `name`) VALUES ('5', '1', 'Snayder');
INSERT INTO `brawl`.`hero` (`id`, `owner_id`, `name`) VALUES ('6', '1', 'Zak-Zak');

INSERT INTO `brawl`.`hero_squad` (`hero_id`, `squad_id`) VALUES ('5', '3');
INSERT INTO `brawl`.`hero_squad` (`hero_id`, `squad_id`) VALUES ('6', '3');