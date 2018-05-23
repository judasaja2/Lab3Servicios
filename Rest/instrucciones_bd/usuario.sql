SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
--
-- Database: mydatabase
--
-- Estructura de tabla para la tabla `Usuario`
--

--
-- Siempre debemos añadir el atributo id en las propiedades  de nuestra tabla -- como clave primaria porque Loopback lo agrega  por defecto en el modelo
--

CREATE TABLE IF NOT EXISTS `Usuario`(
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `apellido` varchar(30) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contrasena` varchar(10) DEFAULT NULL,
  `foto` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0;
--
-- AUTO_INCREMENT=11 para que los valores del id empiece a                           -- incrementarse desde el número 11, se supone que los datos por defecto a    -- insertar llegan hasta el 10
--
--
-- Insertar datos en la base de datos para la tabla `Student`
--

INSERT INTO `Usuario` (`ID`,`nombre`,`apellido`,`correo`,`contrasena`, `foto`) VALUES
(1, 'Ana', 'Lopez', 'ana.lopez@analopez.com', 'psswrd', 'eStOSiqu3esUnURId3f0t0'),
(2, 'Pedro', 'Diaz', 'pedro.diaz@pedrodiaz.com', 'psswrd', 'eStOSiqu3esUnURId3f0t0');
