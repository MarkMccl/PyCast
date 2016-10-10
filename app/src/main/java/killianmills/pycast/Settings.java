package killianmills.pycast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Settings extends ActionBarActivity {
    //setting instance variables
    private RadioGroup radioGroup;
    private Button saveButton, mouseButton;
    private EditText ip, port;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        addListenerOnSave();
        addListenerOnMouseSens();

    }


    public void addListenerOnSave() {

        final Context context = this;

        saveButton = (Button) findViewById(R.id.connectButton);
        //if button pressed do...
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ip = (EditText) findViewById(R.id.ipAddress);
                port = (EditText) findViewById(R.id.portNumber);

                // takes values from user input
                String holder1 = ip.getText().toString();
                String holderTemp = port.getText().toString();
                int holder2 = Integer.parseInt(holderTemp);

                //stores the values in shared preference
                SharedPreferences settings = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("ipAddress", holder1);
                editor.putInt("portNumber", holder2);
                editor.commit();

                Toast.makeText(context, "Connection Settings Saved", Toast.LENGTH_LONG).show();

            }

        });

    }

    public void addListenerOnMouseSens() {
        //Setting objects for the radio buttons
        final RadioButton low = (RadioButton) findViewById(R.id.lowMouse);
        final RadioButton med = (RadioButton) findViewById(R.id.medMouse);
        final RadioButton high = (RadioButton) findViewById(R.id.highMouse);

        mouseButton = (Button) findViewById(R.id.mouseButton);
        mouseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                //Open shared preference object
                SharedPreferences settings = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                //Set mouse sens in shared pred depending on radio button selection
                if (selectedId == low.getId()) {
                    Toast.makeText(getApplicationContext(), " Low Sensitivity", Toast.LENGTH_LONG).show();
                    editor.putFloat("mouseSensitivity", .005f);
                    editor.commit();

                } else if (selectedId == med.getId()) {
                    Toast.makeText(getApplicationContext(), " Medium Sensitivity", Toast.LENGTH_LONG).show();
                    editor.putFloat("mouseSensitivity", .0125f);
                    editor.commit();
                } else {
                    selectedId = high.getId();
                    Toast.makeText(getApplicationContext(), " High Sensitivity", Toast.LENGTH_LONG).show();
                    editor.putFloat("mouseSensitivity", .017f);
                    editor.commit();

                }

            }

        });

    }
}
