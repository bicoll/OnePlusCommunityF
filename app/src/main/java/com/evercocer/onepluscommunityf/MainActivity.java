package com.evercocer.onepluscommunityf;


import android.app.Activity;
import android.os.Bundle;

import com.evercocer.onepluscommunityf.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends Activity {
    private static final String URL = "https://api.oneplusbbs.com/api/mobile/index.php?version=6&page=2&module=recommendnew&sign=77fb64602cf15889f6b4c18918fbfe2e2fc182a9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpUtil.getInstance()
                .get(URL, new OkHttpUtil.CallBack() {
                    @Override
                    public void onSuccess(Call call, Response response) throws IOException {
                        parseData(response.body().string());
                    }

                    @Override
                    public void onFailed(IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void parseData(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject data = jsonObject.getJSONObject("data");
            String perpage = data.getString("perpage");
            System.out.println("总数：" + perpage);
            JSONArray threadList = data.getJSONArray("threadList");
            for (int i = 0; i < threadList.length(); i++) {
                JSONObject item = threadList.getJSONObject(i);
                String title = item.getString("title");
                String author = item.getString("author");
                String avatar = item.getString("avatar");
                String image = item.getString("image");
                String lastpost = item.getString("lastpost");
                String replace = lastpost.replace("&nbsp;", " ");
                String replies = item.getString("replies");
                String views = item.getString("views");
                System.out.println("标题：" + title);
                System.out.println("作者：" + author);
                System.out.println("avatar：" + avatar);
                System.out.println("image：" + image);
                System.out.println("最新回复：" + replace);
                System.out.println("查看数：" + views);
                System.out.println("回复数：" + replies);
                System.out.println();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}