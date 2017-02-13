package android.dineconnect.com.searchdrop.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.dineconnect.com.searchdrop.R;
import android.dineconnect.com.searchdrop.listener.FilterItemListener;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DrawableUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by ADMIN on 2/9/2017.
 */

public class FilterItem extends FrameLayout implements Serializable{

    Boolean isFilterSelected = false;
    Boolean isIncreased = false;
    Float startX = 0f;
    Float startY = 0f;
    Context context;

    @ColorInt
    Integer cancelIconTint = android.R.color.white;
    @DrawableRes
    Integer cancelIcon = R.drawable.ic_cancel;

    @ColorInt
    Integer color=0;
    @ColorInt
    Integer checkedColor=0;
    @ColorInt
    Integer strokeColor=0;
    @ColorInt
    Integer checkedTextColor=0;
    @ColorInt
    Integer textColor=0;

    String stringLabelText = "";
    Typeface typeface = Typeface.SANS_SERIF;

    protected int fullSize = 0;
    protected FilterItemListener filterItemListener = null;

    String mText = "";
    int mStrokeWidth = dpToPx(1.25f);

    TextView textView;
    RelativeLayout textBackground;
    AppCompatImageView viewLeft,viewRight,buttonCancel;
    View topStroke,bottomStroke;
    private int measuredWidth;


    public FilterItem(Context context) {
        this(context, null);
    }

    public FilterItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_filter, this, true);
        textView = (TextView) findViewById(R.id.textView);
        textBackground = (RelativeLayout) findViewById(R.id.textBackground);
        viewLeft = (AppCompatImageView) findViewById(R.id.viewLeft);
        viewRight = (AppCompatImageView) findViewById(R.id.viewRight);
        buttonCancel = (AppCompatImageView) findViewById(R.id.buttonCancel);
        topStroke = (View) findViewById(R.id.topStroke);
        bottomStroke = (View) findViewById(R.id.bottomStroke);
        textView.setTypeface(typeface);
        viewLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isIncreased) {
                    if (isFilterSelected) {
                        deselect();
                    } else {
                        select();
                    }
                } else {
                    dismiss();
                }
            }
        });
        viewRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLeft.performClick();
            }
        });
        textBackground.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLeft.performClick();
            }
        });
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isIncreased) {
                    dismiss();
                } else {
                    viewLeft.performClick();
                }
            }
        });
        //buttonCancel.supportBackgroundTintList = ColorStateList.valueOf(getColor(cancelIconTint));
        buttonCancel.setSupportBackgroundTintList(ColorStateList.valueOf(getColor(cancelIconTint)));
        buttonCancel.setBackgroundDrawable(getDrawable(cancelIcon));
        isIncreased = true;

    }

    private void deselect() {
        deselect(true);
    }
    private void select() {
        select(true);
    }

    private void deselect(boolean notify) {
        isFilterSelected = false;
        updateView();

        if (notify) {
            filterItemListener.onItemDeselected(this);
        }
    }

    private void select(boolean notify) {
        isIncreased = true;
        isFilterSelected = true;
        updateView();

        if (notify) {
            filterItemListener.onItemSelected(this);
        }
    }

    private void dismiss() {
        filterItemListener.onItemRemoved(this);
    }


    public void decrease(float ratio){
        textView.setScaleX(1 - 0.2f * ratio);
        textView.setAlpha(1 - ratio);
        buttonCancel.setAlpha(ratio);
        textBackground.setScaleX(1 - ratio);
        viewLeft.setTranslationX(circlePosition() * ratio);
        viewRight.setTranslationX(-circlePosition() * ratio);

        if (ratio == 0f) {
            buttonCancel.setVisibility(VISIBLE);
            buttonCancel.setAlpha(0f);
        }

        if (ratio == 1f) {
            textView.setScaleX(0f);
        }

        isIncreased = false;
    }

    public void increase(float ratio){
        textView.setScaleX(1f);
        textView.setAlpha(ratio);
        buttonCancel.setAlpha(1 - ratio);
        textBackground.setScaleX(ratio);
        viewLeft.setTranslationX(circlePosition() * (1 - ratio));
        viewRight.setTranslationX(-circlePosition() * (1 - ratio));

        if (ratio == 1f) {
            buttonCancel.setVisibility(GONE);
            fullSize = measuredWidth;
        }

        isIncreased = true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(fullSize == 0){
            fullSize = measuredWidth;
        }
    }

    private void updateView() {
        updateTextColor();
        updateBackground();
    }

    private void updateBackground() {
        int color = (isFilterSelected) ? checkedColor : textColor;
        color = removeAlpha(color);

        int strokeColor = isFilterSelected ? color : removeAlpha(getColor(android.R.color.white));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(100);
        if(color != 0){
            gradientDrawable.setColor(color);
            textBackground.setBackgroundColor(color);
        }else{
            gradientDrawable.setColor(getColor(android.R.color.white));
            textBackground.setBackgroundColor(getColor(android.R.color.white));
        }

        if (strokeColor != 0) {
            gradientDrawable.setStroke(mStrokeWidth, getColor(strokeColor));
            topStroke.setBackgroundColor(getColor(strokeColor));
            bottomStroke.setBackgroundColor(getColor(strokeColor));
        }

        viewLeft.setBackgroundDrawable(gradientDrawable);
        viewRight.setBackgroundDrawable(gradientDrawable);
    }

    private void updateTextColor() {
        @ColorInt int color = (isFilterSelected) ? checkedTextColor : textColor;
        if(color!=0){
            textView.setTextColor(color);
        }
    }

    private int removeAlpha(@ColorInt int color) {
        return color==0 ? Integer.parseInt("0xff000000") : color;//, 16) : color;
    }

    private int getColor(int color){
        return ContextCompat.getColor(context, color);
    }

    private Drawable getDrawable(int drawable){
        return ContextCompat.getDrawable(context, drawable);
    }

    public String getStringLabelText() {
        return textView.getText().toString();
    }

    public void setStringLabelText(String stringLabelText) {
        this.stringLabelText = stringLabelText;
        textView.setText(this.stringLabelText);
    }

    public float circlePosition(){
        return ((float)(textBackground.getWidth() / 2+1));
    }

    public float collapsedSize(){
        return ((float)(viewLeft.getWidth()));
    }


    public void removeFromParent(){

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof FilterItem) return false;

        if (mText != obj) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return !mText.equals("") ? 0 : mText.hashCode();
    }

    public int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
