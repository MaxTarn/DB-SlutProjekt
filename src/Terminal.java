import java.sql.SQLException;
import java.util.Scanner;

public class Terminal {
    static Scanner console = new Scanner(System.in);

    public static void printActions(ConnetionToDB wrapper) throws SQLException {
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
                DB.demandLogIn(wrapper);
            }
            case "2" ->{
                DB.addNewUserToDB(wrapper);
            }
            case "3" ->{

            }
            /*case "4" ->{

            }
            case "5" ->{

            }
            case "6" ->{

            }*/
        }
    }

    public static String readLine(){
        return console.nextLine();
    }

    public static String ask(String question){
        Scanner console = new Scanner(System.in);
        System.out.print(question + " :   ");
        String input = console.nextLine();
        return input;
    }




    public static void close(){
        console.close();
    }
}
