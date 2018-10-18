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
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseLessonsDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetLessonDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

import java.util.ArrayList;
import java.util.List;

import katex.hourglass.in.mathlib.MathView;


public class LessonActivity extends AppCompatActivity {

    //private List<MathTask> mathTaskList;
    private int mathTaskActual=0;

    private Lesson actLesson;
    private long idCourse;
    private long idLesson;

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
        /*mathTaskList = new ArrayList<MathTask>();
        List<MathTaskOption> mathTaskOptionList = new ArrayList<MathTaskOption>();
        mathTaskOptionList.add(new MathTaskOption("1", true));
        mathTaskOptionList.add(new MathTaskOption("2", false));
        mathTaskOptionList.add(new MathTaskOption("3", false));
        mathTaskList.add(new MathTask(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true), "Introducción", "Breve introducción al álgebra", null, 3),null, "$$x^2 + x - 2 = 0$$", "Calcula x en la siguiente ecuación", mathTaskOptionList));

        List<MathTaskOption> mathTaskOptionList2 = new ArrayList<MathTaskOption>();
        mathTaskOptionList2.add(new MathTaskOption("4", false));
        mathTaskOptionList2.add(new MathTaskOption("5", false));
        mathTaskOptionList2.add(new MathTaskOption("3", true));
        mathTaskList.add(new MathTask(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true), "Introducción", "Breve introducción al álgebra", null, 3), null, "$$x^2 + x - 12 = 0$$", "Calcula x en la siguiente ecuación", mathTaskOptionList2));

        List<MathTaskOption> mathTaskOptionList3 = new ArrayList<MathTaskOption>();
        mathTaskOptionList3.add(new MathTaskOption("4", true));
        mathTaskOptionList3.add(new MathTaskOption("5", false));
        mathTaskOptionList3.add(new MathTaskOption("2", false));
        mathTaskList.add(new MathTask(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true), "Introducción", "Breve introducción al álgebra", null, 3),null, "$$x^2 - x = 12$$", "Calcula x en la siguiente ecuación", mathTaskOptionList3));
*/



    }

    private void createNewTask(final MathTask item){
        TextView tvDescription = findViewById(R.id.tvDescriptionTask);
        MathView mathView = findViewById(R.id.mathView);
        //final RadioGroup radioGroup = findViewById(R.id.idRadioGroupAnswers);

        tvDescription.setText(item.getDescription());
        mathView.setDisplayText(item.getEcuation());


        actualAnswers = new ArrayList<>();
        for(MathTaskOption mto : item.getAnswers()){
            actualAnswers.add(new MathTaskOption(mto.getId(), mto.getText(), false));
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

                //int selectedId = radioGroup.getCheckedRadioButtonId();
                //RadioButton rb = findViewById(selectedId);
                //MathTaskOption mtoCorrect = new MathTaskOption("NaN", false);
                /*for(MathTaskOption mto : item.getAnswers()){
                    if(mto.isCorrect()){
                        mtoCorrect=mto;
                    }
                }*/
                /*if(rb.getText().equals(mtoCorrect.getText())){
                    Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "¡Error, sigue practicando!", Toast.LENGTH_SHORT).show();
                }*/

                int totalCorrect=0;
                for(int i =0; i<actualAnswers.size(); i++){
                    if(actualAnswers.get(i).isCorrect()==item.getAnswers().get(i).isCorrect()){
                        totalCorrect++;
                    }
                }

                if(totalCorrect>=actualAnswers.size()){
                    Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "¡Error, sigue practicando!", Toast.LENGTH_SHORT).show();
                }

                mathTaskActual++;
                if(mathTaskActual<actLesson.getTasks().size()){
                    createNewTask(actLesson.getTasks().get(mathTaskActual));
                }else{
                    Toast.makeText(getApplicationContext(), "¡Has terminado la prueba!", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                    intent.putExtra("idCourse", idCourse);
                    startActivity(intent);*/
                    finish();
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
                            onBackPressed();
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

}
