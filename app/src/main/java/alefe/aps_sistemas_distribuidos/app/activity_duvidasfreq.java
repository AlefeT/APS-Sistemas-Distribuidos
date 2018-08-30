package alefe.aps_sistemas_distribuidos.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_duvidasfreq extends AppCompatActivity implements View.OnClickListener
{
    CardView cvDuvPesquisar, cvDuvRanking;
    TextView tvDuvPesquisarR, tvDuvRankingR;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_duvidasfreq onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_duvidasfreq);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  CardView's  //
        cvDuvPesquisar           = findViewById(R.id.cvDuvPesquisar);
        cvDuvRanking             = findViewById(R.id.cvDuvRanking);

        //  TextView's  //
        tvDuvPesquisarR          = findViewById(R.id.tvDuvPesquisarR);
        tvDuvRankingR            = findViewById(R.id.tvDuvRankingR);

        //  ImageView's  //
        ImageView ivDuvArrowback = findViewById(R.id.ivDuvArrowback);


        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivDuvArrowback.setOnClickListener((View.OnClickListener) this);
        cvDuvPesquisar.setOnClickListener((View.OnClickListener) this);
        cvDuvRanking.setOnClickListener((View.OnClickListener) this);



    }


    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_duvidasfreq onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_duvidasfreq onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_duvidasfreq onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_duvidasfreq onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_duvidasfreq onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivDuvArrowback:
                //  Log de onClick  //
                //Log.d("tag", "onClick no ArrowLeft");

                finish();
                break;


            case R.id.cvDuvPesquisar:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvDuvPesquisar");

                int abertocvDuvPesquisar = tvDuvPesquisarR.getVisibility();
                tvDuvPesquisarR.setVisibility(View.VISIBLE);
                tvDuvRankingR.setVisibility(View.GONE);

                if (abertocvDuvPesquisar == View.VISIBLE)
                {
                    tvDuvPesquisarR.setVisibility(View.GONE);
                }

                break;


            case R.id.cvDuvRanking:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvDuvRanking");

                int abertocvDuvRanking = tvDuvRankingR.getVisibility();
                tvDuvPesquisarR.setVisibility(View.GONE);
                tvDuvRankingR.setVisibility(View.VISIBLE);

                if (abertocvDuvRanking == View.VISIBLE)
                {
                    tvDuvRankingR.setVisibility(View.GONE);
                }

                break;

        }
    }
}
