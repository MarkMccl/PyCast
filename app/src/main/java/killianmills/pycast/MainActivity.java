package killianmills.pycast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    private Button connect;
    private final Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //For 5 buttons on main menu
        addListenerOnButton1();
        addListenerOnButton2();
        addListenerOnButton3();
        addListenerOnButton4();
        addListenerOnButton5();
    }

    // CONNECTION  launch on button press
    public void addListenerOnButton1() {

        connect = (Button) findViewById(R.id.connectionButton);

        connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context,Settings.class);
                startActivity(intent);

            }

        });

    }

    // GENERAL MODE launch on button press
        public void addListenerOnButton2() {

        connect = (Button) findViewById(R.id.generalButton);

        connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, GeneralMode.class);
                startActivity(intent);

            }

        });

    }

    // PRESENTATION MODE launch on button press
    public void addListenerOnButton3() {

        connect = (Button) findViewById(R.id.presentationButton);

        connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, PresentationMode.class);
                startActivity(intent);

            }

        });

    }

    // PRESENTATION MODE launch on button press
    public void addListenerOnButton4() {

        connect = (Button) findViewById(R.id.shareButton);

        connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Share.class);
                startActivity(intent);

            }

        });

    }

    // ABOUT launch on button press
    public void addListenerOnButton5() {

        connect = (Button) findViewById(R.id.aboutButton);

        connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, About.class);
                startActivity(intent);

            }

        });

    }

}
