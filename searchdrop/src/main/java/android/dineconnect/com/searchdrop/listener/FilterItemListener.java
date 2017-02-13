package android.dineconnect.com.searchdrop.listener;

import android.dineconnect.com.searchdrop.widget.FilterItem;

/**
 * Created by ADMIN on 2/9/2017.
 */

public interface FilterItemListener {
    void onItemSelected(FilterItem filterItem);
    void onItemDeselected(FilterItem filterItem);
    void onItemRemoved(FilterItem filterItem);
}
