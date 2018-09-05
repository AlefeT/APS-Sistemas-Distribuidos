package alefe.aps_sistemas_distribuidos.app;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

import org.json.JSONException;
import org.json.JSONObject;

import alefe.aps_sistemas_distribuidos.app.R;

public class activity_verdetalhes extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar vdCarregando;
    String hospitalF2;
    Uri gmapsIntentUri, wazeIntentUri;
    TextView tvVDREnderecoResult, tvVDRTempoesperaResult, tvVDRDistanciaResult, tvVDRNotageralResult;

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
        TextView tvVDRNomeResult            =    findViewById(R.id.tvVDRNomeResult);
        tvVDREnderecoResult                 =    findViewById(R.id.tvVDREnderecoResult);
        tvVDRTempoesperaResult              =    findViewById(R.id.tvVDRTempoesperaResult);
        tvVDRNotageralResult                =    findViewById(R.id.tvVDRNotageralResult);
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
        hospitalF2 = bundle.getString("hospitalF2");
        tvVDRNomeResult.setText(hospitalF2);

        String nometosearch = hospitalF2.replaceAll("[ ]", "%20");



        ////////////////////////////////////////
        //CONEXÃO VOLLEY COM O BANCO DE DADOS //
        ////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://dkvox.ngrok.io/qservices/detalhes.php?token=&nomehospital="+nometosearch;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jObject = new JSONObject(response);
                    //Log.d("TAG","" + jObject);


                    //SET Endereço
                    tvVDREnderecoResult.setText(jObject.getString("endereco"));

                    //SET Tempo de Espera
                            tvVDRTempoesperaResult.setText(jObject.getString("poluicao"));

                    //SET Nota Geral
                            tvVDRNotageralResult.setText(jObject.getString("notageral"));

                            //Cor que a Nota Geral será exibida
                            if (Float.parseFloat(jObject.getString("notageral")) >= 7)
                            {
                                //Verde
                                tvVDRNotageralResult.setTextColor(Color.parseColor("#048823"));
                            }
                            else
                                if (Float.parseFloat(jObject.getString("notageral")) >= 5)
                                {
                                    //Amarelo
                                    tvVDRNotageralResult.setTextColor(Color.parseColor("#A99E00"));
                                }
                                else
                                    {
                                        //Vermelho
                                        tvVDRNotageralResult.setTextColor(Color.parseColor("#A91F00"));
                                    }


                    //Trata endereço do hospital pra ser utilizado nas Uri's
                    String hospcomplete = jObject.getString("endereco")+","+ jObject.getString("uf");
                    String hospend = hospcomplete.replace(" ","%20");

                    //Variavel usada na Intent do Google Maps
                    gmapsIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+hospend+"&travelmode=walking");

                    //Variavel usada na Intent do Waze
                    wazeIntentUri = Uri.parse("https://waze.com/ul?q="+hospend+"&navigate=yes");

                    vdCarregando.setVisibility(View.GONE);
                }
                catch (JSONException e)
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
                //Display an error if the connection failed
                //Log.e("TAG","Volley connection miserably failed.");
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
