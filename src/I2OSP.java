import java.io.UnsupportedEncodingException;

public class I2OSP {

    private String returned;

    public I2OSP(int x, int xLen) {

        byte[] temp = new byte[xLen];

        temp[0] = (byte) (x >>> 24);
        temp[1] = (byte) (x >>> 16);
        temp[2] = (byte) (x >>> 8);
        temp[3] = (byte) (x >>> 0);

        try {
            returned = new String(temp, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getString() {
        return returned;
    }
}
