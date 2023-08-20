package fsanchez.a10;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class ListItem implements Serializable {
    private long dttm;
    private String item;

    /**
     * ListItem Constructor for listing of items
     * @param item String sets item
     */

    public ListItem(String item) {
        this.item = item;
        dttm = System.nanoTime();
    }

    /**
     * ListItem Constructor with use of dttm and item
     * @param dttm id of item
     * @param item item of user
     */

    public ListItem(long dttm, String item) {
        this.dttm = dttm;
        this.item = item;
    }

    /**
     * toString function used for getting string of item
     * @return item of user
     */
    @Nullable
    @Override
    public String toString(){
        return item;
    }

    /**
     * getDttm function used to get the id
     * @return id item
     */

    public long getDttm() {
        return dttm;
    }

    /**
     * getItem function used to get item
     * @return item of user input
     */

    public String getItem() {
        return item;
    }
}
