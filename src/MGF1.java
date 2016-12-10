import java.security.MessageDigest;

public class MGF1 {
    private MessageDigest md;
    private String mfgSeed;
    private int maskLen;


    public MGF1(String mfgSeed, int maskLen) {
        this.mfgSeed = mfgSeed;
        this.maskLen = maskLen;
        if (maskLen > Math.pow(2, 32)) {
            System.out.println("Mask too long");
            System.exit(0);
        }
    }
    public String calculate(){

        String t = "";

        int iter = (int) Math.ceil((maskLen/md.getDigestLength())-1);
        for (int i = 0; i < iter; i++) {
            String c = I2OSP(i, 4);
            String temp = mfgSeed.concat(c);
            try {
                md = MessageDigest.getInstance("SHA-1");
                md.update(temp.getBytes("UTF-8"));
                //fixa så att sha fungerar, och hasha temp och gör till string
                t = t.concat(temp);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t.substring(0, maskLen*2);
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
