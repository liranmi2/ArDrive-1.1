package liran.ardrive1;

/**
 * Created by Liran on 04/11/2015.
 */
public class MainSettings {

    private static MainSettings Ins;
    public static int speed;
    /*
    settings options
     */

    private MainSettings(){

    }

    public static MainSettings getInstance() {
        if (Ins == null) {
            Ins = new MainSettings();
        }
        return Ins;
    }

}
