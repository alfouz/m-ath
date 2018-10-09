package com.alfouz.tfm.tfm;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import katex.hourglass.in.mathlib.MathView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MathTaskDataFragment extends Fragment {

    TextInputEditText title;

    private View root;
    OnClickButtonSaveData mCallback;

    public MathTaskDataFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface OnClickButtonSaveData {
        public void onButtonDataClicked(String title);
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

        //correct keyboard from equation fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);


        Bundle arguments = getArguments();
        String titleArguments = arguments.getString("title");

        if(titleArguments!=null){
            title.setText(titleArguments);
        }

        Button buttonOptionList = root.findViewById(R.id.button_mathtaskoptionlist);
        buttonOptionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonDataClicked(title.getText().toString());
            }
        });


        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        title.requestFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
        title.clearFocus();
    }
}
