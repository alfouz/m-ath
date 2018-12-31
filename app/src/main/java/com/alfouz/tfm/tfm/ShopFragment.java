package com.alfouz.tfm.tfm;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alfouz.tfm.tfm.Adapters.CourseBoardAdapter;
import com.alfouz.tfm.tfm.Adapters.CourseShopAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.alfouz.tfm.tfm.Util.JSONHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CourseShopAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView tvNoConnection;

    private JSONHelper jsonHelper;
    View root;

    private long idUser;

    public ShopFragment() {
        // Required empty public constructor
        jsonHelper = new JSONHelper(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_shop, container, false);
        idUser = MyApplication.getIdUser();

        progressBar = root.findViewById(R.id.progressBar);
        tvNoConnection = root.findViewById(R.id.tvNoConnection);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.list_courses_shop);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Prepare the Request
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, //GET or POST
                APIRestUtil.getCurses(), //URL
                null, //Parameters
                new Response.Listener<JSONArray>() { //Listener OK

                    @Override
                    public void onResponse(JSONArray responsePlaces) {
                        progressBar.setVisibility(View.GONE);
                        tvNoConnection.setVisibility(View.GONE);
                        //Response OK!! :)
                        List<Course> courseList=new ArrayList<>();
                        try {
                            courseList = jsonHelper.getCoursesFromJSON(responsePlaces);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(courseList!=null) {
                            mAdapter = new CourseShopAdapter(courseList, new CourseShopAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Course item) {
                                    Log.d("tst", Long.toString(item.getId()) + " " + item.getTitle());
                                }
                            });
                            mRecyclerView.setAdapter(mAdapter);
                        }

                    }
                }, new Response.ErrorListener() { //Listener ERROR

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                tvNoConnection.setVisibility(View.VISIBLE);

            }
        });

        //Send the request to the requestQueue
        requestQueue.add(request);


    }
}
