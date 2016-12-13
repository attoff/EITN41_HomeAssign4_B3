import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {
        // MGF1 mfg1 = new MGF1("0123456789abcdef" , 30);

        String m = "c107782954829b34dc531c14b40e9ea482578f988b719497aa0687";
        String seed = "1e652ec152d0bfcd65190ffc604c0933d0423381";

        OAEP oaep = new OAEP();
        oaep.encrypt(m, seed);


        String em = "";
        oaep.decrypt(em);
    }
}
