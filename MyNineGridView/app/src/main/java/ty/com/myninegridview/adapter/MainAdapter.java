package ty.com.myninegridview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

import ty.com.myninegridview.R;
import ty.com.myninegridview.widget.NineGridAdapter;
import ty.com.myninegridview.widget.NineGridlayout;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private List<List<Map<String, Object>>> datalist;

    private NineGridAdapter adapter;


    public MainAdapter(Context context, List<List<Map<String, Object>>> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final List<Map<String, Object>> itemList = datalist.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nineGridlayout = (NineGridlayout) convertView.findViewById(R.id.iv_ngrid_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (itemList.isEmpty()) {
            viewHolder.nineGridlayout.setVisibility(View.GONE);
        } else {
                viewHolder.nineGridlayout.setVisibility(View.VISIBLE);
                adapter = new NineGridAdapter(context,itemList);
                viewHolder.nineGridlayout.setAdapter(adapter);
        }

        viewHolder.nineGridlayout.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("info","--->图片张数="+itemList.size()+  " 点击的位置=="+position  +"  url==>"+itemList.toString());
            }
        });


        return convertView;
    }



    class ViewHolder {
        public NineGridlayout nineGridlayout;
    }

}
