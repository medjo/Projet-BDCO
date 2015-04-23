CREATE TABLE test (
	att char(5),
	check (REGEXP_LIKE (att,'^[[:alpha:]+][[:alpha:]]*$?'))
);

insert into test values('d5');
