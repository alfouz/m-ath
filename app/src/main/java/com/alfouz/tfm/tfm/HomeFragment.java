package com.alfouz.tfm.tfm;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View root;

    private long idUser;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);
        idUser = MyApplication.getIdUser();

        //Warning
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((TextView) root.findViewById(R.id.textView)).setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        return root;
    }

}
