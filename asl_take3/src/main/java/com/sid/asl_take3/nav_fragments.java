package com.sid.asl_take3;

/**
 * Created by Siddharth on 2/9/14.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Siddharth on 2/9/14.
 */
public class nav_fragments extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private Spinner daySpinner,classSpinner,subjectSpinner;
    private TextView period1,period2,period3,period8,period5,period6,period7,guide,guide1;
    private String day;
    private long class_index;
    private Cursor timetable_cursor;
    private Database_Helper db;
    private View simpleFragmentView;
    private WebView wv;
    private int id,sub_id,class_id;

    public void onStop(){
        super.onStop();
        saveData();
    }

    public void saveData(){
        SharedPreferences.Editor outState = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
        outState.putInt("selected_subject", sub_id);
        outState.putInt("selected_class", class_id);
        outState.commit();
    }

    public static nav_fragments newInstance(int sectionNumber) {
        nav_fragments fragment = new nav_fragments();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public nav_fragments() {
    }

    public void onStart(){
        super.onStart();

        SharedPreferences orderData = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        class_id = orderData.getInt("selected_class", 2);
        sub_id = orderData.getInt("selected_subject", 0);

        switch (id){
            case 0:
                initialize_timetable();
                break;
            case 1:
                initialize_subjects();
                break;
            case 2:
                initialize_settings();
                break;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        simpleFragmentView = inflater.inflate(R.layout.fragment_timetable, container,false);
        id=getArguments().getInt(ARG_SECTION_NUMBER);
        switch (id){
            case 0:
                simpleFragmentView = inflater.inflate(R.layout.fragment_timetable, container,false);
                return simpleFragmentView;
            case 1:
                simpleFragmentView = inflater.inflate(R.layout.fragment_subjects, container,false);
                return simpleFragmentView;
            case 2:
                simpleFragmentView = inflater.inflate(R.layout.fragment_about, container,false);
                return simpleFragmentView;
        }
        return simpleFragmentView;
    }

    public void initialize_subjects(){
        initialize_subjects_Views();
        instantiate_subjects_spinners();
        setDefaultSub();
        //showZoomHint();
    }

    private void showZoomHint(){
        Toast.makeText(getActivity(), R.string.showzoom_hint, Toast.LENGTH_LONG).show();
    }

    private void setDefaultSub(){
        subjectSpinner.setSelection(sub_id);
    }

    private void initialize_subjects_Views(){
        subjectSpinner=(Spinner)getView().findViewById(R.id.selectSubjectspinner);
        wv = (WebView) getView().findViewById(R.id.webview);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setSupportZoom(true);
     }

    private void instantiate_subjects_spinners(){
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sub_id=subjectSpinner.getSelectedItemPosition();
                displaywebpage(sub_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subjectSpinner.setSelection(sub_id);
            }
        });
    }

    public void displaywebpage(int pos){
        switch (pos){
            case 0:
                wv.loadUrl("file:///android_asset/m3.html");
                break;
            case 1:
                wv.loadUrl("file:///android_asset/cso.html");
                break;
            case 2:
                wv.loadUrl("file:///android_asset/ada.html");
                break;
            case 3:
                wv.loadUrl("file:///android_asset/oot.html");
                break;
            case 4:
                wv.loadUrl("file:///android_asset/adc.html");
                break;
            case 5:
                wv.loadUrl("file:///android_asset/net.html");
                break;
        }

    }

    public void initialize_settings(){

    }



    public void initialize_timetable(){
        initialize_timetable_Views();
        db_operation();
        instantiate_spinners();
        setGuideListener();
    }

    private void setGuideListener(){
        
        guide.setOnClickListener(new View.OnClickListener(){
            boolean expand=false;    
            int i;
            public void onClick(View v) {
                expand=!expand;
                i=expand?View.VISIBLE:View.GONE;
                guide1.setVisibility(i);
                if(expand)
                    Toast.makeText(getActivity(), R.string.showguide_hint, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initialize_timetable_Views(){
        daySpinner=(Spinner)getView().findViewById(R.id.selectdayspinner2);
        classSpinner=(Spinner)getView().findViewById(R.id.selectclassspinner);

        period1=(TextView)getView().findViewById(R.id.period1);
        period2=(TextView)getView().findViewById(R.id.period2);
        period3=(TextView)getView().findViewById(R.id.period3);
        period8=(TextView)getView().findViewById(R.id.period8);
        period5=(TextView)getView().findViewById(R.id.period5);
        period6=(TextView)getView().findViewById(R.id.period6);
        period7=(TextView)getView().findViewById(R.id.period7);

        guide=(TextView)getView().findViewById(R.id.guide);
        guide1=(TextView)getView().findViewById(R.id.guide1);
    }

    private void db_operation(){
        db = new Database_Helper(getActivity());
    }

    private void instantiate_spinners(){
        addItemSelectedListenerToSpinner();
        setDefaultClass();
        setDefaultDay();
    }

    private void setDefaultClass(){
        classSpinner.setSelection(class_id);
    }

    private void addItemSelectedListenerToSpinner(){

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                class_index=classSpinner.getSelectedItemPosition();
                class_id=(int)class_index;
                day=daySpinner.getSelectedItem().toString();
                timetable_cursor = db.getTimetableByDay(class_index,day);
                changeText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                classSpinner.setSelection(class_id);
            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                class_index=classSpinner.getSelectedItemPosition();
                day=daySpinner.getSelectedItem().toString();
                timetable_cursor = db.getTimetableByDay(class_index,day);
                changeText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    private void changeText(){
        period1.setText(timetable_cursor.getString(2));
        period2.setText(timetable_cursor.getString(3));
        period3.setText(timetable_cursor.getString(4));
        period5.setText(timetable_cursor.getString(5));
        period6.setText(timetable_cursor.getString(6));
        period7.setText(timetable_cursor.getString(7));
        period8.setText(timetable_cursor.getString(8));
    }

    private void setDefaultDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day=(day==7||day==1)?2:day;
        daySpinner.setSelection(day-2);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

