package me.hotcode.eatdee.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.hotcode.eatdee.R;
import me.hotcode.eatdee.models.Profile;

public class SetProfileActivity extends AppCompatActivity implements
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener{
    RadioGroup sexradioGroup;
    EditText weightedit;
    EditText heightedit;
    EditText birthedit;
    String sex;
    Button saveprofilebutton;
    Calendar now;
    Date birth;
    Spinner dropdown_ecercise_per_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        sexradioGroup = (RadioGroup)findViewById(R.id.sexradio);
        weightedit = (EditText) findViewById(R.id.weightedit);
        heightedit = (EditText) findViewById(R.id.heightedit);

        birthedit = (EditText) findViewById(R.id.birthedit);
        birthedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SetProfileActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        dropdown_ecercise_per_week = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"no exercise", "1-3 days per week", "3-5 days per week", "6-7 days per week", "very hard exercise "};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown_ecercise_per_week.setAdapter(adapter);


        saveprofilebutton = (Button) findViewById(R.id.save_profile_button);
        saveprofilebutton.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        birthedit.setText(date);
        birth = new Date(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onClick(View view) {
        int checkedRadioButtonId = sexradioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {// No item selected
        }
        else{
            if (checkedRadioButtonId == R.id.male) {
                sex = "male";
            }
            else{
                sex = "female";
            }
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("user_setting", new Profile(birth,Integer.parseInt(heightedit.getText().toString()),Integer.parseInt(weightedit.getText().toString()),sex, dropdown_ecercise_per_week.getSelectedItemPosition()));
        setResult(RESULT_OK, resultIntent);
        onBackPressed();
    }

    //@Override
    public void onBackPressed2() {
        // do nothing.

    }
}
