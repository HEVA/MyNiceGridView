package ty.com.myninegridview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ty.com.myninegridview.adapter.MainAdapter;
import ty.com.myninegridview.R;
import ty.com.myninegridview.widget.ImageEntity;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private MainAdapter adapter;

    private List<List<ImageEntity>> imagesList;

    private String[][] images=new String[][]{
            {"http://img4.douban.com/view/photo/photo/public/p2252689992.jpg","1280","800"}
            ,{"file:///android_asset/img2.jpg","300","300"}
            ,{"http://img3.douban.com/view/photo/photo/public/p2249526036.jpg","640","960"}
            ,{"file:///android_asset/img3.jpg","250","250"}
            ,{"file:///android_asset/img4.jpg","250","250"}
            ,{"file:///android_asset/img5.jpg","250","250"}
            ,{"file:///android_asset/img6.jpg","250","250"}
            ,{"file:///android_asset/img7.jpg","250","250"}
            ,{"file:///android_asset/img8.jpg","250","250"}
            ,{"file:///android_asset/img9.jpg","250","250"}
            ,{"file:///android_asset/img1.jpg","220","123"}

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        initData();
//        adapter = new MainAdapter(this,imagesList);

        listView.setAdapter(adapter);
    }

    private void initData() {
        imagesList=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageEntity> singleList=new ArrayList<>();
        singleList.add(new ImageEntity(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));
        imagesList.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for (int i = 0; i < 11; i++) {
            ArrayList<ImageEntity> itemList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                itemList.add(new ImageEntity(images[j][0], Integer.parseInt(images[j][1]), Integer.parseInt(images[j][2])));
            }

            imagesList.add(itemList);

        }
    }

}
