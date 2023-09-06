DROP TABLE IF EXISTS profile CASCADE;
DROP TABLE IF EXISTS friend CASCADE;
DROP TABLE IF EXISTS pendingFriend CASCADE;
DROP TABLE IF EXISTS pendingGroupMember CASCADE;
DROP TABLE IF EXISTS groupInfo CASCADE;
DROP TABLE IF EXISTS groupMember CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS messageRecipient CASCADE;
DROP TABLE IF EXISTS CLOCK CASCADE;
DROP TABLE IF EXISTS message_copy CASCADE;
DROP VIEW IF EXISTS get_clock;


CREATE TABLE profile
(
    userID int,
    name varchar(50) NOT NULL,                      -- assuming name cannot be null
    email varchar(50) NOT NULL,                     -- assuming email cannot be null
    password varchar(50) NOT NULL,                     -- assuming password cannot be null
    date_of_birth date NOT NULL,                     -- assuming date of birth cannot be null
    lastlogin timestamp,                   -- assuming timestamp can be null, because if they create account without logging in it will be null.
    CONSTRAINT PK_profile PRIMARY KEY (userID),
    CONSTRAINT UN_profile UNIQUE(email)                -- assuming that all emails must be unique, this is a usual assumption for accounts
);

CREATE TABLE friend
(
    userID1 int,
    userID2 int,
    JDate date NOT NULL,            -- assuming this cannot be null, because there must be a date associated with a friend request
    requestText varchar(200),       -- assuming that request text can be null, you do not need to have a reason you want to be friends, you can just request
    CONSTRAINT PK_FRIEND PRIMARY KEY (userID1,userID2),
    CONSTRAINT FK_FRIEND1 FOREIGN KEY (userID1) REFERENCES profile  DEFERRABLE,
    CONSTRAINT FK_FRIEND2 FOREIGN KEY (userID2) REFERENCES profile  DEFERRABLE
);

CREATE TABLE pendingFriend
(
    fromID int,
    toID int,
    requestText varchar(200),      -- assuming that request text can be null, you do not need to have a reason you want to be friends, you can just request
    CONSTRAINT PK_pendingFriend PRIMARY KEY (fromID,toID),
    CONSTRAINT FK_FRIEND1 FOREIGN KEY (fromID) REFERENCES profile  DEFERRABLE,
    CONSTRAINT FK_FRIEND2 FOREIGN KEY (toID) REFERENCES profile DEFERRABLE
);

CREATE TABLE groupInfo
(
    gID int,
    name varchar(50) NOT NULL,              -- assuming group name cannot be null
    size int NOT NULL,                      -- assuming that group size cannot be null because this affects if there are pendingGroupMembers or not
    description varchar(200),               -- assuming description can be null, sometimes the name of a group can be enough description
    CONSTRAINT PK_groupInfo PRIMARY KEY (gID),
    CONSTRAINT UN_groupInfo UNIQUE(name)                -- assuming that all group names must be unique, this is a usual assumption for groups
);

CREATE TABLE groupMember
(
    gID int,
    userID int,
    role varchar(20) DEFAULT 'member',      -- assuming that the default role will be member, so that when a group member is added from the pendingGroupMember table, they become a member
    lastConfirmed timestamp,                -- assuming that this can be null, because if they are added off the pendingGroupMember table they will not be confirmed until they send a message, meaning assuming that groupmembers are not confirmed until they send a message
    CONSTRAINT PK_groupMember PRIMARY KEY (gID,userID),
    CONSTRAINT FK_groupMember FOREIGN KEY (userID) REFERENCES profile  DEFERRABLE,
    CONSTRAINT FK_groupMember2 FOREIGN KEY (gID) REFERENCES groupInfo DEFERRABLE
);

CREATE TABLE pendingGroupMember
(
    gID int,
    userID int,
    requestText varchar(200),       -- assuming that request text can be null, you do not need to have a reason you want to join group, you can just request
    requestTime timestamp NOT NULL,         -- assuming requestTime cannot be null, because in order to submit a request to join group there must be a time associated with the request
    CONSTRAINT PK_pendingGroupMember PRIMARY KEY (gID,userID),
    CONSTRAINT FK_pendingGroupMember FOREIGN KEY (userID) REFERENCES profile  DEFERRABLE,
    CONSTRAINT FK_pendingGroupMember2 FOREIGN KEY (gID) REFERENCES groupInfo DEFERRABLE
);

CREATE TABLE message
(
    msgID int,
    fromID int,
    messageBody varchar(200) NOT NULL,              -- assuming message cannot be null
    toUserID int DEFAULT NULL,
    toGroupID int DEFAULT NULL,
    timeSent timestamp NOT NULL,                    -- assuming timeSent cannot be null because when you send a message there will be a time associated with it
    CONSTRAINT PK_message PRIMARY KEY (msgID),
    CONSTRAINT FK_message1 FOREIGN KEY (fromID) REFERENCES profile DEFERRABLE,
    CONSTRAINT FK_message2 FOREIGN KEY (toUserID) REFERENCES profile  DEFERRABLE,
    CONSTRAINT FK_message3 FOREIGN KEY (toGroupID) REFERENCES groupInfo  DEFERRABLE
);

CREATE TABLE messageRecipient
(
    msgID int,
    userID int,
    CONSTRAINT PK_messageRecipient PRIMARY KEY (msgID,userID),
    CONSTRAINT FK_messageRecipient1 FOREIGN KEY (msgID) REFERENCES message  DEFERRABLE,
    CONSTRAINT FK_messageRecipient2 FOREIGN KEY (userID) REFERENCES profile  DEFERRABLE
);

CREATE TABLE CLOCK
(
    pseudo_time timestamp,
    CONSTRAINT PK_CLOCK PRIMARY KEY (pseudo_time)
);

CREATE VIEW get_clock AS
SELECT * FROM CLOCK
ORDER BY pseudo_time DESC
FETCH FIRST 1 ROW ONLY;

CREATE TABLE message_copy AS
    SELECT * FROM message;


--------------------------------------------------
-- CONSTRAINTS
--------------
-- For both structural and semantic integrity constraints, you must state your assumptions as comments in
-- your database creation script. Any semantic integrity constraints involving multiple relations should
-- be specified using triggers in this phase.


-- ------------------- 1 ------------------
-- constraint so that you cannot request to be your own friend
ALTER TABLE pendingFriend
ADD CONSTRAINT pendingFriend_req_not_yourself CHECK (toID != fromID);

-- ------------------- 2 ------------------
-- constraint to enforce the size of a group be greater than 0
ALTER TABLE groupInfo
ADD CONSTRAINT group_size_not_zero CHECK (size > 0);

-- ------------------- 3 ------------------
-- constraint so messages cant be sent to the same user
ALTER TABLE message
ADD CONSTRAINT message_recipient_not_yourself CHECK (toUserID != fromID);



-- ------------------- 6------------------
-- constraint so that you cannot be your own friend
ALTER TABLE friend
ADD CONSTRAINT friend_not_yourself CHECK (userID1 != userID2);

-- ------------------- 6------------------
-- constraint so that role for group member is either manager or member
ALTER TABLE groupmember
ADD CONSTRAINT role_check CHECK  (role IN ('member', 'manager'));


-------------------------------
-- three degrees function
CREATE OR REPLACE FUNCTION get_three_degrees (loggedInUser int)
    RETURNS TABLE (
        root_user INT,
        first_jump INT,
        second_jump INT,
        third_jump INT
)
AS $$
BEGIN

drop table if exists first_hop;
drop table if exists second_hop;
drop table if exists third_hop;
drop table if exists final_hop;


CREATE table first_hop as
SELECT userid1 as first_hop1, userid2 as first_hop2
from friend
where userid1 = loggedInUser;

CREATE table second_hop as
(SELECT userid2 as second_hop2, userid1 as second_hop1
from friend
where (userid1 = loggedInUser) OR
      userid1 in (SELECT userid2
                  from friend
                  where userid1 = loggedInUser)) ;

CREATE table third_hop as
SELECT DISTINCT userid1 as third_hop1, userid2 as third_hop2
 from friend
 where (userid2 != loggedInUser) AND ((userid1 = loggedInUser) OR ( userid1 in
                                                (SELECT userid2
                                                 from friend
                                                 where (userid1 = loggedInUser) OR
                                                       userid1 in (SELECT userid2
                                                                   from friend
                                                                   where userid1 = loggedInUser))));


RETURN QUERY SELECT first_hop1 as root_user,second_hop1 as first_jump,third_hop1 as second_jump, third_hop2 as final_jump FROM (third_hop full outer join
        (Select * from (second_hop  full outer join first_hop on first_hop2 = second_hop1)) as almost_final
        on third_hop1= second_hop2);
END;
    $$
    LANGUAGE plpgsql;







-------------------------------------------------
-- ASSUMPTIONS
-------------------------------------------------
-- 1) we are assuming that this is a follower relationship (like Instagram). So user 1 can follow (friend request) user 2, but this does not mean user 2
--  follows (friend requests) user1. Meaning that a tuple of friend (1, 2, JDate, requestText) is different than friend (2, 1, JDate, requestText). This
--  also means that a tuple (1,2,JDate, requestText) means user 1 follows user 2. And tuple (2, 1, JDate, requestText) means user 2 follows user 1.

-- 2) When a message is sent to another user, they both must be part of the group that the message is sent in

-- 3) message cannot be sent to the same user, fromID cannot equal toUserID

-- 4) assuming lastConfirmed, date_of_birth, lastlogin, requestTime, timeSent, JDate all must have values less than or equal to the maximum timestamp entry in clock table

-- 5) assuming that lastConfirmed in groupMember gets updated to maximum entry in clock table when the user sends a message in that group, assuming they sent the message at maximum entry in clock table
    -- or it gets updated to whatever time they sent the message, if that time is more recent than their previous last confirmed time

-- 6) user lastlogin will only get updated when they login

-- 7) other assumptions are listed throughout the table definitions for NOT NULL and UNIQUE attributes, as well as attributes that can be NULL

-- 8) if a user tries to join a group, and it is full, they will be added to pending group members table

-- 9) there is a way to join the friends table without going through a pendingFriend request, meaning you do not have to be a pendingFriend to become friends

-- 10) I am assuming that messages cannot be sent at a time greater than what is stored in the maximum timestamp entry in the clock table, also
--      i am assuming that every timestamp or date in the system must be less than or equal to the clock time

-- 11) Assuming that if you want to send a message to a user not in a group, the toGroupID will be NULL

-- 12) if a user wants to send a message to a whole group, not a specific user, the toUserID will be null.

-- 13) for the purpose of phase 1, assuming all members who want to be in a group will be automatically added to group member table, unless size at capacity, then they will be added to pending group member table.

-- 14) for the purpose of phase 1, assuming friendships do not have to be confirmed.

-- 15) assuming can send messages to people without being friends, only if the message is being sent to a group. you must be friends with the other person to send the a message not through a group.
    -- meaning either you must be following the user or they have to be following you.

-- 16) for displayFriends, we display all of the users following the loggedInUser and all of the users that the logged in user follows