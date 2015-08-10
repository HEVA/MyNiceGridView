package ty.com.myninegridview.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ty.com.myninegridview.R;

/**
 * Created by Lenny on 2015/8/4.
 */
public class CustomImageViewTwo extends ViewGroup {

    private Context context;

    private List list;

    public CustomImageViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomImageViewTwo(Context context, List list) {
        super(context);
        this.context = context;
        this.list = list;
    }


    /**
     * 测量子控件的长宽
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    /**
     * 代码动态添加子控件
     */
    public void addView() {
        RelativeLayout rl = new RelativeLayout(context);
        RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ImageView iv = new ImageView(context);

        iv.setBackgroundResource(R.mipmap.img7);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        iv.setLayoutParams(params);
        iv.setId(R.id.my_imageview_id);

        RelativeLayout.LayoutParams params_two = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params_two.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params_two.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        TextView tv_two = new TextView(context);
        tv_two.setText("共" + list.size() + "条");

        tv_two.setBackgroundColor(Color.rgb(185, 70, 70));
        tv_two.setLayoutParams(params_two);
        tv_two.setId(R.id.my_textview_id);

        rl.setLayoutParams(rlParam);
        rl.addView(iv);
        rl.addView(tv_two);
        addView(rl);
    }

    /**
     * 重写父类的方法  计算每个子控件添加的位置
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int mTotalHeight = 0;
        int mTotalWidth = 0;
        // 遍历所有子视图
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();
            childView.layout(mTotalWidth, mTotalHeight, mTotalWidth + measuredWidth, mTotalHeight + measureHeight);
            mTotalHeight += measureHeight;
            mTotalWidth += measuredWidth;
        }
    }

    /**
     * 处理图片数量少于九张的情况
     * @param rul  图片的路径
     */
    public void setImageUrl(String rul) {
        addView();
        findViewById(R.id.my_textview_id).setVisibility(View.GONE);
        findViewById(R.id.my_imageview_id).setBackgroundResource(R.mipmap.img0000);
    }



    public void setImageUrlSizeNine
}
