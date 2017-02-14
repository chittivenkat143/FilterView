package android.dineconnect.com.searchdrop.widget;

import android.content.Context;
import android.dineconnect.com.searchdrop.listener.CollapseListener;
import android.dineconnect.com.searchdrop.listener.FilterItemListener;
import android.dineconnect.com.searchdrop.model.FilterModel;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by ADMIN on 2/14/2017.
 */

public class Filter<T extends FilterModel> extends FrameLayout implements FilterItemListener, CollapseListener {

    /*var adapter: FilterAdapter<T>? = null
    var listener: FilterListener<T>? = null
    var margin = dpToPx(getDimen(R.dimen.margin))
    var noSelectedItemText: String = ""
        set(value) {
            collapsedText.text = value
        }
    var textToReplaceArrow: String = ""
        set(value) {
            collapseView.setText(value)
        }

    var replaceArrowByText: Boolean = false
        set(value) {
            collapseView.setHasText(value)
        }

    private var mIsBusy = false

    private var isCollapsed: Boolean? = null

    private val STATE_SUPER = "state_super"
    private val STATE_SELECTED = "state_selected"
    private val STATE_REMOVED = "state_removed"
    private val STATE_COLLAPSED = "state_collapsed"

    private val mSelectedFilters: LinkedHashMap<FilterItem, Coord> = LinkedHashMap()
    private val mRemovedFilters: LinkedHashMap<FilterItem, Coord> = LinkedHashMap()
    private val mItems: LinkedHashMap<FilterItem, T> = LinkedHashMap()
    private var mSelectedItems: ArrayList<T> = ArrayList()
    private var mRemovedItems: ArrayList<T> = ArrayList()*/

    public Filter(Context context) {
        super(context);
    }

    public Filter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Filter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void collapse() {

    }

    @Override
    public void expand() {

    }

    @Override
    public void toggle() {

    }

    @Override
    public void onItemSelected(FilterItem filterItem) {

    }

    @Override
    public void onItemDeselected(FilterItem filterItem) {

    }

    @Override
    public void onItemRemoved(FilterItem filterItem) {

    }
}
