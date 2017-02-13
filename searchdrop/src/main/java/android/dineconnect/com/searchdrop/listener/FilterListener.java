package android.dineconnect.com.searchdrop.listener;

import java.util.ArrayList;

/**
 * Created by ADMIN on 2/9/2017.
 */

public interface FilterListener<T> {
    void onFiltersSelected(ArrayList<T> tArrayList);
    void onNothingSelected();
    void onFilterSelected(T t);
    void onFilterDeselected(T t);
}
