import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
    static Scanner console = new Scanner(System.in);
    static Wrapper wrapper;

    public static void printActions( ) throws SQLException {
        System.out.println("----------");
        System.out.println("What do you want to do?");
        System.out.println("1: Log in");
        System.out.println("2: Add a new user");
        System.out.println("3: Add a new post");
        System.out.println("4: Add a new comment");
        System.out.println("5: Delete a user");
        System.out.println("6: Delete a post");
        System.out.println("7: Delete a comment");
        String input = ask("Your option:");
        System.out.println();
        switch (input){
            case "1" ->{
                DB.demandLogIn();
            }
            case "2" ->{
                DB.addNewUserToDB();
            }
            case "3" ->{
                if(!wrapper.currSession.loggedIn) DB.demandLogIn();
                DB.addNewPostToDB();
            }
            case "4" ->{
                if(!wrapper.currSession.loggedIn) DB.demandLogIn();
                DB.addNewCommentToDB();

            }
            case "5" ->{
                DB.deleteUser();
            }
            /*case "6" ->{

            }*/
        }
    }

    public static String readLine(){
        return console.nextLine();
    }

    public static String ask(String question){
        System.out.print(question + " :   ");
        String input = console.nextLine();
        return input;
    }

    public static int askForInt(String question){
        System.out.print(question + " : ");
        int input = Integer.parseInt(console.nextLine());
        return input;
    }


    public static void init(Wrapper wrapp){
        wrapper = wrapp;
    }
    public static void close(){
        console.close();
    }
}
