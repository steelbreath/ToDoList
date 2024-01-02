create table Tasks(
                      id LONG NOT NULL AUTO_INCREMENT,
                      status ENUM('PLANNED','WORK_IN_PROGRESS','POSTPONED',
                          'NOTIFIED','SIGNED','DONE','CANCELLED') DEFAULT 'PLANNED',
                      description VARCHAR(255),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      deadline TIMESTAMP,
                      PRIMARY KEY ( id )
);