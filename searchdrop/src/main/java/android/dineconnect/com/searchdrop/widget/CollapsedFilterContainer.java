package android.dineconnect.com.searchdrop.widget;

import android.content.Context;
import android.dineconnect.com.searchdrop.R;
import android.dineconnect.com.searchdrop.listener.CollapseListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by ADMIN on 2/14/2017.
 */

public class CollapsedFilterContainer extends RelativeLayout {

    protected CollapseListener collapseListener;
    private float mStartX = 0f;
    private float mStartY = 0f;

    ViewGroupExtensions viewGroupExtensions;
    CollapsedFilterView collapsedFilter;

    public CollapsedFilterContainer(Context context) {
        this(context, null);
    }

    public CollapsedFilterContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsedFilterContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.collapsed_container, this, true);
        collapsedFilter = (CollapsedFilterView) findViewById(R.id.collapsedFilter);
        viewGroupExtensions = new ViewGroupExtensions(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isEmpty = collapsedFilter.getChildCount() == 0;
        boolean containsEvent = ev.getX() >= collapsedFilter.getX() && ev.getX() <= collapsedFilter.getX() + collapsedFilter.getMeasuredWidth();

        return isEmpty || !containsEvent;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
         switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(collapseListener!=null) {
                    if (!collapsedFilter.isBusy && viewGroupExtensions.isClick(mStartX, mStartY, event.getX(), event.getY())) {
                        collapseListener.toggle();
                        mStartX = 0f;
                        mStartY = 0f;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(collapseListener!=null) {
                    if (!collapsedFilter.isBusy && Math.abs(mStartX - event.getX()) < 20 && event.getY() - mStartY > 20) {
                        collapseListener.expand();
                        mStartX = 0f;
                        mStartY = 0f;
                    }else if(!collapsedFilter.isBusy && Math.abs(mStartX - event.getX()) < 20 && event.getY() - mStartY < -20){
                        collapseListener.collapse();
                        mStartX = 0f;
                        mStartY = 0f;
                    }
                }
                break;
        }
        return true;//super.onTouchEvent(event);
    }
}
