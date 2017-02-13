package android.dineconnect.com.searchdrop.adapter;

import android.dineconnect.com.searchdrop.widget.FilterItem;

import java.util.List;

/**
 * Created by ADMIN on 2/9/2017.
 */

public abstract class FilterAdapter<T> {
    List<T> tList;
    public FilterAdapter(final List<T> tList) {this.tList=tList;}
    abstract FilterItem createView(int position, T item);
}
