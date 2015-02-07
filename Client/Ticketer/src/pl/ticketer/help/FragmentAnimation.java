package pl.ticketer.help;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class FragmentAnimation extends Animation {
private View mAnimatedView;
private int mEndHeight;
private int mType;
private ImageView[] images;

/**
 * Initializes expand collapse animation, has two types, collapse (1) and expand (0).
 * @param view The view to animate
 * @param duration
 * @param type The type of animation: 0 will expand from gone and 0 size to visible and layout size defined in xml. 
 * 1 will collapse view and set to gone
 */
public FragmentAnimation(View view, int duration, int type, Activity activity, ImageView[] images) {
    setDuration(duration);
    mAnimatedView = view;
    this.images = images;

    //setHeightForWrapContent(activity, view);

    mEndHeight = 300;//mAnimatedView.getLayoutParams().height;

    mType = type;
    if(mType == 0) {
        mAnimatedView.getLayoutParams().height = 0;
        mAnimatedView.setVisibility(View.VISIBLE);
        for(ImageView image : images){
        	image.setVisibility(View.VISIBLE);
        }
    }
}

@Override
protected void applyTransformation(float interpolatedTime, Transformation t) {
    super.applyTransformation(interpolatedTime, t);
    if (interpolatedTime < 1.0f) {
        if(mType == 0) {
            mAnimatedView.getLayoutParams().height = (int) (mEndHeight * interpolatedTime);
        } else {
            mAnimatedView.getLayoutParams().height = mEndHeight - (int) (mEndHeight * interpolatedTime);
        }
        mAnimatedView.requestLayout();
    } else {
        if(mType == 0) {
            mAnimatedView.getLayoutParams().height = mEndHeight;
            mAnimatedView.requestLayout();
        } else {
            mAnimatedView.getLayoutParams().height = 0;
            mAnimatedView.setVisibility(View.GONE);
            for(ImageView image : images){
            	image.setVisibility(View.GONE);
            }
            mAnimatedView.requestLayout();
            mAnimatedView.getLayoutParams().height = LayoutParams.WRAP_CONTENT;     // Return to wrap
        }
    }
}

/**
 * This method can be used to calculate the height and set it for views with wrap_content as height. 
 * This should be done before ExpandCollapseAnimation is created.
 * @param activity
 * @param view
 */

public static void setHeightForWrapContent(Activity activity, View view) {
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

    int screenWidth = metrics.widthPixels;

    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);

    view.measure(widthMeasureSpec, heightMeasureSpec);
    int height = view.getMeasuredHeight();
    view.getLayoutParams().height = height;
}
}