package alefe.aps_sistemas_distribuidos.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import alefe.aps_sistemas_distribuidos.app.R;

public class activity_resultadobusca extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar rbCarregando;
    String posicaoF, hospitalF, convenioF, distanciaF, tempoesperaF, especialidadeF, notageralF, estacionamentoF, recepcaoF, organizacaoF, sinalizacaoF, cordialidadeF, limpezaF, triagemF, medicoF, enfermariaF, examesF, internacaoF, tituloDaActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_resultadobusca onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_resultadobusca);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivReBRArrowback             =    findViewById(R.id.ivReBRArrowback);
        ImageView ivReBRHome                  =    findViewById(R.id.ivReBRHome);

        //  TextView's //
        TextView tvReBRTitulo1                =    findViewById(R.id.tvReBRTitulo1);
        final TextView tvReBRNenhumencontrado =    findViewById(R.id.tvReBRNenhumencontrado);

        //  ProgressBar's   //
        rbCarregando                          =    findViewById(R.id.rbCarregando);

        rbCarregando.setVisibility(View.VISIBLE);
        tvReBRNenhumencontrado.setText("Aguarde um momento, estamos buscando os hospitais em nossa base.");
        tvReBRNenhumencontrado.setVisibility(View.VISIBLE);



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  ESSAS VARIÁVEIS ABAIXO QUE DEVERÃO SER UTILIZADAS NA QUERY.                                                                                       //
        //  SE O USUARIO NÃO PREENCHER O HOSPITAL, ELE VIRÁ COMO "" (VAZIO)                                                                                   //
        //  SE O USUARIO NÃO PREENCHER CONVENIO/DISTANCIA/TEMPO/ESPECIALIDADE, ELES VIRÃO COMO "Selecione" (PADRÃO)                                           //
        //  SE O USUARIO NÃO PREENCHER QUALQUER CAMPO REFERENTE A NOTA (EX.: NOTAGERAL/ESTACIONAMENTO/RECEPCAO, ELES VIRÃO COMO "Selecione a Nota" (PADRÃO)   //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //BUNDLE GET STRINGS DO PUT EXTRA
        Bundle bundle       = getIntent().getExtras();

        //Busca Rapida
        hospitalF           = bundle.getString("hospitalF").replaceAll("[ ]", "%20");
        convenioF           = bundle.getString("convenioF");
        distanciaF          = bundle.getString("distanciaF").replace("Até%205km","5").replace("Até%2010km","10").replace("Até%2020km","20").replace("Mais%20de%2020km","21");
        tempoesperaF        = bundle.getString("tempoesperaF").replace("Até%2030min","9.0").replace("Até%201%20hora%20e%2030min","5.0").replace("Até%201%20hora","7.0").replace("Mais%20de%201%20hora%20e%2030min","0");
        posicaoF            = bundle.getString("posicaoF");
        tituloDaActivity    = bundle.getString("tituloDaActivity");

        if(tituloDaActivity.equals("Busca Avançada"))
        {
            //Busca Avancada
            especialidadeF = bundle.getString("especialidadeF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            notageralF = bundle.getString("notageralF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            estacionamentoF = bundle.getString("estacionamentoF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            recepcaoF = bundle.getString("recepcaoF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            organizacaoF = bundle.getString("organizacaoF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            sinalizacaoF = bundle.getString("sinalizacaoF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            cordialidadeF = bundle.getString("cordialidadeF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            limpezaF = bundle.getString("limpezaF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            triagemF = bundle.getString("triagemF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            medicoF = bundle.getString("medicoF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            enfermariaF = bundle.getString("enfermariaF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            examesF = bundle.getString("examesF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
            internacaoF = bundle.getString("internacaoF").replace("Menor%20que%202,5", "0").replace("Entre%202,5%20e%205,0", "2.5").replace("Entre%205,1%20e%207,5", "5.0").replace("Maior%20que%207,5", "7.5");
        }

        //LOG pra verificar valores do bundle
        //Log.d("tag", "tituloDaActivity: " + tituloDaActivity + ", posicaoF: " + posicaoF + ", hospitalF: " + hospitalF + ", convenioF: " + convenioF + ", distanciaF: " + distanciaF + ", tempoesperaF: " + tempoesperaF + ", especialidadeF: " + especialidadeF + ", notageralF: " + notageralF + ", estacionamentoF: " + estacionamentoF + ", recepcaoF: " + recepcaoF + ", organizacaoF: " + organizacaoF + ", sinalizacaoF: " + sinalizacaoF + ", cordialidadeF: " + cordialidadeF + ", limpezaF: " + limpezaF + ", triagemF: " + triagemF + ", medicoF: " + medicoF + ", enfermariaF: " + enfermariaF + ", examesF: " + examesF + ", internacaoF: " + internacaoF);

        //Seta o titulo da pagina (Busca Rapida ou Busca Avançada)
        tvReBRTitulo1.setText(tituloDaActivity);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivReBRArrowback.setOnClickListener((View.OnClickListener) this);
        ivReBRHome.setOnClickListener((View.OnClickListener) this);



        //////////////////////////////////
        //  Mostrar Hospitais Resultado //
        //////////////////////////////////

        //Pega tamanho da tela
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int screenWidth = displaymetrics.widthPixels;



        //////////////////////////////////
        //  CONEXÃO VOLLEY COMEÇA AQUI  //
        //////////////////////////////////

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://dkvox.ngrok.io/qservices/busca.php?token=&nomehospital="+hospitalF+"&convenio="+convenioF+"&distancia="+distanciaF+"&tempoespera="+tempoesperaF+"&especialidade="+especialidadeF+"&notageral="+notageralF+"&estacionamento="+estacionamentoF+"&recepcao="+recepcaoF+"&organizacao="+organizacaoF+"&sinalizacao="+sinalizacaoF+"&cordialidade="+cordialidadeF+"&limpeza="+limpezaF+"&triagem="+triagemF+"&medico="+medicoF+"&enfermaria="+enfermariaF+"&exames="+examesF+"&internacao="+internacaoF+"&posicao="+posicaoF;
        //Log.d("TAG","URL de Busca: " + url);

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

                    //  TableLayout's //
                    TableLayout tlReBRLista = findViewById(R.id.tlReBRLista);

                    rbCarregando.setVisibility(View.GONE);
                    tvReBRNenhumencontrado.setVisibility(View.GONE);

                    // Getting hospitallist status//
                    String status = jObject.getString("status");

                    if (status.equals("Nenhum hospital encontrado."))
                    {
                        tvReBRNenhumencontrado.setText("Nenhum hospital encontrado.");
                        tvReBRNenhumencontrado.setVisibility(View.VISIBLE);
                    }

                    //Faz um loop pra cada resultado retornado do BD (hospnum)
                    int hospnum = jObject.length();
                    //Log.d("TAG", "Hospitais encontrados: " + String.valueOf(hospnum-1));
                    for (int i = 0; i < hospnum; i++)
                    {

                        String hospatual = "hospital"+(i+1);
                        //Adicionando um novo row a table layout @VINICIUS
                        TableRow row = new TableRow(activity_resultadobusca.this);
                        TableRow.LayoutParams p1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        row.setLayoutParams(p1);
                        TextView qty = new TextView(activity_resultadobusca.this);
                        qty.setText(jObject.getJSONObject(hospatual).getString("nome"));
                        qty.setTextColor(Color.parseColor("#000000"));
                        qty.setPadding(8, 4, 8, 3);
                        qty.setTextSize(15);
                        qty.setMaxWidth(screenWidth - 440);
                        qty.setMaxLines(3);
                        final String hospitalF2 = qty.getText().toString();
                        final String distanciaF2 = jObject.getJSONObject(hospatual).getString("distancia");
                        Button qty2 = (Button) getLayoutInflater().inflate(R.layout.button_style, null);
                        qty2.setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View v)
                            {
                                //  Log de onClick //
                                //Log.d("tag", "onClick no VerDetalhes do Hospital: " + hospitalF2);
                                Intent intentReBRVerdetalhes = new Intent(activity_resultadobusca.this, activity_verdetalhes.class);
                                intentReBRVerdetalhes.putExtra("hospitalF2", hospitalF2);
                                intentReBRVerdetalhes.putExtra("distanciaF2", distanciaF2);
                                startActivity(intentReBRVerdetalhes);
                            }
                        });
                        qty2.setPadding(2, 3, 10, 3);
                        qty2.setMaxWidth(170);
                        qty2.setMaxHeight(24);
                        qty2.setTextSize(12);
                        row.addView(qty);
                        row.addView(qty2);
                        tlReBRLista.addView(row, i);
                    }
                    //Log.d("tag","começo do loop extra");
                    //row vazia só pra última gerada não ficar grudada no fim da tela
                    TableRow row = new TableRow(activity_resultadobusca.this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView qty = new TextView(activity_resultadobusca.this);
                    qty.setText(" ");
                    qty.setPadding(8, 4, 8, 3);
                    qty.setTextSize(15);
                    qty.setMaxWidth(screenWidth - 265);
                    qty.setMaxLines(2);
                    TextView qty2 = new TextView(activity_resultadobusca.this);
                    qty2.setText(" ");
                    qty2.setPadding(0, 3, 16, 3);
                    qty2.setWidth(230);
                    qty2.setTextSize(11);
                    row.addView(qty);
                    row.addView(qty2);
                    tlReBRLista.addView(row, hospnum);
                    //Log.d("tag","final do loop extra");
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
                Toast.makeText(activity_resultadobusca.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                tvReBRNenhumencontrado.setText("Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente em alguns instantes.");
                tvReBRNenhumencontrado.setVisibility(View.VISIBLE);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_resultadobusca onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_resultadobusca onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_resultadobusca onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_resultadobusca onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_resultadobusca onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivReBRArrowback :
                //  Log de onClick  //
                //Log.d("tag","onClick no ArrowLeft");

                finish();
                break;



            case R.id.ivReBRHome :
                //  Log de onClick  //
                //Log.d("tag","onClick no Home");

                Intent intentHome = new Intent(activity_resultadobusca.this, activity_principal.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
        }
    }

}
