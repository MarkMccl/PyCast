package killianmills.pycast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;




public class MainShare extends ActionBarActivity {
    
    //Instance variables for path and shared preference receive
    final String DEFAULTPATH = null;
    String myPath = null;

    //Creating object to carry authentication
    private DropboxAPI<AndroidAuthSession> coreAPIObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Allowing main thread to allow connection to dropbox server
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Setting appkey and appsecret (Choosing dropbox application to link to)
        AppKeyPair appKeys = new AppKeyPair("muamb4zeugfcbrt", "dg6jxnwlzwk6gqg");

        //Starting session
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        coreAPIObject = new DropboxAPI<AndroidAuthSession>(session);

        //Initiating Authentication procedure to gain session
        coreAPIObject.getSession().startOAuth2Authentication(MainShare.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Getting shared preference from Connection Settings for file path to fetch
        SharedPreferences settings = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        myPath = settings.getString("myPath",DEFAULTPATH);

        //Check and print to see if path if empty
        if(myPath == null){
            Toast.makeText(this, "Path Not Supplied", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Path being fetched", Toast.LENGTH_LONG).show();

        }
    }



    protected void onResume() {
        super.onResume();

        //if session authentication successful
        if (coreAPIObject.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                coreAPIObject.getSession().finishAuthentication();



                if(myPath!=null) {
                    //Create dropbox Link object holding a URL and expiring time value
                    DropboxAPI.DropboxLink myLink;
                    //Fetch Link from supplied path in format <Root>\<Folders>\<Item.extension>
                    myLink = coreAPIObject.share(myPath);
                    //Showing user shareable link
                    Toast.makeText(this,myLink.toString(), Toast.LENGTH_LONG).show();

                    //Adding the link to shared preference
                    SharedPreferences settings = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("myLink",myLink.url.toString());
                    editor.commit();
                }

                else{
                    Toast.makeText(this,"No Path Supplied", Toast.LENGTH_LONG).show();
                }

            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            } catch (DropboxException e) {
                e.printStackTrace();
            }
            final Context context = this;
            //Return to main activity once link fetched
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);

        }
    }
}





