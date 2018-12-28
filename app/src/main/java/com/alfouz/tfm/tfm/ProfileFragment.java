package com.alfouz.tfm.tfm;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetImageFromUrl;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View root;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);


        String name = getActivity().getIntent().getStringExtra("personName");
        TextView accountName = root.findViewById(R.id.accountName);
        accountName.setText(name);
        final ImageView accountProfileImage;accountProfileImage = root.findViewById(R.id.accountProfileImage);

        new GetImageFromUrl(new CallbackInterface<Bitmap>() {
            @Override
            public void doCallback(Bitmap image) {
                if(image != null) {
                    Bitmap output = Bitmap.createBitmap(image.getWidth(), image
                            .getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(output);

                    final int color = 0xff424242;
                    final Paint paint = new Paint();
                    final Rect rect = new Rect(0, 0, image.getWidth(), image.getHeight());
                    final RectF rectF = new RectF(rect);
                    final float roundPx = 250;

                    paint.setAntiAlias(true);
                    canvas.drawARGB(0, 0, 0, 0);
                    paint.setColor(color);
                    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(image, rect, rect, paint);

                    accountProfileImage.setImageBitmap(output);
                }
            }
        }).execute(getActivity().getIntent().getStringExtra("personPhotoUrl"));
        /*PieChart chart = (PieChart) root.findViewById(R.id.chart);

        List<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(55, "Completado"));
        entries.add(new PieEntry(45, "Pendiente"));

        PieDataSet dataSet = new PieDataSet(entries, "Curso"); // add entries to dataset
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        dataSet.setColors(colors);
        dataSet.setValueTextColor(R.color.colorText); // styling, ...

        PieData lineData = new PieData(dataSet);
        chart.setData(lineData);
        chart.setTouchEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.getDescription().setEnabled(false);
        chart.invalidate(); // refresh*/

        return root;
    }

}
