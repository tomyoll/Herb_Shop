-- phpMyAdmin SQL Dump
-- version 4.7.3
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Авг 30 2021 г., 21:17
-- Версия сервера: 5.6.37
-- Версия PHP: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `Herb_shop`
--
CREATE DATABASE IF NOT EXISTS `Herb_shop` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `Herb_shop`;

-- --------------------------------------------------------

--
-- Структура таблицы `actions`
--

DROP TABLE IF EXISTS `actions`;
CREATE TABLE `actions` (
  `id_action` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `actions`
--

INSERT INTO `actions` (`id_action`, `name`) VALUES
(1, 'Відхаркувальна'),
(2, 'Обволікаюча//захист слизових'),
(3, 'Протизапальна'),
(4, 'Протикашльова'),
(5, 'Репаративна'),
(8, 'Знеболювальна'),
(9, 'Імуностимулююча'),
(10, 'Протизапальна'),
(11, 'Тонізуюча'),
(13, 'Test4');

-- --------------------------------------------------------

--
-- Структура таблицы `actions_product`
--

DROP TABLE IF EXISTS `actions_product`;
CREATE TABLE `actions_product` (
  `id_product` int(11) NOT NULL,
  `id_action` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `actions_product`
--

INSERT INTO `actions_product` (`id_product`, `id_action`) VALUES
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(6, 3),
(6, 8),
(6, 9),
(6, 10),
(6, 11);

-- --------------------------------------------------------

--
-- Структура таблицы `Admins`
--

DROP TABLE IF EXISTS `Admins`;
CREATE TABLE `Admins` (
  `login` varchar(30) NOT NULL DEFAULT 'root',
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Дамп данных таблицы `Admins`
--

INSERT INTO `Admins` (`login`, `password`) VALUES
('admin', '21232F297A57A5A743894A0E4A801FC3');

-- --------------------------------------------------------

--
-- Структура таблицы `diseases`
--

DROP TABLE IF EXISTS `diseases`;
CREATE TABLE `diseases` (
  `id` int(11) NOT NULL,
  `CategoryID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `diseases`
--

INSERT INTO `diseases` (`id`, `CategoryID`, `name`) VALUES
(1, 2, 'Пневмонія'),
(2, 2, 'Бронхіальна астма'),
(3, 2, 'Коклюш'),
(4, 2, 'Бронхіт'),
(5, 2, 'Трахеїт'),
(7, 3, 'Цукровий діабет 2 типу'),
(8, 2, 'ГРВІ/ГРЗ'),
(9, 7, 'Нервое збудження, нервоз'),
(10, 7, 'Безсоння'),
(11, 7, 'Нейроциркуляторна дистонія'),
(14, 5, 'Інше'),
(16, 5, 'Test2'),
(17, 5, 'Тест1');

-- --------------------------------------------------------

--
-- Структура таблицы `diseases_category`
--

DROP TABLE IF EXISTS `diseases_category`;
CREATE TABLE `diseases_category` (
  `id_category` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `diseases_category`
--

INSERT INTO `diseases_category` (`id_category`, `name`) VALUES
(1, 'Дерматологічні захворювання'),
(2, 'Дихальних шляхів захворювання'),
(3, 'Ендокринні захворювання'),
(4, 'Захворювання порожнини рота'),
(5, 'Інші захворювання'),
(6, 'Лор захворювання'),
(7, 'Неврологічні захворювання'),
(8, 'Серцево-судинні захворювання'),
(9, 'Хірургічні захворювання');

-- --------------------------------------------------------

--
-- Структура таблицы `diseases_product`
--

DROP TABLE IF EXISTS `diseases_product`;
CREATE TABLE `diseases_product` (
  `id_disease` int(11) NOT NULL,
  `id_product` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `diseases_product`
--

INSERT INTO `diseases_product` (`id_disease`, `id_product`) VALUES
(1, 4),
(2, 4),
(3, 4),
(4, 4),
(5, 4),
(14, 6);

-- --------------------------------------------------------

--
-- Структура таблицы `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
CREATE TABLE `ingredients` (
  `id` int(11) NOT NULL,
  `name` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `ingredients`
--

INSERT INTO `ingredients` (`id`, `name`) VALUES
(7, 'Іван-чай'),
(1, 'Алтеї корені'),
(8, 'Анісу плоди'),
(9, 'Березові пагони'),
(10, 'Брусниці листя'),
(11, 'Бузини чорної квіти'),
(13, 'Вільхи супліддя'),
(15, 'Горобина'),
(16, 'Деревію трава'),
(17, 'Дубова трава'),
(18, 'Екваліпту листя'),
(19, 'Елеустерокок'),
(4, 'Звіробій'),
(20, 'Звіробою трава'),
(21, 'Золототисячника трава'),
(22, 'Калгану корінь'),
(23, 'Календули квітки'),
(24, 'Кмину плоди'),
(14, 'Корінь валеріани'),
(25, 'Кропиви листя'),
(26, 'Кропу плоди'),
(27, 'Крушина кора'),
(28, 'Кукурудза'),
(30, 'Льону насіння'),
(31, 'М\'яти перцевої листя'),
(32, 'Меліси трава'),
(33, 'Мучниці листя'),
(3, 'Ромашки квіти'),
(34, 'Тест2'),
(5, 'Чорниці пагони'),
(2, 'Шипшина');

-- --------------------------------------------------------

--
-- Структура таблицы `ingredients_products`
--

DROP TABLE IF EXISTS `ingredients_products`;
CREATE TABLE `ingredients_products` (
  `id_product` int(11) NOT NULL,
  `id_ingredient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `ingredients_products`
--

INSERT INTO `ingredients_products` (`id_product`, `id_ingredient`) VALUES
(4, 1),
(6, 7);

-- --------------------------------------------------------

--
-- Структура таблицы `Production`
--

DROP TABLE IF EXISTS `Production`;
CREATE TABLE `Production` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(100) NOT NULL DEFAULT 'Без назви',
  `image_path` varchar(100) DEFAULT 'no-image.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `Production`
--

INSERT INTO `Production` (`product_id`, `product_name`, `image_path`) VALUES
(4, 'Алтеї корені', 'Altea roots.png'),
(6, 'Іван-чай', '1.png');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `actions`
--
ALTER TABLE `actions`
  ADD PRIMARY KEY (`id_action`);

--
-- Индексы таблицы `actions_product`
--
ALTER TABLE `actions_product`
  ADD KEY `id_action` (`id_action`),
  ADD KEY `id_product` (`id_product`);

--
-- Индексы таблицы `Admins`
--
ALTER TABLE `Admins`
  ADD PRIMARY KEY (`login`);

--
-- Индексы таблицы `diseases`
--
ALTER TABLE `diseases`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CategoryID` (`CategoryID`);

--
-- Индексы таблицы `diseases_category`
--
ALTER TABLE `diseases_category`
  ADD PRIMARY KEY (`id_category`);

--
-- Индексы таблицы `diseases_product`
--
ALTER TABLE `diseases_product`
  ADD KEY `id_product` (`id_product`),
  ADD KEY `id_disease` (`id_disease`);

--
-- Индексы таблицы `ingredients`
--
ALTER TABLE `ingredients`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name`);

--
-- Индексы таблицы `ingredients_products`
--
ALTER TABLE `ingredients_products`
  ADD KEY `id_ingredient` (`id_ingredient`),
  ADD KEY `id_product` (`id_product`);

--
-- Индексы таблицы `Production`
--
ALTER TABLE `Production`
  ADD PRIMARY KEY (`product_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `actions`
--
ALTER TABLE `actions`
  MODIFY `id_action` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT для таблицы `diseases`
--
ALTER TABLE `diseases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT для таблицы `diseases_category`
--
ALTER TABLE `diseases_category`
  MODIFY `id_category` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT для таблицы `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT для таблицы `Production`
--
ALTER TABLE `Production`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `actions_product`
--
ALTER TABLE `actions_product`
  ADD CONSTRAINT `actions_product_ibfk_1` FOREIGN KEY (`id_action`) REFERENCES `actions` (`id_action`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `actions_product_ibfk_2` FOREIGN KEY (`id_product`) REFERENCES `production` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `diseases`
--
ALTER TABLE `diseases`
  ADD CONSTRAINT `diseases_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `diseases_category` (`id_category`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `diseases_product`
--
ALTER TABLE `diseases_product`
  ADD CONSTRAINT `diseases_product_ibfk_1` FOREIGN KEY (`id_disease`) REFERENCES `diseases` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `diseases_product_ibfk_2` FOREIGN KEY (`id_product`) REFERENCES `production` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `ingredients_products`
--
ALTER TABLE `ingredients_products`
  ADD CONSTRAINT `ingredients_products_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `production` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ingredients_products_ibfk_2` FOREIGN KEY (`id_ingredient`) REFERENCES `ingredients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
