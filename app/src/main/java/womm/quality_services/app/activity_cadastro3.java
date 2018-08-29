package womm.quality_services.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import womm.quality_services.app.R;

public class activity_cadastro3 extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_cadastro3 onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_cadastro3);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  CardView's  //
        CardView cvCad3Entrar          = (CardView)    findViewById(R.id.cvCad3Entrar);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        cvCad3Entrar.setOnClickListener((View.OnClickListener) this);


    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_cadastro3 onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_cadastro3 onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_cadastro3 onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_cadastro3 onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_cadastro3 onDestroy");
    }


    @Override
    public void onBackPressed()
    {
        moveTaskToBack(false);
        Toast.makeText(this, "Para prosseguir, aperte em Entrar.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cvCad3Entrar :
                //  Log de onClick no cvCad3Entrar  //
                //Log.d("tag","onClick no cvCad3Entrar");

                Intent intentCad3Entrar = new Intent(activity_cadastro3.this, activity_principal.class);
                startActivity(intentCad3Entrar);
                break;
        }
    }

}
