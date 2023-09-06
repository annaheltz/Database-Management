-- CHECKING IF TABLES UPDATED CORRECTLY
-- 1) checking if messages inserted into message recipient
SELECT * FROM messagerecipient; -- works

-- 2) checking if users trying to join full group are pending members
SELECT * FROM pendinggroupmember; -- works

-- 3) checking if users from pending group will be added once group member leaves
SELECT * FROM groupmember; -- works

--4) checking if timestamp is updated on message send
SELECT * from groupmember
WHERE userid = 5; -- works

--5) check groupinfo
SELECT * FROM groupinfo; -- works

-- 6) checking clock updates
SELECT * FROM clock;

--7) checking friendship
SELECT * FROM friend;


Select * from message;

SELECT userID, count(*) as Num_messages
FROM ((SELECT fromID as userID, messageBody, toGroupID, toUserID, timeSent FROM message)
UNION (SELECT toUserID as userID, messageBody, toGroupID, fromID, timeSent FROM message_copy)) as msgs
WHERE ((DATE_PART('year', CAST((SELECT * FROM get_clock) as DATE)) - DATE_PART('year', CAST((msgs.timeSent) as DATE))) * 12 + (DATE_PART('month', CAST((SELECT * FROM get_clock) as DATE)) - DATE_PART('month', CAST(msgs.timeSent as DATE)))) < 20
GROUP BY userID
ORDER BY Num_messages DESC
FETCH FIRST 10 ROWS ONLY;

SELECT * from profile where email like '%anna1%' or name like '%anna%';
SELECT * from message;

SELECT count(userID) as num_friends
FROM ((SELECT userID2 AS userID FROM friend WHERE userID1 =  1 )
UNION  (SELECT userID1 AS userID FROM friend WHERE userID2 =  1)UNION
    (Select userID from groupMember  WHERE gID =  1 AND userID !=  1 )) AS users  ;

SELECT * FROM pendinggroupmember;
SELECT * FROM groupmember;
SELECT * FROM profile;

INSERT INTO pendinggroupmember values(1,99,'i want to join',(SELECT * FROM get_clock));


SELECT count(*) FROM pendinggroupmember WHERE gID in (SELECT gID from groupmember where userID = 1 and role = 'manager');

SELECT count(*) FROM pendinggroupmember WHERE gID in (SELECT gID from groupmember where userID = 1 and role = 'manager')

SELECT * from friend;

SELECT * from clock;




SELECT * from get_three_degrees(10);

SELECT * FROM messagerecipient;

INSERT INTO message VALUES(30233,66,'hi!',77, null, (SELECT * FROM get_clock));

(SELECT CAST(pseudo_time as date) FROM get_clock);

SELECT * FROM profile where email similar to 'anna%';

INSERT INTO groupmember VALUES (13,101,'manager',(SELECT * FROM get_clock));

