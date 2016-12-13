import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Random;

public class OAEP_Encryption {
    private int k = 64;
    private String M;
    private String seed;

    public OAEP_Encryption(String M, String seed) {
        this.M = M;
        this.seed = seed;
        String L = "";
        int hLen = 20;

        byte[] message = hexStringToByteArray(M);
        int mLen = message.length;

        if (mLen > k - hLen * 2 - 2) {
            System.out.println("message too long");
            System.exit(1);
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] LByte = hexStringToByteArray(L);
        md.update(LByte);
        byte[] lHash = md.digest();

        int psLen = k - mLen - hLen * 2 - 2;
        byte[] ps = new byte[psLen];
        byte[] hexVal = {0x00};

        byte[] db = new byte[k - hLen - 1];


        int start = 0;
        System.arraycopy(lHash, 0, db, start, lHash.length);
        start = lHash.length;
        System.arraycopy(ps, 0, db, start, ps.length);
        start += ps.length;
        System.arraycopy(hexVal, 0, db, start, 1);
        start += 1;
        System.arraycopy(message, 0, db, start, message.length);


        MGF1 mgf1 = new MGF1(seed, k - hLen - 1);
        String dbMask = mgf1.getMask();
        byte[] dbMaskByte = hexStringToByteArray(dbMask);

        byte[] maskedDB = xOR(db, dbMaskByte);

        MGF1 mgf12 = new MGF1(byteToHex(maskedDB), hLen);
        String seedMask = mgf12.getMask();
        byte[] seedMaskByte = hexStringToByteArray(seedMask);

        byte[] maskedSeed = xOR(hexStringToByteArray(seed), seedMaskByte);

        byte[] hexVal2 = {0x00};
        byte[] EMByte = new byte[1 + maskedSeed.length + maskedDB.length];
        start = 0;
        System.arraycopy(hexVal2, 0, EMByte, start, hexVal2.length);
        start += hexVal2.length;
        System.arraycopy(maskedSeed, 0, EMByte, start, maskedSeed.length);
        start += maskedSeed.length;
        System.out.println(byteToHex(maskedSeed));
        System.arraycopy(maskedDB, 0, EMByte, start, maskedDB.length);

        System.out.println("REAL: 0000255975c743f5f11ab5e450825d93b52a160aeef9d3778a18b7aa067f90b2178406fa1e1bf77f03f86629dd5607d11b9961707736c2d16e7c668b367890bc6ef1745396404ba7832b1cdfb0388ef601947fc0aff1fd2dcd279dabde9b10bfc51f40e13fb29ed5101dbcb044e6232e6371935c8347286db25c9ee20351ee82");
        System.out.println("OURS: " + byteToHex(EMByte));


    }


    private byte[] xOR(byte[] a, byte[] b) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ b[i % b.length]);
        }
        return out;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
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
