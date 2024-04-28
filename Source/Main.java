import impl.Decrypter;

public class Main {

    public static void main(String[] args) {
        try {
            Decrypter decrypter = Decrypter.getInstance();
            decrypter.initialize();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
