


public class Main {

    public static void main(String[] args) {
        String mgfSeed = "0123456789abcdef";
        int maskLen = 30;
        MGF1 mg = new MGF1(mgfSeed, maskLen);
        String output = mg.calculate();
        System.out.println(output);

    }
}
