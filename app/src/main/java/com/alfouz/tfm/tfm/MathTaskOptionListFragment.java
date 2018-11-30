package com.alfouz.tfm.tfm;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Adapters.MathTaskOptionAdapter;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;

import java.util.ArrayList;
import java.util.List;

import katex.hourglass.in.mathlib.MathView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MathTaskOptionListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MathTaskOptionAdapter mAdapter;
    private List<MathTaskOption> mathTaskOptionList;

    private CheckBox isFunction;
    private ImageButton info;

    OnClickButtonSaveOptionsData mCallback;

    View root;

    public MathTaskOptionListFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface OnClickButtonSaveOptionsData {
        public void onButtonOptionsClicked(List<MathTaskOption> listOption);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (MathTaskOptionListFragment.OnClickButtonSaveOptionsData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnClickButtonSaveData");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_fragment_list_math_task_option, container, false);

        //correct keyboard from equation fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);

        mathTaskOptionList = new ArrayList<MathTaskOption>();
        List<MathTaskOption> mtoL = ((MathTaskNewActivity) getActivity()).getMathTaskOptionL();
        if(mtoL!=null) {
            mathTaskOptionList = ((MathTaskNewActivity) getActivity()).getMathTaskOptionL();
        }
        ((TextInputEditText) root.findViewById(R.id.et_text_option)).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isFunction.isChecked()) {
                    ((MathView) root.findViewById(R.id.createMathTaskOptionText)).setDisplayText("$$"+s.toString()+"$$");
                }else{
                    ((MathView) root.findViewById(R.id.createMathTaskOptionText)).setDisplayText(s.toString());
                }
            }
        });

        Button buttonAddOption = root.findViewById(R.id.addNewOptionButton);
        buttonAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MathTaskOption mto;
                if(isFunction.isChecked()) {
                    mto = new MathTaskOption("$$" + ((TextInputEditText) root.findViewById(R.id.et_text_option)).getText().toString() + "$$", false);
                }else{
                    mto = new MathTaskOption(((TextInputEditText) root.findViewById(R.id.et_text_option)).getText().toString(), false);
                }
                mathTaskOptionList.add(mto);
                mAdapter.notifyDataSetChanged();
                ((TextInputEditText) root.findViewById(R.id.et_text_option)).setText("");
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.list_mathtaskoptions);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);


        mAdapter = new MathTaskOptionAdapter(mathTaskOptionList, new MathTaskOptionAdapter.OnItemClickListener() {
            @Override public void onItemClick(final MathTaskOption item) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);


        Button btn_save = root.findViewById(R.id.btn_save_options);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int correct = 0;
                for(MathTaskOption mto : mathTaskOptionList){
                    if(mto.isCorrect()){
                        correct++;
                    }
                }
                if(correct>0) {*/
                mCallback.onButtonOptionsClicked(mathTaskOptionList);
                /*}else{
                    Toast.makeText(getContext(), getText(R.string.new_mathtaskoption_select_at_least_one_correct),Toast.LENGTH_SHORT).show();
                }*/
            }
        });


        info = root.findViewById(R.id.infoButton);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle(getString(R.string.new_mathtask_equation));
                alertDialog.setMessage(getString(R.string.new_mathtask_info_options));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.misc_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                final Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
                neutralButton.setLayoutParams(positiveButtonLL);
            }
        });

        isFunction = root.findViewById(R.id.isEcuation);

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        ((TextInputEditText) root.findViewById(R.id.et_text_option)).clearFocus();
    }
}
