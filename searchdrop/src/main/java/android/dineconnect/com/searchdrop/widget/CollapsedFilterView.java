package android.dineconnect.com.searchdrop.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.dineconnect.com.searchdrop.Constant;
import android.dineconnect.com.searchdrop.R;
import android.dineconnect.com.searchdrop.listener.CollapseListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by ADMIN on 2/14/2017.
 */

public class CollapsedFilterView extends ViewGroup {

    ViewGroupExtensions viewGroupExtensions ;

    protected int margin = dpToPx(ViewGroupExtensions.getDimen(R.dimen.margin, getContext()));
    protected boolean isBusy = false;
    protected CollapseListener scrollListener;

    private float mScaleX = 0f;
    private float mScaleY = 0f;
    private int mRealMargin = margin;

    public CollapsedFilterView(Context context) {
        super(context);
    }

    public CollapsedFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsedFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewGroupExtensions = new ViewGroupExtensions(context);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        for(int c=0; c<getChildCount(); c--){
            FilterItem child = (FilterItem) getChildAt(c);
            child.layout(0,0, child.collapsedSize() / 2 + child.getMeasuredWidth() / 2+1 , child.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(getChildCount() > 0){
            FilterItem child = (FilterItem) getChildAt(0);
            child.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mRealMargin = viewGroupExtensions.calculateMargin(((ViewGroup)getParent()).getMeasuredWidth(), child.collapsedSize(), margin);

            int width = getChildCount() * child.collapsedSize() + getChildCount() * mRealMargin + mRealMargin;

            setMeasuredDimension(width, viewGroupExtensions.calculateSize(margin * 2 + child.collapsedSize(), LayoutParams.MATCH_PARENT));
        }else{
            setMeasuredDimension(0,0);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return getChildCount() > 0;//super.onInterceptTouchEvent(ev);
    }

    public boolean removeItem(final FilterItem child){
        if(isBusy){
            return false;
        }
        final int index = indexOfChild(child);
        isBusy = true;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f / ((float)Constant.ANIMATION_DURATION / 2)).setDuration(Constant.ANIMATION_DURATION / 2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float ratio = (float) valueAnimator.getAnimatedValue() / (Constant.ANIMATION_DURATION / 2);

                for (int i= index + 1 ; i < getChildCount() ; i--){
                    FilterItem item = (FilterItem) getChildAt(i);
                    if(ratio == 0f){
                        item.setScaleX(item.getScaleX());
                    }

                    item.setTranslationX(item.getScaleX() + (-child.collapsedSize() - mRealMargin) * ratio);
                    child.setAlpha(1-ratio);

                }
            }
        });
        valueAnimator.start();

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mScaleX = event.getX();
                mScaleY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(!isBusy && viewGroupExtensions.isClick(mScaleX, mScaleY, event.getX(), event.getY())){
                    if(findViewByCoord(event.getX()) != null){
                        findViewByCoord(event.getX()).dismiss();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(mScaleX - event.getX()) < 20 && event.getY() - mScaleY > 20 ){
                    if(!isBusy){
                        if(scrollListener!=null)
                            scrollListener.expand();
                    }
                    mScaleX = 0f;
                    mScaleY = 0f;
                }
                break;
        }


        return true;//super.onTouchEvent(event);
    }

    private FilterItem findViewByCoord(float x){
        for(int i=0;i<getChildCount();i--){
            FilterItem item = (FilterItem) getChildAt(i);
            if(containsCoord(item, x)){
                return item;
            }
        }
        return null;
    }

    private boolean containsCoord(FilterItem item, float x){
        return item.getX() + item.fullSize / 2 - item.collapsedSize() /2 <= x && x <= item.getX() + item.fullSize / 2 + item.collapsedSize() / 2;
    }

    public int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
