-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 25, 2018 lúc 09:45 AM
-- Phiên bản máy phục vụ: 10.1.31-MariaDB
-- Phiên bản PHP: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `food_manager`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `area`
--

CREATE TABLE `area` (
  `id` int(11) NOT NULL,
  `area` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `area`
--

INSERT INTO `area` (`id`, `area`) VALUES
(1, 'Khu A'),
(2, 'Khu B'),
(3, 'Khu C'),
(4, 'Khu D'),
(5, 'Khu E');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bill`
--

CREATE TABLE `bill` (
  `id` int(11) NOT NULL,
  `table_id` int(11) NOT NULL,
  `staff_id` int(11) NOT NULL,
  `customer` varchar(255) DEFAULT NULL,
  `time_order` datetime DEFAULT CURRENT_TIMESTAMP,
  `bill_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_price` bigint(20) NOT NULL DEFAULT '0',
  `guest_money` bigint(11) NOT NULL DEFAULT '0',
  `money_back` bigint(11) NOT NULL DEFAULT '0',
  `discount` int(11) NOT NULL DEFAULT '0',
  `surcharge` bigint(11) NOT NULL DEFAULT '0',
  `ship` int(11) NOT NULL DEFAULT '0',
  `bill_note` text,
  `status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `bill`
--

INSERT INTO `bill` (`id`, `table_id`, `staff_id`, `customer`, `time_order`, `bill_date`, `total_price`, `guest_money`, `money_back`, `discount`, `surcharge`, `ship`, `bill_note`, `status`) VALUES
(3, 17, 0, NULL, '0000-00-00 00:00:00', '2018-06-06 20:52:32', 20000, 20000, 0, 0, 0, 0, '', 1),
(4, 20, 0, 'tùng', '0000-00-00 00:00:00', '2018-06-12 18:01:34', 110000, 200000, 90000, 10000, 10000, 10000, '', 1),
(5, 21, 0, NULL, '0000-00-00 00:00:00', '2018-06-12 18:27:56', 57500, 200000, 142500, 10000, 0, 0, '', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `food`
--

CREATE TABLE `food` (
  `id` int(11) NOT NULL,
  `food_name` varchar(255) NOT NULL,
  `kind_food` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `promotions` int(3) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `food`
--

INSERT INTO `food` (`id`, `food_name`, `kind_food`, `quantity`, `price`, `promotions`, `status`) VALUES
(1, 'Rau muốn xào', 2, NULL, 10000, 0, 0),
(2, 'Rau lang xào', 2, NULL, 10000, 0, 0),
(3, 'Bắp cải nướng', 2, NULL, 10000, 0, 0),
(4, 'Khoai lang nướng', 2, NULL, 10000, 0, 0),
(5, 'Cocktail Shochu', 1, NULL, 50000, 5, 0),
(6, 'Cocktail Sake', 1, NULL, 50000, 5, 0),
(7, 'Cafe sữa đá', 3, NULL, 15000, 0, 0),
(8, 'Coca Cola', 1, NULL, 9000, 0, 0),
(9, 'Pessi', 1, NULL, 9000, 0, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `kind_food`
--

CREATE TABLE `kind_food` (
  `id` int(11) NOT NULL,
  `kind_food_name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `kind_food`
--

INSERT INTO `kind_food` (`id`, `kind_food_name`, `status`) VALUES
(1, 'Nước uống', 0),
(2, 'Món ăn', 0),
(3, 'cafe', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `kind_of_staff`
--

CREATE TABLE `kind_of_staff` (
  `id` int(11) NOT NULL,
  `kind_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `kind_of_staff`
--

INSERT INTO `kind_of_staff` (`id`, `kind_name`) VALUES
(1, 'Quản Lý'),
(2, 'Phục vụ'),
(3, 'Thu ngân');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_f`
--

CREATE TABLE `order_f` (
  `id` int(11) NOT NULL,
  `table_id` int(11) NOT NULL,
  `staff_id` int(11) NOT NULL,
  `total_price` int(11) NOT NULL,
  `date_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `order_f`
--

INSERT INTO `order_f` (`id`, `table_id`, `staff_id`, `total_price`, `date_time`, `status`) VALUES
(17, 1, 1, 20000, NULL, 1),
(20, 10, 1, 115000, NULL, 1),
(21, 15, 1, 67500, NULL, 1),
(22, 1, 1, 172500, NULL, 1),
(23, 1, 1, 115000, NULL, 1),
(24, 1, 1, 30000, NULL, 1),
(25, 1, 1, 247500, NULL, 1),
(26, 1, 1, 47500, NULL, 1),
(27, 1, 1, 105000, NULL, 1),
(28, 1, 1, 152500, NULL, 1),
(29, 1, 2, 95000, NULL, 1),
(30, 5, 1, 57500, NULL, 1),
(31, 4, 1, 105000, NULL, 1),
(32, 3, 1, 95000, NULL, 1),
(33, 3, 1, 105000, NULL, 1),
(34, 3, 2, 67500, NULL, 1),
(35, 16, 2, 95000, NULL, 1),
(36, 16, 2, 47500, NULL, 1),
(37, 8, 2, 95000, '2018-07-03 14:22:57', 0),
(38, 9, 2, 20000, '2018-07-03 14:33:22', 0),
(39, 6, 2, 10000, '2018-07-03 14:51:40', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_info`
--

CREATE TABLE `order_info` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `food_id` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `note` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `order_info`
--

INSERT INTO `order_info` (`id`, `order_id`, `food_id`, `quantity`, `note`) VALUES
(77, 17, 1, 2, NULL),
(89, 20, 5, 1, NULL),
(90, 20, 6, 1, NULL),
(91, 20, 2, 2, NULL),
(92, 21, 1, 1, NULL),
(94, 21, 2, 1, NULL),
(95, 21, 6, 1, NULL),
(96, 22, 1, 2, NULL),
(97, 22, 2, 1, NULL),
(98, 22, 5, 3, NULL),
(99, 23, 1, 1, NULL),
(100, 24, 2, 3, NULL),
(101, 23, 2, 1, NULL),
(102, 23, 6, 2, NULL),
(103, 25, 6, 2, NULL),
(104, 26, 5, 1, NULL),
(105, 25, 1, 1, NULL),
(106, 25, 5, 3, NULL),
(107, 27, 4, 1, NULL),
(108, 27, 5, 2, NULL),
(109, 28, 1, 1, NULL),
(110, 28, 6, 3, NULL),
(111, 29, 6, 1, NULL),
(112, 29, 5, 1, NULL),
(113, 30, 3, 1, NULL),
(114, 30, 6, 1, NULL),
(115, 31, 2, 1, NULL),
(116, 31, 5, 2, NULL),
(117, 32, 6, 1, NULL),
(118, 32, 5, 1, NULL),
(119, 33, 6, 1, NULL),
(120, 33, 5, 1, NULL),
(121, 33, 2, 1, NULL),
(122, 34, 2, 1, NULL),
(123, 34, 3, 1, NULL),
(125, 34, 6, 1, NULL),
(126, 35, 6, 2, NULL),
(127, 36, 5, 1, NULL),
(128, 37, 6, 1, NULL),
(129, 37, 5, 1, NULL),
(130, 38, 2, 1, NULL),
(131, 38, 1, 1, NULL),
(132, 39, 1, 1, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `restaurant`
--

CREATE TABLE `restaurant` (
  `id` int(11) NOT NULL,
  `res_name` varchar(255) NOT NULL,
  `res_address` varchar(300) DEFAULT NULL,
  `res_describe` varchar(2000) DEFAULT NULL,
  `phone` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `restaurant`
--

INSERT INTO `restaurant` (`id`, `res_name`, `res_address`, `res_describe`, `phone`) VALUES
(1, 'AsaAsa', 'Bouvet - Na Uy', 'là đảo xa nhất trên thế giới, hoàn toàn không có người ở.\r\nNó không có cảng hoặc cầu tàu, chỉ có bến tàu ở bờ biển, và do đó rất khó tiếp cận. Cách dễ nhất để vào đảo là bằng trực thăng xuất phát từ tàu. Những dòng sông băng tạo thành một lớp băng dày hình thành nên những vách đá cao nhô ra biển hoặc về phía những bờ biển đen với cát núi lửa. Bờ biển dài 29,6 km (18,4 dặm) thường bao bọc bởi những đám băng. Điểm cao nhất trên đảo được gọi là Olavtoppen, đỉnh của nó cao 780 m (2.559 ft) từ mặt nước biển. Có một bãi đá ngầm dung nham trên bờ biển phía tây của đảo, dường như xuất hiện khoảng giữa năm 1955 và 1958, tạo thành nơi làm tổ cho chim chóc.\r\n\r\nDo khí hậu khắc nghiệt và địa thế băng bao phủ, thực vật chỉ giới hạn ở địa y và rêu. Hải cẩu, hải âu và chim cánh cụt là những cư dân duy nhất trên đảo.', '01666051989'),
(2, 'AsaAsa', 'Bouvet - Na Uy', 'là đảo xa nhất trên thế giới, hoàn toàn không có người ở.\r\nNó không có cảng hoặc cầu tàu, chỉ có bến tàu ở bờ biển, và do đó rất khó tiếp cận. Cách dễ nhất để vào đảo là bằng trực thăng xuất phát từ tàu. Những dòng sông băng tạo thành một lớp băng dày hình thành nên những vách đá cao nhô ra biển hoặc về phía những bờ biển đen với cát núi lửa. Bờ biển dài 29,6 km (18,4 dặm) thường bao bọc bởi những đám băng. Điểm cao nhất trên đảo được gọi là Olavtoppen, đỉnh của nó cao 780 m (2.559 ft) từ mặt nước biển. Có một bãi đá ngầm dung nham trên bờ biển phía tây của đảo, dường như xuất hiện khoảng giữa năm 1955 và 1958, tạo thành nơi làm tổ cho chim chóc.\r\n\r\nDo khí hậu khắc nghiệt và địa thế băng bao phủ, thực vật chỉ giới hạn ở địa y và rêu. Hải cẩu, hải âu và chim cánh cụt là những cư dân duy nhất trên đảo.', '01666051989');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `staff`
--

CREATE TABLE `staff` (
  `id` int(11) NOT NULL,
  `staff_name` varchar(200) NOT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `kind_of_staff` int(11) NOT NULL,
  `phone_number` varchar(12) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `res_id` int(11) DEFAULT NULL,
  `staff_root` int(11) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `staff`
--

INSERT INTO `staff` (`id`, `staff_name`, `email`, `password`, `kind_of_staff`, `phone_number`, `address`, `res_id`, `staff_root`) VALUES
(1, 'Nguyễn Văn Tùng', 'tugtitu@gmail.com', '123456', 1, '01666051989', 'abcxyz', 1, -1),
(2, 'zoro', 'zoro123@example.com', 'abc123', 2, '0122456789', 'tp.HCM', 1, 1),
(3, 'nami', 'nami999@yahoo.com', '123456', 3, '01354657', 'tp.HCM', 1, 1),
(5, 'Hung', 'hung344@yahoo.com', '1234567', 2, '0987343223', 'tp . HCM', 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `table_f`
--

CREATE TABLE `table_f` (
  `id` int(11) NOT NULL,
  `table_name` varchar(200) NOT NULL,
  `area_id` int(11) NOT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `table_f`
--

INSERT INTO `table_f` (`id`, `table_name`, `area_id`, `status`) VALUES
(1, 'A_1', 1, 0),
(2, 'A_2', 1, 0),
(3, 'A_3', 1, 0),
(4, 'A_4', 1, 0),
(5, 'A_5', 1, 0),
(6, 'B_1', 2, 1),
(7, 'B_2', 2, 0),
(8, 'B_3', 2, 1),
(9, 'B_4', 2, 1),
(10, 'B_5', 2, 0),
(11, 'C_1', 3, 0),
(12, 'C_2', 3, 0),
(13, 'C_3', 3, 0),
(14, 'C_4', 3, 0),
(15, 'C_5', 3, 0),
(16, 'D_1', 4, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `working_time`
--

CREATE TABLE `working_time` (
  `id` int(11) NOT NULL,
  `working_time_name` varchar(100) NOT NULL,
  `weekdays` varchar(150) DEFAULT NULL,
  `from_hour` time DEFAULT NULL,
  `come_hour` time DEFAULT NULL,
  `staff_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `working_time`
--

INSERT INTO `working_time` (`id`, `working_time_name`, `weekdays`, `from_hour`, `come_hour`, `staff_id`) VALUES
(1, 'Full time', '1,2,4,8,16,32,64', '07:00:00', '20:00:00', 2),
(2, 'Ca 1', '1,2,4,8,16,32', '08:00:00', '10:30:00', 1),
(3, 'Full time', '1,2,4,8,16,32,64', '08:00:00', '21:00:00', 3),
(4, 'ca2', '1,4,16', '13:00:00', '20:00:00', 4),
(6, 'ca3', '2,8,32', '17:00:00', '21:30:00', 5),
(7, 'ca5', '2,4,8,16,32', '07:00:00', '21:30:00', 6),
(8, 'd', '1,4,16,64', '07:00:00', '21:30:00', 6),
(9, 'ca5', '1,4,8,16,32,64', '07:00:00', '21:30:00', 6);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `area`
--
ALTER TABLE `area`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`table_id`),
  ADD KEY `customer_id` (`customer`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Chỉ mục cho bảng `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kind_food` (`kind_food`);

--
-- Chỉ mục cho bảng `kind_food`
--
ALTER TABLE `kind_food`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `kind_of_staff`
--
ALTER TABLE `kind_of_staff`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `order_f`
--
ALTER TABLE `order_f`
  ADD PRIMARY KEY (`id`),
  ADD KEY `table_id` (`table_id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Chỉ mục cho bảng `order_info`
--
ALTER TABLE `order_info`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `food_id` (`food_id`);

--
-- Chỉ mục cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kind_of_staff` (`kind_of_staff`),
  ADD KEY `cty_id` (`res_id`),
  ADD KEY `staff_root` (`staff_root`);

--
-- Chỉ mục cho bảng `table_f`
--
ALTER TABLE `table_f`
  ADD PRIMARY KEY (`id`),
  ADD KEY `area_id` (`area_id`);

--
-- Chỉ mục cho bảng `working_time`
--
ALTER TABLE `working_time`
  ADD PRIMARY KEY (`id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `area`
--
ALTER TABLE `area`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `bill`
--
ALTER TABLE `bill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `kind_food`
--
ALTER TABLE `kind_food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `kind_of_staff`
--
ALTER TABLE `kind_of_staff`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `order_f`
--
ALTER TABLE `order_f`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT cho bảng `order_info`
--
ALTER TABLE `order_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=133;

--
-- AUTO_INCREMENT cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `staff`
--
ALTER TABLE `staff`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `table_f`
--
ALTER TABLE `table_f`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `working_time`
--
ALTER TABLE `working_time`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `order_f` (`id`);

--
-- Các ràng buộc cho bảng `food`
--
ALTER TABLE `food`
  ADD CONSTRAINT `food_ibfk_1` FOREIGN KEY (`kind_food`) REFERENCES `kind_food` (`id`);

--
-- Các ràng buộc cho bảng `order_f`
--
ALTER TABLE `order_f`
  ADD CONSTRAINT `order_f_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `table_f` (`id`),
  ADD CONSTRAINT `order_f_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`);

--
-- Các ràng buộc cho bảng `order_info`
--
ALTER TABLE `order_info`
  ADD CONSTRAINT `order_info_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_f` (`id`),
  ADD CONSTRAINT `order_info_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`);

--
-- Các ràng buộc cho bảng `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`),
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`kind_of_staff`) REFERENCES `kind_of_staff` (`id`);

--
-- Các ràng buộc cho bảng `table_f`
--
ALTER TABLE `table_f`
  ADD CONSTRAINT `table_f_ibfk_1` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
