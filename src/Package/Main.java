package Package;

public class Main {
    public static void main(String[] args) throws Exception {
        new Mongo().insertOne();
        new Mongo().insertMany();
        new Mongo().udate();
        new Mongo().delete();
        new Mongo().find("Peto");
    }
}
