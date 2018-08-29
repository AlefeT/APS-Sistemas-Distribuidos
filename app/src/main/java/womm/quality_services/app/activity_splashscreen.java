package womm.quality_services.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import womm.quality_services.app.R;

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

                SharedPreferences wmbPreference;
                wmbPreference    = PreferenceManager.getDefaultSharedPreferences(activity_splashscreen.this);
                String isLogged  = wmbPreference.getString("isLogged", "");
                String idUsuario = wmbPreference.getString("idUsuario", "");
                //Log.d("TAG", "SPLASH 1! isLogged: " + isLogged + ", idUsuario: " + idUsuario);

                if (isLogged.equals("Y"))
                {
                    //Log.d("TAG", "SPLASH Y! Ja estava logado: " + isLogged + ", e o ID e: " + idUsuario);

                    Intent intentSplashPrincipal = new Intent(activity_splashscreen.this, activity_principal.class);
                    startActivity(intentSplashPrincipal);
                }

                else
                {
                    //Log.d("TAG", "SPLASH N! Ja estava logado: " + isLogged);

                    Intent intentSplashLogin = new Intent(activity_splashscreen.this, activity_login.class);
                    startActivity(intentSplashLogin);
                }
            }
        }, 110);

    }

}
