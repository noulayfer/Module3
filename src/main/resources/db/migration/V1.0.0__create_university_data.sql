create EXTENSION IF NOT EXISTS "uuid-ossp";

drop table IF EXISTS grade;
drop table IF EXISTS teacher;
drop table IF EXISTS subject;
drop table IF EXISTS student;
drop table IF EXISTS student_group;


create TABLE IF NOT EXISTS student_group (
  group_id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

create TABLE IF NOT EXISTS student (
  student_id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  age INTEGER NOT NULL,
  date_of_admission VARCHAR(255) NOT NULL,
  group_id VARCHAR(255) NOT NULL REFERENCES student_group(group_id)
);

create TABLE IF NOT EXISTS subject (
  subject_id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

create TABLE IF NOT EXISTS grade (
  grade_id VARCHAR(255) PRIMARY KEY,
  subject_id VARCHAR(255) NOT NULL,
  value FLOAT NOT NULL,
  student_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);

create TABLE IF NOT EXISTS teacher (
  teacher_id VARCHAR(255) PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  subject_id VARCHAR(255) NOT NULL,
  age INTEGER NOT NULL,
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);

insert into student_group (group_id, name)
values
  (uuid_generate_v4(), 'Group 1'),
  (uuid_generate_v4(), 'Group 2'),
  (uuid_generate_v4(), 'Group 3'),
  (uuid_generate_v4(), 'Group 4'),
  (uuid_generate_v4(), 'Group 5'),
  (uuid_generate_v4(), 'Group 6');

insert into student (student_id, name, age, date_of_admission, group_id)
select
  uuid_generate_v4(),
  case
    when n % 7 = 0 THEN 'Sasha'
    WHEN n % 7 = 1 THEN 'Oleg'
    WHEN n % 7 = 2 THEN 'Maria'
    WHEN n % 7 = 3 THEN 'Anna'
    WHEN n % 7 = 4 THEN 'Ivan'
    WHEN n % 7 = 5 THEN 'Katya'
    ELSE 'Alex'
  END AS name,
  floor(random() * 7 + 18),
    DATE_TRUNC('year', CURRENT_DATE - INTERVAL '5 years') +
   ((random() * 365) || ' days')::INTERVAL +
   ((random() * 31) || ' days')::INTERVAL +
   INTERVAL '7 months',
  sg.group_id
FROM generate_series(1, 36) n
JOIN student_group sg ON sg.name = 'Group ' || ceil(n/6)::text;

insert into subject (subject_id, name)
values
  (uuid_generate_v4(), 'Math'),
  (uuid_generate_v4(), 'History'),
  (uuid_generate_v4(), 'Science');


insert into grade (grade_id, subject_id, value, student_id)
select uuid_generate_v4(),
       s.subject_id,
       (random() * 3 + 2)::numeric(2, 1),
       st.student_id
FROM (select student_id from student order by random() LIMIT 36) AS st
CROSS JOIN (select subject_id from subject order by random() LIMIT 3) AS s;


insert into teacher (teacher_id, first_name, last_name, age, subject_id)
select uuid_generate_v4(),
  case
    when n % 7 = 0 THEN 'John'
    WHEN n % 7 = 1 THEN 'Mary'
    WHEN n % 7 = 2 THEN 'David'
    WHEN n % 7 = 3 THEN 'Sarah'
    WHEN n % 7 = 4 THEN 'William'
    WHEN n % 7 = 5 THEN 'Elizabeth'
    ELSE 'James'
  END AS first_name,
  CASE
    WHEN n % 5 = 0 THEN 'Smith'
    WHEN n % 5 = 1 THEN 'Johnson'
    WHEN n % 5 = 2 THEN 'Brown'
    WHEN n % 5 = 3 THEN 'Garcia'
    ELSE 'Jones'
  END AS last_name,
  floor(random() * 30 + 25),
  s.subject_id
FROM generate_series(1, 3) n
JOIN subject s ON s.name =
  CASE
    WHEN n % 3 = 0 THEN 'Math'
    WHEN n % 3 = 1 THEN 'History'
    ELSE 'Science'
  END;




