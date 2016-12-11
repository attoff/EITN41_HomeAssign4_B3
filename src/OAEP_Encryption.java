import java.util.Formatter;
import java.util.Random;

public class OAEP_Encryption {
    private int k = 64;
    private String M;
    private String seed;
    int hLen2;

    public OAEP_Encryption(String M, String seed) {
        this.M = M;
        this.seed = seed;

        int mLen = M.length() / 2;
        hLen2 = 20;
        //lenght checking...
        if (mLen > k - hLen2 - 2) {
            System.out.println("message too long");
            System.exit(0);
        }
        //encoding
        String lHash = "da39a3ee5e6b4b0d3255bfef95601890afd80709";

        int hLen = lHash.length() / 2;
        int psLen = k - mLen - hLen2 - 2;
        String ps = "";
        for (int i = 0; i < psLen; i++) {
            ps = ps.concat("00");
        }

        String db = lHash.concat(ps).concat("01").concat(M);
        MGF1 mgf1 = new MGF1(seed, k - hLen - 1);
        String dbMask = mgf1.getMask();

        byte[] dbBytes = db.getBytes();
        byte[] dbMaskBytes = dbMask.getBytes();
        byte[] maskedDB = xOR(dbBytes, dbMaskBytes);


        MGF1 mgf12 = new MGF1(byteToHex(maskedDB), hLen);
        String seedMask = mgf12.getMask();

        byte[] seedBytes = seed.getBytes();
        byte[] seedMaskBytes = seedMask.getBytes();
        byte[] maskedSeed = xOR(seedBytes, seedMaskBytes);

        String em = "00".concat(byteToHex(maskedSeed).concat(byteToHex(maskedDB)));

        System.out.println(em);


    }


    private byte[] xOR(byte[] a, byte[] b) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ b[i%b.length]);
        }
        return out;
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
}
