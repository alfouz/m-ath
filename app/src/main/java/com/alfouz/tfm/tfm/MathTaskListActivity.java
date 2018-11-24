package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Adapters.MathTaskAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetMathTasksDB;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;

import java.util.ArrayList;
import java.util.List;

public class MathTaskListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MathTaskAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathtask_list);

        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        Intent intent = getIntent();
        final long idLesson = intent.getLongExtra("idLesson", -1);
        final String nameLesson = intent.getStringExtra("nameLesson");

        getSupportActionBar().setTitle(String.format(getResources().getString(R.string.edit_lesson_mathtask_list_title),nameLesson));

        mRecyclerView = (RecyclerView) findViewById(R.id.list_tasks);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        new GetMathTasksDB(new CallbackInterface<List<MathTaskEntity>>() {
            @Override
            public void doCallback(List<MathTaskEntity> listMathTaskEntity) {
                List<MathTask> mathTaskList = new ArrayList<MathTask>();
                for(MathTaskEntity m : listMathTaskEntity){
                    mathTaskList.add(new MathTask(m.getId(), -1, null, m.getEcuation(), m.getDescription(), new ArrayList<MathTaskOption>()));
                }
                mAdapter = new MathTaskAdapter(mathTaskList, new MathTaskAdapter.OnItemClickListener() {
                    @Override public void onItemClick(final MathTask item) {

                        Toast.makeText(getApplicationContext(),"Estamos trabajando en esta funcionalidad", Toast.LENGTH_SHORT);
                        /*DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Intent intent = new Intent(getApplicationContext(), LessonActivity.class);
                                        intent.putExtra("idLesson", item.getId());
                                        startActivity(intent);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage(String.format(getString(R.string.course_lesson_start_message), item.getDuration())).setPositiveButton(getString(R.string.misc_yes), dialogClickListener)
                                .setNegativeButton(getString(R.string.misc_no), dialogClickListener).show();
                        */
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
        }, this).execute(idLesson);

        FloatingActionButton fab = findViewById(R.id.id_floating_button_add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MathTaskNewActivity.class);
                intent.putExtra("idLesson", idLesson);
                intent.putExtra("nameLesson", nameLesson);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

    }
}
