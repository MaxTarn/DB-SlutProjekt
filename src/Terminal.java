import java.util.Scanner;

public class Terminal {
    public static String ReadLine(){
        Scanner console = new Scanner(System.in);
        String input = console.nextLine();
        console.close();
        return input;
    }

    public static String Ask(String question){
        Scanner console = new Scanner(System.in);
        System.out.print(question + ":   ");
        return console.nextLine();
    }
}
