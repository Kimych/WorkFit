INSERT INTO `workfit`.`name_month`
(
`NAME`)
VALUES
('Январь'),
('Февраль'),
('Март'),
('Апрель'),
('Май'),
('Июнь'),
('Июль'),
('Август'),
('Сентябрь'),
('Октябрь'),
('Ноябрь'),
('Декабрь');

INSERT INTO `workfit`.`year`
(`NUMBER`,
`DESKRIPTION`)
VALUES
(2014,
'По вопросам размещения рекламы...');


INSERT INTO `workfit`.`month`
(`DESCRIPTION`,
`NAME_MONTH_ID`,
`YEAR_ID`)
VALUES
(null,
2,
1),
(null,
3,
1),
(null,
4,
1),
(null,
5,
1),
(null,
6,
1),
(null,
7,
1);

INSERT INTO `workfit`.`worker`
(`FIRST_NAME`,
`SECOND_NAME`,
`THIRD_NAME`)
VALUES
('Андрей',
'Фамилия1',
'Отчество1'),
('Денис',
'Фамилия2',
'Отчество2'),
('Антон',
'Фамилия3',
'Отчество3');


INSERT INTO `workfit`.`days_of_work`
(`WORKLOG`,
`TIMESTAMP`,
`BONUS_TIME`,
`BONUS_TIME_DESCRIPTION`,
`AKTUAL_WORKED_DAYS`,
`MONTH_ID`,
`WORKER_ID`)
VALUES
(75.2000000000000000,
'2014-02-18',
2,
'варил много кофе...',
12,
1,
3),

(100.2833333333333333,
'2014-02-18',
0,
null,
12,
1,
1),

(53.7500000000000000,
'2014-02-18',
1,
null,
12,
1,
2),


(120.7166666666666667,
'2014-02-26',
0,
null,
17,
1,
3),

(146.7166666666666667,
'2014-02-26',
0,
null,
17,
1,
1),

(99.6333333333333333,
'2014-02-26',
3,
null,
17,
1,
2),


(34.6333333333333333,
'2014-03-07',
0,
null,
5,
2,
3),

(39.3333333333333333,
'2014-03-07',
0,
null,
5,
2,
1),

(23.3166666666666667,
'2014-03-07',
0,
null,
17,
2,
2),

(82.7666666666666667,
'2014-03-17',
0,
null,
10,
2,
3),

(86.9000000000000000,
'2014-03-17',
0,
null,
10,
2,
1),

(78.1000000000000000,
'2014-03-17',
4,
null,
10,
2,
2),


(162.4333333333333333,
'2014-03-31',
0,
null,
20,
2,
3),

(168.6833333333333333,
'2014-03-31',
0,
null,
20,
2,
1),

(168.4000000000000000,
'2014-03-31',
0,
null,
20,
2,
2);


