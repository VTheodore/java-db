USE adv_q_bookshop_system;

DROP PROCEDURE IF EXISTS GetNumberOfBooksWritten;

DELIMITER //

CREATE PROCEDURE GetNumberOfBooksWritten(
    IN first_name VARCHAR(50),
    IN last_name VARCHAR(60)
)

BEGIN

    SELECT COUNT(b.id) FROM authors AS a
    JOIN books b on b.author_id = a.id
    WHERE a.first_name = first_name AND a.last_name = last_name
    GROUP BY b.author_id;

END//

DELIMITER ;