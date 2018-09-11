package alefe.aps_sistemas_distribuidos.app;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
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

public class activity_rankinglocais extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar pbCarregando;
    String lista[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_rankinglocais onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_rankinglocais);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivRankArrowback = findViewById(R.id.ivRankArrowback);

        //  ProgressBar's   //
        pbCarregando              = findViewById(R.id.pbCarregando);

        pbCarregando.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivRankArrowback.setOnClickListener((View.OnClickListener) this);



        //////////////////////////////////
        //  Mostrar Top 10 de Locais    //
        //////////////////////////////////

        //pega tamanho da tela
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int screenWidth = displaymetrics.widthPixels;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://dkvox.com.br/AMBIENTETESTE/SD/rankinglocais.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                lista = response.split(",");

                try
                {
                    //Indicando o TableLayout que está no layout
                    TableLayout ll = findViewById(R.id.tlRankLista);

                    //gera rank
                    int qtdeRanking = 11;
                    for (int i = 0; i < qtdeRanking; i++)
                    {
                        if (i == 0)
                        {
                            //Adicionando um novo row a table layout
                            TableRow row = new TableRow(activity_rankinglocais.this);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);
                            TextView qty = new TextView(activity_rankinglocais.this);
                            qty.setText(" ");
                            qty.setTextSize(16);
                            qty.setTextColor(Color.parseColor("#000000"));
                            qty.setPadding(8, 12, 8, 28);
                            qty.setMaxLines(1);
                            TextView qty2 = new TextView(activity_rankinglocais.this);
                            qty2.setText("Ranking de Locais");
                            qty2.setTypeface(null, Typeface.BOLD);
                            qty2.setTextSize(16);
                            qty2.setTextColor(Color.parseColor("#000000"));
                            qty2.setPadding(8, 12, 8, 28);
                            qty2.setMaxWidth(screenWidth - 260);
                            qty2.setMaxLines(1);
                            TextView qty3 = new TextView(activity_rankinglocais.this);
                            qty3.setText(" ");
                            qty3.setTextSize(16);
                            qty3.setTextColor(Color.parseColor("#000000"));
                            qty3.setPadding(8, 12, 0, 28);
                            qty3.setMaxWidth(40);
                            TextView qty4 = new TextView(activity_rankinglocais.this);
                            qty4.setText("Nota");
                            qty4.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                            qty4.setTypeface(null, Typeface.BOLD);
                            qty4.setTextSize(16);
                            qty4.setTextColor(Color.parseColor("#000000"));
                            qty4.setPadding(0, 12, 16, 28);
                            qty4.setMaxLines(1);
                            row.addView(qty);
                            row.addView(qty2);
                            row.addView(qty3);
                            row.addView(qty4);
                            ll.addView(row, i);
                        }
                        else
                        {
                            String accuratenum = String.valueOf(i);
                            String localnum = "local" + accuratenum;

                            //Adicionando um novo row a table layout
                            TableRow row = new TableRow(activity_rankinglocais.this);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);
                            TextView qty = new TextView(activity_rankinglocais.this);
                            qty.setText("" + i);
                            qty.setTextColor(Color.parseColor("#000000"));
                            qty.setPadding(8, 0, 4, 20);
                            qty.setTextSize(15);
                            qty.setMaxLines(1);
                            TextView qty2 = new TextView(activity_rankinglocais.this);
                            int j = i - 1;
                            qty2.setText(lista[j]);
                            qty2.setTextColor(Color.parseColor("#000000"));
                            qty2.setPadding(12, 0, 8, 20);
                            qty2.setTextSize(15);
                            qty2.setMaxWidth(screenWidth - 260);
                            qty2.setMaxLines(2);
                            ImageView qty3 = new ImageView(activity_rankinglocais.this);
                            TextView qty4 = new TextView(activity_rankinglocais.this);
                            int k = i + 9;
                            if (Float.parseFloat(lista[k]) >= 7.0f)
                            {
                                qty3.setImageResource(R.drawable.ic_stargreen);
                                qty4.setTextColor(Color.parseColor("#048823"));
                            }
                            else if (Float.parseFloat(lista[k]) >= 5.0f)
                            {
                                qty3.setImageResource(R.drawable.ic_staryellow);
                                qty4.setTextColor(Color.parseColor("#A99E00"));
                            }
                            else
                            {
                                qty3.setImageResource(R.drawable.ic_starred);
                                qty4.setTextColor(Color.parseColor("#A91F00"));
                            }
                            qty3.setPadding(8, 0, 0, 20);
                            qty3.setMaxWidth(40);
                            qty4.setText(lista[k]);
                            qty4.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                            qty4.setPadding(0, 0, 32, 20);
                            qty4.setTextSize(15);
                            qty4.setMaxLines(1);
                            row.addView(qty);
                            row.addView(qty2);
                            row.addView(qty3);
                            row.addView(qty4);
                            ll.addView(row, i);
                            if (i == 10)
                            {
                                pbCarregando.setVisibility(View.GONE);
                            }
                        }
                    }
                    //row vazia só pra última gerada não ficar grudada no fim da tela
                    TableRow row = new TableRow(activity_rankinglocais.this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView qty = new TextView(activity_rankinglocais.this);
                    qty.setText(" ");
                    qty.setPadding(8, 3, 8, 3);
                    qty.setTextSize(16);
                    TextView qty2 = new TextView(activity_rankinglocais.this);
                    qty2.setText(" ");
                    qty2.setPadding(8, 3, 8, 3);
                    qty2.setTextSize(16);
                    TextView qty3 = new TextView(activity_rankinglocais.this);
                    qty3.setText(" ");
                    qty3.setPadding(8, 3, 8, 3);
                    qty3.setTextSize(16);
                    TextView qty4 = new TextView(activity_rankinglocais.this);
                    qty4.setText(" ");
                    qty4.setPadding(8, 3, 8, 3);
                    qty4.setTextSize(16);
                    row.addView(qty);
                    row.addView(qty2);
                    row.addView(qty3);
                    row.addView(qty4);
                    ll.addView(row, 16);
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
                Toast.makeText(activity_rankinglocais.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();

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
        //Log.d("tag","In activity_rankinglocais onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_rankinglocais onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_rankinglocais onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_rankinglocais onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_rankinglocais onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivRankArrowback :
                //  Log de onClick  //
                //Log.d("tag","onClick no ArrowLeft");

                finish();
                break;
        }
    }

}
