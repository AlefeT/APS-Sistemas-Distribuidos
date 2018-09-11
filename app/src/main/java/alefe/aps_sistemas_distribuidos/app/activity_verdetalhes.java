package alefe.aps_sistemas_distribuidos.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class activity_verdetalhes extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar vdCarregando;
    String localF2;
    String dados[];
    Uri gmapsIntentUri, wazeIntentUri;
    TextView tvVDPoluicao, tvVDTransito, tvVDAlagamento, tvVDInundacoes, tvVDDesmatamento;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_verdetalhes onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_verdetalhes);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivVDRArrowback            =    findViewById(R.id.ivVDRArrowback);
        ImageView ivVDRHome                 =    findViewById(R.id.ivVDRHome);
        ImageView ivMaps                    =    findViewById(R.id.ivMaps);
        ImageView ivWaze                    =    findViewById(R.id.ivWaze);

        //  TextView's //
        TextView tvVDRNomeResult            =    findViewById(R.id.tvVDNomeR);
        tvVDPoluicao                        =    findViewById(R.id.tvVDPoluicaoR);
        tvVDTransito                        =    findViewById(R.id.tvVDTransitoR);
        tvVDAlagamento                      =    findViewById(R.id.tvVDAlagamentoR);
        tvVDInundacoes                      =    findViewById(R.id.tvVDInundacoesR);
        tvVDDesmatamento                    =    findViewById(R.id.tvVDDesmatamentoR);
        TextView tvMaps                     =    findViewById(R.id.tvMaps);
        TextView tvWaze                     =    findViewById(R.id.tvWaze);

        //  ProgressBar's   //
        vdCarregando                        = findViewById(R.id.vdCarregando);

        vdCarregando.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivVDRArrowback.setOnClickListener((View.OnClickListener) this);
        ivVDRHome.setOnClickListener((View.OnClickListener) this);
        ivMaps.setOnClickListener((View.OnClickListener) this);
        ivWaze.setOnClickListener((View.OnClickListener) this);
        tvMaps.setOnClickListener((View.OnClickListener) this);
        tvWaze.setOnClickListener((View.OnClickListener) this);



        //////////////////////////////////
        //  Recebe valores do PutExtra  //
        //////////////////////////////////

        Bundle bundle = getIntent().getExtras();
        localF2 = bundle.getString("localF2");
        tvVDRNomeResult.setText(localF2);


        ////////////////////////////////////////
        //CONEXÃO VOLLEY COM O BANCO DE DADOS //
        ////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://dkvox.com.br/AMBIENTETESTE/SD/verdetalhes.php?local="+localF2;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                dados = response.split(",");

                try
                {
                    //SET poluicao
                    tvVDPoluicao.setText(dados[0]);

                    //SET tvVDTransito
                    tvVDTransito.setText(dados[1]);

                    //SET tvVDAlagamento
                    tvVDAlagamento.setText(dados[2]);

                    //SET tvVDInundacoes
                    tvVDInundacoes.setText(dados[3]);

                    //SET tvVDDesmatamento
                    tvVDDesmatamento.setText(dados[4]);


                    //Trata endereço do local pra ser utilizado nas Uri's
                    String loccomplete = dados[5] + ", " + dados[6] + ",SP";
                    String locend = loccomplete.replace(" ","%20");

                    //Variavel usada na Intent do Google Maps //endereco ex.="Av. Albert Einstein, 627" //uf ex.="SP"
                    gmapsIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+locend+"&travelmode=walking");

                    //Variavel usada na Intent do Waze
                    wazeIntentUri = Uri.parse("https://waze.com/ul?q="+locend+"&navigate=yes");

                    vdCarregando.setVisibility(View.GONE);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Log.e("TAG","Conexao falhou.");
                Toast.makeText(activity_verdetalhes.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }




    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_verdetalhes onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_verdetalhes onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_verdetalhes onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_verdetalhes onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_verdetalhes onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivVDRArrowback :
                //  Log de onClick  //
                //Log.d("tag","onClick no ArrowLeft");

                finish();
                break;



            case R.id.ivVDRHome :
                //  Log de onClick  //
                //Log.d("tag","onClick no Home");

                Intent intentHome = new Intent(activity_verdetalhes.this, activity_principal.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;



            case R.id.ivMaps :
                //  Log de onClick  //
                //Log.d("tag","onClick no ivMaps");

                Intent intentivMaps = new Intent(Intent.ACTION_VIEW, gmapsIntentUri);
                intentivMaps.setPackage("com.google.android.apps.maps");
                startActivity(intentivMaps);
                break;



            case R.id.tvMaps :
                //  Log de onClick  //
                //Log.d("tag","onClick no tvMaps");

                Intent intenttvMaps = new Intent(Intent.ACTION_VIEW, gmapsIntentUri);
                intenttvMaps.setPackage("com.google.android.apps.maps");
                startActivity(intenttvMaps);
                break;



            case R.id.ivWaze :
                //  Log de onClick  //
                //Log.d("tag","onClick no ivWaze");

                Intent intentivWaze = new Intent(Intent.ACTION_VIEW, wazeIntentUri);
                startActivity(intentivWaze);
                break;



            case R.id.tvWaze :
                //  Log de onClick  //
                //Log.d("tag","onClick no tvWaze");

                Intent intenttvWaze = new Intent(Intent.ACTION_VIEW, wazeIntentUri);
                startActivity(intenttvWaze);
                break;
        }
    }

}
