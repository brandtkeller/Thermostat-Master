CREATE TABLE IF NOT EXISTS thermostat (id SERIAL PRIMARY KEY, Title VARCHAR(255), Threshold INT, Scheduleid SERIAL);

GRANT ALL PRIVILEGES ON TABLE thermostat TO developer;

INSERT INTO thermostat (Title, Threshold, Scheduleid)
VALUES ('Main', 3, 1);

CREATE TABLE IF NOT EXISTS schedule (id SERIAL PRIMARY KEY, Title VARCHAR(255));

GRANT ALL PRIVILEGES ON TABLE schedule TO developer;

INSERT INTO schedule (Title)
VALUES ('Main');

CREATE TABLE IF NOT EXISTS settings (id SERIAL PRIMARY KEY, Day VARCHAR(255), Scheduleid SERIAL,
Wake VARCHAR(255), WakeTemp INT,
Leave VARCHAR(255), LeaveTemp INT,
Home VARCHAR(255), HomeTemp INT,
Sleep VARCHAR(255), SleepTemp INT);

GRANT ALL PRIVILEGES ON TABLE settings TO developer;

INSERT INTO settings (Day, Scheduleid, Wake, WakeTemp, Leave, LeaveTemp, Home, HomeTemp, Sleep, SleepTemp) 
VALUES ('Sun', 1, '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55),
('Mon', 1, '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55),
('Tue', 1, '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55),
('Wed', 1, '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55),
('Thu', 1, '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55),
('Fri', 1, '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55),
('Sat', 1, '06:00:00', 65, '09:00:00', 60, '15:00:00', 70, '19:00:00', 55);