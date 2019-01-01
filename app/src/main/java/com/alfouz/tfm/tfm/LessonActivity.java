package com.alfouz.tfm.tfm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Adapters.AnswerAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CreateResultUserLessonDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseLessonsDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetLessonDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import katex.hourglass.in.mathlib.MathView;


public class LessonActivity extends AppCompatActivity {

    //private List<MathTask> mathTaskList;
    private int mathTaskActual=0;

    private Lesson actLesson;
    private long idCourse;
    private long idLesson;
    private long idUser;

    private int score = 0;

    private RecyclerView mRecyclerView;
    private AnswerAdapter mAdapter;
    private List<MathTaskOption> actualAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idCourse = getIntent().getLongExtra("idCourse", -1);
        idLesson = getIntent().getLongExtra("idLesson", -1);
        idUser = MyApplication.getIdUser();

        Log.d("tst", "iduser lesson = "+Long.toString(idUser));

        mRecyclerView = (RecyclerView) findViewById(R.id.idListAnswers);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        new GetLessonDB(new CallbackInterface<Lesson>() {
            @Override
            public void doCallback(Lesson lesson) {
                getSupportActionBar().setTitle(lesson.getTitle());
                actLesson = lesson;
                if(actLesson.getTasks().size()>mathTaskActual) {
                    createNewTask(actLesson.getTasks().get(mathTaskActual));
                }else{
                    Toast.makeText(getApplicationContext(), R.string.lesson_empty, Toast.LENGTH_SHORT);
                }
            }
        }, getApplicationContext()).execute(idLesson);

    }

    private void createNewTask(final MathTask item){
        TextView tvDescription = findViewById(R.id.tvDescriptionTask);
        MathView mathView = findViewById(R.id.mathView);
        //final RadioGroup radioGroup = findViewById(R.id.idRadioGroupAnswers);

        tvDescription.setText(item.getDescription());
        mathView.setDisplayText(item.getEcuation());


        actualAnswers = new ArrayList<>();
        for(MathTaskOption mto : item.getAnswers()){
            actualAnswers.add(new MathTaskOption(mto.getId(), mto.getText(), false, mto.isEcuation()));
        }
        mAdapter = new AnswerAdapter(actualAnswers, new AnswerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(MathTaskOption item) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);



        /*radioGroup.removeAllViews();
        for(MathTaskOption mto : item.getAnswers()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(mto.getText());

            radioGroup.addView(rb);
        }*/

        Button button = findViewById(R.id.idButtonConfirmAnswer);
        if(mathTaskActual==(actLesson.getTasks().size()-1)){
            button.setText(R.string.lesson_confirm_message);
        }else{
            button.setText(R.string.lesson_next_message);
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int totalCorrect=0;
                int selected = 0;
                for(int i =0; i<actualAnswers.size(); i++){
                    if (actualAnswers.get(i).isCorrect()) {
                        Log.d("tst", "Opcion " + actualAnswers.get(i).getText() + " está activo" );
                        selected++;
                    }else{
                        Log.d("tst", "Opcion " + actualAnswers.get(i).getText() + " está desactivado" );

                    }
                    if(actualAnswers.get(i).isCorrect()==item.getAnswers().get(i).isCorrect()){
                        totalCorrect++;
                    }
                }

                if(selected != 1){
                    Toast.makeText(getApplicationContext(), R.string.lesson_select_one_option, Toast.LENGTH_SHORT).show();
                }else {
                    if (totalCorrect >= actualAnswers.size()) {
                        final DialogAnswerLesson dialog = new DialogAnswerLesson(LessonActivity.this, true);/*, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            score++;
                            mathTaskActual++;
                            if(mathTaskActual<actLesson.getTasks().size()){
                                createNewTask(actLesson.getTasks().get(mathTaskActual));
                            }else{
                                Toast.makeText(getApplicationContext(), "¡Has terminado la prueba!", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }
                    });*/
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        Button buttontrue = dialog.getButtonNext();
                        buttontrue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                score++;
                                mathTaskActual++;
                                if (mathTaskActual < actLesson.getTasks().size()) {
                                    createNewTask(actLesson.getTasks().get(mathTaskActual));
                                } else {
                                    dialog.dismiss();
                                    final int scoreFinal = (score * 100 / actLesson.getTasks().size());
                                    new CreateResultUserLessonDB(new CallbackInterface() {
                                        @Override
                                        public void doCallback(Object object) {

                                            DialogEndLesson dialogEnd = new DialogEndLesson(LessonActivity.this, idCourse, actLesson, scoreFinal);
                                            dialogEnd.setCancelable(false);
                                            dialogEnd.setCanceledOnTouchOutside(false);
                                            dialogEnd.show();
                                        }
                                    }, getApplicationContext()).execute(idUser, actLesson.getId(), (long) scoreFinal);
                                }
                                dialog.dismiss();
                            }
                        });
                    } else {
                        final DialogAnswerLesson dialog = new DialogAnswerLesson(LessonActivity.this, false);/*, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mathTaskActual++;
                            if(mathTaskActual<actLesson.getTasks().size()){
                                createNewTask(actLesson.getTasks().get(mathTaskActual));
                            }else{
                                Toast.makeText(getApplicationContext(), "¡Has terminado la prueba!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });*/
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        Button buttonfalse = dialog.getButtonNext();
                        buttonfalse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mathTaskActual++;
                                if (mathTaskActual < actLesson.getTasks().size()) {
                                    createNewTask(actLesson.getTasks().get(mathTaskActual));
                                } else {
                                    dialog.dismiss();
                                    final int scoreFinal = (score * 100 / actLesson.getTasks().size());
                                    new CreateResultUserLessonDB(new CallbackInterface() {
                                        @Override
                                        public void doCallback(Object object) {
                                            DialogEndLesson dialogEnd = new DialogEndLesson(LessonActivity.this, idCourse, actLesson, scoreFinal);
                                            dialogEnd.setCancelable(false);
                                            dialog.setCanceledOnTouchOutside(false);
                                            dialogEnd.show();
                                        }
                                    }, getApplicationContext()).execute(idUser, actLesson.getId(), (long) scoreFinal);
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                }


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.lesson_close_message)).setPositiveButton(getString(R.string.misc_yes), dialogClickListener)
                    .setNegativeButton(getString(R.string.misc_no), dialogClickListener).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.lesson_close_message)).setPositiveButton(getString(R.string.misc_yes), dialogClickListener)
                .setNegativeButton(getString(R.string.misc_no), dialogClickListener).show();
    }



}
