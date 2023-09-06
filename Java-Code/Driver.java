
import java.util.*;
import java.sql.Date;

public class Driver{
    public static void main(String[] args) // throws SQLException, ClassNotFoundException, do we need this, was in recitation
    {
        try (Scanner myObj = new Scanner(System.in)) {
            // get user name and pass from user
            // pass that into besocial constructor
            System.out.print("Enter datagrip username: ");
            String userName = myObj.nextLine();  // Read user input
            System.out.print("Enter datagrip password: ");
            String password = myObj.nextLine();  // Read user input

            BeSocial tester = new BeSocial(userName, password, true);
            // need to rerun schema so the friendship is not already in table
            // tester.login("admin@besocial.com", "admin");
            // tester.createUser("Bob", "bob@bob.com", "securepass123", Date.valueOf("2002-11-01"));
            // tester.getAllUsers();
            // tester.dropUser("bob@bob.com");
            // tester.getAllUsers();
            // tester.initiateFriendship("61", "lets be friends");
            // tester.getAllFriends();
            // tester.confirmFriendRequests();
            // tester.getAllFriends();
            // tester.createGroup("the best group", 23, "the best group");
            // tester.getAllGroups();
            // tester.initiateAddingGroup(3, "can i join?");
            // tester.getAllGroups();
            // tester.getAllPendingGroupMembers();
            // tester.confirmGroupMembership();
            // tester.getAllGroupMembers();
            // tester.leaveGroup(5);
            // tester.getAllGroupMembers();
            // tester.searchForUser("anna");
            // tester.displayFriends();
            // tester.displayMessages();
            // tester.displayNewMessages();
            // tester.rankGroups();
            // tester.rankUsers();
            // tester.topMessages(5,5);
            // tester.threeDegrees(5);
            // tester.logout();
            // tester.exit();

            new BeSocial(userName, password, false);
        }
        catch (Exception e) {
            System.out.println(e);
        }        
    }
}