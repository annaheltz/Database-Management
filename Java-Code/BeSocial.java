import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BeSocial {
    String user;
    String pass;
    int loggedInUser; // will be -1 if no one logged in 
    Timestamp lastLoginUser;
    Connection conn; //connection
    boolean testing;

    // this constructor makes the recursize be social, need to uncomment to userLoggedIn()
    public BeSocial(String username, String password, boolean test)
    {
        user = username;
        pass = password;
        loggedInUser = -1;// will be -1 if no one logged in 
        if(test)
        {
            testing = true;
        }
        else
        {
            testing = false;
        }
        // setting database connections
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/";
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", pass);
            conn = DriverManager.getConnection(url, props);
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void applicationNoUserLoggedIn()
    {
        try(Scanner scanner321 = new Scanner(System.in))
        {            
            System.out.println("You are not logged in here are your options.");
            System.out.println("Read the options, then press an number to continue.");
            System.out.println("1. Login");
            System.out.println("2. Exit.");
            System.out.print("Enter a valid number: ");

            String functionToCall =  scanner321.nextLine();  // Read user input
            while(!functionToCall.equals("1")  && !functionToCall.equals("2") )
            {
                System.out.println("1. Login");
                System.out.println("2. Exit.");
                System.out.print("Enter a Valid Number: ");
                functionToCall =  scanner321.nextLine();
            }
            
            int function = Integer.parseInt(functionToCall);
            
            if(function == 1)
            {
                System.out.println("Logging In");
                System.out.print("Enter valid email for user: ");
                String email =  scanner321.nextLine();  // Read user input
                System.out.print("Enter valid password for user: ");
                String password =  scanner321.nextLine();  // Read user input
                login(email,password);
            }
            else//function == 2
            {
                exit();
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void applicationUserLoggedIn()
    {      
        try(Scanner scanner123 = new Scanner(System.in))
        {            
            System.out.println("You are logged in here are your options.");
            System.out.println("Read the options, then press an number to continue.");
            System.out.println("1. Initiate Friendship");
            System.out.println("2. Confirm Friend Requests");
            System.out.println("3. Create Group");
            System.out.println("4. Initiate Adding Group");
            System.out.println("5. Confirm Group Membership");
            System.out.println("6. Leave Group");
            System.out.println("7. Search For User");
            System.out.println("8. Send Message To User");
            System.out.println("9. Send Message To Group");
            System.out.println("10. Display Messages");
            System.out.println("11. Display New Messages");
            System.out.println("12. Display Friends");
            System.out.println("13. Rank Groups");
            System.out.println("14. Rank Users");
            System.out.println("15. Top Messages");
            System.out.println("16. Three Degrees");
            System.out.println("17. Logout");
            System.out.println("18. Create User");
            System.out.println("19. Drop User");
            System.out.print("Enter a Valid Number: ");

            String functionToCall =  scanner123.nextLine();  // Read user input
            while(!functionToCall.equals("1")  && !functionToCall.equals("2") && !functionToCall.equals("3") && !functionToCall.equals("4") && !functionToCall.equals("5")  && !functionToCall.equals("6")  && !functionToCall.equals("7")  && !functionToCall.equals("8")  && !functionToCall.equals("9")  && !functionToCall.equals("10")  && !functionToCall.equals("11")  && !functionToCall.equals("12")  && !functionToCall.equals("13")  && !functionToCall.equals("14")  && !functionToCall.equals("15")  && !functionToCall.equals("16")  && !functionToCall.equals("17")  && !functionToCall.equals("18")  && !functionToCall.equals("19")  )
            {
                System.out.println("Enter a Valid Number.");
                System.out.println("1. Initiate Friendship");
                System.out.println("2. Confirm Friend Requests");
                System.out.println("3. Create Group");
                System.out.println("4. Initiate Adding Group");
                System.out.println("5. Confirm Group Membership");
                System.out.println("6. Leave Group");
                System.out.println("7. Search For User");
                System.out.println("8. Send Message To User");
                System.out.println("9. Send Message To Group");
                System.out.println("10. Display Messages");
                System.out.println("11. Display New Messages");
                System.out.println("12. Display Friends");
                System.out.println("13. Rank Groups");
                System.out.println("14. Rank Users");
                System.out.println("15. Top Messages");
                System.out.println("16. Three Degrees");
                System.out.println("17. Logout");
                System.out.println("18. Create User");
                System.out.println("19. Drop User");
                System.out.print("Enter a Valid Number: ");
                functionToCall =  scanner123.nextLine();
            }
            
            int function = Integer.parseInt(functionToCall);
            
            if(function == 1)
            {
                System.out.println("Initiating Friendship");
                System.out.print("Who would you like to send a friend request to?");
                String toUserID =  scanner123.nextLine();  // Read user input
                System.out.println("The friend Request will be sent to: " + toUserID);

                System.out.print("Enter Friend Request Text: ");

                String requestText = scanner123.nextLine();  // Read user input

                System.out.print("Enter Y to confirm Friend Request: ");
                String confirm = scanner123.nextLine();  // Read user input
                
                if(confirm.equals("Y"))
                {
                    initiateFriendship(toUserID, requestText);   
                }
                else
                {
                    System.out.println("Confirmation was not recieved, not friend request sent.");
                    applicationUserLoggedIn();
                }             
            }
            else if(function == 2)
            {
                confirmFriendRequests();
            }
            else if(function == 3)
            {
                System.out.println("Creating Group");
                System.out.print("Enter valid group name: ");
                String name =  scanner123.nextLine();  // Read user input
                System.out.print("Enter valid group description: ");
                String description =  scanner123.nextLine();  // Read user input
                System.out.print("Enter valid group membership limit: ");
                String limit =  scanner123.nextLine();  // Read user input
                createGroup(name, Integer.parseInt(limit), description);
                 
            }
            else if(function == 4)
            {
                System.out.println("Initiate Adding Group");
                System.out.print("Enter valid group ID: ");
                String id =  scanner123.nextLine();  // Read user input
                System.out.print("Enter valid group request: ");
                String description =  scanner123.nextLine();  // Read user input
                initiateAddingGroup(Integer.parseInt(id),description);
                
            }
            else if(function == 5)
            {
                confirmGroupMembership();
            }
            else if(function == 6)
            {
                System.out.print("What group would you like to leave?");
                String group =  scanner123.nextLine();  // Read user input
                leaveGroup(Integer.parseInt(group));
            }
            else if(function == 7)
            {
                System.out.println("Searching for User");
                System.out.print("Enter string to search for users: ");
                String string =  scanner123.nextLine();  // Read user input
                searchForUser(string);
                                
            }
            else if(function == 8)
            {
                System.out.print("Enter userID for user you would like to send a message to: ");
                String get_user = scanner123.nextLine();
                int userID = Integer.parseInt(get_user);
                PreparedStatement SQL = conn.prepareStatement("Select name from profile WHERE userID = ?");
                SQL.setInt(1, userID);
                ResultSet rs = SQL.executeQuery();
                
                String name;
                if (rs.next()) {
                    name = rs.getString("name");
                    System.out.print("Enter the message you would like to send to " + name + ": ");
                    String message = "";
                    String line;
                    while (scanner123.hasNextLine()) {
                        line = scanner123.nextLine();
                        if (line.isEmpty()) {
                            break;
                        }
                        message += line + "\n";
                    }
                    sendMessageToUser(userID, message);
                }
                
            }
            else if(function == 9)
            {
                System.out.print("Enter groupID for group you would like to send a message to: ");
                String get_group = scanner123.nextLine();
                int groupID = Integer.parseInt(get_group);
                
                System.out.print("Enter the message you would like to send: ");
                String message = "";
                String line;
                while (scanner123.hasNextLine()) {
                    line = scanner123.nextLine();
                    if (line.isEmpty()) {
                        break;
                    }
                    message += line + "\n";
                }
                sendMessageToGroup(groupID, message);
            }
            else if(function == 10)
            {
                displayMessages();
            }
            else if(function == 11)
            {
                displayNewMessages();
            }
            else if(function == 12)
            {
                displayFriends();
            }
            else if(function == 13)
            {
                rankGroups();
            }
            else if(function == 14)
            {
                rankUsers();
            }
            else if(function == 15)
            {
                System.out.println("Displaying Top Messages");
                System.out.print("Enter value for k (number of users you would like displayed): ");
                String get_k = scanner123.nextLine();
                int k =  Integer.parseInt(get_k);  // Read user input
                System.out.print("Enter value for x (number of months to go back and look for message counts): ");
                String get_x = scanner123.nextLine();
                int x =  Integer.parseInt(get_x);  // Read user input
                topMessages(k, x);
            }
            else if(function == 16)
            {
                System.out.println("Displaying Three Degrees");
                System.out.print("Enter userID to find path: ");
                String find_friend = scanner123.nextLine();
                threeDegrees(Integer.parseInt(find_friend));
            }
            else if(function == 17)
            {
                logout();
            }
            else if(function == 18)
            {
                System.out.println("User Creation");
                System.out.print("Enter valid name for user: ");
                String name =  scanner123.nextLine();  // Read user input
                System.out.print("Enter valid email for user: ");
                String email =  scanner123.nextLine();  // Read user input
                System.out.print("Enter valid password for user: ");
                String password =  scanner123.nextLine();  // Read user input
                System.out.print("Enter valid birthday in YYYY-MM-DD form for user: ");
                String dob =  scanner123.nextLine();  // Read user input
                createUser(name, email, password, Date.valueOf(dob));
                
                
            }
            else // function == 19
            {                
                System.out.println("Dropping User");
                System.out.print("Enter user email: ");
                String email =  scanner123.nextLine();  // Read user input
                dropUser(email);
                
            }
        }
        catch (Exception e) {
            System.out.println(e);
       }
    }

    public void createUser(String name, String email, String password, Date date_of_birth) 
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in, please log in to create another user.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try {                
                PreparedStatement SQL = conn.prepareStatement("SELECT name FROM profile WHERE userID = ?");
                SQL.setInt(1, loggedInUser);    
                ResultSet rs = SQL.executeQuery();
                if(rs.next())
                {
                    String user_name = rs.getString(1);
                    if(!user_name.equals("admin"))
                    {
                        System.out.println("Cannot add user, you do not have the priviledges.");
                        if(!testing)
                        {
                            applicationUserLoggedIn();
                        }
                    }
                    else
                    {
                        try {
                            // find max userID to autogenerate
                            Statement st = conn.createStatement();
                            conn.setAutoCommit(false);
                            ResultSet get_user_id_max = st.executeQuery("SELECT MAX(userid) as max FROM profile");
                            int userid1;
                            if  (get_user_id_max.next())
                            {                    
                                userid1 = get_user_id_max.getInt("max");
                                // created a user, last login will be null
                                PreparedStatement SQL_insert_pro = conn.prepareStatement("INSERT INTO profile VALUES(?, ?, ?, ?, ?, ?)");
                                SQL_insert_pro.setInt(1, userid1 + 1);
                                SQL_insert_pro.setString(2, name);
                                SQL_insert_pro.setString(3, email);
                                SQL_insert_pro.setString(4, password);
                                SQL_insert_pro.setDate(5, date_of_birth);
                                SQL_insert_pro.setTimestamp(6, null);     
                                SQL_insert_pro.executeUpdate();
                                
                                System.out.println("User: " + userid1 + " successfully added.");
                                conn.commit();
                            }
                            if(!testing)
                            {
                                applicationUserLoggedIn();
                            }
                        }
                        catch (Exception e) {
                             System.out.println(e);
                             conn.rollback();
                        }
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }            
        }
    }

    public void dropUser(String email)
    {
        // trigger should be in sql, not java
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in, please log in to drop a user.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try {                
                conn.setAutoCommit(false);
                PreparedStatement SQL = conn.prepareStatement("SELECT name FROM profile WHERE userID = ?");
                SQL.setInt(1, loggedInUser);    
                ResultSet rs = SQL.executeQuery();
                if(rs.next())
                {
                    String name = rs.getString(1);
                    if(!name.equals("admin"))
                    {
                        System.out.println("Cannot drop user, you do not have the priviledges.");
                        if(!testing)
                        {
                            applicationUserLoggedIn();
                        }
                    }
                    else
                    {
                        try {                
                            PreparedStatement SQL2 = conn.prepareStatement("DELETE FROM profile WHERE email = ?");
                            SQL2.setString(1, email);    
                            SQL2.executeUpdate();
                            System.out.println("Drop User successful!");
                            System.out.println("User with email: " + email + " was dropped.");

                            if(!testing)
                            {
                                applicationUserLoggedIn();
                            }
                            conn.commit();
                        }
                        catch (Exception e) {
                            System.out.println(e);
                            conn.rollback();
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }            
        }        
    }

    public void initiateFriendship(String toUserID, String requestText)
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are logged out, please log in to create a friendship.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try {                        
                PreparedStatement SQL = conn.prepareStatement("INSERT INTO pendingFriend VALUES(?, ?, ?)");
                SQL.setInt(1, loggedInUser);
                SQL.setInt(2, Integer.parseInt(toUserID));
                SQL.setString(3, requestText);    
                SQL.executeUpdate();
                
                System.out.println("Friend Request successful!");
                if(!testing)
                {
                    applicationUserLoggedIn();
                }
            }
            catch (Exception e) {
                System.out.println(e);
            } 
        }
    }

    public void confirmFriendRequests()
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to confirm your friend requests.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {

            try {
                PreparedStatement countFriends = conn.prepareStatement("Select COUNT(*) as count from pendingFriend where toID = ?");

                countFriends.setInt(1, loggedInUser);
                ResultSet rs3 = countFriends.executeQuery();
                int count;
                if(rs3.next())
                {
                    count = rs3.getInt("count");
                    if(count == 0)
                    {
                        System.out.println("Friend Requests to Accept.");
                    }
                    else
                    {
                        PreparedStatement SQL = conn.prepareStatement("Select * from pendingFriend WHERE toID = ?");
                        SQL.setInt(1, loggedInUser);
                        ResultSet rs = SQL.executeQuery();
        
                        PreparedStatement SQL2 = conn.prepareStatement("Select * from pendingFriend WHERE toID = ?");
                        SQL2.setInt(1, loggedInUser);
                        ResultSet getSize = SQL.executeQuery();
        
                        int size = 0;
                        while(getSize.next())
                        {
                            if(getSize.isLast())
                            {
                                size = getSize.getRow(); // get row id 
                            }
                        }
                        int fromID;
                        String requestText;
                        String[] friends = new String[size];
                        int i = 0;
                        System.out.println("Your pending friend requests: ");
                        while (rs.next()) {
                            fromID = rs.getInt("fromID");
                            requestText = rs.getString("requestText");
                            String friendReqs = fromID + ": " + requestText;
                            System.out.println(friendReqs);
                            friends[i] = friendReqs;
                            i++;
                        }
        
                        // prompt user to pick a user to accept
                        String acceptedFriends = "";
                        try (Scanner pickUser = new Scanner(System.in))
                        {
                            System.out.println("Enter single userID to accept friend requests individually, enter A to accept all, enter 0 to stop accepting or reject the rest of the requests.");
                            String userName = pickUser.nextLine();  // Read user input
                            while(!userName.equals("0"))
                            {
                                if(userName.equals("A"))
                                {
                                    PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM pendingFriend WHERE toID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE);
                                    pstmt.setInt(1, loggedInUser);
                                    ResultSet profile = pstmt.executeQuery();
        
                                    int fromID1;
                                    int toID1;
                                    String requestT;
        
                                    while (profile.next()) {
                                        fromID1 = profile.getInt("fromID");
                                        toID1 = profile.getInt("toID");
                                        requestT = profile.getString("requestText");
                                        //(SELECT * FROM get_clock) AS date
                                        PreparedStatement pstmt4 = conn.prepareStatement("(SELECT CAST(pseudo_time as date) FROM get_clock)");
                                        ResultSet time = pstmt4.executeQuery();
                                        Date friendDate;
                                        if(time.next())
                                        {
                                            friendDate = time.getDate(1);
                                            PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO friend VALUES(?,?,?,?)");
                                            pstmt1.setInt(1, fromID1);
                                            pstmt1.setInt(2, toID1);
                                            pstmt1.setDate(3, friendDate);
                                            pstmt1.setString(4, requestT);
                                            pstmt1.executeUpdate();
                                            
                                            PreparedStatement pstmt15 = conn.prepareStatement("DELETE FROM pendingFriend WHERE fromID = ? AND toID = ? AND requestText = ?");
                                            pstmt15.setInt(1, fromID1);
                                            pstmt15.setInt(2, toID1);
                                            pstmt15.setString(3, requestT);
                                            pstmt15.executeUpdate();
                                        }
                                    }
                                    userName = "0";
                                }
                                else
                                {
                                    PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM pendingFriend WHERE toID = ? AND fromID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE);
                                    pstmt.setInt(1, loggedInUser);
                                    pstmt.setInt(2, Integer.parseInt(userName));
                                    ResultSet profile = pstmt.executeQuery();
        
                                    int fromID1;
                                    int toID1;
                                    String requestT;
        
                                    while (profile.next()) {
                                        fromID1 = profile.getInt("fromID");
                                        toID1 = profile.getInt("toID");
                                        requestT = profile.getString("requestText");
                                        //(SELECT * FROM get_clock) AS date
                                        PreparedStatement pstmt4 = conn.prepareStatement("(SELECT CAST(pseudo_time as date) FROM get_clock);");
                                        ResultSet time = pstmt4.executeQuery();
                                        Date friendDate;
                                        if(time.next())
                                        {
                                            friendDate = time.getDate(1);
                                            PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO friend VALUES(?,?,?,?)");
                                            pstmt1.setInt(1, fromID1);
                                            pstmt1.setInt(2, toID1);
                                            pstmt1.setDate(3, friendDate);
                                            pstmt1.setString(4, requestT);
                                            pstmt1.executeUpdate();
        
                                            PreparedStatement pstmt15 = conn.prepareStatement("DELETE FROM pendingFriend WHERE fromID = ? AND toID = ? AND requestText = ?");
                                            pstmt15.setInt(1, fromID1);
                                            pstmt15.setInt(2, toID1);
                                            pstmt15.setString(3, requestT);
                                            pstmt15.executeUpdate();
                                            acceptedFriends = acceptedFriends + fromID1 + " ";
                                        }
                                    }
                                    System.out.println("Here are your friend requests again. Enter single userID to accept friend requests individually, enter A to accept all, enter 0 to stop accepting or reject the rest of the requests.");
                                    for(String s : friends)
                                    {
                                        for(String a : acceptedFriends.split(" "))
                                        {
                                            if(s.charAt(0) == ( a.charAt(0)))
                                            {
                                                continue;
                                            }
                                        }
                                        System.out.println(s);
                                    }
                                    userName = pickUser.nextLine();  // Read user input
                                }
                                
                            }
                            
                            System.out.println("You have entered 0, no more looking through accepting friend requests. The rest will be rejected.");
                            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM pendingFriend WHERE toID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE);
                            pstmt.setInt(1, loggedInUser);
                            ResultSet profile = pstmt.executeQuery();
        
                            int fromID1;
                            int toID1;
                            String requestT;
        
                            while (profile.next()) {
                                fromID1 = profile.getInt(1);
                                toID1 = profile.getInt(2);
                                requestT = profile.getString(3);
        
                                PreparedStatement pstmt15 = conn.prepareStatement("DELETE FROM pendingFriend WHERE fromID = ? AND toID = ? AND requestText = ?");
                                pstmt15.setInt(1, fromID1);
                                pstmt15.setInt(2, toID1);
                                pstmt15.setString(3, requestT);
                                pstmt15.executeUpdate();
                            }
                            System.out.println("Rest of your pending requests have been deleted.");
                            
                            if(!testing)
                            {
                                applicationUserLoggedIn();
                            }
        
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        } 
                    }
                    System.out.println("Done confirming friend requests.");
                    if(!testing)
                    {
                        applicationUserLoggedIn();
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }    
        }
    }

    public void login(String email, String password)
    {
        if(loggedInUser != -1)
        {
            System.out.println("You are already logged in.");
            if(!testing)
            {
                applicationUserLoggedIn();
            }
        }
        else
        {
            try {
                conn.setAutoCommit(false);
                PreparedStatement SQL = conn.prepareStatement("Select * from profile WHERE email = ? AND password = ?");
                SQL.setString(1, email);
                SQL.setString(2, password);
                ResultSet rs = SQL.executeQuery();
                
                int userid1;
                while (rs.next()) {
                    userid1 = rs.getInt("userid");
                    loggedInUser = userid1;
                    lastLoginUser = rs.getTimestamp("lastlogin");
                    System.out.println("User: " + userid1 + ", you are now logged in!");
                }
                // update lastlogin time stamp, also insert into clock, insert into clock when send message
                PreparedStatement pstmt = conn.prepareStatement("UPDATE profile SET lastlogin = (SELECT * FROM get_clock) WHERE userid = ?");
                pstmt.setInt(1, loggedInUser);
                pstmt.executeUpdate();
                
                conn.commit();
                if(!testing)
                {
                    applicationUserLoggedIn();
                    conn.rollback();
                }
            }
            catch (Exception e) {
                 System.out.println(e);
            }
        }
    }
    
    public void createGroup(String name, int size, String description) 
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to create a group.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try {
                // find max userID to autogenerate
                conn.setAutoCommit(false);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT MAX(gid) as max FROM groupInfo");
                if  (rs.next())
                {
                    int gid;
                    gid = rs.getInt("max");
                
                    PreparedStatement SQL = conn.prepareStatement("INSERT INTO groupInfo VALUES(?, ?, ?, ?)");
                    SQL.setInt(1, gid + 1);
                    SQL.setString(2, name);
                    SQL.setInt(3, size);
                    SQL.setString(4, description);     
                    SQL.executeUpdate();
                    System.out.println("Group: " + name + " was successfully created.");

                    conn.commit();
                }
                if(!testing)
                {
                    applicationUserLoggedIn();
                }
                
            }
            catch (Exception e) {
                 System.out.println(e);
            }
        }
    }

    public void initiateAddingGroup(int groupID, String requestText)
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to join a group.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try {    
                conn.setAutoCommit(false);            
                PreparedStatement SQL = conn.prepareStatement("INSERT INTO pendingGroupMember VALUES(?, ?, ?, ?)");
                SQL.setInt(1, groupID);
                SQL.setInt(2, loggedInUser);
                SQL.setString(3, requestText);
                // get timestamp from clock for request time
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM get_clock");
                Timestamp time;
                if(rs.next())
                {
                    time = rs.getTimestamp(1);
                    SQL.setTimestamp(4, time);    
                    SQL.executeUpdate();
                    System.out.println("You are now a pending group member of group: " + groupID);
                }
                conn.commit();
                if(!testing)
                {
                    applicationUserLoggedIn();
                }
                
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void confirmGroupMembership()
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to confirm your friend requests.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            // do same thing as pending friend requests
            // user must have role manager
            // need to insert people in groups with manager role
            try {
                conn.setAutoCommit(false);
                PreparedStatement getGroups = conn.prepareStatement("Select COUNT(*) as count from groupMember where role = ? and userID = ?");
                getGroups.setString(1,"manager");
                getGroups.setInt(2,loggedInUser);
                ResultSet count_groups = getGroups.executeQuery();
                if(count_groups.next())
                {
                    int group_count = count_groups.getInt(1);
                    if(group_count == 0)
                    {
                        System.out.println("No groups are currently managed" );
                    }
                    else
                    {
                        PreparedStatement getpend1 = conn.prepareStatement("SELECT count(*) FROM pendinggroupmember WHERE gID in (SELECT gID from groupmember where userID = ? and role = 'manager')");
                        getpend1.setInt(1,loggedInUser);
                        ResultSet pend_reqs1 = getpend1.executeQuery();
                        if(pend_reqs1.next())
                        {
                            int count = pend_reqs1.getInt(1);
                            if(count == 0)
                            {
                                System.out.println("No Pending Group Membership Requests" );
                            }
                            else
                            {
                                // SELECT * FROM pendinggroupmember WHERE gID in (SELECT gID from groupmember where userID = 1 and role = 'manager');
                                PreparedStatement getpend = conn.prepareStatement("SELECT * FROM pendinggroupmember WHERE gID in (SELECT gID from groupmember where userID = ? and role = 'manager')");
                                getpend.setInt(1,loggedInUser);
                                ResultSet pend_reqs = getpend.executeQuery();
                                int group_id;
                                int userID;
                                String requestText;
                                System.out.println("Your pending requests are: ");
                                int i = 0;
                                int[] groups = new int[count];
                                int[] users = new int[count];
                                String[] requests = new String[count];
                                while(pend_reqs.next())
                                {
                                    group_id = pend_reqs.getInt(1);
                                    groups[i] = group_id;
                                    userID = pend_reqs.getInt(2);
                                    users[i] = userID;
                                    requestText = pend_reqs.getString(3);
                                    requests[i] = requestText;
                                    System.out.println(i + ": Group: " + groups[i]  + ", User: " + users[i] + ", Request Text: " + requests[i]);
                                    i++;
                                }
                                try(Scanner accept_mems = new Scanner(System.in))
                                {
                                    System.out.println("Enter A to accept all group requests, Enter the number at the beginning of the line to accept one at a time, enter S to stop.");
                                    String userI = accept_mems.nextLine();
                                    String chosenMembs = "";
                                    while(!userI.equals("S"))
                                    {
                                        if(userI.equals("A"))
                                        {
                                            // add all in arrays to groupMember table
                                            for(int j = 0; j < count;j++)
                                            {
                                                PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO groupMember VALUES(?,?,default,(SELECT * FROM get_clock))");
                                                pstmt1.setInt(1, groups[j]);
                                                pstmt1.setInt(2, users[j]);
                                                pstmt1.executeUpdate();
                                                System.out.println("Confirmation Complete for Group: " + groups[j] + ", User: " + users[j]);
                                            }
                                            break;
                                        }
                                        else
                                        {
                                            chosenMembs = chosenMembs + userI + " ";
                                            PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO groupMember VALUES(?,?,default,(SELECT * FROM get_clock))");
                                            pstmt1.setInt(1, groups[Integer.parseInt(userI)]);
                                            pstmt1.setInt(2, users[Integer.parseInt(userI)]);
                                            pstmt1.executeUpdate();
                                            System.out.println("Confirmation Complete for Group: " + groups[Integer.parseInt(userI)] + ", User: " + users[Integer.parseInt(userI)]);
                                            for(int k = 0; k < count; k++)
                                            {
                                                if(!chosenMembs.contains(k+""))
                                                {
                                                    System.out.println(k + ": Group: " + groups[k]  + ", User: " + users[k] + ", Request Text: " + requests[k]);
                                                }
                                                
                                            }
                                            if(chosenMembs.split(" ").length == count)
                                            {
                                                break;
                                            }
                                            System.out.println("Enter a different number at the beginning of the line to accept another, enter S to stop.");
                                            userI = accept_mems.nextLine();
                                        }
                                    }
                                    //delete the rest
                                    for(int j = 0; j < count;j++)
                                    {
                                        PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM pendingGroupMember WHERE gID = ? and userID = ?");
                                        pstmt1.setInt(1, groups[j]);
                                        pstmt1.setInt(2, users[j]);
                                        pstmt1.executeUpdate();
                                    }
                                    
                                    System.out.println("Done confirming pending group members");
                                    conn.commit();
                                    if(!testing)
                                    {
                                        applicationUserLoggedIn();
                                    }
                                }  
                                catch(Exception e)
                                {
                                    System.out.println(e);
                                    conn.rollback();
                                }                              
                            }
                        }                        
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        } 
    }

    public void leaveGroup(int group)
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to leave group.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try 
            {
                conn.setAutoCommit(false);
                PreparedStatement sQL2PreparedStatement = conn.prepareStatement("Select COUNT(*) as count from groupMember Where userID = ? AND gID = ?");
                sQL2PreparedStatement.setInt(1,loggedInUser);
                sQL2PreparedStatement.setInt(2, group);
                ResultSet rs = sQL2PreparedStatement.executeQuery();
                int count;
                if(rs.next())
                {
                    count = rs.getInt("count");
                    if(count == 0)
                    {
                        System.out.println("Not a Member of the Group you are trying to leave.");
                    }
                    else
                    {
                        PreparedStatement SQL = conn.prepareStatement("DELETE FROM groupMember WHERE gID = ? AND userID = ?");
                        SQL.setInt(1, group);   
                        SQL.setInt(2, loggedInUser);    
                        SQL.executeUpdate();
                        conn.commit();
                        System.out.println("Successfully left group");   
                        if(!testing)
                        {
                            applicationUserLoggedIn();
                        }   
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }           
        }   
    }

    public void searchForUser(String search)
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to search for a user.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            String[] arrOfStr = search.split(" ");        
            try {
                String query = "Select * from profile ";
                for (int i = 0; i < arrOfStr.length; i++)
                {
                    if(arrOfStr[i].equals(""))
                    {
                        break;
                    }
                    if(i == 0 && i == arrOfStr.length-1)
                    {
                        String a = "'%" + arrOfStr[i] + "%'";
                        String addQuery = " WHERE email LIKE " + a + " OR " + " name LIKE " + a ;
                        query = query + addQuery;
                    }
                    else if( i == 0)
                    {
                        String a = "'%" + arrOfStr[i] + "%'";
                        String addQuery = " WHERE email LIKE " + a + " OR " + " name LIKE " + a + " OR ";
                        query = query + addQuery;
                    }
                    else if( i == arrOfStr.length -1)
                    {
                        String a = "'%" + arrOfStr[i] + "%'";
                        String addQuery = "  email LIKE " + a + " OR " + " name LIKE " + a ;
                        query = query + addQuery;
                    }
                    else
                    {
                        String a = "'%" + arrOfStr[i] + "%'";
                        String addQuery = " email LIKE " + a + " OR " + " name LIKE " + a + " OR ";
                        query = query + addQuery;
                    }
                    
                }
                PreparedStatement SQL = conn.prepareStatement(query);
                ResultSet rs = SQL.executeQuery();
                
                int userid1;
                String name;
                String email;
                String password;
                Date date_of_birth;
                Timestamp lastlogin;
                while (rs.next()) {
                    userid1 = rs.getInt("userid");
                    name = rs.getString("name");
                    email = rs.getString("email");
                    password = rs.getString("password");
                    date_of_birth = rs.getDate("date_of_birth");
                    lastlogin = rs.getTimestamp("lastlogin");
                    
                    System.out.println(userid1 + " " + name + " " + email + " " + password + " " + date_of_birth + " " + lastlogin);
                } 
                System.out.println("All users that contain your search phrases are displayed.");
                if(!testing)
                {
                    applicationUserLoggedIn();
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void sendMessageToUser(int userID, String message)
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to send a message.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try{
                conn.setAutoCommit(false);
                Statement st = conn.createStatement();
                PreparedStatement SQL2 = conn.prepareStatement("Select * from get_clock");
                ResultSet rs2 = SQL2.executeQuery();
                if(rs2.next())
                {
                    Timestamp clock_time = rs2.getTimestamp(1);

                    ResultSet rs3 = st.executeQuery("SELECT MAX(msgID) as max FROM message");
                    int msgID;
                    if  (rs3.next())
                    {                    
                        msgID = rs3.getInt("max");

                        PreparedStatement SQL3 = conn.prepareStatement("INSERT into message VALUES(?,?,?,?,?,?)");
                        SQL3.setInt(1, msgID + 1);
                        SQL3.setInt(2, loggedInUser);
                        SQL3.setString(3, message);
                        SQL3.setInt(4, userID);
                        SQL3.setNull(5,Types.INTEGER);
                        SQL3.setTimestamp(6, clock_time);
                        SQL3.executeUpdate();

                        conn.commit();
                        System.out.println("Message sent to user.");
                        if(!testing)
                        {
                            applicationUserLoggedIn();
                        }
                    }
                } 
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void sendMessageToGroup(int groupID, String message)
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to send a message to a group.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try
            {
                conn.setAutoCommit(false);
                Statement st = conn.createStatement();
                PreparedStatement SQL2 = conn.prepareStatement("Select * from get_clock");
                ResultSet rs2 = SQL2.executeQuery();
                if(rs2.next())
                {
                    Timestamp clock_time = rs2.getTimestamp(1);

                    ResultSet rs3 = st.executeQuery("SELECT MAX(msgID) as max FROM message");
                    int msgID;
                    if  (rs3.next())
                    {                    
                        msgID = rs3.getInt("max");
                        PreparedStatement SQL3 = conn.prepareStatement("INSERT into message VALUES(?,?,?,?,?,?)");
                        SQL3.setInt(1, msgID + 1);
                        SQL3.setInt(2, loggedInUser);
                        SQL3.setString(3, message);
                        SQL3.setNull(4, Types.INTEGER);
                        SQL3.setInt(5, groupID);
                        SQL3.setTimestamp(6, clock_time);
                        SQL3.executeUpdate();

                        conn.commit();
                        System.out.println("Message sent to group");            
                        if(!testing)
                        {
                            applicationUserLoggedIn();
                        }
                    }
                }       
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void displayMessages()
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to display your messages.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            try {    
                PreparedStatement SQL = conn.prepareStatement("Select * from message WHERE toUserID = ?");
                SQL.setInt(1, loggedInUser);
                ResultSet rs = SQL.executeQuery();
    
                int fromID;
                String messageBody;
                int toUserID;
                while (rs.next()) {
                    fromID = rs.getInt("fromID");
                    messageBody = rs.getString("messageBody");
                    toUserID = rs.getInt("toUserID");
                    System.out.println("You are user " + toUserID + ". User " + fromID + " sent you a message saying: " + messageBody);
                }
                System.out.println("All messages displayed.");
                
               if(!testing)
               {
                    applicationUserLoggedIn();
               }
            }
            catch (Exception e) {
                 System.out.println(e);
            }
        }
    }

    public void displayNewMessages()
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to display your new messages.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {           
            if(lastLoginUser == null)
            {
                System.out.println("This is your first time logging in. All your messages are new.");
                displayMessages();
            } 
            else
            {
                try {        
                    PreparedStatement SQL = conn.prepareStatement("Select * from message WHERE toUserID = ? AND timesent > ?");
                    SQL.setInt(1, loggedInUser);
                    SQL.setTimestamp(2, lastLoginUser);
                    ResultSet rs = SQL.executeQuery();
        
                    int fromID;
                    String messageBody;
                    int toUserID;
                    while (rs.next()) {
                        fromID = rs.getInt("fromID");
                        messageBody = rs.getString("messageBody");
                        toUserID = rs.getInt("toUserID");
                        System.out.println("You are user " + toUserID + ". User " + fromID + " sent you a NEW message saying: " + messageBody);
                    }
                    System.out.println("All new messages displayed.");
                
                    if(!testing)
                    {
                        applicationUserLoggedIn();
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }      
            }    
        }  
    }

    public void displayFriends()
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in. Please log in to display your new messages.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {
            System.out.println("\nDisplaying Your Friends: ");
            try {
                String q1 = "(SELECT userID2 AS userID FROM friend WHERE userID1 = " + loggedInUser + ")";
                String q2 = "(SELECT userID1 AS userID FROM friend WHERE userID2 = "+ loggedInUser+ ")";
                String q3 = q1 + " UNION " + q2;
                String q4 = "(" + q3 + ") AS Users";
                String q5 = "SELECT userID, name FROM (" + q4 + " NATURAL JOIN profile)" ;
                // statement to get users friends id and name
                PreparedStatement SQL = conn.prepareStatement(q5, ResultSet.TYPE_SCROLL_INSENSITIVE);
                ResultSet yourFriends = SQL.executeQuery();
                // to find number of users friends
                PreparedStatement SQL2 = conn.prepareStatement(q5, ResultSet.TYPE_SCROLL_INSENSITIVE);
                ResultSet getSize = SQL2.executeQuery();
                //getting size of friend list to make array of friends
                int size = 0;
                while(getSize.next())
                {
                    if(getSize.isLast())
                    {
                        size = getSize.getRow(); // get row id 
                    }
                }

                int friendUser;
                String name;
                String[] friends = new String[size];
                int i = 0;
                System.out.println("Your friends are: ");
                
                while (yourFriends.next()) {
                    friendUser = yourFriends.getInt("userID");
                    name = yourFriends.getString("name");
                    String printString = name + ", UserID: " + friendUser;
                    System.out.println(printString);
                    friends[i] = printString;
                    i++;
                }

                if(!testing)
                {
                    // prompt user to pick a user to display profile of
                    try (Scanner pickUser = new Scanner(System.in))
                    {
                        System.out.println("Enter userID to view their profile, enter 0 to stop.");
                        int userName = Integer.parseInt(pickUser.next());  // Read user input
                        while(userName!= 0 )
                        {
                            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM profile WHERE userID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE);
                            pstmt.setInt(1, userName);
                            ResultSet profile = pstmt.executeQuery();

                            int userID;
                            String name1;
                            String email;
                            Date date_of_birth;
                            while (profile.next()) {
                                userID = profile.getInt("userID");
                                name1 = profile.getString("name");
                                email = profile.getString("email");
                                date_of_birth = profile.getDate("date_of_birth");

                                System.out.print("\nViewing profile for ");
                                System.out.println("UserID: " + userID);
                                System.out.println("Name: " + name1);
                                System.out.println("Email: " + email);
                                System.out.println("Birthday: " + date_of_birth);
                                System.out.println();
                                System.out.println("Here are your friends again. Enter userID to view a profile, or enter 0 to stop.");
                                for(String s : friends)
                                {
                                    System.out.println(s);
                                }
                                userName = Integer.parseInt(pickUser.next());  // Read user input
                            }
                        }
                        System.out.println("You have entered 0, no more looking through profiles.");
                        if(!testing)
                        {
                            applicationUserLoggedIn();
                        }
                    }
                    catch (Exception e) {
                        System.out.println(e);
                        conn.rollback();
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void rankGroups()
    {
        if(loggedInUser == -1)
        {
            System.out.println("Please log in to rank groups.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {   
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("Select COUNT(*) as count from groupMember");
                int count;
                if(rs.next())
                {
                    count = rs.getInt("count");
                    if(count == 0)
                    {
                        System.out.println("No Groups to Rank");
                    }
                    else
                    {
                        ResultSet rs1 = st.executeQuery("Select gID,count(*) as Members from groupMember GROUP BY gID");
                        int groupID;
                        int members;
    
                        while (rs1.next()) {
                            groupID = rs1.getInt("gID");
                            members = rs1.getInt("Members");
                            System.out.println("Group " + groupID + " has " + members + " members.");
                        }
                        System.out.println("Groups ranked above.");
                
                        if(!testing)
                        {
                            applicationUserLoggedIn();
                        }
                    }
                }            
            }
            catch (Exception e) {
                 System.out.println(e);
            }
        } 
    }

    public void rankUsers()
    {
        if(loggedInUser == -1)
        {
            System.out.println("Please log in to rank users.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {  
            try {
                Statement st = conn.createStatement();
                ResultSet rs_get_num_users = st.executeQuery("Select count(*) from profile");
                int num_users = 0;
                if(rs_get_num_users.next())
                {
                    num_users = rs_get_num_users.getInt(1);
                    int[] user_friends = new int[num_users];
                    int[] user_id = new int [num_users];

                    ResultSet rs = st.executeQuery("Select userID from profile");
                    int userid1;
                    int count = 0;
                    while (rs.next()) {
                        userid1 = rs.getInt("userid");
                        user_id[count] = userid1;
                        // get all groups this user is in 
                        PreparedStatement SQL1 = conn.prepareStatement("SELECT gID from groupMember WHERE userID = ?");
                        SQL1.setInt(1,userid1);
                        ResultSet rs1 = SQL1.executeQuery();
                        String allGroups = "";
                        while (rs1.next()) {
                            int groupID = rs1.getInt(1);
                            allGroups = allGroups + groupID + " ";
                        }
                        // all groups has all groups the user is in 
                        String[] arrOfStr = allGroups.split(" ");      
                        
                        /*
                         *  SELECT count(userID) as num_friends
                            FROM ((SELECT userID2 AS userID FROM friend WHERE userID1 =  1 )
                            UNION  (SELECT userID1 AS userID FROM friend WHERE userID2 =  1) UNION
                            (Select userID from groupMember WHERE gID =  1 AND userID !=  1 )) AS users  ;
                         */
    
                        String query = "(Select userID from groupMember ";
                        for (int i = 0; i < arrOfStr.length; i++)
                        {
                            if(arrOfStr[i].equals(""))
                            {
                                break;
                            }
                            if(i == arrOfStr.length - 1 && i == 0)
                            {
                                String addQuery = " WHERE gID = " + Integer.parseInt(arrOfStr[i]);
                                query = query + addQuery;
                            }
                            else if( i == 0)
                            {
                                String addQuery = " WHERE gID = " + Integer.parseInt(arrOfStr[i])+ " OR ";
                                query = query + addQuery;
                            }
                            else if(i == arrOfStr.length-1)
                            {
                                String addQuery = " gID = " + Integer.parseInt(arrOfStr[i]);
                                query = query + addQuery;
                            }
                            else
                            {
                                String addQuery = " gID = " + Integer.parseInt(arrOfStr[i]) + " OR ";
                                query = query + addQuery;
                            }
                        }
                        if(query.contains("WHERE"))
                        {
                            query = query + " AND userID != " + userid1 + ")";
                        }
                        else
                        {
                            query = query + " WHERE userID != " + userid1 + ")";
                        }
                        
                        // select all friends
                        String q1 =  "SELECT count(userID) as num_friends FROM ((SELECT userID2 AS userID FROM friend WHERE userID1 = " + userid1 + " ) UNION  (SELECT userID1 AS userID FROM friend WHERE userID2 = " + userid1 + " ) UNION ";
                        q1 = q1 + query + ") AS users ";
                        PreparedStatement SQL2 = conn.prepareStatement(q1);
                        ResultSet rs2 = SQL2.executeQuery();

                        if(rs2.next())
                        {
                            int num_friends = rs2.getInt(1);
                            user_friends[count] = num_friends;
                            System.out.println("User: " + userid1 + ", has " + num_friends + " friends!");
                        }
                        count ++;
                    }
                }
                System.out.println("Users ranked.");
                if(!testing)
                {
                    applicationUserLoggedIn();
                }
            }
            catch (Exception e) {
                 System.out.println(e);
            }

            
        }
    }

    public void topMessages(int numUsers, int numMonths)
    {
        if(loggedInUser == -1)
        {
            System.out.println("Please log in to view top messages.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {  
            /*
            * create a table that contains all same messages, but switch to User and from user
            * make sure only contains valid messages (based on x months)
            * group by to userid
            * sort by desc
            * then fetch only k users
            * display userID and count(*)
            * 
            * SELECT userID, count(*) as Num_messages
            FROM ((SELECT fromID as userID, messageBody, toGroupID, toUserID, timeSent FROM message)
            UNION (SELECT toUserID as userID, messageBody, toGroupID, fromID, timeSent FROM message_copy)) as msgs
            WHERE ((DATE_PART('year', CAST((SELECT * FROM get_clock) as DATE)) - DATE_PART('year', CAST((msgs.timeSent) as DATE))) * 12 + (DATE_PART('month', CAST((SELECT * FROM get_clock) as DATE)) - DATE_PART('month', CAST(msgs.timeSent as DATE)))) < 20
            GROUP BY userID
            ORDER BY Num_messages DESC
            FETCH FIRST 10 ROWS ONLY;
            */

            String q1 = "SELECT userID, count(*) as Num_messages FROM ((SELECT fromID as userID, messageBody, toGroupID, toUserID, timeSent FROM message) UNION (SELECT toUserID as userID, messageBody, toGroupID, fromID, timeSent FROM message_copy)) as msgs WHERE ((DATE_PART('year', CAST((SELECT * FROM get_clock) as DATE)) - DATE_PART('year', CAST((msgs.timeSent) as DATE))) * 12 + (DATE_PART('month', CAST((SELECT * FROM get_clock) as DATE)) - DATE_PART('month', CAST(msgs.timeSent as DATE)))) < ";
            q1 = q1 + numMonths + " GROUP BY userID ORDER BY Num_messages DESC FETCH FIRST " + numUsers + "  ROWS ONLY ";

            try{
                PreparedStatement selectMessages = conn.prepareStatement(q1);
                ResultSet getMessages = selectMessages.executeQuery();
                while (getMessages.next())
                {
                    int getUser = getMessages.getInt(1);
                    int getNumMessages = getMessages.getInt(2);
                    System.out.println("User: " + getUser + " has " + getNumMessages + " messages!" );
                }
                System.out.println("Top messages displayed.");
                if(!testing)
                {
                    applicationUserLoggedIn();
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void threeDegrees(int find_friend)
    {
        if(loggedInUser == -1)
        {
            System.out.println("Please log in to find a path between you and this user.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {  
            // SELECT * from get_three_degrees(10);
            try{
                PreparedStatement SQL = conn.prepareStatement("SELECT * from get_three_degrees(?)");
                SQL.setInt(1, loggedInUser);
                ResultSet rs = SQL.executeQuery();
    
                int root_user;
                int first_jump;
                int second_jump;
                int third_jump;
                boolean found = false;
                while (rs.next()) {
                    root_user = rs.getInt(1);
                    first_jump = rs.getInt(2);
                    second_jump = rs.getInt(3);
                    third_jump = rs.getInt(4);
                    if(root_user == loggedInUser && first_jump == find_friend)
                    {
                        System.out.println("There is a path between you and " + find_friend + " which is the friendship between you and " + find_friend );
                        found = true;
                        break;
                    }
                    else if(root_user == loggedInUser && second_jump == find_friend)
                    {
                        System.out.println("There is a path between you and " + find_friend + " which is the friendship between you and " + first_jump + " and their friendship with " + find_friend );
                        found = true;
                        break;
                    }
                    else if(root_user == loggedInUser && third_jump == find_friend)
                    {
                        System.out.println("There is a path between you and " + find_friend + " which is the friendship between you and " + first_jump + " and their friendship with " + second_jump + " and their friendship with " + find_friend );
                        found = true;
                        break;
                    }
                    else if(root_user == find_friend && first_jump == loggedInUser)
                    {
                        System.out.println("There is a path between you and " + find_friend + " which is the friendship between you and " + find_friend );
                        found = true;
                        break;
                    }
                    else if(root_user == find_friend && second_jump == loggedInUser)
                    {
                        System.out.println("There is a path between you and " + find_friend + " which is the friendship between them and " + second_jump + " and your friendship with " + second_jump );
                        found = true;
                        break;
                    }
                    else if(root_user == find_friend && third_jump == loggedInUser)
                    {
                        System.out.println("There is a path between you and " + find_friend + " which is the friendship between them and " + second_jump + " and their friendship with " + first_jump + " and your friendship with " + first_jump );
                        found = true;
                        break;
                    }
                }
                if(found)
                {
                    System.out.println("Three degrees displayed.");
                    System.out.println("Path found!");
                    if(!testing)
                    {
                        applicationUserLoggedIn();
                    }
                }
                else
                {
                    System.out.println("Three degrees displayed.");
                    System.out.println("No path found.");
                    if(!testing)
                    {
                        applicationUserLoggedIn();
                    }
                }                
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public void logout()
    {
        if(loggedInUser == -1)
        {
            System.out.println("You are not logged in, you cannot log out.");
            if(!testing)
            {
                applicationNoUserLoggedIn();
            }
        }
        else
        {  
            System.out.println(loggedInUser + " is logging out.");

            try {
                // store this and use this timestamp to update clock
                // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                
                
                // update lastlogin time stamp, also insert into clock, insert into clock when send message
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement("UPDATE profile SET lastlogin = (SELECT * FROM get_clock) WHERE userid = ?");
                pstmt.setInt(1, loggedInUser);        
                pstmt.executeUpdate();
                
                // logging out the user
                loggedInUser = -1;//setting logged in user to -1, operations cant work if user NOT logged in 
                System.out.println("Successfully logged out.");
                loggedInUser = -1;
                conn.commit();
                if(!testing)
                {
                    applicationNoUserLoggedIn();
                }
            }
            catch (Exception e) {
                 System.out.println(e);
            }
        }
    }

    public void exit()
    {
        if(loggedInUser != -1)
        {
            System.out.println("You are logged in. Please log out to exit.");
            if(!testing)
            {
                applicationUserLoggedIn();
            }
        }
        else
        {
            //do not call function to see what they want to do next.
            System.out.println("Successfully Exited.");
        }
    }

    // ----------------- HELPER METHODS TO CHECK ---------------------

    public void getAllUsers()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from profile");
            int userid1;
            String name1;
            System.out.println("Users: ");
            while (rs.next()) {
                userid1 = rs.getInt("userid");
                name1 = rs.getString("name");
                System.out.println(userid1 + " " + name1);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }

    public void getAllGroups()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from groupInfo");
            int gid;
            String name1;
            System.out.println("Groups: ");
            while (rs.next()) {
                gid = rs.getInt("gid");
                name1 = rs.getString("name");
                System.out.println(gid + " " + name1);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }

    public void getAllPendingFriends()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from pendingFriend");
            int fromID;
            int toID;
            String requestText;
            System.out.println("Pending Friends: ");
            while (rs.next()) {
                fromID = rs.getInt("fromID");
                toID = rs.getInt("toID");
                requestText = rs.getString("requestText");
                System.out.println(fromID + " " + toID + " " + requestText);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }

    public void getAllPendingGroupMembers()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from pendingGroupMember");
            int groupID;
            int userID;
            String requestText;
            Timestamp requestTime;
            System.out.println("Pending Group Members: ");
            while (rs.next()) {
                groupID = rs.getInt("gID");
                userID = rs.getInt("userID");
                requestText = rs.getString("requestText");
                requestTime = rs.getTimestamp("requestTime");
                System.out.println(groupID + " " + userID + " " + requestText + " " + requestTime);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }

    public void getAllFriends()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from friend");
            int userID1;
            int userID2;
            Date Jdate;
            String requestText;
            System.out.println("Friends: ");
            while (rs.next()) {
                userID1 = rs.getInt("userID1");
                userID2 = rs.getInt("userID2");
                Jdate = rs.getDate("JDate");
                requestText = rs.getString("requestText");
                System.out.println(userID1 + " " + userID2 + " " + Jdate + " " + requestText);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }

    public void getAllGroupMembers()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from groupMember");
            int groupID;
            int userID;
            String role;
            Timestamp lastConfirmed;
            System.out.println("Group Members: ");
            while (rs.next()) {
                groupID = rs.getInt("gID");
                userID = rs.getInt("userID");
                role = rs.getString("role");
                lastConfirmed = rs.getTimestamp("lastConfirmed");
                System.out.println(groupID + " " + userID + " " + role + " " + lastConfirmed);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }
   
    public void getAllMessages()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from message");
            int msgID;
            int fromID;
            String messageBody;
            int toUserID;
            int toGroupID;
            Timestamp timeSent;
            System.out.println("Messages: ");
            while (rs.next()) {
                msgID = rs.getInt("msgID");
                fromID = rs.getInt("fromID");
                messageBody = rs.getString("messageBody");
                toUserID = rs.getInt("toUserID");
                toGroupID = rs.getInt("toGroupID");
                timeSent = rs.getTimestamp("timeSent");
                System.out.println(msgID + " " + fromID + " " + messageBody + " " + toUserID + " " + toGroupID + " " + timeSent);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }
    
    public void getAllMessageRecipients()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from messageRecipient");
            int msgID;
            int userID;
            while (rs.next()) {
                msgID = rs.getInt("msgID");
                userID = rs.getInt("userID");
                System.out.println(msgID + " " + userID);
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }
    
    public void getLoggedInUser()
    {
        System.out.println("The logged in user is: " + loggedInUser);
    }

    public void getLoggedInUserLastTimeStamp()
    {
        System.out.println("The logged in user last timestamp is: " + lastLoginUser);
    }

    public void getClockTable()
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from CLOCK");
            Timestamp pseudotime;
            System.out.println("Clock table: ");
            while (rs.next()) {
                pseudotime = rs.getTimestamp(1);
                System.out.println(pseudotime +"");
            }
        }
        catch (Exception e) {
             System.out.println(e);
        }
    }
}