package ty.com.myninegridview.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ty.com.myninegridview.adapter.MainAdapter;
import ty.com.myninegridview.R;

/**
 * Description:
 * User: guoyongping
 * Date: 2015-07-26
 */
public class MyActivity extends Activity {

    private List<List<Map<String, Object>>> datas = new ArrayList<>();
    private List<Map<String, Object>> datasitem = null;

    private String[][] images = new String[][]{{"http://img4.douban.com/view/photo/photo/public/p2252689992.jpg", "1280", "800"},
            {"img2.jpg", "300", "300"},
            {"http://img3.douban.com/view/photo/photo/public/p2249526036.jpg", "640", "960"},
            {"img3.jpg", "250", "250"},
            {"img4.jpg", "250", "250"},
            {"img5.jpg", "250", "250"},
            {"img6.jpg", "250", "250"},
            {"img7.jpg", "250", "250"},
            {"img8.jpg", "250", "250"},
            {"img9.jpg", "250", "250"},
            {"img1.jpg", "220", "123"}

    };

    private ListView listView;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setDatas();
        setupView();
    }

    private void setupView() {

        listView = (ListView) findViewById(R.id.listView);
        adapter = new MainAdapter(this, datas);
        listView.setAdapter(adapter);
    }

    private void setDatas() {
        datasitem = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("url", "http://img4.douban.com/view/photo/photo/public/p2252689992.jpg");
        map.put("width", "1280");
        map.put("height", "800");
        datasitem.add(map);
//        datas.add(datasitem);

        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<Map<String, Object>> singleList = new ArrayList<>();
        singleList.add(map);
//        datas.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for (int i = 0; i < 11; i++) {
            datasitem = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                map = new HashMap<>();
                map.put("url", images[j][0]);
                map.put("width", images[j][1]);
                map.put("height", images[j][2]);
                datasitem.add(map);
            }
            datas.add(datasitem);
        }
    }
}

