-- TRIGGERS
-- addMessageRecipient: which adds a corresponding entry into the messageRecipient relation
-- upon adding a new message to the message relation
-- if user is sending a message to the whole group, meaning the to userID is null, we will not add this to the messageRecipient table


CREATE OR REPLACE FUNCTION func_1()
RETURNS TRIGGER AS $$
DECLARE
rec_groupMember RECORD;
BEGIN

    IF (new.toUserID IS NOT NULL)
        THEN
            INSERT INTO messageRecipient(msgID,userID)
            VALUES (new.msgID,new.toUserID);
            RETURN NEW;
    END IF;
    IF (new.toUserID IS NULL) -- sending group message so need to update all group members to have this message
        THEN
        FOR rec_groupMember IN SELECT * FROM groupMember
        LOOP
                IF rec_groupMember.gID = new.togroupid THEN
                    INSERT INTO messageRecipient(msgID,userID)
                    VALUES (new.msgID,new.toUserID);
                END IF;
        END LOOP;
        RETURN NEW;
    END IF;

    RETURN null;

END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_1 ON message;
CREATE TRIGGER trig_1
AFTER INSERT
ON message
FOR EACH ROW
EXECUTE PROCEDURE func_1();

----------------------------------------------------------------------------
-- updateGroup: which moves a pending accepted request in the pendingGroupMember relation to
-- the groupMember relation when a member leaves the group.
-- grabs pending gruop member who joined pending table at earliest time
CREATE OR REPLACE FUNCTION func_2()
RETURNS TRIGGER AS $$
BEGIN

DELETE from pendinggroupmember where userid = old.userid and gid = old.gid;
DELETE from pendinggroupmember where userid is null;


RETURN old;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_1 ON groupMember;
CREATE TRIGGER trig_1
BEFORE DELETE
ON groupMember
FOR EACH ROW
EXECUTE PROCEDURE func_2();

CREATE OR REPLACE FUNCTION func_add_new_mem()
RETURNS TRIGGER AS $$
BEGIN
    IF( EXISTS ((SELECT userID
                  FROM pendingGroupMember
                  WHERE gID = old.gID
                  ORDER BY requesttime DESC
                  FETCH FIRST 1 ROW ONLY)))
    THEN
        INSERT INTO groupMember(gID, userID, role, lastConfirmed)
        VALUES(old.gID, (SELECT userID
                  FROM pendingGroupMember
                  WHERE gID = old.gID
                  ORDER BY requesttime DESC
                  FETCH FIRST 1 ROW ONLY), default, CAST((SELECT * FROM get_clock) AS date));
    END if;
RETURN old;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_new_mem ON groupMember;
CREATE TRIGGER trig_new_mem
AFTER DELETE
ON groupMember
FOR EACH ROW
EXECUTE PROCEDURE func_add_new_mem();




-----------------------------------
-- NEW TRIGGERS

-- trigger that deletes from pending group member when you have been added to group
CREATE OR REPLACE FUNCTION func_2a()
RETURNS TRIGGER AS $$
BEGIN
DELETE FROM pendinggroupmember
WHERE gID = new.gID AND userID = new.userid;

RETURN new;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_3 ON groupMember;
CREATE TRIGGER trig_3
AFTER INSERT
ON groupMember
FOR EACH ROW
EXECUTE PROCEDURE func_2a();

-- ------------------- 1 ------------------
-- trigger that updates timestamps of groupMember lastconfirmed attribute
-- meaning, once a user sends a message, their last confirmed timestamp gets updated to maximum timestamp entry in clock table, or whatever time they send the message
-- from their group member of the group they sent the message in, gets updated to maximum timestamp entry in clock table
-- do not want to update the last confirmed in all the groups they are in, just the group they sent the message in
-- only update last confirmed time stamp when the time the message was sent is more recent than previous last confirmed


CREATE OR REPLACE FUNCTION func_3()
RETURNS TRIGGER AS $$
BEGIN
    IF ((SELECT lastconfirmed FROM groupmember
        WHERE gID = new.toGroupID AND userID = new.fromID) < new.timesent)
    THEN
        UPDATE groupMember
        SET lastConfirmed = new.timesent
        WHERE gID = new.toGroupID AND userID = new.fromID;
    END IF;

RETURN new;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_2 ON message;
CREATE TRIGGER trig_2
AFTER INSERT
ON message
FOR EACH ROW
EXECUTE PROCEDURE func_3();

--------------------- 2 --------------
-- trigger so a user can only post a message in a group that they are apart of
-- constraint so a user can only send a message in a group to a user in that group
-- and must send message to group or user or both


CREATE OR REPLACE FUNCTION func_4()
RETURNS TRIGGER AS
$$
DECLARE NOW_time timestamp;
BEGIN
   IF ((EXISTS (SELECT * FROM groupMember
               WHERE userID = new.fromID AND gID = new.toGroupID) = False) AND new.togroupid IS NOT NULL AND new.touserid IS NOT NULL)
   THEN
      RAISE EXCEPTION 'Sorry, you are not a member of the group you are trying to send a message to.';
   END IF;

   IF (new.togroupid IS NULL AND new.touserid IS NULL)
   THEN
      RAISE EXCEPTION 'Sorry, you must either send a message to a group or a user or both, not neither.';
   END IF;

   IF ((EXISTS (SELECT * FROM groupMember
               WHERE userID = new.touserid AND gID = new.toGroupID) = False) AND new.togroupid IS NOT NULL AND new.touserid IS NOT NULL)
   THEN
      RAISE EXCEPTION 'Sorry, the person you are trying to send a message to is not in this group.';
   END IF;

    IF (new.timesent < CAST((SELECT date_of_birth FROM profile
                        WHERE userID = new.fromID ) AS timestamp ))
   THEN
      RAISE EXCEPTION 'Sorry, not a valid time to send this message';
   END IF;
    IF (new.timeSent > (SELECT * FROM get_clock) )
   THEN
      RAISE EXCEPTION 'Sorry, not a valid time to send message.';
   END IF;


    IF (new.togroupid IS NULL AND (EXISTS (SELECT * FROM friend
                 WHERE userID1 = new.touserid AND userID2 = new.fromID) = FALSE AND
                                  EXISTS (SELECT * FROM friend
                 WHERE userID1 = new.fromID AND userID2 = new.touserid) = FALSE))
    THEN
           RAISE EXCEPTION 'Sorry, you are not friends with the person you are trying to send a message to.';
    END IF;

RETURN new;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_3 ON message;
CREATE TRIGGER trig_3
BEFORE INSERT
ON message
FOR EACH ROW
EXECUTE PROCEDURE func_4();



---------------------------------- 4 ---------------------
-- trigger so date that people became friends must be greater than the birth date of each; may not be needed

CREATE OR REPLACE FUNCTION func_6()
RETURNS TRIGGER AS
$$
BEGIN
   IF (EXISTS ( SELECT * FROM profile
                WHERE userID = new.userID1 AND date_of_birth <= new.JDate) = False)
   THEN
      RAISE EXCEPTION 'Sorry, not a valid date to become friends.';
   END IF;

   IF (EXISTS ( SELECT * FROM profile
                WHERE userID = new.userID2 AND date_of_birth <= new.JDate) = False)
   THEN
      RAISE EXCEPTION 'Sorry, not a valid date to become friends.';
   END IF;
    IF (new.JDate > CAST((SELECT * FROM get_clock) AS date ))
   THEN
      RAISE EXCEPTION 'Sorry, not a valid date to be friends.';
   END IF;

RETURN new;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_1 ON friend;
CREATE TRIGGER trig_1
BEFORE INSERT
ON friend
FOR EACH ROW
EXECUTE PROCEDURE func_6();



-------------------- 6 ------------------
-- constraint to enforce size of group, meaning the amount of members in the group member table belonging to one group cannot exceed the group size
-- trigger that if someone tries to join a group, but it is full, you are added to the pending group members table

CREATE OR REPLACE FUNCTION func_8()
RETURNS TRIGGER AS
$$
BEGIN
    IF (new.lastConfirmed > (SELECT * FROM get_clock) )
   THEN
      RAISE EXCEPTION 'Sorry, not a valid time lastconfirmed timestamp.';
   END IF;
   IF (EXISTS ( SELECT * FROM groupMember
                  WHERE gID = new.gID
                    OFFSET (SELECT size - 1
                            FROM groupinfo
                            WHERE gID = new.gID )  ROW))
   THEN
       INSERT INTO pendingGroupMember (gID, userID, requestText, requestTime)
       VALUES (new.gID, new.userID, 'Group is full, can I join?', CAST((SELECT * FROM get_clock) AS date));
       RAISE NOTICE 'Sorry, group is full. You are now a pending member.';
       RETURN NULL;
   ELSE
       RETURN new;
   END IF;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_2 ON groupMember;
CREATE TRIGGER trig_2
BEFORE INSERT
ON groupMember
FOR EACH ROW
EXECUTE PROCEDURE func_8();


-- ------------------- 8 ------------------
-- constraint on user date of birth so it is less than current date
-- constraint on profile so lastlogin is less than or equal to now

CREATE OR REPLACE FUNCTION func_10()
RETURNS TRIGGER AS
$$
BEGIN
   IF (new.date_of_birth > CAST((SELECT * FROM get_clock) AS date ))
   THEN
      RAISE EXCEPTION 'Sorry, not a valid date of birth.';
   END IF;

RETURN new;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_1 ON profile;
CREATE TRIGGER trig_1
BEFORE INSERT
ON profile
FOR EACH ROW
EXECUTE PROCEDURE func_10();


-- ------------------- 11 ------------------
-- constraint on pendingGroupMember request time stamp so it is less than or equal to now

CREATE OR REPLACE FUNCTION func_13()
RETURNS TRIGGER AS
$$
BEGIN
   IF (new.requestTime > (SELECT * FROM get_clock) )
   THEN
      RAISE EXCEPTION 'Sorry, not a valid time to request to be in a group.';
   END IF;

RETURN new;
END ;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_1 ON pendingGroupMember;
CREATE TRIGGER trig_1
BEFORE INSERT
ON pendingGroupMember
FOR EACH ROW
EXECUTE PROCEDURE func_13();


-------------------------------------------------
-- trigger to delete all references to dropped users
CREATE OR REPLACE FUNCTION func_14()
RETURNS TRIGGER AS
$$
BEGIN
    SET CONSTRAINTS ALL DEFERRED ;
   DELETE from message
       where toUserID = old.userid;
   DELETE from message
       where fromID = old.userid;
   DELETE from friend
       where userID1 = old.userid;
   DELETE from friend
       where userID2 = old.userid;
   DELETE from pendinggroupmember
       where userID = old.userid;
   DELETE from pendingfriend
       where toid = old.userid;
   DELETE from pendingfriend
       where fromid = old.userid;
   DELETE from groupmember
       where userid = old.userid;

RETURN old;
    END

$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS drop_all_ref_message ON profile;
CREATE TRIGGER drop_all_ref_message
BEFORE DELETE
ON profile
FOR EACH ROW
EXECUTE PROCEDURE func_14();


-------------------------------------------------
-- trigger to delete all message recipients before delete message on delete user
CREATE OR REPLACE FUNCTION func_delete_msg_r()
RETURNS TRIGGER AS
$$
BEGIN
    SET CONSTRAINTS ALL DEFERRED ;
    DELETE from messagerecipient
       where messagerecipient.msgid = old.msgid;

RETURN old;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS delete_m_r ON message;
CREATE TRIGGER delete_m_r
BEFORE DELETE
ON message
FOR EACH ROW
EXECUTE PROCEDURE func_delete_msg_r();
