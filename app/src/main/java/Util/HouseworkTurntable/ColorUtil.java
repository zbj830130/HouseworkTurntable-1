package Util.HouseworkTurntable;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by zhangbojin on 6/02/17.
 */

public class ColorUtil {
    public static int getColor() {
        Random random = new Random();

        return Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
