INSERT INTO `squad` (`id`, `name`, `owner_id`) VALUES ('3', 'Zui', '2');

INSERT INTO `hero` (`id`, `owner_id`, `name`) VALUES ('5', '1', 'Snayder');
INSERT INTO `hero` (`id`, `owner_id`, `name`) VALUES ('6', '1', 'Zak-Zak');

INSERT INTO `hero_squad` (`hero_id`, `squad_id`) VALUES ('5', '3');
INSERT INTO `hero_squad` (`hero_id`, `squad_id`) VALUES ('6', '3');