import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MGF1 {
    private MessageDigest md;

    public MGF1(String mfgSeed, int maskLen) {

        if (maskLen > Math.pow(2, 32)) {
            System.out.println("Mask too long");
            System.exit(0);
        }

        String t = "";

        int iter = (int) Math.ceil((maskLen/md.getDigestLength())-1);
        for (int i = 0; i < iter; i++) {
            t = I2OSP(i, 4);
        }


    }

    private String I2OSP(int x, int xLen) {
        byte[] temp = new byte[xLen];

        temp[0] = (byte) (x >>> 24);
        temp[1] = (byte) (x >>> 16);
        temp[2] = (byte) (x >>> 8);
        temp[3] = (byte) (x >>> 0);
        
        return "";
    }

}
