package killianmills.pycast;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GeneralMode extends ActionBarActivity {

    //Declaring instance variables
    private Socket clientSocket;
    private PrintWriter myPrintWriter;
    private EditText textField;
    
    private Button button;
    private Button buttonLeft;
    private Button buttonRight;
    private Button buttonEnter;

    private Button keyRight;
    private Button keyLeft;
    private Button keyUp;
    private Button keyDown;

    private String ipAddressServer;
    private int portNumberServer;
    private float mouseSensitivity;

    //String for message to server
    private String message;

    private VelocityTracker myVelTracker = null;

    //Variables for shared preference reception
    private final String DEFAULTIP = "N/A";
    private final int DEFAULTPORT = 0;
    private final float DEFAULTMOUSE = 0.125f;

    //Method to be called on a touch event
    public boolean onTouchEvent(MotionEvent event) {
        //Creating new Display object dependant on current screen properties
        Display display = getWindowManager().getDefaultDisplay();
        Point sizeOfScreen = new Point();
        //setting the sizeOfScreen Point = to the size
        display.getSize(sizeOfScreen);

        // return action value
        int action = event.getActionMasked();

        if(  ((int) event.getY())  <  ((sizeOfScreen.y / 2)  +  ((int)(sizeOfScreen.y*.05))  )
                && (  ((int) event.getX())  <  sizeOfScreen.x )  ){
            //switch case statement to execute code depending on type of on touch press action
            switch (action) {

                //If the screen is pressed down
                case MotionEvent.ACTION_DOWN:


                    if (myVelTracker == null) {
                        myVelTracker = VelocityTracker.obtain();
                    } else {
                        myVelTracker.clear();
                    }
                    myVelTracker.addMovement(event);
                    break;


                case MotionEvent.ACTION_MOVE:


                    //Add a user's movement to the tracker
                    myVelTracker.addMovement(event);
                    //Compute the current velocity based on the points that have been collected
                    myVelTracker.computeCurrentVelocity(1000);
                    //Creating string to send to server, starting with message buffer "0" to indicate mouse movement
                    message = ("0" + (int) (myVelTracker.getXVelocity() * mouseSensitivity) + " " + (int) (myVelTracker.getYVelocity() * mouseSensitivity)).toString();
                    //Creating new client object
                    ServerMessage ServerMessageTask = new ServerMessage();
                    //Executing message to server from client
                    ServerMessageTask.execute();
                    break;



                case MotionEvent.ACTION_CANCEL:

                    //Resetting the tracker
                    myVelTracker.recycle();
                    myVelTracker = null;
                    break;

                case MotionEvent.ACTION_UP:
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_mode);

        //linking buttons to xml file id's
        textField = (EditText) findViewById(R.id.editText1);
        button = (Button) findViewById(R.id.button1);
        buttonLeft= (Button) findViewById(R.id.buttonLeftClick);
        buttonRight= (Button) findViewById(R.id.buttonRightClick);
        buttonEnter= (Button) findViewById(R.id.buttonEnterClick);

        keyRight= (Button) findViewById(R.id.buttonRightk);
        keyLeft= (Button) findViewById(R.id.buttonLeftk);
        keyUp= (Button) findViewById(R.id.buttonUpk);
        keyDown= (Button) findViewById(R.id.buttonDownk);

        //Creating a series of OnClickListeners for each button
        //Each button sends a different message buffer to let server know button id
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message = "2"+textField.getText().toString();
                textField.setText("");
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="11";
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="12";
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });

        buttonEnter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="3";
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });
        keyRight.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="6";
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });
        keyLeft.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="7";
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });
        keyUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="4";
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });
        keyDown.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                message="5";
                ServerMessage ServerMessageTask = new ServerMessage();
                ServerMessageTask.execute();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //When activity starts fetch data from Connection settings
        SharedPreferences settings = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        ipAddressServer = settings.getString("ipAddress",DEFAULTIP);
        portNumberServer = settings.getInt("portNumber", DEFAULTPORT);
        mouseSensitivity = settings.getFloat("mouseSensitivity",DEFAULTMOUSE);


        //Check to see if any connection settings were received
        if(ipAddressServer.equals(DEFAULTIP) || portNumberServer == DEFAULTPORT){

            Toast.makeText(this,"No Valid Connection Settings Were Found",Toast.LENGTH_LONG).show();
        }
        else{

            Toast.makeText(this, "Connection Settings Loaded Successfully",Toast.LENGTH_LONG).show();
        }

        mouseSensitivity = settings.getFloat("mouseSensitivity",DEFAULTMOUSE);
        //Check to see if any mouse settings were received
        if(mouseSensitivity == DEFAULTMOUSE){

            Toast.makeText(this,"Loaded Default Mouse",Toast.LENGTH_LONG).show();
        }
        else{

            Toast.makeText(this, "Loaded Mouse Settings",Toast.LENGTH_LONG).show();
        }

    }
    //Class to handle socket connection using AsyncTask to allow for computation while waiting for server
    private class ServerMessage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //Socket connection handled withing try catch in case of failure
            try {
                //Establish client taking connection settings as arguments
                clientSocket = new Socket(ipAddressServer, portNumberServer);
                //Opening a PrintWriter
                myPrintWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                //Writing previously created message to Server
                myPrintWriter.write(message);
                //Emptying PrintWriter
                myPrintWriter.flush();
                //Closing PrintWriter
                myPrintWriter.close();
                //Closing printWriter
                clientSocket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}