package android.dineconnect.com.searchdrop.model;

import java.io.Serializable;

/**
 * Created by ADMIN on 2/9/2017.
 */

public final class Coord implements Serializable {
    public int x;
    public int y;
    public Coord(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}
