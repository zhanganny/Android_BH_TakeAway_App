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
    private String sellerId = Local.getUserLoginId();
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
        LineDataSet lineDataSet = new LineDataSet(entries, "???????????????");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//???????????????????????????????????????????????????????????????????????????????????????
        lineDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));//??????????????????
        lineDataSet.setLineWidth(1.5f);//??????????????????
        lineDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));//?????????????????????
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
                        Toast.makeText(getActivity(), "????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("??????", volleyError.toString());
                Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        list1.add(new LineChartBaseBean("??????", 3.8f));
        list1.add(new LineChartBaseBean("??????", 3.8f));
//        list1.add(new LineChartBaseBean("??????", 6.8f));
//        list1.add(new LineChartBaseBean("??????", 7.8f));
//        list1.add(new LineChartBaseBean("??????", 5.4f));
//        list1.add(new LineChartBaseBean("??????", 0f));
        list1.add(new LineChartBaseBean("??????", 6f));
    }
}
