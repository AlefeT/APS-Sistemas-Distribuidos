package womm.quality_services.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import womm.quality_services.app.R;

public class activity_ajuda extends AppCompatActivity implements View.OnClickListener
{
    CardView cvAjuBuscrap, cvAjuBuscava, cvAjuAvaliar, cvAjuEditperf, cvAjuContato, cvAjuRanking;
    TextView tvAjuBuscrapR, tvAjuBuscavaR, tvAjuAvaliarR, tvAjuEditperfR, tvAjuContatoR, tvAjuRankingR;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_ajuda onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_ajuda);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  CardView's  //
        cvAjuBuscrap             = findViewById(R.id.cvAjuBuscrap);
        cvAjuBuscava             = findViewById(R.id.cvAjuBuscava);
        cvAjuAvaliar             = findViewById(R.id.cvAjuAvaliar);
        cvAjuEditperf            = findViewById(R.id.cvAjuEditperf);
        cvAjuContato             = findViewById(R.id.cvAjuContato);
        cvAjuRanking             = findViewById(R.id.cvAjuRanking);

        //  TextView's  //
        tvAjuBuscrapR            = findViewById(R.id.tvAjuBuscrapR);
        tvAjuBuscavaR            = findViewById(R.id.tvAjuBuscavaR);
        tvAjuAvaliarR            = findViewById(R.id.tvAjuAvaliarR);
        tvAjuEditperfR           = findViewById(R.id.tvAjuEditperfR);
        tvAjuContatoR            = findViewById(R.id.tvAjuContatoR);
        tvAjuRankingR            = findViewById(R.id.tvAjuRankingR);

        //  ImageView's  //
        ImageView ivAjuArrowback = findViewById(R.id.ivAjuArrowback);


        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivAjuArrowback.setOnClickListener((View.OnClickListener) this);
        cvAjuBuscrap.setOnClickListener((View.OnClickListener) this);
        cvAjuBuscava.setOnClickListener((View.OnClickListener) this);
        cvAjuAvaliar.setOnClickListener((View.OnClickListener) this);
        cvAjuEditperf.setOnClickListener((View.OnClickListener) this);
        cvAjuContato.setOnClickListener((View.OnClickListener) this);
        cvAjuRanking.setOnClickListener((View.OnClickListener) this);



    }


    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_ajuda onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_ajuda onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_ajuda onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_ajuda onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_ajuda onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivAjuArrowback:
                //  Log de onClick  //
                //Log.d("tag", "onClick no ArrowLeft");

                finish();
                break;


            case R.id.cvAjuBuscrap:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvAjuBuscrap");

                int abertocvAjuBuscrap = tvAjuBuscrapR.getVisibility();
                tvAjuBuscrapR.setVisibility(View.VISIBLE);
                tvAjuBuscavaR.setVisibility(View.GONE);
                tvAjuAvaliarR.setVisibility(View.GONE);
                tvAjuEditperfR.setVisibility(View.GONE);
                tvAjuContatoR.setVisibility(View.GONE);
                tvAjuRankingR.setVisibility(View.GONE);

                if (abertocvAjuBuscrap == View.VISIBLE)
                {
                    tvAjuBuscrapR.setVisibility(View.GONE);
                }

                break;


            case R.id.cvAjuBuscava:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvAjuBuscava");

                int abertocvAjuBuscava = tvAjuBuscavaR.getVisibility();
                tvAjuBuscrapR.setVisibility(View.GONE);
                tvAjuBuscavaR.setVisibility(View.VISIBLE);
                tvAjuAvaliarR.setVisibility(View.GONE);
                tvAjuEditperfR.setVisibility(View.GONE);
                tvAjuContatoR.setVisibility(View.GONE);
                tvAjuRankingR.setVisibility(View.GONE);

                if (abertocvAjuBuscava == View.VISIBLE)
                {
                    tvAjuBuscavaR.setVisibility(View.GONE);
                }

                break;


            case R.id.cvAjuAvaliar:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvAjuAvaliar");

                int abertocvAjuAvaliar = tvAjuAvaliarR.getVisibility();
                tvAjuBuscrapR.setVisibility(View.GONE);
                tvAjuBuscavaR.setVisibility(View.GONE);
                tvAjuAvaliarR.setVisibility(View.VISIBLE);
                tvAjuEditperfR.setVisibility(View.GONE);
                tvAjuContatoR.setVisibility(View.GONE);
                tvAjuRankingR.setVisibility(View.GONE);

                if (abertocvAjuAvaliar == View.VISIBLE)
                {
                    tvAjuAvaliarR.setVisibility(View.GONE);
                }

                break;


            case R.id.cvAjuEditperf:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvAjuEditperf");

                int abertocvAjuEditperf = tvAjuEditperfR.getVisibility();
                tvAjuBuscrapR.setVisibility(View.GONE);
                tvAjuBuscavaR.setVisibility(View.GONE);
                tvAjuAvaliarR.setVisibility(View.GONE);
                tvAjuEditperfR.setVisibility(View.VISIBLE);
                tvAjuContatoR.setVisibility(View.GONE);
                tvAjuRankingR.setVisibility(View.GONE);

                if (abertocvAjuEditperf == View.VISIBLE)
                {
                    tvAjuEditperfR.setVisibility(View.GONE);
                }

                break;


            case R.id.cvAjuContato:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvAjuContato");

                int abertocvAjuContato = tvAjuContatoR.getVisibility();
                tvAjuBuscrapR.setVisibility(View.GONE);
                tvAjuBuscavaR.setVisibility(View.GONE);
                tvAjuAvaliarR.setVisibility(View.GONE);
                tvAjuEditperfR.setVisibility(View.GONE);
                tvAjuContatoR.setVisibility(View.VISIBLE);
                tvAjuRankingR.setVisibility(View.GONE);

                if (abertocvAjuContato == View.VISIBLE)
                {
                    tvAjuContatoR.setVisibility(View.GONE);
                }

                break;


            case R.id.cvAjuRanking:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvAjuRanking");

                int abertocvAjuRanking = tvAjuRankingR.getVisibility();
                tvAjuBuscrapR.setVisibility(View.GONE);
                tvAjuBuscavaR.setVisibility(View.GONE);
                tvAjuAvaliarR.setVisibility(View.GONE);
                tvAjuEditperfR.setVisibility(View.GONE);
                tvAjuContatoR.setVisibility(View.GONE);
                tvAjuRankingR.setVisibility(View.VISIBLE);

                if (abertocvAjuRanking == View.VISIBLE)
                {
                    tvAjuRankingR.setVisibility(View.GONE);
                }

                break;

        }
    }
}
