package killianmills.pycast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Share extends ActionBarActivity {

    Button fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_share);

        addListenerOnFetch();
    }


    public void addListenerOnFetch() {

        final Context context = this;
        //asking user for path to file in dropbox to fetch

        fetch = (Button) findViewById(R.id.fetchButton);
        fetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                SharedPreferences settings = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                //saving the path in shared pref
                editor.putString("myPath",((EditText) findViewById(R.id.myPath) ).getText().toString());
                editor.commit();

                //Once path has been supplied by user launch dropbox fetch activity
                Intent intent = new Intent(context, MainShare.class);
                startActivity(intent);

            }

        });

    }

}
