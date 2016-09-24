class OD extends Person implements growing{
    public OD(String name, int age, String job){
        this.name = name;
        this.age = age;
        this.job = job;
    }
    public void talk(){
        System.out.println("My name is " + name + "\nMy age is " + age + "\nMy job is "+ job);
    } 
    @Override
    public void drive(){
        System.out.println("My name is " + name + "\nMy age is " + age + "\nMy job is "+ job + "\nI am the best driver.");
    }



    public static void main(String[] args){
        OD od = new OD("DENGLIN", 20, "233");
        od.drive();
    }
    
}