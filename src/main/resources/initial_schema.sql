create table _user (
	_id int primary key not null,
	_username varchar(255),
	_password varchar(255)
);
create table _schedule (
	_id int primary key not null,
	_user_id int not null,
	_name varchar(255)
);
create table _workout (
	_id int primary key not null,
	_name varchar(255)
);
create table _exercise (
	_id int primary key not null,
	_group varchar(25),
	_name varchar(255),
	_repeats varchar(255),
	_weight int,
	_external_link varchar(255)
);
create table _schedule_workout_binding (
	_id int primary key not null,
	_schedule_id int not null,
	_workout_id int not null
);
create table _workout_exercise_binding (
	_id int primary key not null,
	_workout_id int not null,
	_exercise_id int not null
);