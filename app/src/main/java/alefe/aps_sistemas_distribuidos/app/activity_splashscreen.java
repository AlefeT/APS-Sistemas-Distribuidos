package alefe.aps_sistemas_distribuidos.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import alefe.aps_sistemas_distribuidos.app.R;

public class activity_splashscreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splashscreen);

        //Handler to execute task after 110 miliseconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //////////////////////////////////
                //        isLogged verif        //
                //////////////////////////////////

                    Intent intentSplashPrincipal = new Intent(activity_splashscreen.this, activity_principal.class);
                    startActivity(intentSplashPrincipal);
            }
        }, 110);

    }

}
