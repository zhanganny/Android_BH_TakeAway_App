package com.example.mybhtakeawayapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class addOderItem extends Activity {
    private LinearLayout comment_item_view;
    private TextView order_name;
    private TextView order_time;
    private TextView order_statement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_item);
        comment_item_view=findViewById(R.id.comment_item_view);
        order_name=findViewById(R.id.order_name);
        order_time=findViewById(R.id.order_time);
        order_statement=findViewById(R.id.order_statement);

    }

//    private void Add(String name,String img,String author,String info,String lianjie){
//        JSONObject jsonObject=new JSONObject();
//        String url="http://192.168.1.103:8085/item/insertItem?book_name="+name+"&book_img="+img+"&book_author="+author+"&book_info="+info+"&book_download="+lianjie+"";
//        RequestQueue requestQueue= Volley.newRequestQueue(AddActivity.this);
//        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    String info1 = jsonObject.getString("info");
//                    if(info1.equals("添加成功")){
//                        Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("错误", volleyError.toString());
//                Toast.makeText(AddActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//
//    }
}
