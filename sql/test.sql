USE javaee_library;

DROP TABLE IF EXISTS javaee_library.student;
CREATE TABLE javaee_library.student (
  id       INT AUTO_INCREMENT PRIMARY KEY
  COMMENT 'ID PK',
  name     VARCHAR(255) NOT NULL
  COMMENT '名字',
  password VARCHAR(255) NOT NULL
  COMMENT '密码'
)
  COMMENT '学生表';

DROP TABLE IF EXISTS javaee_library.paper;
CREATE TABLE javaee_library.paper (
  id    INT AUTO_INCREMENT PRIMARY KEY
  COMMENT 'ID PK',
  title VARCHAR(255) COMMENT '试卷名称'
)
  COMMENT '试卷表';

DROP TABLE IF EXISTS javaee_library.test;
CREATE TABLE javaee_library.test (
  id       INT AUTO_INCREMENT PRIMARY KEY
  COMMENT 'ID PK',
  question VARCHAR(255) COMMENT '题目',
  answer   VARCHAR(255) COMMENT '答案',
  score    INT COMMENT '分数',
  paperId  INT COMMENT '试卷ID'
)
  COMMENT '试题表';

DROP TABLE IF EXISTS javaee_library.student_test;
CREATE TABLE javaee_library.student_test (
  id        INT AUTO_INCREMENT PRIMARY KEY
  COMMENT 'ID PK',
  studentId INT COMMENT '学生ID',
  testId    INT COMMENT '试题ID',
  score     INT COMMENT '得分'
)
  COMMENT '学生试题关联表';

ALTER TABLE javaee_library.test
  ADD CONSTRAINT
  test_fk_paperId
FOREIGN KEY (paperId)
REFERENCES javaee_library.paper (id);

ALTER TABLE javaee_library.student_test
  ADD CONSTRAINT
  student_test_fk_studentId
FOREIGN KEY (studentId)
REFERENCES javaee_library.student (id);

ALTER TABLE javaee_library.student_test
    ADD CONSTRAINT
    student_test_fk_testId
FOREIGN KEY (testId)
  REFERENCES javaee_library.test(id);


INSERT INTO javaee_library.paper VALUES (NULL,'JavaEE程序设计课程考试');

INSERT INTO javaee_library.test VALUES (NULL ,'javaEE','自己去百度',100,1);

SELECT *
FROM javaee_library.paper;

SELECT *
FROM javaee_library.test;

SELECT *
FROM javaee_library.student;



