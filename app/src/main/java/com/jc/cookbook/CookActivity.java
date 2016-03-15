package com.jc.cookbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class CookActivity extends AppCompatActivity {

    private String cookJson="[{'cookName':'麻婆豆腐'},{'cookName':'千叶豆腐'},{'cookName':'炒土豆片'},{'cookName':'佛手白菜'},{'cookName':'香辣肉丝'}," +
            "{'cookName':'红烧排骨'},{'cookName':'水煮鱼'},{'cookName':'炒三丝'},{'cookName':'韭菜炒鸡蛋'},{'cookName':'雪落火焰山'}]";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private SeekBar cook_seekbar;
    private int SEEKBAR_MIN_PROGRESS = 0;
    private int SEEKBAR_MAX_PROGRESS = 20;

    private Button cook_button;

    private String COOK_NAME = "COOK";

    private int cookRealValue;
    private int cookProgress;
    private String cookRandomJson;
    private List<Cook> cookTotalList;
    private List<Cook> cookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        // 初始化菜单列表
        cookTotalList = loadCookList(this);
        cookTotalList = Collections.unmodifiableList(cookTotalList);
        cookList = loadCookList(this);
        Log.i("data", cookList.toString());

        recyclerView = (RecyclerView)findViewById(R.id.cook_recyclerview);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // 设置分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));


        adapter = new CookAdapter(cookList);
        recyclerView.setAdapter(adapter);

        cook_button = (Button)findViewById(R.id.cook_button);
        cook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Cook> list = getRandomList(cookRealValue);
                updateCookList(list);
            }
        });


        cook_seekbar = (SeekBar)findViewById(R.id.cook_seekbar);
        cook_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("data", "" + progress);

                // 计算真实值
                int range = SEEKBAR_MAX_PROGRESS - SEEKBAR_MIN_PROGRESS;
                int realValue = SEEKBAR_MIN_PROGRESS + progress * range / 100;

                // 确保范围可用
                if (realValue < SEEKBAR_MIN_PROGRESS) {
                    realValue = SEEKBAR_MIN_PROGRESS;
                } else if (realValue > SEEKBAR_MAX_PROGRESS) {
                    realValue = SEEKBAR_MAX_PROGRESS;
                }

                cook_button.setText(String.format("生成(%s)", String.valueOf(realValue)));

                cookProgress = progress;
                cookRealValue = realValue;

                NotePad notePad = NotePad.getInstance(CookActivity.this, COOK_NAME);
                notePad.putInt("cookProgress", cookProgress);
                notePad.putInt("cookRealValue", cookRealValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        initValue();
    }

    private void updateCookList(List<Cook> list) {
        cookList.clear();
        for (Cook cook : list) {
            cookList.add(cook);
        }

        adapter.notifyDataSetChanged();
    }

    private void initValue() {
        // 初始化数值
        NotePad notePad = NotePad.getInstance(CookActivity.this, COOK_NAME);
        cookProgress = notePad.getInt("cookProgress", 0);
        cookRealValue = notePad.getInt("cookRealValue", 0);
        cook_seekbar.setProgress(cookProgress);

        // 初始化菜单
        cookRandomJson = notePad.getString("cookRandomJson", "");
        if (cookRandomJson.length()>0) {
            Gson gson = new Gson();
            List<Cook> list = gson.fromJson(cookRandomJson, new TypeToken<List<Cook>>() {}.getType());
            updateCookList(list);
        }
    }

    private List<Cook> getRandomList(int totalCount) {

        List<Cook> tmpCook = new LinkedList<>();
        List<Cook> randomCook = new ArrayList<>();
        //Collections.copy(cookTotalList, tmpCook);
        for (Cook cook : cookTotalList) {
            tmpCook.add(cook);
        }

        while (tmpCook.size()>0 && totalCount>0) {
            totalCount --;

            int pos = (int)(Math.random()*tmpCook.size()-1);
            randomCook.add(tmpCook.get(pos));
            tmpCook.remove(pos);
        }

        Gson gson = new Gson();
        cookRandomJson = gson.toJson(randomCook, new TypeToken<List<Cook>>(){}.getType());
        NotePad notePad = NotePad.getInstance(CookActivity.this, COOK_NAME);
        notePad.putString("cookRandomJson", cookRandomJson);

        return randomCook;
    }

    private List<Cook> loadCookList(Context context) {

        String cookJson = loadCookJson(context);
        List<Cook> cookList = new ArrayList<>();

        try {
            Gson gson = new Gson();
            cookList = gson.fromJson(cookJson, new TypeToken<List<Cook>>(){}.getType());
        } catch (Exception e) {
            Log.e("error", "loadCookList have a Exception", e);
        }
        return cookList;
    }

    private String loadCookJson(Context context) {

        String cookJson = "";

        try {
            Properties pro = new Properties();
            InputStream is = context.getAssets().open("cooklist.properties");
            pro.load(is);
            cookJson = pro.getProperty("cookJson");
            cookJson = this.cookJson;
        } catch (Exception e) {
            Log.e("error", "loadCookJson have a Exception", e);
        }

        return cookJson;
    }

}
