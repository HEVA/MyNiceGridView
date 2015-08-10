package ty.com.myninegridview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import ty.com.myninegridview.util.DensityUtils;

/**
 * decs:
 *
 * @author:guoyongping
 * @date:2015-07-20
 */
public class NineGridlayout extends ViewGroup {

    private NineGridAdapter adapter;
    private OnItemClickListerner onItemClickListerner;
    /**
     * 默认图片间隔
     */
    private final int ITEM_GAP = 3;
    private final int DEFAULT_WIDTH = 200;
    /**
     * 图片之间的间隔
     */
    private int gap;
    private int columns;//
    private int rows;//
    private int totalWidth;
    private Context context;
    int singleWidth = 0, singleHeight = 0;
    private int defaultWidth, defaultHeight;//单张图片时默认的宽度

    private int oldCount;

    public NineGridlayout(Context context) {
        this(context, null);
    }

    public NineGridlayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        gap = dip2px(context, ITEM_GAP);
        defaultWidth = defaultHeight = dip2px(context, DEFAULT_WIDTH);
    }

    /**
     * 设置单张图片时的宽度
     *
     * @param defaultWidth
     */
    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    /**
     * 设置单张图片时的高度
     *
     * @param defaultHeight
     */
    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public void setAdapter(NineGridAdapter adapter) {
        this.adapter = adapter;
        if (adapter == null) {
            return;
        }
        //初始化布局形状
        generateChildrenLayout(adapter.getCount());
        removeAllViews();

        for (int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null);
            addView(itemView, generateDefaultLayoutParams());
        }
        oldCount = adapter.getCount();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        totalWidth = sizeWidth - getPaddingLeft() - getPaddingRight();
        if (adapter != null && adapter.getCount() > 0) {
            int measureWidth, measureHeight;
            int childrenCount = adapter.getCount();
            if (childrenCount == 1 || childrenCount > 9) {

                Map<String, Object> imageEntity = (Map<String, Object>) adapter.getItem(0);
                int imageWidth = DensityUtils.dp2px(context, Integer.parseInt((String) imageEntity.get("width")));
                int imageHeight = DensityUtils.dp2px(context, Integer.parseInt((String) imageEntity.get("height")));
                int w = Integer.parseInt((String) imageEntity.get("width"));
                int h = Integer.parseInt((String) imageEntity.get("height"));
                if (w < h) {
                    if (imageHeight > totalWidth) {
                        imageHeight = totalWidth;
                        imageWidth = (imageHeight * w) / h;
                    }
                } else {
                    if (imageWidth > totalWidth) {
                        imageWidth = totalWidth;
                        imageHeight = (imageWidth * h) / w;
                    }
                }

                singleWidth = imageWidth;
                singleHeight = imageHeight;

            } else {
                singleWidth = (totalWidth - gap * (3 - 1)) / 3;
                singleHeight = singleWidth;
            }
            measureChildren(MeasureSpec.makeMeasureSpec(singleWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(singleHeight, MeasureSpec.EXACTLY));
            measureWidth = singleWidth * columns + gap * (columns - 1);
            measureHeight = singleHeight * rows + gap * (rows - 1);
            setMeasuredDimension(sizeWidth, measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        layoutChildrenView();
    }

    private void layoutChildrenView() {
        if (adapter == null || adapter.getCount() == 0) {
            return;
        }
        int childrenCount = adapter.getCount();
        for (int i = 0; i < childrenCount; i++) {
            int[] position = findPosition(i);
            int left = (singleWidth + gap) * position[1] + getPaddingLeft();
            int top = (singleHeight + gap) * position[0] + getPaddingTop();
            int right = left + singleWidth;
            int bottom = top + singleHeight;
            CustomImageViewTwo childrenView = (CustomImageViewTwo) getChildAt(i);
            if (childrenCount == 1 || childrenCount > 9) {
                //只有一张图片
//                childrenView.setScaleType(CustomImageView.ScaleType.FIT_CENTER);
            } else {
//                childrenView.setScaleType(CustomImageView.ScaleType.CENTER_CROP);
            }

            final int itemPosition = i;
            childrenView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListerner != null) {
                        onItemClickListerner.onItemClick(v, itemPosition);
                    }
                }
            });
            childrenView.layout(left, top, right, bottom);
        }
    }


    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i * columns + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    /**
     * 根据图片个数确定行列数量
     * 对应关系如下
     * num	row	column
     * 1	   1	1
     * 2	   1	2
     * 3	   1	3
     * 4	   2	2
     * 5	   2	3
     * 6	   2	3
     * 7	   3	3
     * 8	   3	3
     * 9	   3	3
     * 大于9    1    1
     *
     * @param length
     */
    private void generateChildrenLayout(int length) {
        if (length <= 9) {
            if (length <= 3) {
                rows = 1;
                columns = length;
            } else if (length <= 6) {
                rows = 2;
                columns = 3;
                if (length == 4) {
                    columns = 2;
                }
            } else {
                rows = 3;
                columns = 3;
            }
        } else {
            rows = 1;
            columns = 1;
        }

    }

    /**
     * dp to px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnItemClickListerner(OnItemClickListerner onItemClickListerner) {
        this.onItemClickListerner = onItemClickListerner;
    }

    public interface OnItemClickListerner {
        public void onItemClick(View view, int position);
    }
}
