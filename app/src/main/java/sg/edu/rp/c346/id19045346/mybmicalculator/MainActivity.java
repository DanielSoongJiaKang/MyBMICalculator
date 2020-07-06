package sg.edu.rp.c346.id19045346.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etweight;
    EditText etHeight;

    Button btnCalculate;
    Button btnReset;

    TextView tvlastdate;
    TextView tvlastBMI;
    TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHeight = findViewById(R.id.editTextHeight);
        etweight = findViewById(R.id.editTextWeight);

        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);

        tvlastdate = findViewById(R.id.textViewlastdate);
        tvlastBMI = findViewById(R.id.textViewlastBMI);
        tvResult = findViewById(R.id.textViewResult);

        Calendar now = Calendar.getInstance();
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etweight.getText().toString().isEmpty() && !etHeight.getText().toString().isEmpty()) {
                    String date = datetime;
                    String result = "";
                    Float weight = Float.parseFloat(etweight.getText().toString());
                    Float height = Float.parseFloat(etHeight.getText().toString());
                    float bmi = weight/(height*height);
                    if(bmi <18.5) {
                        result = "You are underweight";
                    }
                    else if(bmi <24.9) {
                        result = "Your BMI is normal";
                    }
                    else if(bmi <29.9)  {
                        result = "You are overweight";
                    }
                    else {
                        result = "You are obese";
                    }
                    //Shared Preference
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor prefEdit = pref.edit();

                    prefEdit.putString("date",date);
                    prefEdit.putFloat("bmi",bmi);
                    prefEdit.putString("result",result);

                    prefEdit.commit();
                    etweight.setText("");
                    etHeight.setText("");
                    onResume();
                }
                else {
                    Toast.makeText(MainActivity.this,"Please Enter Details",Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                etHeight.setText("");
                etweight.setText("");
                String result = "";
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();


                prefEdit.putString("date","");
                prefEdit.putFloat("bmi",0);
                prefEdit.putString("result",result);
                //Step 1e: Call commit() to save the changes into SharedPreference
                prefEdit.commit();

                onResume();



            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        etweight.setText("");
        etHeight.setText("");





    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Float retrieveBMI = prefs.getFloat("bmi",0);
        String retrieveDate = prefs.getString("date","");
        String retrieveResult = prefs.getString("result","");

        tvlastdate.setText(retrieveDate);
        tvlastBMI.setText(retrieveBMI+"");
        tvResult.setText(retrieveResult);
    }
}
