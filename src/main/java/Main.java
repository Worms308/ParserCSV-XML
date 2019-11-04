import db.InitDB;

public class Main {

    public static void main(String[] args){
        InitDB initDB = new InitDB();
        initDB.initDatabase("jdbc:h2:~/rekrutacja", "sa","");



        System.out.println("Hello world");
    }
}
