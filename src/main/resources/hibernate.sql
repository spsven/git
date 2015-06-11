/* Data category table */
INSERT INTO category VALUE (NULL, 'Development');
INSERT INTO category VALUE (NULL, 'Project Management');

/* Data courses table */
INSERT INTO courses_table
  VALUE (NULL, 'Project Management description', 'Link to Project Management 101', 0, 0, 'Project Management 101',
         'lecturer-a@tc.edu', 'Draft', 1);
INSERT INTO courses_table VALUE (NULL, 'NET Technology description', 'Link to NET Technology', 0, 0,
                                 'NET Technology', 'lecturer-a@tc.edu', 'Proposal', 1);
INSERT INTO courses_table VALUE (NULL, 'Courses 3 description', 'Link to Courses 3', 0, 0,
                                 'Courses 3', 'lecturer-a@tc.edu', 'Rejected', 1);
INSERT INTO courses_table VALUE (NULL, 'Courses 4 description', 'Link to Courses 4', 0, 0,
                                 'Courses 4', 'lecturer-a@tc.edu', 'New', 1);
INSERT INTO courses_table VALUE (NULL, 'Courses 5 description', 'Link to Courses 5', 0, 0,
                                 'Courses 5', 'lecturer-a@tc.edu', 'Draft', 1);
INSERT INTO courses_table VALUE (NULL, 'Courses 6 description', 'Link to Courses 6', 0, 0,
                                 'Courses 6', 'lecturer-a@tc.edu', 'Draft', 2);
INSERT INTO courses_table VALUE (NULL, 'Courses 7 description', 'Link to Courses 7', 0, 0,
                                 'Courses 7', 'lecturer-a@tc.edu', 'Proposal', 2);
INSERT INTO courses_table VALUE (NULL, 'Courses 8 description', 'Link to Courses 8', 0, 0,
                                 'Courses 8', 'lecturer-a@tc.edu', 'Rejected', 2);

/* Data users table */
INSERT INTO users VALUE (NULL, TRUE, '123', 'km@tc.edu');
INSERT INTO users VALUE (NULL, TRUE, '123', 'dm@tc.edu');
INSERT INTO users VALUE (NULL, TRUE, '123', 'lecturer-a@tc.edu');
INSERT INTO users VALUE (NULL, TRUE, '123', 'lecturer-b@tc.edu');
INSERT INTO users VALUE (NULL, TRUE, '123', 'user-a@tc.edu');
INSERT INTO users VALUE (NULL, TRUE, '123', 'user-b@tc.edu');
INSERT INTO users VALUE (NULL, TRUE, '123', 'user-c@tc.edu');


/* Data user roles */
INSERT INTO user_roles VALUE (NULL, 'USER', '1');
INSERT INTO user_roles VALUE (NULL, 'USER', '2');
INSERT INTO user_roles VALUE (NULL, 'USER', '3');
INSERT INTO user_roles VALUE (NULL, 'USER', '4');
INSERT INTO user_roles VALUE (NULL, 'USER', '5');
INSERT INTO user_roles VALUE (NULL, 'USER', '6');
INSERT INTO user_roles VALUE (NULL, 'USER', '7');


INSERT INTO user_roles VALUE (NULL, 'LECTURER', '3');
INSERT INTO user_roles VALUE (NULL, 'LECTURER', '4');

INSERT INTO user_roles VALUE (NULL, 'Knowledge Manager', '1');
INSERT INTO user_roles VALUE (NULL, 'Department Manager', '2');




