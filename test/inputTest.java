import java.util.Scanner;
public class inputTest{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input your score:");
        while(scanner.hasNext()){
            int a = scanner.nextInt();
            System.out.println("Your score is:" + a);
            System.out.print("Please input your score:");
        }
    }
}