# Privilegios para `appuser`@`%`

GRANT EXECUTE ON *.* TO `appuser`@`%` IDENTIFIED BY PASSWORD '*A8AE23DABF6C375911326D7D16F8018C540ECBF7';

GRANT SELECT ON `mysql`.`proc` TO `appuser`@`%`;


# Privilegios para `appuser`@`localhost`

GRANT EXECUTE ON *.* TO `appuser`@`localhost` IDENTIFIED BY PASSWORD '*A8AE23DABF6C375911326D7D16F8018C540ECBF7';

GRANT SELECT ON `mysql`.`proc` TO `appuser`@`localhost`;
