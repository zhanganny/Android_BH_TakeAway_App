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
import com.example.mybhtakeawayapp.Local;
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

    private String localIP = Local.getLocalIp();
    LineChart lc1;
    LineChart lc2;
    List<LineChartBaseBean> list1;
    List<LineChartBaseBean> list2;
    Dialog dia;
    private TextView tv;

    public class News {
        public String good_name; // ??????
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

        // ???????????????????????????????????? todo
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
        //??????true?????????????????????????????????dialog????????????false??????????????????
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
                        //editText.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(sellerId, BarcodeFormat.QR_CODE,350,350);
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

        String providerUrl = localIP + "provider/getIncomeSum/" + sellerId;
        RequestQueue requestQueue = Volley.newRequestQueue(SalerInfo.this);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, providerUrl,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        saler_name.setText(jsonObject.getString("sellerName"));
                        saler_income.setText(String.valueOf(jsonObject.getDouble("income")));
                    } else {
                        Toast.makeText(SalerInfo.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("??????", volleyError.toString());
                Toast.makeText(SalerInfo.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
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
                        Toast.makeText(SalerInfo.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("??????", volleyError.toString());
                Toast.makeText(SalerInfo.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        mNewsList.add(new News("????????????","x1"));
//        mNewsList.add(new News("????????????","x3"));
//        mNewsList.add(new News("???????????????","x3"));
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
                        Toast.makeText(SalerInfo.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("??????", volleyError.toString());
                Toast.makeText(SalerInfo.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        list1.add(new LineChartBaseBean("??????", 3.8f));
//        list1.add(new LineChartBaseBean("??????", 3.8f));
//        list1.add(new LineChartBaseBean("??????", 6.8f));
//        list1.add(new LineChartBaseBean("??????", 7.8f));
//        list1.add(new LineChartBaseBean("??????", 5.4f));
//        list1.add(new LineChartBaseBean("??????", 0f));
//        list1.add(new LineChartBaseBean("??????", 6f));
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
                        Toast.makeText(SalerInfo.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("??????", volleyError.toString());
                Toast.makeText(SalerInfo.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        list2.add(new LineChartBaseBean("??????", 3.8f));
//        list2.add(new LineChartBaseBean("??????", 3.8f));
//        list2.add(new LineChartBaseBean("??????", 6.8f));
//        list2.add(new LineChartBaseBean("??????", 7.8f));
//        list2.add(new LineChartBaseBean("??????", 5.4f));
//        list2.add(new LineChartBaseBean("??????", 0f));
//        list2.add(new LineChartBaseBean("??????", 6f));

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
        LineDataSet lineDataSet = new LineDataSet(entries, "???????????????");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//???????????????????????????????????????????????????????????????????????????????????????
        lineDataSet.setColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//??????????????????
        lineDataSet.setLineWidth(1.5f);//??????????????????
        lineDataSet.setCircleColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//?????????????????????
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//??????????????????????????????y???
        lineDataSet.setDrawFilled(true);//????????????????????????????????????
        lineDataSet.setDrawValues(true);//?????????????????????
        lineDataSet.setValueTextSize(15f);//??????????????????????????? ??????????????????????????????
        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);//??????????????????
        lineDataSet.setFormLineWidth(10f);//??????lineDataSet.setForm(Legend.LegendForm.LINE);??????????????? ?????????????????????????????????????????????????????????
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));//?????????????????????lineDataSet.setForm(Legend.LegendForm.LINE);???????????????
        lineDataSet.setCircleRadius(4f);//??????????????????????????????
        lineDataSet.setFormSize(0);//???????????????????????????????????????
        lineDataSet.setForm(Legend.LegendForm.LINE);//????????????????????????
        LineData data = new LineData(lineDataSet);//?????????????????????
        lc1.setData(data);//??????????????????
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
        LineDataSet lineDataSet2 = new LineDataSet(entries3, "???????????????");
        lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);//???????????????????????????????????????????????????????????????????????????????????????
        lineDataSet2.setColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//??????????????????
        lineDataSet2.setLineWidth(1.5f);//??????????????????
        lineDataSet2.setCircleColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));//?????????????????????
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);//??????????????????????????????y???
        lineDataSet2.setDrawFilled(true);//????????????????????????????????????
        lineDataSet2.setDrawValues(true);//?????????????????????
        lineDataSet2.setValueTextSize(15f);//??????????????????????????? ??????????????????????????????
        lineDataSet2.enableDashedHighlightLine(10f, 5f, 0f);//??????????????????
        lineDataSet2.setFormLineWidth(10f);//??????lineDataSet.setForm(Legend.LegendForm.LINE);??????????????? ?????????????????????????????????????????????????????????
        lineDataSet2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));//?????????????????????lineDataSet.setForm(Legend.LegendForm.LINE);???????????????
        lineDataSet2.setCircleRadius(4f);//??????????????????????????????
        lineDataSet2.setFormSize(0);//???????????????????????????????????????
        lineDataSet2.setForm(Legend.LegendForm.LINE);//????????????????????????
        LineData data2 = new LineData(lineDataSet2);//?????????????????????
        lc2.setData(data2);//??????????????????
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
