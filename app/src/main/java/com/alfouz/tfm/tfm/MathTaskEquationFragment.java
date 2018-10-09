package com.alfouz.tfm.tfm;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Util.MathElement;

import java.util.List;

import katex.hourglass.in.mathlib.MathView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MathTaskEquationFragment extends Fragment {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;
    Button buttonpoint;
    Button buttonDelete;
    Button buttonEqual;
    Button buttonPlus;
    Button buttonMinus;
    Button buttonMultiply;
    Button buttonDiv;
    Button buttonBrackeOn;
    Button buttonBrackeOff;
    Button buttonLn;
    Button buttonLog;
    Button buttonExp;
    Button buttonSin;
    Button buttonCos;
    Button buttonTan;
    Button buttonPi;
    Button buttonE;
    Button buttonPercent;
    Button buttonSqrt;
    Button buttonFact;
    Button buttonX;
    Button buttonY;
    Button buttonZ;
    Button buttonMod;
    Button buttonCLR;

    EditText etEquation;
    ImageButton infoButton;

    MathView mathView;

    View root;


    Button nextButton;
    Button prevButton;

    OnClickButtonSaveEquation mCallback;

    //Not Completed
    //List<MathElement> equationQueue;
    String actEquation;

    int stateMod = 0;

    public MathTaskEquationFragment() {
        // Required empty public constructor
    }


    // Container Activity must implement this interface
    public interface OnClickButtonSaveEquation {
        public void onButtonEquationClicked(String ecuation);
        public void onButtonBackToDataClicked();
        public void onButtonInfoClicked(String ecuation);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnClickButtonSaveEquation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnClickButtonSaveData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_math_task_equation, container, false);

        //This line prevents keyboard hide fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        button1 = root.findViewById(R.id.button1);
        button2 = root.findViewById(R.id.button2);
        button3 = root.findViewById(R.id.button3);
        button4 = root.findViewById(R.id.button4);
        button5 = root.findViewById(R.id.button5);
        button6 = root.findViewById(R.id.button6);
        button7 = root.findViewById(R.id.button7);
        button8 = root.findViewById(R.id.button8);
        button9 = root.findViewById(R.id.button9);
        button0 = root.findViewById(R.id.button0);
        buttonpoint = root.findViewById(R.id.buttonpoint);
        buttonDelete = root.findViewById(R.id.buttonDelete);
        buttonEqual = root.findViewById(R.id.buttonEqual);
        buttonPlus = root.findViewById(R.id.buttonPlus);
        buttonMinus = root.findViewById(R.id.buttonMinus);
        buttonMultiply = root.findViewById(R.id.buttonMultiply);
        buttonDiv = root.findViewById(R.id.buttonDiv);
        buttonBrackeOn = root.findViewById(R.id.buttonBrackeOn);
        buttonBrackeOff = root.findViewById(R.id.buttonBrackeOff);
        buttonLn = root.findViewById(R.id.buttonLn);
        buttonLog = root.findViewById(R.id.buttonLog);
        buttonExp = root.findViewById(R.id.buttonExp);
        buttonSin = root.findViewById(R.id.buttonSin);
        buttonCos = root.findViewById(R.id.buttonCos);
        buttonTan = root.findViewById(R.id.buttonTan);
        buttonPi = root.findViewById(R.id.buttonPi);
        buttonE = root.findViewById(R.id.buttonE);
        buttonPercent = root.findViewById(R.id.buttonPercent);
        buttonSqrt = root.findViewById(R.id.buttonSqrt);
        buttonFact = root.findViewById(R.id.buttonFact);
        buttonX = root.findViewById(R.id.buttonX);
        buttonY = root.findViewById(R.id.buttonY);
        buttonZ = root.findViewById(R.id.buttonZ);
        buttonMod = root.findViewById(R.id.buttonMod);
        buttonCLR = root.findViewById(R.id.buttonClear);

        mathView = root.findViewById(R.id.newMathViewEquation);

        etEquation = root.findViewById(R.id.etEquation);
        infoButton = root.findViewById(R.id.infoButton);

        nextButton = root.findViewById(R.id.btnOptions);
        prevButton = root.findViewById(R.id.btnBack);

        //mathView.setInitialScale(1000);
        //Idea para reescalar pero que no funciona bien
        //mathView.getSettings().setLoadWithOverviewMode(true);
        //mathView.getSettings().setUseWideViewPort(true);

        //mathView.setInitialScale(100);

        actEquation = "";
        displayText(actEquation);

        Bundle arguments = getArguments();
        String ecuationArguments = arguments.getString("ecuation");


        if(ecuationArguments!=null){
            actEquation=ecuationArguments.replace("$", "");
            displayText(actEquation);
        }

        return root;


    }

    @Override
    public void onStart(){
        super.onStart();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="1";
                displayText(actEquation);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="2";
                displayText(actEquation);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="3";
                displayText(actEquation);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="4";
                displayText(actEquation);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="5";
                displayText(actEquation);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="6";
                displayText(actEquation);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="7";
                displayText(actEquation);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="8";
                displayText(actEquation);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="9";
                displayText(actEquation);
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="0";
                displayText(actEquation);
            }
        });

        buttonpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+=".";
                displayText(actEquation);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actEquation.length()>0) {
                    if(actEquation.length()>=3) {
                        String exceptduo = actEquation.substring(actEquation.length() - 3, actEquation.length());
                        if(exceptduo.equals("\\ln")||exceptduo.equals("\\pi")){
                            actEquation = actEquation.substring(0, actEquation.length() - 3);
                        }else {
                            if (actEquation.length() >= 4) {
                                String except = actEquation.substring(actEquation.length() - 4, actEquation.length());
                                if (except.equals("\\sin")||except.equals("\\cos")||except.equals("\\tan")||except.equals("\\log")||except.equals("\\csc")||except.equals("\\sec")||except.equals("\\cot")){
                                    actEquation = actEquation.substring(0, actEquation.length() - 4);
                                }else{
                                    if (actEquation.length() >= 5) {
                                        String exceptquatro = actEquation.substring(actEquation.length() - 5, actEquation.length());
                                        if (exceptquatro.equals("\\sqrt")||exceptquatro.equals("\\sinh")||exceptquatro.equals("\\cosh")||exceptquatro.equals("\\tanh")) {
                                            actEquation = actEquation.substring(0, actEquation.length() - 5);
                                        } else {
                                            if (actEquation.length() >= 7) {
                                                String exceptseven = actEquation.substring(actEquation.length() - 7, actEquation.length());

                                                if (exceptseven.equals("\\arcsin")||exceptseven.equals("\\arccos")||exceptseven.equals("\\arctan")) {
                                                    actEquation = actEquation.substring(0, actEquation.length() - 7);
                                                } else {
                                                    actEquation = actEquation.substring(0, actEquation.length() - 1);
                                                }
                                            }else{
                                                actEquation = actEquation.substring(0, actEquation.length() - 1);
                                            }
                                        }
                                    }else{
                                        actEquation = actEquation.substring(0, actEquation.length() - 1);
                                    }
                                }
                            } else {
                                actEquation = actEquation.substring(0, actEquation.length()-1);
                            }
                        }
                    }else{
                        actEquation = actEquation.substring(0, actEquation.length()-1);
                    }
                }
                displayText(actEquation);
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="+";
                displayText(actEquation);
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="-";
                displayText(actEquation);
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="*";
                displayText(actEquation);
            }
        });

        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="/";
                displayText(actEquation);
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="=";
                displayText(actEquation);
            }
        });

        buttonBrackeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="(";
                displayText(actEquation);
            }
        });

        buttonBrackeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+=")";
                displayText(actEquation);
            }
        });

        buttonLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="\\ln";
                displayText(actEquation);
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="\\log";
                displayText(actEquation);
            }
        });

        buttonExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="^";
                displayText(actEquation);
            }
        });

        buttonSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (stateMod){
                    case 0:
                        actEquation+="\\sin";
                        break;

                    case 1:
                        actEquation+="\\csc";
                        break;

                    case 2:
                        actEquation+="\\arcsin";
                        break;

                    case 3:
                        actEquation+="\\sinh";
                        break;
                }
                displayText(actEquation);
            }
        });

        buttonCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (stateMod){
                    case 0:
                        actEquation+="\\cos";
                        break;

                    case 1:
                        actEquation+="\\sec";
                        break;

                    case 2:
                        actEquation+="\\arccos";
                        break;

                    case 3:
                        actEquation+="\\cosh";
                        break;
                }
                displayText(actEquation);
            }
        });

        buttonTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (stateMod){
                    case 0:
                        actEquation+="\\tan";
                        break;

                    case 1:
                        actEquation+="\\cot";
                        break;

                    case 2:
                        actEquation+="\\arctan";
                        break;

                    case 3:
                        actEquation+="\\tanh";
                        break;
                }
                displayText(actEquation);
            }
        });

        buttonPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="\\pi";
                displayText(actEquation);
            }
        });

        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="e";
                displayText(actEquation);
            }
        });

        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="%";
                displayText(actEquation);
            }
        });


        buttonSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="\\sqrt";
                displayText(actEquation);
            }
        });

        buttonFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="!";
                displayText(actEquation);
            }
        });

        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="x";
                displayText(actEquation);
            }
        });

        buttonY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="y";
                displayText(actEquation);
            }
        });

        buttonZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation+="z";
                displayText(actEquation);
            }
        });

        buttonMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateMod=++stateMod%4;
                switch (stateMod){
                    case 0:
                        buttonSin.setText("sin");
                        buttonCos.setText("cos");
                        buttonTan.setText("tan");
                        break;

                    case 1:
                        buttonSin.setText("csc");
                        buttonCos.setText("sec");
                        buttonTan.setText("ctg");

                        break;

                    case 2:
                        buttonSin.setText("asin");
                        buttonCos.setText("acos");
                        buttonTan.setText("atan");

                        break;

                    case 3:
                        buttonSin.setText("sinh");
                        buttonCos.setText("cosh");
                        buttonTan.setText("tanh");

                        break;
                }
            }
        });

        buttonCLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actEquation="";
                displayText(actEquation);
            }
        });


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonInfoClicked("$$"+actEquation+"$$");
            }
        });


        etEquation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                actEquation=s.toString();
                setMathViewDisplay(s.toString());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonEquationClicked("$$"+actEquation+"$$");
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonBackToDataClicked();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        etEquation.clearFocus();
    }

    private void displayText(String display){
        setMathViewDisplay(display);

        etEquation.setText(display);
        etEquation.setSelection(display.length());
    }

    private void setMathViewDisplay(String display){
        mathView.setDisplayText("$$"+display+"$$");

    }

}
