package com.alfouz.tfm.tfm;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alfouz.tfm.tfm.Adapters.MathTaskOptionAdapter;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;

import java.util.List;

import katex.hourglass.in.mathlib.MathView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MathTaskDataFragment extends Fragment {

    TextInputEditText title;
    Button btnEcuation;
    Button btnOption;

    TextView tvPreviewStatement;
    MathView mathView;
    RecyclerView listAnswers;
    private MathTaskOptionAdapter mAdapter;

    private ImageButton btnRefresh;

    boolean optionsValid = false;

    private View root;
    OnClickButtonSaveData mCallback;

    public MathTaskDataFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface OnClickButtonSaveData {
        public void onButtonDataEcuationClicked(String title);
        public void onButtonDataOptionsClicked(String title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnClickButtonSaveData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnClickButtonSaveData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_fragment_data_math_task, container, false);

        title = root.findViewById(R.id.et_mathtask_description);


        btnEcuation = root.findViewById(R.id.button_mathtask_ecuation);

        btnEcuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonDataEcuationClicked(title.getText().toString());
            }
        });

        //correct keyboard from equation fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);


        Bundle arguments = getArguments();
        String titleArguments = arguments.getString("title");

        if(titleArguments!=null){
            title.setText(titleArguments);
        }

        btnOption = root.findViewById(R.id.button_mathtaskoptionlist);
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonDataOptionsClicked(title.getText().toString());
            }
        });

        tvPreviewStatement = root.findViewById(R.id.tvPreviewStatement);
        mathView = root.findViewById(R.id.mathView);
        listAnswers = root.findViewById(R.id.idListAnswers);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        listAnswers.setHasFixedSize(true);
        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listAnswers.setLayoutManager(layoutManager);

        btnRefresh = root.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MathTaskOption> list = ((MathTaskNewActivity)getActivity()).getMathTaskOptionL();
                if(list!=null) {
                    mAdapter = new MathTaskOptionAdapter(list, new MathTaskOptionAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(final MathTaskOption item) {

                        }
                    });
                    listAnswers.setAdapter(mAdapter);

                    optionsValid=false;
                    for(MathTaskOption mto: list){
                        if (mto.isCorrect()){
                            optionsValid = true;
                        }
                    }

                    if(optionsValid){
                        btnOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_green_24dp, 0);
                    }else{
                        btnOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_red_24dp, 0);
                    }
                }
            }
        });

        //corrección temporal para el tamaño (en pruebas)
        mathView.getSettings().setUseWideViewPort(true);
        mathView.getSettings().setLoadWithOverviewMode(true);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvPreviewStatement.setText(title.getText());
                ((MathTaskNewActivity)getActivity()).setTitleMathTask(title.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        List<MathTaskOption> list = ((MathTaskNewActivity)getActivity()).getMathTaskOptionL();
        String titleM = ((MathTaskNewActivity)getActivity()).getTitleMathTask();
        String ecuation = ((MathTaskNewActivity)getActivity()).getEcuationMathTask();
        if(ecuation!=null){Log.d("tst", ecuation);}else{Log.d("tst", "empty");}
        title.setText(titleM);

        tvPreviewStatement.setText(titleM);
        mathView.setDisplayText(ecuation);
        if(list!=null) {
            mAdapter = new MathTaskOptionAdapter(list, new MathTaskOptionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final MathTaskOption item) {

                }
            });
            listAnswers.setAdapter(mAdapter);

            optionsValid=false;
            for(MathTaskOption mto: list){
                if (mto.isCorrect()){
                    optionsValid = true;
                }
            }

            if(optionsValid){
                btnOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_green_24dp, 0);
            }else{
                btnOption.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_red_24dp, 0);
            }
        }

        if(ecuation!=null && (!ecuation.equals("")) &&  (!ecuation.equals("$$$$"))){
            btnEcuation.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_green_24dp, 0);
        }else{
            btnEcuation.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_remove_orange_24dp, 0);
        }
        title.requestFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
        title.clearFocus();
    }
}
