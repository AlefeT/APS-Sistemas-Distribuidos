package womm.quality_services.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import womm.quality_services.app.R;

public class activity_termos extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_termos onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_termos);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  CardView's  //
        CardView cvTerOk  = findViewById(R.id.cvTerOk);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        cvTerOk.setOnClickListener((View.OnClickListener) this);

    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_termos onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_termos onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_termos onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_termos onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_termos onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cvTerOk :
                //  Log de onClick no cvTerOk  //
                //Log.d("tag","onClick no cvTerOk");

                //  Finaliza a activity_termos  //
                finish();
                break;
        }
    }

}

