package ty.com.myninegridview.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: guoyongping
 * Date: 2015-07-22
 */
public class NineGridAdapter {

    private List list = null;
    private Context context;

    public NineGridAdapter(Context context, List datas) {
        this.list = datas;
        this.context = context;
    }

    public int getCount() {
        return (list == null) ? 0 : list.size();
    }

    public String getUrl(int position) {
        return getItem(position) == null ? null : (String) ((Map<String, Object>) getItem(position)).get("url");
    }

    public Object getItem(int position) {
        return (list == null) ? null : list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int i, View view) {
        CustomImageViewTwo customImageViewTwo = null;
        if (view != null && view instanceof CustomImageViewTwo) {
            customImageViewTwo = (CustomImageViewTwo) view;
        } else {
            customImageViewTwo = new CustomImageViewTwo(context,list);
        }
        customImageViewTwo.setBackgroundColor(context.getResources().getColor((android.R.color.transparent)));
        String url = null;
        if (list.size() == 1 || list.size() > 9) {
            Map<String, Object> img = (Map<String, Object>) list.get(0);
            url = (String) img.get("url");
            customImageViewTwo.addView();
        } else {
            url = getUrl(i);
            customImageViewTwo.setImageUrl(url);
        }
        if (!TextUtils.isEmpty(url)) {
            customImageViewTwo.setTag(url);
        }
        return customImageViewTwo;
    }
}
