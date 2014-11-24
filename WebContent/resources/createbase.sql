/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/*==============================================================*/

create sequence Examination_seq
increment by 1
start with 1
nocycle
/

create sequence GroupStudent_seq
increment by 1
start with 1
nocycle
/

create sequence University_seq
increment by 1
start with 1
nocycle
/

create sequence Olympiad_seq
increment by 1
start with 1
nocycle
/

create sequence Progress_seq
increment by 1
start with 1
nocycle
/

create sequence Publication_seq
increment by 1
start with 1
nocycle
/

create sequence Student_seq
increment by 1
start with 1
nocycle
/

create sequence Subject_seq
increment by 1
start with 1
nocycle
/

create sequence Teacher_seq
increment by 1
start with 1
nocycle
/

/*==============================================================*/
/* Table: Bridge                                              */
/*==============================================================*/
create table Bridge 
(
   StudentID          NUMBER(6)            not null,
   PublicationID      NUMBER(6)            not null
)
/

alter table Bridge
   add constraint Bridge_pk primary key (StudentID, PublicationID)
/

/*==============================================================*/
/* Table: Bridge2                                              */
/*==============================================================*/
create table Bridge2 
(
   TeacherID          NUMBER(6)            not null,
   PublicationID      NUMBER(6)            not null
)
/

alter table Bridge2
   add constraint Bridge2_pk primary key (TeacherID, PublicationID)
/

/*==============================================================*/
/* Table: Examination                                         */
/*==============================================================*/
create table Examination 
(
   ExaminationID      NUMBER(6)            not null,
   StudentID          NUMBER(6)            not null,
   ExaminationMark    VARCHAR2(7)          not null,
   ExaminationName    VARCHAR2(10)         not null
)
/

alter table Examination
   add constraint ExaminationMark_ch check (ExaminationMark in ('A','B','C','D','E','F','Fx'))
/

alter table Examination
   add constraint Examination_pk primary key (ExaminationID)
/

/*==============================================================*/
/* Table: GroupStudent                                        */
/*==============================================================*/
create table GroupStudent 
(
   GroupStudentID     NUMBER(6)            not null,
   GroupStudentNumber VARCHAR2(25)         not null
)
/

alter table GroupStudent
   add constraint GroupStudent_pk primary key (GroupStudentID)
/

/*==============================================================*/
/* Table: Olympiad                                            */
/*==============================================================*/
create table Olympiad 
(
   OlympiadID         NUMBER(6)            not null,
   StudentID          NUMBER(6)            not null,
   OlympiadDirection  VARCHAR2(500)        not null,
   OlympiadPlace      NUMBER(1)            not null,
   OlympiadType       VARCHAR2(40)         not null,
   OlympiadDate       NUMBER(4)            not null,
   OlympiadCore       VARCHAR2(7)          not null
)
/

alter table Olympiad
   add constraint OlympiadPlace_ch check (OlympiadPlace between 1 and 3)
/

alter table Olympiad
   add constraint OlympiadType_ch check (OlympiadType in ('Всеукраїнська','Міжнародна','Університетська'))
/

alter table Olympiad
   add constraint OlympiadCore_ch check (OlympiadCore in ('True','False'))
/

alter table Olympiad
   add constraint Olympiad_pk primary key (OlympiadID)
/

/*==============================================================*/
/* Table: Progress                                           */
/*==============================================================*/
create table Progress 
(
   ProgressID         NUMBER(6)            not null,
   SubjectID          NUMBER(6)            not null,
   StudentID          NUMBER(6)            not null,
   ProgressMark       VARCHAR2(4)          not null,
   ProgressExam		  NUMBER(1)			   not null
)
/

alter table Progress
   add constraint ProgressMark_ch check (ProgressMark in ('A','B','C','D','E','F','Fx'))
/

alter table Progress
   add constraint Progress_pk primary key (ProgressID)
/

/*==============================================================*/
/* Table: Publication                                         */
/*==============================================================*/
create table Publication 
(
   PublicationID      NUMBER(6)            not null,
   PublicationTitle   VARCHAR2(250)        not null,
   PublicationType    VARCHAR2(80),
   PublicationMag	  VARCHAR2(250)		   not null,
   PublicationMagNum  VARCHAR2(20),
   PublicationDate    NUMBER(4)            not null,
   PublicationPage	  VARCHAR2(25)		   not null,
   PublicationPlace   VARCHAR2(120),
   PublicationBase	  NUMBER(1)			   not null,
   PublicationPubl    VARCHAR2(250),
   PublicationThesis  NUMBER(1)			   not null
)
/

alter table Publication
   add constraint PublicationBase_ch check (PublicationBase in (1,2))
/

alter table Publication
   add constraint AchievementType_ch check (PublicationType in ('', 'Міжнародна конференція','Всеукраїнська конференція','Університетська'))
/

alter table Publication
   add constraint Achievement_pk primary key (PublicationID)
/

/*==============================================================*/
/* Table: Student                                             */
/*==============================================================*/
create table Student 
(
   StudentID          NUMBER(6)            not null,
   GroupStudentID     NUMBER(6)            not null,
   StudentFullName    VARCHAR2(120)        not null,
   StudentBook        VARCHAR2(12)         not null,
   StudentEnter       NUMBER(5)            not null,
   StudentOKR         VARCHAR2(20)         not null,
   StudentMode		  VARCHAR2(20)         not null
)
/

alter table Student
   add constraint StudentMode_ch check (StudentMode in ('Денна', 'Заочна'))
/

alter table Student
   add constraint Student_pk primary key (StudentID)
/

alter table Student
   add constraint StudentBook_uq unique (StudentBook)
/

/*==============================================================*/
/* Table: Subject                                             */
/*==============================================================*/
create table Subject 
(
   SubjectID          NUMBER(6)            not null,
   SubjectTitle       VARCHAR2(450)        not null,
   SubjectHours       NUMBER (3)           not null
)
/

alter table Subject
   add constraint SubjectHours_ch check (SubjectHours between 1 and 500)
/

alter table Subject
   add constraint Subject_pk primary key (SubjectID)
/

alter table Subject
   add constraint SubjectTitle_uq unique (SubjectTitle)
/

/*==============================================================*/
/* Table: Teacher                                             */
/*==============================================================*/
create table Teacher 
(
   TeacherID          NUMBER(6)            not null,
   TeacherFullName    VARCHAR2(200)        not null,
   TeacherStatus      VARCHAR2(25)         not null
)
/

alter table Teacher
   add constraint TeacherStatus_ch check (TeacherStatus in ('Асистент', 'Старший викладач', 'Доцент', 'Професор'))
/

alter table Teacher
   add constraint Teacher_pk primary key (TeacherID)
/

alter table Bridge
   add constraint Bridge_Student_fk foreign key (StudentID)
      references Student (StudentID)
/

alter table Bridge
   add constraint Bridge_Publication_fk foreign key (PublicationID)
      references Publication (PublicationID)
/

alter table Bridge2
   add constraint Bridge2_Teacher_fk foreign key (TeacherID)
      references Teacher (TeacherID)
/

alter table Bridge2
   add constraint Bridge2_Publication_fk foreign key (PublicationID)
      references Publication (PublicationID)
/

alter table Examination
   add constraint Examination_Student_fk foreign key (StudentID)
      references Student (StudentID)
      on delete cascade
/

alter table Olympiad
   add constraint Olympiad_Student_fk foreign key (StudentID)
      references Student (StudentID)
      on delete cascade
/

alter table Progress
   add constraint Progress_Student_fk foreign key (StudentID)
      references Student (StudentID)
      on delete cascade
/

alter table Progress
   add constraint Progress_Subject_fk foreign key (SubjectID)
      references Subject (SubjectID)
      on delete cascade
/

alter table Student
   add constraint Student_GroupStudent_fk foreign key (GroupStudentID)
      references GroupStudent (GroupStudentID)
      on delete cascade
/