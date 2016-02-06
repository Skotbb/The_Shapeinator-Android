package com.example.scott.theshapinator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView m_lengthTextView, m_widthTextView, m_areaDisplayTextView, m_perimeterDisplayTextView,
        m_perimLabelTV, m_areaLabelTV;
    EditText m_widthEditText, m_lengthEditText;
    Button m_calcButton;
    Spinner m_shapeDrop;

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedValues = getSharedPreferences("SavedValues",MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        m_widthEditText = (EditText) findViewById(R.id.widthEditText);
        m_lengthEditText = (EditText) findViewById(R.id.lengthEditText);

        m_lengthTextView = (TextView) findViewById(R.id.heightTextView);
        m_widthTextView = (TextView) findViewById(R.id.widthTextView);
        m_areaDisplayTextView = (TextView) findViewById(R.id.areaDisplayTextView);
        m_perimeterDisplayTextView = (TextView) findViewById(R.id.perimeterDisplayTextView);
        m_areaLabelTV = (TextView) findViewById(R.id.areaLabelTextView);
        m_perimLabelTV = (TextView) findViewById(R.id.perimeterLabelTextView);

        m_calcButton = (Button) findViewById(R.id.calcButton);

        m_shapeDrop = (Spinner) findViewById(R.id.shapeSpinner);

        setupSpinner();
    }
    public void calcButton(View v){
       int pos = m_shapeDrop.getSelectedItemPosition();
        switch(pos){
            case 0 : {setSquare();calcSquare();}
                break;
            case 1 : {setCircle();calcCircle();}
                break;
            case 2 : {setTriangle();calcTriangle();}
                break;
        }
    }

    public void setupSpinner(){

        m_shapeDrop.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.shapes, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        m_shapeDrop.setAdapter(dataAdapter);
    }

    protected void setSquare(){
        m_lengthTextView.setText(R.string.lengthText);
        m_widthTextView.setVisibility(View.VISIBLE);
        m_widthTextView.setText(R.string.widthText);
        m_widthEditText.setVisibility(View.VISIBLE);

        m_areaLabelTV.setText(R.string.areaText);
        m_perimLabelTV.setText(R.string.perimeterText);
    }
    public void calcSquare(){
        double length=0,width=0, area=0,perimeter=0;

            //Parse numbers from fields
        if(m_lengthEditText.getText().length() > 0) {
            length = Double.parseDouble(m_lengthEditText.getText().toString());
            width = Double.parseDouble(m_widthEditText.getText().toString());
            //Calculate results
            area = length * width;
            perimeter = (2 * length) + (2 * width);
            //Display results
            m_areaDisplayTextView.setText("" + String.format("%.2f",area));
            m_perimeterDisplayTextView.setText("" + perimeter);
        }
    }
    protected void setCircle(){
        m_lengthTextView.setText(R.string.radiusText);
        m_widthTextView.setVisibility(View.INVISIBLE);
        m_widthEditText.setVisibility(View.INVISIBLE);

        m_areaLabelTV.setText(R.string.areaText);
        m_perimLabelTV.setText(R.string.circumText);
    }
    public void calcCircle(){
        double radius=0,pi=Math.PI,
                area=0,circumference=0;

        //Parse numbers from fields
        if(m_lengthEditText.getText().length() > 0) {
            radius = Double.parseDouble(m_lengthEditText.getText().toString());
        }
        //Calculate results
        circumference = 2*pi*radius;
        area = Math.pow((pi * radius), 2);
        //Display results
        m_areaDisplayTextView.setText("" + String.format("%.2f",area));
        m_perimeterDisplayTextView.setText("" + String.format("%.2f",circumference));
    }
    protected void setTriangle(){
        m_lengthTextView.setText(R.string.baseText);
        m_widthTextView.setVisibility(View.VISIBLE);
        m_widthTextView.setText(R.string.heightText);
        m_widthEditText.setVisibility(View.VISIBLE);

        m_areaLabelTV.setText(R.string.areaText);
        m_perimLabelTV.setText(R.string.perimeterText);
    }
    public void calcTriangle(){
        double length=0,width=0, area=0,perimeter=0;

        //Parse numbers from fields
        if(m_lengthEditText.getText().length() > 0) {
            length = Double.parseDouble(m_lengthEditText.getText().toString());
            width = Double.parseDouble(m_widthEditText.getText().toString());
            //Calculate results
            area = length * width;
            perimeter = (2 * length) + (2 * width);
            //Display results
            m_areaDisplayTextView.setText("" + area);
            m_perimeterDisplayTextView.setText("" + perimeter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        if(parent.getItemAtPosition(position).equals("Square")){
//            calcSquare();}
//        if(parent.getItemAtPosition(position).equals("Circle")){
//            calcCircle();}
//        if(parent.getItemAtPosition(position).equals("Triangle")){
//            calcTriangle();}
        switch(position){
            case 0 : setSquare();
                break;
            case 1 : setCircle();
                break;
            case 2 : setTriangle();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onPause(){
        String length,width="",area,perimeter;
        int pos;
        SharedPreferences.Editor editor = savedValues.edit();

        length = m_lengthEditText.getText().toString();
        if (m_widthEditText.getText().length() > 0){
            width = m_widthEditText.getText().toString();
        }
        area = m_areaDisplayTextView.getText().toString();
        perimeter = m_perimeterDisplayTextView.getText().toString();
        pos = m_shapeDrop.getSelectedItemPosition();

        editor.putString("length", length);
        editor.putString("width", width);
        editor.putString("are", area);
        editor.putString("perimeter", perimeter);
        editor.putInt("pos",pos);
        editor.commit();

        super.onPause();
    }
    public void onResume(){
       super.onResume();
        String length,width="",area,perimeter;
        int pos;


        pos = m_shapeDrop.getSelectedItemPosition();
        switch(pos){
            case 0 : {setSquare();calcSquare();}
            break;
            case 1 : {setCircle();calcCircle();}
            break;
            case 2 : {setTriangle();calcTriangle();}
            break;
        }
    }
}
