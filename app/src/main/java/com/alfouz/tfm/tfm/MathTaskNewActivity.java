package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Adapters.MathTaskOptionAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CreateMathTaskDB;
import com.alfouz.tfm.tfm.AsyncTasks.CreateMathTaskOptionsDB;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MathTaskNewActivity extends AppCompatActivity implements MathTaskDataFragment.OnClickButtonSaveData, MathTaskOptionListFragment.OnClickButtonSaveOptionsData, MathTaskEquationFragment.OnClickButtonSaveEquation, MathTaskEquationInfoFragment.OnClickButtonInfoEquation {



    private Fragment actualFragment;

    private String titleMathTask;
    private String ecuationMathTask;

    private List<MathTaskOption> mathTaskOptionL;

    long idLesson;
    String nameLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_math_task);


        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.new_mathtask_title));

        Intent intent = getIntent();
        idLesson = intent.getLongExtra("idLesson", -1);
        nameLesson = intent.getStringExtra("nameLesson");

        loadFragment(new MathTaskDataFragment(), null, null);

        //loadFragment(new MathTaskEquationFragment(), null, null);




        /*new GetMathTaskOptionByMathTaskDB(new CallbackInterface<List<MathTaskOptionEntity>>() {
            @Override
            public void doCallback(List<MathTaskOptionEntity> listMathTaskOptionentity) {
                List<MathTaskOption> lessonList = new ArrayList<MathTaskOption>();
                for(MathTaskOptionEntity l : listMathTaskOptionentity){
                    lessonList.add(new MathTaskOption(l.getId(), l.getText(), l.isCorrect()));
                }
                mAdapter = new MathTaskOptionAdapter(lessonList, new MathTaskOptionAdapter.OnItemClickListener() {
                    @Override public void onItemClick(final MathTaskOption item) {

                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
        }, this).execute(idLesson);*/
    }

    // create an action bar button
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_mathtask, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mathtask_menu_save) {
            TextInputEditText description = findViewById(R.id.et_mathtask_description);
            TextInputEditText ecuation = findViewById(R.id.et_mathtask_ecuation);


            Intent intent = getIntent();
            final long idLesson = intent.getLongExtra("idLesson", -1);



        }
        return super.onOptionsItemSelected(item);
    }*/

    private boolean loadFragment(Fragment fragment, String title, String ecuation) {
        //switching fragment
        if (fragment != null) {
            Bundle arguments = new Bundle();
            if (title!=null){arguments.putString( "title" , title);}
            if (ecuation!=null){arguments.putString("ecuation", ecuation);}
            fragment.setArguments(arguments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.new_mathtask_fragment_layout, fragment)
                    .commit();
            actualFragment = fragment;
            return true;
        }
        return false;
    }

    //Data fragment buttons

    @Override
    public void onButtonDataClicked(String title) {
        if(!title.equals("")) {
            titleMathTask = title;
            loadFragment(new MathTaskEquationFragment(), titleMathTask, null);
        }else{
            Toast.makeText(this, R.string.request_insert_data, Toast.LENGTH_SHORT).show();
        }
    }

    //Equation fragment buttons

    @Override
    public void onButtonEquationClicked(String ecuation) {
        ecuationMathTask=ecuation;
        loadFragment(new MathTaskOptionListFragment(), titleMathTask, ecuationMathTask);
    }


    @Override
    public void onButtonBackToDataClicked() {
        loadFragment(new MathTaskDataFragment(), titleMathTask, ecuationMathTask);
    }

    @Override
    public void onButtonInfoClicked(String ecuation) {
        ecuationMathTask = ecuation;
        loadFragment(new MathTaskEquationInfoFragment(), titleMathTask, ecuationMathTask);
    }


    //Options fragment buttons

    @Override
    public void onButtonOptionsClicked(final List<MathTaskOption> listOption) {
        new CreateMathTaskDB(new CallbackInterface<MathTaskEntity>() {
            @Override
            public void doCallback(MathTaskEntity mathTaskEntity) {
                List<MathTaskOptionEntity> mtoeList = new ArrayList<>();
                for(MathTaskOption mto : listOption){
                    MathTaskOptionEntity mtoE = new MathTaskOptionEntity();
                    mtoE.setText(mto.getText());
                    mtoE.setCorrect(mto.isCorrect());
                    mtoE.setMathTask(mathTaskEntity.getId());

                    mtoeList.add(mtoE);
                }

                MathTaskOptionEntity[] mtoeArray = new MathTaskOptionEntity[mtoeList.size()];
                mtoeList.toArray(mtoeArray);
                new CreateMathTaskOptionsDB(new CallbackInterface() {
                    @Override
                    public void doCallback(Object object) {
                        Intent intent = new Intent(getApplicationContext(), MathTaskListActivity.class);
                        intent.putExtra("idLesson", idLesson);
                        intent.putExtra("nameLesson", nameLesson);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                },getApplicationContext()).execute(mtoeArray);
            }
        },this).execute(Long.toString(idLesson), titleMathTask, ecuationMathTask);
    }

    @Override
    public void onButtonBackClicked() {
        loadFragment(new MathTaskEquationFragment(), titleMathTask, ecuationMathTask);
    }


    //Info Equation Fragment
    @Override
    public void onButtonBackInfoClicked() {
        loadFragment(new MathTaskEquationFragment(), titleMathTask, ecuationMathTask);
    }
}
