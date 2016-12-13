import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {
        // MGF1 mfg1 = new MGF1("0123456789abcdef" , 30);

        String m = "c107782954829b34dc531c14b40e9ea482578f988b719497aa0687";
        String seed = "1e652ec152d0bfcd65190ffc604c0933d0423381";

        OAEP oaep = new OAEP();
        oaep.encrypt(m, seed);


        String em = "0000255975c743f5f11ab5e450825d93b52a160aeef9d3778a18b7aa067f90b2" +
                "178406fa1e1bf77f03f86629dd5607d11b9961707736c2d16e7c668b367890bc" +
                "6ef1745396404ba7832b1cdfb0388ef601947fc0aff1fd2dcd279dabde9b10bf" +
                "c51f40e13fb29ed5101dbcb044e6232e6371935c8347286db25c9ee20351ee82";
        oaep.decrypt(em);
    }
}
