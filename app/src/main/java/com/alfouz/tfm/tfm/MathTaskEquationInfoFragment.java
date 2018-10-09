package com.alfouz.tfm.tfm;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MathTaskEquationInfoFragment extends Fragment {

    OnClickButtonInfoEquation mCallback;

    TextView tvInfo;

    Button btnBack;

    View root;

    public MathTaskEquationInfoFragment() {
        // Required empty public constructor
    }

    // Container Activity must implement this interface
    public interface OnClickButtonInfoEquation {
        public void onButtonBackInfoClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnClickButtonInfoEquation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnClickButtonSaveData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_math_task_equation_info, container, false);

        tvInfo = root.findViewById(R.id.tvInfo);
        btnBack = root.findViewById(R.id.buttonBack);

        //Warning
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvInfo.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonBackInfoClicked();
            }
        });

        return root;
    }


}
