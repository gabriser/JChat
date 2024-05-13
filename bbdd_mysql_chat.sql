-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 13-05-2024 a las 14:53:31
-- Versión del servidor: 10.6.16-MariaDB-0ubuntu0.22.04.1
-- Versión de PHP: 8.1.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `chat`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`admin`@`localhost` PROCEDURE `connect` (`pnick` VARCHAR(50))  begin
  declare vid_message int(11);
  if exists ( select nick from users where userhost=user())
  then
    signal sqlstate 'HY000' set message_text = 'Yet connected';
  else
    if exists ( select nick from users where nick=pnick  )
    then
      signal sqlstate 'HY000' set message_text = 'Nick already in use';
    else
      select MAX(id_message) into vid_message from messages;
      if(vid_message IS NULL)
      then
          set vid_message = 0;
      end if;
      insert into users(nick,userhost, date_con, last_read) values(pnick, user(), now(), vid_message);
    end if;
  end if;
end$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `disconnect` ()  begin
  delete from users where userhost = user();
end$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `getConnectedUsers` ()  begin
  select nick,date_con from users;
end$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `getMessages` ()  begin
  declare vnick VARCHAR(50);
  declare vid_message int(11);
  select nick into vnick from users where userhost=user();
  if ( vnick is not null )
  then
    
    select nick,message,ts from messages where id_message > (select last_read from users where nick=vnick);
    
    select MAX(id_message) into vid_message from messages; 
    if( vid_message is not null)
    then
    	update users set last_read = vid_message where nick=vnick;   
    end if;
    
  else
    signal sqlstate 'HY000' set message_text = 'Not connected.'; 
  end if;
end$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `send` (`msg` VARCHAR(255))  begin 
  declare vnick VARCHAR(50);
  select nick into vnick from users where userhost=user();
  if ( vnick is not null )
  then
    insert into messages(nick,message) values(vnick,msg);
  else
    signal sqlstate 'HY000' set message_text = 'Not connected.';    
  end if;        
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `messages`
--

CREATE TABLE `messages` (
  `id_message` int(11) NOT NULL,
  `nick` varchar(50) NOT NULL,
  `message` varchar(255) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `messages`
--

INSERT INTO `messages` (`id_message`, `nick`, `message`, `ts`) VALUES
(1, 'antonio_nick', 'hola a todos', '2024-03-24 10:45:30'),
(7, 'TalkFox', 'Hola,Soy Yuan', '2024-04-09 14:52:46'),
(44, 'pablo', 'lorem ipsum dolor sit amet', '2024-04-28 08:29:42'),
(50, 'olawe', 'adios', '2024-04-28 09:21:57');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `nick` varchar(50) NOT NULL,
  `userhost` varchar(50) DEFAULT NULL,
  `date_con` datetime DEFAULT NULL,
  `last_read` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`nick`, `userhost`, `date_con`, `last_read`) VALUES
('usuario_de_prueba', 'appuser@192.168.19.44', '2024-04-28 11:40:53', 456);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id_message`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`nick`),
  ADD UNIQUE KEY `userhost` (`userhost`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `messages`
--
ALTER TABLE `messages`
  MODIFY `id_message` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
