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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class activity_resultadopesquisa extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar rbCarregando;
    String locais[];
    String localF, poluicaoF, transitoF, alagamentoF, inundacaoF, desmatamentoF;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_resultadopesquisa onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_resultadopesquisa);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivRPArrowback             =    findViewById(R.id.ivRPArrowback);
        ImageView ivRPHome                  =    findViewById(R.id.ivRPHome);

        //  TextView's //
        final TextView tvRPNenhumencontrado =    findViewById(R.id.tvRPNenhumencontrado);

        //  ProgressBar's   //
        rbCarregando                        =    findViewById(R.id.rbCarregando);

        rbCarregando.setVisibility(View.VISIBLE);
        tvRPNenhumencontrado.setText("Aguarde um momento, estamos buscando os locais em nossa base.");
        tvRPNenhumencontrado.setVisibility(View.VISIBLE);



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  ESSAS VARIÁVEIS ABAIXO QUE DEVERÃO SER UTILIZADAS NA QUERY.                                                               //
        //  SE O USUARIO NÃO PREENCHER O LOCAL, ELE VIRÁ COMO "" (VAZIO)                                                           //
        //  SE O USUARIO NÃO PREENCHER UM DOS FILTROS, ELES VIRÃO COMO "Selecione" (PADRÃO)                                           //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //BUNDLE GET STRINGS DO PUT EXTRA
        Bundle bundle   = getIntent().getExtras();

        localF          = bundle.getString("localF");
        poluicaoF       = bundle.getString("poluicaoF");
        transitoF       = bundle.getString("transitoF");
        alagamentoF     = bundle.getString("alagamentoF");
        inundacaoF      = bundle.getString("inundacaoF");
        desmatamentoF   = bundle.getString("desmatamentoF");

        //LOG pra verificar valores do bundle
        //Log.d("tag", ", localF: " + localF + ", poluicaoF: " + poluicaoF + ", transitoF: " + transitoF + ", alagamentoF: " + alagamentoF + ", inundacaoF: " + inundacaoF + ", desmatamentoF: " + desmatamentoF);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivRPArrowback.setOnClickListener((View.OnClickListener) this);
        ivRPHome.setOnClickListener((View.OnClickListener) this);



        //////////////////////////////////
        //  Mostrar Locais Resultado    //
        //////////////////////////////////

        //Pega tamanho da tela
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int screenWidth = displaymetrics.widthPixels;



        //////////////////////////////////
        //  CONEXÃO VOLLEY COMEÇA AQUI  //
        //////////////////////////////////

        //CONEXÃO VOLLEY COMEÇA AQUI
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        //Link pra puxar a STRING com todos os locais.
        String url ="https://dkvox.com.br/AMBIENTETESTE/SD/buscaresultado.php?local="+localF+"&poluicao="+poluicaoF+"&transito="+transitoF+"&alagamento="+alagamentoF+"&inundacoes="+inundacaoF+"&desmatamento="+desmatamentoF;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                //Depois de tratar, quebrar a STRING em vários campos e armazenar cada um num campo do ARRAY DE STRINGS:
                locais = response.split(",");

                try
                {
                    String respostaj = response;
                    //Log.d("TAG","" + respostaj);

                    //  TableLayout's //
                    TableLayout tlRPLista = findViewById(R.id.tlRPLista);

                    rbCarregando.setVisibility(View.GONE);
                    tvRPNenhumencontrado.setVisibility(View.GONE);

                    //Faz um loop pra cada resultado retornado do PHP (localnum)
                    int localpnum = respostaj.length();
                    //Log.d("TAG", "Locais encontrados: " + String.valueOf(localpnum-1));
                    for (int i = 0; i < localpnum; i++)
                    {
                        //Adicionando um novo row a table layout
                        TableRow row = new TableRow(activity_resultadopesquisa.this);
                        TableRow.LayoutParams p1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        row.setLayoutParams(p1);
                        TextView qty = new TextView(activity_resultadopesquisa.this);
                        qty.setText(locais[i]);
                        qty.setTextColor(Color.parseColor("#000000"));
                        qty.setPadding(8, 4, 8, 3);
                        qty.setTextSize(15);
                        qty.setMaxWidth(screenWidth - 440);
                        qty.setMaxLines(3);
                        final String localF2 = locais[i];
                        Button qty2 = (Button) getLayoutInflater().inflate(R.layout.button_style, null);
                        qty2.setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View v)
                            {
                                //  Log de onClick //
                                //Log.d("tag", "onClick no VerDetalhes do Local: " + localF2);
                                Intent intentRPVerdetalhes = new Intent(activity_resultadopesquisa.this, activity_verdetalhes.class);
                                intentRPVerdetalhes.putExtra("localF2", localF2);
                                startActivity(intentRPVerdetalhes);
                            }
                        });
                        qty2.setPadding(2, 3, 10, 3);
                        qty2.setMaxWidth(170);
                        qty2.setMaxHeight(24);
                        qty2.setTextSize(12);
                        row.addView(qty);
                        row.addView(qty2);
                        tlRPLista.addView(row, i);
                    }
                    //Log.d("tag","começo do loop extra");
                    //row vazia só pra última gerada não ficar grudada no fim da tela
                    TableRow row = new TableRow(activity_resultadopesquisa.this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView qty = new TextView(activity_resultadopesquisa.this);
                    qty.setText(" ");
                    qty.setPadding(8, 4, 8, 3);
                    qty.setTextSize(15);
                    qty.setMaxWidth(screenWidth - 265);
                    qty.setMaxLines(2);
                    TextView qty2 = new TextView(activity_resultadopesquisa.this);
                    qty2.setText(" ");
                    qty2.setPadding(0, 3, 16, 3);
                    qty2.setWidth(230);
                    qty2.setTextSize(11);
                    row.addView(qty);
                    row.addView(qty2);
                    tlRPLista.addView(row, localpnum);
                    //Log.d("tag","final do loop extra");
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
                //Display an error if the connection failed
                //Log.e("TAG","Conexao falhou.");
                Toast.makeText(activity_resultadopesquisa.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                tvRPNenhumencontrado.setText("Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente em alguns instantes.");
                tvRPNenhumencontrado.setVisibility(View.VISIBLE);
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
        //Log.d("tag","In activity_resultadopesquisa onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_resultadopesquisa onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_resultadopesquisa onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_resultadopesquisa onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_resultadopesquisa onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivRPArrowback:
                //  Log de onClick  //
                //Log.d("tag","onClick no ArrowLeft");

                finish();
                break;



            case R.id.ivRPHome:
                //  Log de onClick  //
                //Log.d("tag","onClick no Home");

                Intent intentHome = new Intent(activity_resultadopesquisa.this, activity_principal.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
        }
    }

}
