package com.example.mybhtakeawayapp.saler;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.DashPathEffect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.admin.AdministratorHomeActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class SalerInfo extends AppCompatActivity {
    private TextView saler_name;
    private TextView saler_income;
    private Button erweima;
    private Button help;
    private String sellerId = Local.getUserLoginId();

    private String localIP = "http://192.168.110.79:8081/";
    LineChart lc1;
    LineChart lc2;
    List<LineChartBaseBean> list1;
    List<LineChartBaseBean> list2;
    Dialog dia;
    private TextView tv;

    public class News {
        public String good_name; // 标题
        public String good_num;
        public News(String store_name, String good_num) {
            this.good_name = store_name;
            this.good_num = good_num;
        }
    }

    RecyclerView mRecyclerView;
    SalerInfo.MyAdapter mMyAdapter ;
    List<SalerInfo.News> mNewsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 个人信息和帮助的跳转链接 todo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_activity_my);
        lc1 = findViewById(R.id.line1);
        lc2 = findViewById(R.id.line2);
        saler_name = findViewById(R.id.saler_home_name);
        saler_income = findViewById(R.id.saler_home_income);
        erweima = findViewById(R.id.erweima);
        help = findViewById(R.id.saler_help);


        Context context = SalerInfo.this;
        dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.dialog);
        ImageView imageView = (ImageView) dia.findViewById(R.id.ivdialog);
        //选择true的话点击其他地方可以使dialog消失，为false的话不会消失
        dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = 40;
        dia.onWindowAttributesChanged(lp);
        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dia.dismiss();
                    }
                });

        erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreakIterator editText = null;
                String s = "abcdef";
                    // todo 用户名
                        //editText.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(s, BarcodeFormat.QR_CODE,350,350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    imageView.setImageBitmap(bitmap);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    }
//                    manager.hideSoftInputFromWindow(editText.getApplicationWindowToken(),0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                dia.show();
            }
        });


        // todo
        saler_name.setText("商家1");
        saler_income.setText("100");
        initData();
        initChart1();
        initChart2();
        initRecycler();

    }

    public void initRecycler() {
        mRecyclerView = findViewById(R.id.order_list);
        String predictUrl = localIP + "indent/getPredicts/" + sellerId;
        RequestQueue requestQueue = Volley.newRequestQueue(SalerInfo.this);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, predictUrl,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        JSONArray predicts = (JSONArray) jsonObject.getJSONArray("data");
                        for (int i = 0; i < predicts.length(); i++) {
                            JSONObject dish = predicts.getJSONObject(i);
                            mNewsList.add(new News(dish.getString("name"),"x" + dish.getInt("num")));
                        }
                    } else {
                        Toast.makeText(SalerInfo.this, "加载预测数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(SalerInfo.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        mNewsList.add(new News("鱼香肉丝","x1"));
//        mNewsList.add(new News("肉末茄子","x3"));
//        mNewsList.add(new News("西红柿炒蛋","x3"));
        mMyAdapter = new SalerInfo.MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SalerInfo.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initData() {

        //todo
        String incomeUrl = localIP + "provider/getProfit/" + sellerId;
        RequestQueue requestQueue = Volley.newRequestQueue(SalerInfo.this);
        JSONObject jsonObject = new JSONObject();
        list1= new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, incomeUrl,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        JSONArray incomes = (JSONArray) jsonObject.getJSONArray("data");
                        for (int i = 0; i < incomes.length(); i++) {
                            JSONObject oneDayIncome = incomes.getJSONObject(i);
                            list1.add(new LineChartBaseBean(oneDayIncome.getString("time"),(float) oneDayIncome.getDouble("money")));
                        }
                    } else {
                        Toast.makeText(SalerInfo.this, "加载收益数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(SalerInfo.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        list1.add(new LineChartBaseBean("周一", 3.8f));
//        list1.add(new LineChartBaseBean("周二", 3.8f));
//        list1.add(new LineChartBaseBean("周三", 6.8f));
//        list1.add(new LineChartBaseBean("周四", 7.8f));
//        list1.add(new LineChartBaseBean("周五", 5.4f));
//        list1.add(new LineChartBaseBean("周六", 0f));
//        list1.add(new LineChartBaseBean("周日", 6f));
        String orderNumUrl = localIP + "provider/getOrderNum/" + sellerId;
        list2= new ArrayList<>();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, orderNumUrl,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        JSONArray incomes = (JSONArray) jsonObject.getJSONArray("data");
                        for (int i = 0; i < incomes.length(); i++) {
                            JSONObject oneDayIncome = incomes.getJSONObject(i);
                            list2.add(new LineChartBaseBean(oneDayIncome.getString("time"),oneDayIncome.getInt("num")));
                        }
                    } else {
                        Toast.makeText(SalerInfo.this, "加载订单数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(SalerInfo.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        list2.add(new LineChartBaseBean("周一", 3.8f));
//        list2.add(new LineChartBaseBean("周二", 3.8f));
//        list2.add(new LineChartBaseBean("周三", 6.8f));
//        list2.add(new LineChartBaseBean("周四", 7.8f));
//        list2.add(new LineChartBaseBean("周五", 5.4f));
//        list2.add(new LineChartBaseBean("周六", 0f));
//        list2.add(new LineChartBaseBean("周日", 6f));

    }

    private void initChart1() {
        List<Float> m = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            float t = list1.get(i).getValue();
            m.add(t);
        }
        List<Entry> entries = new ArrayList<>();
        Float mMin = m.get(0);
        Float mMax = m.get(0);
        for (int i = 0; i < m.size(); i++) {
            if (mMin > m.get(i)) {
                mMin = m.get(i);
            }
            if (mMax < m.get(i)) {
                mMax = m.get(i);
            }
            entries.add(new Entry(i, m.get(i)));
        }
        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            float mult = 10;
            float val = (float) (Math.random() * mult);
            entries2.add(new Entry(i, val));
        }
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int v = (int) value;
                if (v <= list1.size() && v >= 0) {
                    String st = list1.get(v).getKey();
                    String tim1 = "";
                    tim1 = st;
                    return tim1;
                } else {
                    return null;
                }
            }
        };
        LineDataSet lineDataSet = new LineDataSet(entries, "本周营业额");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置折线图的显示模式，可以自行设置上面的值进行查看不同之处
        lineDataSet.setColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//设置线的颜色
        lineDataSet.setLineWidth(1.5f);//设置线的宽度
        lineDataSet.setCircleColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//设置圆圈的颜色
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于左侧y轴
        lineDataSet.setDrawFilled(true);//设置不画数据覆盖的阴影层
        lineDataSet.setDrawValues(true);//不绘制线的数据
        lineDataSet.setValueTextSize(15f);//如果不绘制线的数据 这句代码也不用设置了
        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);//没看出来效果
        lineDataSet.setFormLineWidth(10f);//只有lineDataSet.setForm(Legend.LegendForm.LINE);时才有作用 这里我们设置的是圆所以这句代码直接注释
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));//设置虚线，只有lineDataSet.setForm(Legend.LegendForm.LINE);时才有作用
        lineDataSet.setCircleRadius(4f);//设置每个折线点的大小
        lineDataSet.setFormSize(0);//设置当前这条线的图例的大小
        lineDataSet.setForm(Legend.LegendForm.LINE);//设置图例显示为线
        LineData data = new LineData(lineDataSet);//创建图表数据源
        lc1.setData(data);//设置图表数据
    }

    public void initChart2() {
        List<Float> m2 = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            float t = list2.get(i).getValue();
            m2.add(t);
        }
        List<Entry> entries3 = new ArrayList<>();
        Float mMin2 = m2.get(0);
        Float mMax2 = m2.get(0);
        for (int i = 0; i < m2.size(); i++) {
            if (mMin2 > m2.get(i)) {
                mMin2 = m2.get(i);
            }
            if (mMax2 < m2.get(i)) {
                mMax2 = m2.get(i);
            }
            entries3.add(new Entry(i, m2.get(i)));
        }
        List<Entry> entries4 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            float mult = 10;
            float val = (float) (Math.random() * mult);
            entries4.add(new Entry(i, val));
        }
        IAxisValueFormatter formatter2 = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int v = (int) value;
                if (v <= list2.size() && v >= 0) {
                    String st = list2.get(v).getKey();
                    String tim1 = "";
                    tim1 = st;
                    return tim1;
                } else {
                    return null;
                }
            }
        };
        LineDataSet lineDataSet2 = new LineDataSet(entries3, "本周营业额");
        lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置折线图的显示模式，可以自行设置上面的值进行查看不同之处
        lineDataSet2.setColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//设置线的颜色
        lineDataSet2.setLineWidth(1.5f);//设置线的宽度
        lineDataSet2.setCircleColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//设置圆圈的颜色
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于左侧y轴
        lineDataSet2.setDrawFilled(true);//设置不画数据覆盖的阴影层
        lineDataSet2.setDrawValues(true);//不绘制线的数据
        lineDataSet2.setValueTextSize(15f);//如果不绘制线的数据 这句代码也不用设置了
        lineDataSet2.enableDashedHighlightLine(10f, 5f, 0f);//没看出来效果
        lineDataSet2.setFormLineWidth(10f);//只有lineDataSet.setForm(Legend.LegendForm.LINE);时才有作用 这里我们设置的是圆所以这句代码直接注释
        lineDataSet2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));//设置虚线，只有lineDataSet.setForm(Legend.LegendForm.LINE);时才有作用
        lineDataSet2.setCircleRadius(4f);//设置每个折线点的大小
        lineDataSet2.setFormSize(0);//设置当前这条线的图例的大小
        lineDataSet2.setForm(Legend.LegendForm.LINE);//设置图例显示为线
        LineData data2 = new LineData(lineDataSet2);//创建图表数据源
        lc2.setData(data2);//设置图表数据
    }

    class MyAdapter extends RecyclerView.Adapter<SalerInfo.MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(SalerInfo.this, R.layout.good_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }


        @Override
        public void onBindViewHolder(@NonNull SalerInfo.MyViewHoder holder, int position) {
            SalerInfo.News news = mNewsList.get(position);
            // todo
            holder.goodName.setText(news.good_name);
            holder.goodNum.setText(news.good_num);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView goodName;
        TextView goodNum;
        // todo
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            goodName = itemView.findViewById(R.id.payment_good_ame);
            goodNum = itemView.findViewById(R.id.payment_good_number);
        }
    }
}
