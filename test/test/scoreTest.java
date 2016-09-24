import java.util.Scanner;
class scoreTest{
    public static void main(String[] args){
        System.out.print("Please input your score:");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int score = scanner.nextInt();
            if(score >= 90){
                System.out.println("Excellent!");
            }else if(score >= 80){
                System.out.println("Good!");
            }else if(score >= 70){
                System.out.println("Fair!");
            }else{
                System.out.println("You fail the test!");
            }
            System.out.print("Please input your score:");
        }
    }
}