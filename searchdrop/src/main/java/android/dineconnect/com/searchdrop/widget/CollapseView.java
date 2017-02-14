package android.dineconnect.com.searchdrop.widget;

import android.content.Context;
import android.dineconnect.com.searchdrop.R;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ADMIN on 2/14/2017.
 */

public class CollapseView extends FrameLayout {

    AppCompatButton buttonOk;
    AppCompatImageView imageArrow;

    public CollapseView(Context context) {
        this(context, null);
    }

    public CollapseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_collapse, this, true);

        buttonOk = (AppCompatButton) findViewById(R.id.buttonOk);
        imageArrow = (AppCompatImageView) findViewById(R.id.imageArrow);

    }

    protected void setText(String text){
        buttonOk.setText(text);
    }

    protected void setHasText(boolean hasText){
        buttonOk.setVisibility(hasText ? VISIBLE : GONE);
    }

    protected void rotateArrow(float rotation){
        imageArrow.setRotation(rotation);
    }

    protected void turnIntoOkButton(float ratio){
        if(buttonOk.getVisibility() != VISIBLE) return;
        scale(getIncreasingScale(ratio), getDecreasingScale(ratio));
    }

    protected void turnIntoArrow(float ratio){
        if(buttonOk.getVisibility() != VISIBLE) return;
        scale(getDecreasingScale(ratio), getIncreasingScale(ratio));
    }

    private float getIncreasingScale(float ratio){
        return (ratio < 0.5f) ? 0f : (2 * ratio - 1);
    }

    private float getDecreasingScale(float ratio){
        return (ratio > 0.5f) ? 0f : (1 - 2 * ratio);
    }

    private void scale(float okScale, float arrowScale){
        buttonOk.setScaleX(okScale);
        buttonOk.setScaleY(okScale);
        imageArrow.setScaleX(arrowScale);
        imageArrow.setScaleY(arrowScale);
    }


    @Override
    public void setOnClickListener(OnClickListener l) {
        //super.setOnClickListener(l);
        buttonOk.setOnClickListener(l);
        imageArrow.setOnClickListener(l);
    }
}
