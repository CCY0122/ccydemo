package com.example.ccydemo.selectitem;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccydemo.R;
import com.kyleduo.switchbutton.SwitchButton;

import io.reactivex.annotations.Nullable;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by ccy(17022) on 2018/10/22 上午10:28
 * 通用选项item
 */

public class SelectItem extends FrameLayout {

    private static final int TITLE_COLOR = 0xff000000;
    private static final int HINT_COLOR = 0xffB1B1B1;
    private static final int RIGHT_TEXT_COLOR = 0xffB1B1B1;
    private static final int DIVIDER_COLOR = 0xffefefef;
    private final float TITLE_SIZE = sp2px(14f);
    private final float HINT_SIZE = sp2px(12f);
    private final float RIGHT_TEXT_SIZE = sp2px(12f);

    /**
     * 普通型选项)(用于展示、点击进入二级页面等）
     */
    private static final int TYPE_SELECT = 0;
    /**
     * 开关型选项（右边是一个切换按钮，用于开关）
     */
    private static final int TYPE_CHECKABLE = 1;

    private LinearLayout rootLayout, rightLayout;
    private ImageView ivLeftImage, ivRightImage;
    private TextView tvTitle, tvHint, tvRightText;
    private View divider, llLeftImageParent;
    private ViewStub checkableViewStub;
    @Nullable
    private SwitchButton switchButton;
    /**
     * 左侧图片、标题、右侧文字、右侧图片角标。
     * <p>
     * 说明：除了左侧图片，其他三个子布局若需角标，考虑仅使用红点角标，数字角标效果可能不好
     * （因为考虑到右侧文字和右侧图片可能会同时显示，默认留出的padding不多。可根据实际情况自己获取子布局并设置padding）
     */
    @Nullable
    private QBadgeView leftImgBadge, titleBadge, rightTextBadge, rightImgBadge;

    /**
     * 文字内容。title默认显示，hint、rightText有内容才显示
     */
    private String title, hint, rightText;
    /**
     * 各文字颜色（可以int颜色值、xml定义的ColorStateList）
     */
    private ColorStateList titleColor, hintColor, rightTextColor;

    private int dividerColor;
    /**
     * 文字大小（xml里解析后会转为px值）
     */
    private float titleSize, hintSize, rightTextSize;
    /**
     * 图片资源（可以int颜色值（需指定宽高）、资源id、xml定义的drawable）
     */
    private Drawable leftImage, rightImage;

    /**
     * 图片大小，默认 {@link LayoutParams#WRAP_CONTENT}
     */
    private float leftImageWidth, leftImageHeight, rightImageWidth, rightImageHeight;

    /**
     * 选项item类型
     *
     * @see #TYPE_SELECT
     * @see #TYPE_CHECKABLE
     */
    private int type = TYPE_SELECT;
    /**
     * 是否显示分割线
     */
    private boolean dividerVisiable;
    private float dividerMarginStart;
    private float dividerMarginEnd;

    /**
     * 是否接管子类状态变化（如enable、checked、pressed等变化）。
     * 例：假如当前给item中的某张图片设置了一个StateListDrawable或给某文字设置了一个ColorStateList，
     * 若handleChildState为false，那么当点击该图片或文字时，它会触发state_pressed=true时的样式，而其他子类并没变化。
     * 若handleChildState为true，点击item任何位置时，所有子类都会表现出state_pressed=true时的样式，即由父类同步状态变化（需将子类clickable置为false）。
     * <p>
     * 若子类声明了自己的点击事件，点击该子类仍只能单独触发自己的state_pressed=true。
     */
    private boolean handleChildState = true;

    public SelectItem(Context context) {
        this(context, null);
    }

    public SelectItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        addView(LayoutInflater.from(context).inflate(R.layout.si_select_item, this, false));

        rootLayout = (LinearLayout) findViewById(R.id.si_layoutRoot);
        rightLayout = (LinearLayout) findViewById(R.id.si_right_layout);
        llLeftImageParent = findViewById(R.id.si_left_img_parent);
        ivLeftImage = (ImageView) findViewById(R.id.si_left_img);
        ivRightImage = (ImageView) findViewById(R.id.si_right_img);
        tvTitle = (TextView) findViewById(R.id.si_title);
        tvHint = (TextView) findViewById(R.id.si_hint);
        tvRightText = (TextView) findViewById(R.id.si_right_text);
        divider = findViewById(R.id.si_divider);
        checkableViewStub = (ViewStub) findViewById(R.id.checkable_view_stub);
//        switchButton = (SwitchButton) findViewById(R.id.si_switch_button); //此时为null


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelectItem);
        title = ta.getString(R.styleable.SelectItem_title);
        hint = ta.getString(R.styleable.SelectItem_hint);
        rightText = ta.getString(R.styleable.SelectItem_rightText);

        titleColor = ta.getColorStateList(R.styleable.SelectItem_titleColor);
        hintColor = ta.getColorStateList(R.styleable.SelectItem_hintColor);
        rightTextColor = ta.getColorStateList(R.styleable.SelectItem_rightTextColor);
        dividerColor = ta.getColor(R.styleable.SelectItem_dividerColor, DIVIDER_COLOR);

        titleSize = ta.getDimension(R.styleable.SelectItem_titleSize, TITLE_SIZE);
        hintSize = ta.getDimension(R.styleable.SelectItem_hintSize, HINT_SIZE);
        rightTextSize = ta.getDimension(R.styleable.SelectItem_rightTextSize, RIGHT_TEXT_SIZE);

        leftImage = ta.getDrawable(R.styleable.SelectItem_leftImgSrc);
        rightImage = ta.getDrawable(R.styleable.SelectItem_rightImgSrc);

        leftImageWidth = ta.getDimension(R.styleable.SelectItem_leftImgWidth, LayoutParams.WRAP_CONTENT);
        leftImageHeight = ta.getDimension(R.styleable.SelectItem_leftImgHeight, LayoutParams.WRAP_CONTENT);
        rightImageWidth = ta.getDimension(R.styleable.SelectItem_rightImgWidth, LayoutParams.WRAP_CONTENT);
        rightImageHeight = ta.getDimension(R.styleable.SelectItem_rightImgHeight, LayoutParams.WRAP_CONTENT);

        dividerVisiable = ta.getBoolean(R.styleable.SelectItem_dividerVisiable, true);
        dividerMarginStart = ta.getDimension(R.styleable.SelectItem_dividerMarginStart, 0);
        dividerMarginEnd = ta.getDimension(R.styleable.SelectItem_dividerMarginEnd, 0);
        type = ta.getInt(R.styleable.SelectItem_type, TYPE_SELECT);
        ta.recycle();

        if (titleColor == null) {
            titleColor = generateDefaultColorStateList(TITLE_COLOR);
        }
        if (hintColor == null) {
            hintColor = generateDefaultColorStateList(HINT_COLOR);
        }
        if (rightTextColor == null) {
            rightTextColor = generateDefaultColorStateList(RIGHT_TEXT_COLOR);
        }

        initView();

    }


    private void initView() {
        tvTitle.setClickable(false);
        tvHint.setClickable(false);
        tvRightText.setClickable(false);
        ivLeftImage.setClickable(false);
        ivRightImage.setClickable(false);
        setClickable(true);

        if (type == TYPE_SELECT) {
            rightLayout.setVisibility(View.VISIBLE);
        } else {
            rightLayout.setVisibility(View.GONE);
            checkableViewStub.setVisibility(View.VISIBLE);
            switchButton = (SwitchButton) findViewById(R.id.si_switch_button);
            switchButton.setVisibility(View.VISIBLE);
        }

        divider.setVisibility(dividerVisiable ? View.VISIBLE : View.GONE);
        ViewGroup.LayoutParams layoutParams = divider.getLayoutParams();
        ((MarginLayoutParams) layoutParams).setMarginStart((int) dividerMarginStart);
        ((MarginLayoutParams) layoutParams).setMarginEnd((int) dividerMarginEnd);
        divider.setLayoutParams(layoutParams);

        tvTitle.setTextColor(titleColor);
        tvHint.setTextColor(hintColor);
        tvRightText.setTextColor(rightTextColor);
        divider.setBackgroundColor(dividerColor);

        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        tvHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintSize);
        tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);

        layoutParams = ivLeftImage.getLayoutParams();
        layoutParams.width = (int) leftImageWidth;
        layoutParams.height = (int) leftImageHeight;
        ivLeftImage.setLayoutParams(layoutParams);

        layoutParams = ivRightImage.getLayoutParams();
        layoutParams.width = (int) rightImageWidth;
        layoutParams.height = (int) rightImageHeight;
        ivRightImage.setLayoutParams(layoutParams);

        if (title != null) { //title 允许为""
            tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(hint)) {
            tvHint.setVisibility(View.VISIBLE);
            tvHint.setText(hint);
        } else {
            tvHint.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(rightText)) {
            tvRightText.setVisibility(View.VISIBLE);
            tvRightText.setText(rightText);
        } else {
            tvRightText.setVisibility(View.GONE);
        }

        if (leftImage != null) {
            llLeftImageParent.setVisibility(View.VISIBLE);
            ivLeftImage.setImageDrawable(leftImage);
        } else {
            llLeftImageParent.setVisibility(View.GONE);
        }

        if (rightImage != null) {
            ivRightImage.setVisibility(View.VISIBLE);
            ivRightImage.setImageDrawable(rightImage);
        } else {
            ivRightImage.setVisibility(View.GONE);
        }

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        //接管了子类状态变化的话，子类同步父类enable
        if (handleChildState) {
            ivLeftImage.setEnabled(enabled);
            ivRightImage.setEnabled(enabled);
            tvTitle.setEnabled(enabled);
            tvHint.setEnabled(enabled);
            tvRightText.setEnabled(enabled);
        }
    }


    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        //同步子类的state
        if (handleChildState) {
            setDrawableState(ivLeftImage.getDrawable());
            setDrawableState(ivRightImage.getDrawable());
            tvTitle.setTextColor(titleColor.getColorForState(getDrawableState(), titleColor.getDefaultColor()));
            tvHint.setTextColor(hintColor.getColorForState(getDrawableState(), hintColor.getDefaultColor()));
            tvRightText.setTextColor(rightTextColor.getColorForState(getDrawableState(), rightTextColor.getDefaultColor()));
        }
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            int[] myDrawableState = getDrawableState();
            drawable.setState(myDrawableState);
//            invalidate();
        }
    }

    /**
     * 生成仅用默认颜色的ColorStateList
     *
     * @param defaultColor
     * @return
     */
    public static ColorStateList generateDefaultColorStateList(int defaultColor) {
        int[][] states = new int[1][];
        states[0] = new int[]{};
        int color[] = new int[]{defaultColor};
        return new ColorStateList(states, color);
    }


    //--------------------getter/setter--------------------


    //1、-----------------布局和角标控件。---------------------


    public QBadgeView getTitleBadge() {
        if (titleBadge == null && tvTitle != null) {
            titleBadge = new QBadgeView(getContext());
            titleBadge.bindTarget(tvTitle)
                    .setGravityOffset(1, 1, true)
                    .setBadgePadding(4, true)
                    .setShowShadow(false); //阴影需一定外边距空间来绘制，空间不够会出现裁剪
        }
        return titleBadge;
    }

    public QBadgeView getLeftImgBadge() {
        if (leftImgBadge == null && llLeftImageParent != null) {
            leftImgBadge = new QBadgeView(getContext());
            leftImgBadge.bindTarget(llLeftImageParent)
                    .setGravityOffset(1, 1, true)
                    .setBadgePadding(4, true)
                    .setShowShadow(false);
        }
        return leftImgBadge;
    }

    public QBadgeView getRightTextBadge() {
        if (rightTextBadge == null && tvRightText != null) {
            rightTextBadge = new QBadgeView(getContext());
            rightTextBadge.bindTarget(tvRightText)
                    .setGravityOffset(1, 1, true)
                    .setBadgePadding(4, true)
                    .setShowShadow(false);
        }
        return rightTextBadge;
    }

    public QBadgeView getRightImgBadge() {
        if (rightImgBadge == null && ivRightImage != null) {
            rightImgBadge = new QBadgeView(getContext());
            rightImgBadge.bindTarget(ivRightImage)
                    .setGravityOffset(1, 1, true)
                    .setBadgePadding(4, true)
                    .setShowShadow(false);
        }
        return rightImgBadge;
    }


    public LinearLayout getRootLayout() {
        return rootLayout;
    }


    public LinearLayout getRightLayout() {
        return rightLayout;
    }


    public ImageView getIvLeftImage() {
        return ivLeftImage;
    }


    public ImageView getIvRightImage() {
        return ivRightImage;
    }


    public TextView getTvTitle() {
        return tvTitle;
    }


    public TextView getTvHint() {
        return tvHint;
    }


    public TextView getTvRightText() {
        return tvRightText;
    }


    public View getDivider() {
        return divider;
    }


    public SwitchButton getSwitchButton() {
        return switchButton;
    }


    //2、-----------------------属性----------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (title != null) { //title 允许为""
            tvTitle.setText(title);
        }
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        if (!TextUtils.isEmpty(hint)) {
            tvHint.setVisibility(View.VISIBLE);
            tvHint.setText(hint);
        } else {
            tvHint.setVisibility(View.GONE);
        }
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        if (!TextUtils.isEmpty(rightText)) {
            tvRightText.setVisibility(View.VISIBLE);
            tvRightText.setText(rightText);
        } else {
            tvRightText.setVisibility(View.GONE);
        }
    }

    public ColorStateList getTitleColor() {
        return titleColor;
    }

    /**
     * 若仅需要单一颜色值，参考{@link #generateDefaultColorStateList(int)}
     */
    public void setTitleColor(ColorStateList titleColor) {
        this.titleColor = titleColor;
        tvTitle.setTextColor(titleColor);

    }

    public ColorStateList getHintColor() {
        return hintColor;
    }

    /**
     * 若仅需要单一颜色值，参考{@link #generateDefaultColorStateList(int)}
     */
    public void setHintColor(ColorStateList hintColor) {
        this.hintColor = hintColor;
        tvHint.setTextColor(hintColor);
    }

    public ColorStateList getRightTextColor() {
        return rightTextColor;
    }

    /**
     * 若仅需要单一颜色值，参考{@link #generateDefaultColorStateList(int)}
     */
    public void setRightTextColor(ColorStateList rightTextColor) {
        this.rightTextColor = rightTextColor;
        tvRightText.setTextColor(rightTextColor);
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        divider.setBackgroundColor(dividerColor);
    }

    public float getTitleSize() {
        return titleSize;
    }

    /**
     * @param titleSize 单位px，不是sp
     */
    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
    }

    public float getHintSize() {
        return hintSize;
    }

    /**
     * @param hintSize 单位px，不是sp
     */
    public void setHintSize(float hintSize) {
        this.hintSize = hintSize;
        tvHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintSize);
    }

    public float getRightTextSize() {
        return rightTextSize;
    }

    /**
     * @param rightTextSize 单位px，不是sp
     */
    public void setRightTextSize(float rightTextSize) {
        this.rightTextSize = rightTextSize;
        tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
    }

    public Drawable getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(Drawable leftImage) {
        this.leftImage = leftImage;
        if (leftImage != null) {
            llLeftImageParent.setVisibility(View.VISIBLE);
            ivLeftImage.setImageDrawable(leftImage);
        } else {
            llLeftImageParent.setVisibility(View.GONE);
        }
    }

    public Drawable getRightImage() {
        return rightImage;
    }

    public void setRightImage(Drawable rightImage) {
        this.rightImage = rightImage;
        if (rightImage != null) {
            ivRightImage.setVisibility(View.VISIBLE);
            ivRightImage.setImageDrawable(rightImage);
        } else {
            ivRightImage.setVisibility(View.GONE);
        }
    }

    public float getLeftImageWidth() {
        return leftImageWidth;
    }


    public float getLeftImageHeight() {
        return leftImageHeight;
    }


    public float getRightImageWidth() {
        return rightImageWidth;
    }


    public float getRightImageHeight() {
        return rightImageHeight;
    }


    public int getType() {
        return type;
    }

    //type一般xml里确定后就不改了
//    public void setType(int type) {
//        this.type = type;
//    }

    public boolean isDividerVisiable() {
        return dividerVisiable;
    }

    public void setDividerVisiable(boolean dividerVisiable) {
        this.dividerVisiable = dividerVisiable;
        divider.setVisibility(dividerVisiable ? View.VISIBLE : View.GONE);
    }

    public float getDividerMarginStart() {
        return dividerMarginStart;
    }

    public float getDividerMarginEnd() {
        return dividerMarginEnd;
    }

    public void setDividerMargin(float dividerMarginStart, float dividerMarginEnd) {
        this.dividerMarginStart = dividerMarginStart;
        this.dividerMarginEnd = dividerMarginEnd;
        ViewGroup.LayoutParams layoutParams = divider.getLayoutParams();
        ((MarginLayoutParams) layoutParams).setMarginStart((int) dividerMarginStart);
        ((MarginLayoutParams) layoutParams).setMarginEnd((int) dividerMarginEnd);
        divider.setLayoutParams(layoutParams);
    }


    public boolean isHandleChildState() {
        return handleChildState;
    }

    public void setHandleChildState(boolean handleChildState) {
        this.handleChildState = handleChildState;
    }


    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

}
