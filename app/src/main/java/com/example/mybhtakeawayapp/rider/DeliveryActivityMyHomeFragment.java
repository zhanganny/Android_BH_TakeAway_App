package com.example.mybhtakeawayapp.rider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.saler.LineChartBaseBean;
import com.example.mybhtakeawayapp.user.setUserInformation;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class DeliveryActivityMyHomeFragment extends Fragment {
    private Button riderInfoSet;
    private Button erweima;
    // private String sellerId = Local.getUserLoginId();
    private String sellerId;
    Dialog dia;
    private String localIP = "http://192.168.110.79:8081/";
    List<LineChartBaseBean> list1;
    LineChart lc1;


    public DeliveryActivityMyHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.delivery_activity_my ,container, false);
        riderInfoSet = mView.findViewById(R.id.riderInfoSet);
        riderInfoSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), setDeliveryInformation.class);
                startActivity(intent);
            }
        });

        lc1 = mView.findViewById(R.id.line1);


        erweima = mView.findViewById(R.id.delivery_erweima);
        Context context = getContext();
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
                //editText.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(sellerId, BarcodeFormat.QR_CODE,350,350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    imageView.setImageBitmap(bitmap);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    }
//                    manager.hideSoftInputFromWindow(editText.getApplicationWindowToken(),0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                dia.show();
            }
        });

        return mView;
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
        lineDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));//设置线的颜色
        lineDataSet.setLineWidth(1.5f);//设置线的宽度
        lineDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));//设置圆圈的颜色
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


    private void initData() {

        //todo
        String incomeUrl = localIP + "provider/getProfit/" + sellerId;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                        Toast.makeText(getActivity(), "加载收益数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(getActivity(), "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        list1.add(new LineChartBaseBean("周一", 3.8f));
        list1.add(new LineChartBaseBean("周二", 3.8f));
//        list1.add(new LineChartBaseBean("周三", 6.8f));
//        list1.add(new LineChartBaseBean("周四", 7.8f));
//        list1.add(new LineChartBaseBean("周五", 5.4f));
//        list1.add(new LineChartBaseBean("周六", 0f));
        list1.add(new LineChartBaseBean("周日", 6f));
    }
}
