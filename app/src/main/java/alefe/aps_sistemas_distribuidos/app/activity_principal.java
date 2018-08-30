package alefe.aps_sistemas_distribuidos.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class activity_principal extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_principal onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_principal);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivPriRanking     =  findViewById(R.id.ivPriRanking);
        ImageView ivPriPesquisar   =  findViewById(R.id.ivPriPesquisar);
        ImageView ivPriDuvidas     =  findViewById(R.id.ivPriDuvidas);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivPriRanking.setOnClickListener((View.OnClickListener) this);
        ivPriPesquisar.setOnClickListener((View.OnClickListener) this);
        ivPriDuvidas.setOnClickListener((View.OnClickListener) this);

    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_principal onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_principal onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_principal onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_principal onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_principal onDestroy");
    }



    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivPriRanking :
                //  Log de onClick //
                //Log.d("tag","onClick no ivPriRanking");

                Intent intentPriRanking = new Intent(activity_principal.this, activity_rankinglocais.class);
                startActivity(intentPriRanking);
                break;



            case R.id.ivPriPesquisar:
                //  Log de onClick //
                //Log.d("tag","onClick no ivPriPesquisar");

                Intent intentPriPesquisar = new Intent(activity_principal.this, activity_pesquisarlocais.class);
                startActivity(intentPriPesquisar);
                break;



            case R.id.ivPriDuvidas:
                //  Log de onClick //
                //Log.d("tag","onClick no ivPriDuvidas");

                Intent intentPriDuvidas = new Intent(activity_principal.this, activity_duvidasfreq.class);
                startActivity(intentPriDuvidas);
                break;
        }
    }

}
