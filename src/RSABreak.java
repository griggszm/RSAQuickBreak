import java.util.Scanner;

public class RSABreak {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a number to decode: ");
        long toDecode = Long.decode(in.nextLine());
        Decomposer decomposer = new Decomposer();
        decomposer.decompose(toDecode);
        System.out.println("Time: " + decomposer.getMillis() + " MS");
        long p = decomposer.getP();
        long q = decomposer.getQ();
        System.out.println("Sanity check: " + (p * q));

    }

}
