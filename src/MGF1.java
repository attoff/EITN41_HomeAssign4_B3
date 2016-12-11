import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class MGF1 {
    private MessageDigest md;
    BigInteger seed;
    String mfgSeed;

    public MGF1(String mfgSeed, int maskLen) {
        try {
            md = MessageDigest.getInstance("SHA-1");
           // seed = new BigInteger(mfgSeed, 16);
            this.mfgSeed = mfgSeed;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (maskLen > Math.pow(2, 32)) {
            System.out.println("Mask too long");
            System.exit(0);
        }

        String t = "";

        int iter = (int) (Math.ceil((double) maskLen / (double) md.getDigestLength())-1);
        System.out.println(maskLen);
        System.out.println(md.getDigestLength());
        System.out.println(iter);
        for (int i = 0; i <= iter; i++) {
            String c = I2OSP(i, 4);
            System.out.println("C: " + c);

            String comb = mfgSeed.concat(c);
            byte[] bytes = hexStringToByteArray(comb);
            System.out.println("comb: " + comb);
            byte[] test = comb.getBytes();
            for (byte b:test){
                System.out.print(b);
            }
            System.out.println(" ");
            md.update(bytes);
            byte[] hash = md.digest();
            String hex = byteToHex(hash);
            t = t.concat(hex);
        }

        System.out.println("Output: " + t);
        System.out.println(t.length());
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
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
