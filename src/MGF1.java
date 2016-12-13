import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class MGF1 {
    private MessageDigest md;
    BigInteger seed;
    String mfgSeed;
    String t;

    public MGF1(String mfgSeed, int maskLen) {
        try {
            md = MessageDigest.getInstance("SHA-1");
            this.mfgSeed = mfgSeed;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (maskLen > Math.pow(2, 32)) {
            System.out.println("Mask too long");
            System.exit(0);
        }

        t = "";

        int iter = (int) (Math.ceil((double) maskLen / (double) md.getDigestLength())-1);
        for (int i = 0; i <= iter; i++) {
            String c = I2OSP(i, 4);

            String comb = mfgSeed.concat(c);
            byte[] combBytes = hexToByte(comb);

            md.update(combBytes);
            byte[] hash = md.digest();
            String hex = byteToHex(hash);
            t = t.concat(hex);
        }
        t = t.substring(0, maskLen*2);
      //  System.out.println("Output: " + t);

    }
    public String getMask(){
        return t;
    }

    private String I2OSP(int x, int xLen) {
        if (x > Math.pow(256, xLen)){
            System.out.println("integer too large");
            System.exit(0);
        }


        byte[] temp = new byte[xLen];

        temp[0] = (byte) (x >>> 24);
        temp[1] = (byte) (x >>> 16);
        temp[2] = (byte) (x >>> 8);
        temp[3] = (byte) (x >>> 0);

        return byteToHex(temp);
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    private static byte[] hexToByte(String hex) {
        byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            result[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return result;
    }

}
