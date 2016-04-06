package com.alexandroukyriakos.streetbeescodechallenge.ui.customcomponents;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexandroukyriakos.streetbeescodechallenge.ApiWrapper;
import com.alexandroukyriakos.streetbeescodechallenge.R;

/**
 * a custom header component
 */
public class HeaderComponent extends LinearLayout {
    private View mContentContainer;
    private ImageView mIcon;
    private TextView mTitle;
    private View mDivider;

    public ImageView getIconImageView() {
        return mIcon;
    }
    public void setBackgroundResource(int color) {
        mContentContainer.setBackgroundResource(color);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTitle(int stringRes) {
        mTitle.setText(stringRes);
    }

    public void setTextColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setTextColorResId(int colorResId) {
        mTitle.setTextColor(ApiWrapper.getColor(getContext(), colorResId, null));
    }

    public void setIcon(int iconResId) {
        mIcon.setImageDrawable(ApiWrapper.getDrawable(getContext(), iconResId, null));
    }

    public void setIcon(Bitmap icon) {
        mIcon.setImageBitmap(icon);
    }

    public void setDividerColor(int color) {
        mDivider.setBackgroundColor(color);
    }

    public void setDividerColorResId(int colorResId) {
        mDivider.setBackgroundResource(colorResId);
    }

    public HeaderComponent(Context context) {
        this(context, null);
    }

    public HeaderComponent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.component_header, this, true);
        bindViews(view);
        setDefaultAttributeValues(attrs);
    }

    private void bindViews(View view) {
        this.mContentContainer = view.findViewById(R.id.content_container);
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mIcon = (ImageView) view.findViewById(R.id.icon);
        this.mDivider = view.findViewById(R.id.divider);
    }

    private void setDefaultAttributeValues(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Header);
        int bgColor = a.getColor(
                R.styleable.Header_backgroundColor,
                ApiWrapper.getColor(getContext(), R.color.header_background, null)
        );
        setBackgroundColor(bgColor);

        int textColor = a.getColor(
                R.styleable.Header_textColor,
                ApiWrapper.getColor(getContext(), R.color.header_text, null));
        setTextColor(textColor);

        int dividerColor = a.getColor(
                R.styleable.Header_dividerColor,
                ApiWrapper.getColor(getContext(), R.color.header_divider, null));
        setDividerColor(dividerColor);

        a.recycle();
    }
}