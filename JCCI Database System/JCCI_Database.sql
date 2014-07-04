create database `JCCI_db`;
use `JCCI_db`;

create table if not exists `JCCI_db`.`users`(
	`id` int not null auto_increment primary key,
	`user_type` char not null,
	`username` varchar(20) not null unique,
	`password` varchar(50) not null
);

create table if not exists `JCCI_db`.`applicant_info`(
	`id` int not null auto_increment primary key,
	`date_sourced` date null,
	`date_processed` date null,
	`first_name` varchar(255) not null,
	`last_name` varchar(255) not null,
	`primary_mobile` varchar(15) not null,
	`secondary_mobile` varchar(15) not null,
	`school` varchar(255) not null,
	`last_position` varchar(255) not null,
	`job_classification` char(1) not null,
	`last_company` varchar(255) not null,
	`email` varchar(255) not null,
	`source` varchar(255) not null,
	`source_specifics` varchar(255) not null,
	`position_endorsed` varchar(255) not null,
	`status` varchar(255) not null,
	`sched_date` date null,
	`sched_time` varchar(8) null,
	`sched_outcome` varchar(255) null,
	`resched_date` date null,
	`resched_time` varchar(8) null,
	`resched_outcome` varchar(255) null,
	`recruiter` varchar(255) not null,
	`endorsed_client` varchar(255) null,
	`client_result` varchar(255) null,
	`remarks` varchar(300) null,
	`actual_revenue` varchar(255) null,
	`cv_path` varchar(255) not null
);

create user 'administrator'@'%' identified by 'password';
#grant usage on JCCI_db.* to administrator@'%' identified by 'password'; 
grant all on *.* to administrator@'%' identified by 'password';