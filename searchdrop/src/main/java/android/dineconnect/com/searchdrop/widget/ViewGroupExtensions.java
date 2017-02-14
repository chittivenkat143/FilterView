package android.dineconnect.com.searchdrop.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by ADMIN on 2/14/2017.
 */

public class ViewGroupExtensions {
    Context context;
    public ViewGroupExtensions(Context context) {
        this.context = context;
    }

    public int calculateSize(int measureSpec, int desiredSize){
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(desiredSize);

        int actualSize = (mode == View.MeasureSpec.EXACTLY) ? size : (mode == View.MeasureSpec.AT_MOST) ? Math.min(desiredSize, size) : desiredSize ;

        return actualSize;
    }

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int dpToPx(float dp){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static  int getDimen(int res, Context context){
        return context.getResources().getDimensionPixelOffset(res);
    }

    public int calculateX(int position, int size ,int minMargin, int itemSize){
        int realMargin = calculateMargin(size, itemSize, minMargin);
        return position * itemSize + position * realMargin + realMargin;
    }

    public int calculateMargin(int width, int itemWidth, int margin) {
        int count = calculateCount(width, itemWidth, margin);
        return count > 0 ? (width - count * itemWidth) / count : 0;
    }

    public int calculateCount(int width, int itemWidth, int margin){
        return width / (itemWidth + margin );
    }

    public boolean isClick(float startX, float startY, float x, float y){
        return Math.abs(x - startX) < 20 && Math.abs(y - startY) < 20 ;
    }

}
