


public class Main {

    public static void main(String[] args) {
       // MGF1 mfg1 = new MGF1("0123456789abcdef" , 30);

        String m = "fd5507e917ecbe833878";
        String seed = "1e652ec152d0bfcd65190ffc604c0933d0423381";

        OAEP_Encryption enc = new OAEP_Encryption(m, seed);
    }
}
