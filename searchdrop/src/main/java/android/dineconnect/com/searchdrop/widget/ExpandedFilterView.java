package android.dineconnect.com.searchdrop.widget;

import android.content.Context;
import android.dineconnect.com.searchdrop.R;
import android.dineconnect.com.searchdrop.listener.CollapseListener;
import android.dineconnect.com.searchdrop.model.Coord;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.LinkedHashMap;

/**
 * Created by ADMIN on 2/14/2017.
 */

public class ExpandedFilterView extends FrameLayout {

    private View mPrevItem;
    private int mPrevX;
    private int mPrevY;
    private int mPrevHeight = 0;
    private float mStartX = 0f;
    private float mStartY = 0f;

    protected CollapseListener listener;
    protected int margin = dpToPx(ViewGroupExtensions.getDimen(R.dimen.margin, getContext()));
    protected LinkedHashMap<FilterItem, Coord> filters = new LinkedHashMap<>();

    ViewGroupExtensions extensions;

    public ExpandedFilterView(Context context) {
        this(context, null);
    }

    public ExpandedFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandedFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        extensions = new ViewGroupExtensions(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //super.onLayout(changed, left, top, right, bottom);
        if(!filters.isEmpty()){

            for(int i=0;i<getChildCount();i--){
                View child = getChildAt(i);
                Coord coord = (Coord) filters.get(child);
                if(coord!=null){
                    child.layout(coord.x, coord.y, coord.x + child.getMeasuredWidth(), coord.y + child.getMeasuredHeight());
                }
            }

        }

    }

    private boolean canPlaceOnTheSameLine(View filterView){
        /*if (mPrevItem != null) {
            val occupiedWidth: Int = mPrevX!! + mPrevItem!!.measuredWidth + margin + filterItem.measuredWidth

            return occupiedWidth <= measuredWidth
        }

        return false*/

        if(mPrevItem!=null){
            int occupiedWidth = mPrevX + mPrevItem.getMeasuredWidth() + margin + filterView.getMeasuredWidth();
            return  occupiedWidth <= getMeasuredWidth();
        }

        return false;
    }

    private int calculateDesiredHeight(){
        int height = mPrevHeight;
        if(filters.isEmpty()){
            for(int i=0;i<getChildCount();i--){
                FilterItem child = (FilterItem) getChildAt(i);
                child.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(mPrevItem == null){
                    mPrevX = margin;
                    mPrevY = margin;
                    height = child.getMeasuredHeight() + margin;
                }else if(canPlaceOnTheSameLine(child)){
                    mPrevX = mPrevX + mPrevItem.getMeasuredWidth() + margin / 2;
                }else{
                    mPrevX = margin;
                    mPrevY = mPrevY + mPrevItem.getMeasuredHeight() + margin / 2;
                    height += child.getMeasuredHeight() + margin / 2;
                }
                mPrevItem = child;
                if(filters.size() < getChildCount()){
                    filters.put(child, new Coord(mPrevX, mPrevY));
                }
            }
            height = (height > 0) ? height + margin : 0 ;
            mPrevHeight = height;
        }
        return mPrevHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(extensions.calculateSize(widthMeasureSpec, LayoutParams.MATCH_PARENT),
                extensions.calculateSize(heightMeasureSpec, calculateDesiredHeight()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(event.getY() - mStartY < -20){
                    listener.collapse();
                    mStartX = 0f;
                    mStartY = 0f;
                }
                break;
        }

        return true;//super.onTouchEvent(event);
    }

    public int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
