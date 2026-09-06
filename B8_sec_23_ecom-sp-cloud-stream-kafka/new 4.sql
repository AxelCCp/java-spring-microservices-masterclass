USE db-sp-cloud-config;

create table properties(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	APPLICATION VARCHAR (200),
	PROFILE VARCHAR (200),
	LABEL VARCHAR (200),
	PROP_KEY VARCHAR (200),
	PROP_VALUE VARCHAR (1000),
	CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO properties (application, profile, label, prop_key, prop_value) values
('configdemo', 'prop',  'main', 'build.id', '101'), 
('configdemo', 'prop',  'main', 'build.version', '1.2.3'), 
('configdemo', 'prop',  'main', 'build.name', 'database-production-build server application'), 
('configdemo', 'prop',  'main', 'build.type', 'database production build server application');

INSERT INTO properties (application, profile, label, prop_key, prop_value) values
('configdemo', 'dev',  'main', 'build.id', '101'), 
('configdemo', 'dev',  'main', 'build.version', '1.2.3'), 
('configdemo', 'dev',  'main', 'build.name', 'database-development-build server db'), 
('configdemo', 'dev',  'main', 'build.type', 'database development build server db');


http://localhost:8080/h2-console