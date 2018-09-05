package alefe.aps_sistemas_distribuidos.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;

public class activity_pesquisarlocais extends AppCompatActivity implements View.OnClickListener
{
    AutoCompleteTextView actvPLLocal;
    TextView tvPLLocalinv;
    ProgressBar pbBuscando, pbCarregandolista;
    Spinner spPLPoluicao, spPLTransito, spPLAlagamento, spPLInundacoes, spPLDesmatamento;
    String locais[], selectedPoluicaoText, selectedTransitoText, selectedAlagamentoText, selectedInundacoesText, selectedDesmatamentoText;
    String[] poluicao       = new String[]{"Selecione", "Baixa", "Media", "Alta"};
    String[] transito       = new String[]{"Selecione", "Livre", "Médio", "Intenso"};
    String[] alagamento     = new String[]{"Selecione", "Nenhum Risco", "Baixo Risco", "Médio Risco", "Alto Risco"};
    String[] inundacoes     = new String[]{"Selecione", "Nenhum Risco", "Baixo Risco", "Médio Risco", "Alto Risco"};
    String[] desmatamento   = new String[]{"Selecione", "Zero", "Baixo", "Médio", "Alto"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_pesquisarlocais onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_pesquisarlocais);


        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivPLArrowback = findViewById(R.id.ivPLArrowback);

        //  Spinner's //
        spPLPoluicao             = findViewById(R.id.spPLPoluicao);
        spPLTransito             = findViewById(R.id.spPLTransito);
        spPLAlagamento           = findViewById(R.id.spPLAlagamento);
        spPLInundacoes           = findViewById(R.id.spPLInundacoes);
        spPLDesmatamento         = findViewById(R.id.spPLDesmatamento);

        //  AutoCompleteTextView's //
        actvPLLocal              = findViewById(R.id.actvPLLocal);

        //  CardView's  //
        CardView cvBRaBuscar     = findViewById(R.id.cvPLPesquisar);

        //  TextView's  //
        tvPLLocalinv             = findViewById(R.id.tvPLLocalinv);

        //  ProgressBar's   //
        pbBuscando               = findViewById(R.id.pbBuscando);
        pbCarregandolista        = findViewById(R.id.pbCarregandolista);

        pbCarregandolista.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivPLArrowback.setOnClickListener((View.OnClickListener) this);
        cvBRaBuscar.setOnClickListener((View.OnClickListener) this);



        ///////////////////////////////////////////
        //    AutoCompleteTextView: Locais       //
        ///////////////////////////////////////////

        //CONEXÃO VOLLEY COMEÇA AQUI
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        //Link pra puxar a STRING com todos os locais.
        String url ="https://dkvox.com.br/AMBIENTETESTE/SD/listalocais.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //Tratar essa STRING ainda inteira dando replaces para tirar as aspas e substituir os códigos “u00...” pelas letras com acento:
                response = response.replace("\"","")
                        .replace("\\u00e3","ã")
                        .replace("\\u00f5","õ")
                        .replace("\\u00f1","ñ")
                        .replace("\\u00e1","á")
                        .replace("\\u00e9","é")
                        .replace("\\u00ed","í")
                        .replace("\\u00f3","ó")
                        .replace("\\u00fa","ú")
                        .replace("\\u00e2","â")
                        .replace("\\u00ea","ê")
                        .replace("\\u00ee","î")
                        .replace("\\u00f4","ô")
                        .replace("\\u00fb","û")
                        .replace("\\u00e7","ç");

                //Depois de tratar, quebrar a STRING em vários campos e armazenar cada um num campo do ARRAY DE STRINGS:
                locais = response.split(",");

                //AutoCompleteTextView: linka, puxa lista de itens válidos, seta o validador.
                actvPLLocal.setAdapter(new ArrayAdapter<String>(activity_pesquisarlocais.this, android.R.layout.simple_dropdown_item_1line, locais));
                actvPLLocal.setValidator(new Validator());
                actvPLLocal.setOnFocusChangeListener(new FocusListener());

                pbCarregandolista.setVisibility(View.GONE);
            }
        }
                , new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Display an error if the connection failed
                //Log.e("TAG","Volley connection miserably failed.");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);



        ///////////////////////////////////////////////////////////
        //                      Spinners                         //
        ///////////////////////////////////////////////////////////

        //  Poluicao do Ar    //
        ArrayAdapter<String> adapterPoluicao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, poluicao);
        adapterPoluicao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPLPoluicao.setAdapter(adapterPoluicao);
        spPLPoluicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedPoluicaoText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "Poluicao Selected: " + selectedPoluicaoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedPoluicaoText = "Selecione";
                //Log.d("tag", "Poluicao Selected: " + selectedPoluicaoText);
            }
        });

        //  Transito    //
        ArrayAdapter<String> adapterTransito = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, transito);
        adapterTransito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPLTransito.setAdapter(adapterTransito);
        spPLTransito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedTransitoText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "Transito Selected: " + selectedTransitoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedTransitoText = "Selecione";
                //Log.d("tag", "Transito Selected: " + selectedTransitoText);
            }
        });

        //  Alagamento    //
        ArrayAdapter<String> adapterAlagamento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alagamento);
        adapterAlagamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPLAlagamento.setAdapter(adapterAlagamento);
        spPLAlagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedAlagamentoText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "Alagamento Selected: " + selectedAlagamentoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedAlagamentoText = "Selecione";
                //Log.d("tag", "Alagamento Selected: " + selectedAlagamentoText);
            }
        });

        //  Inundacao    //
        ArrayAdapter<String> adapterInundacao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, inundacoes);
        adapterInundacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPLInundacoes.setAdapter(adapterInundacao);
        spPLInundacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedInundacoesText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "Inundacao Selected: " + selectedInundacoesText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedInundacoesText = "Selecione";
                //Log.d("tag", "Inundacao Selected: " + selectedInundacoesText);
            }
        });

        //  Desmatamento    //
        ArrayAdapter<String> adapterDesmatamento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, poluicao);
        adapterDesmatamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPLDesmatamento.setAdapter(adapterDesmatamento);
        spPLDesmatamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedDesmatamentoText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "Desmatamento Selected: " + selectedDesmatamentoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedDesmatamentoText = "Selecione";
                //Log.d("tag", "Desmatamento Selected: " + selectedDesmatamentoText);
            }
        });

    }


    //VALIDADOR Verifica se o Local inputado é VÁLIDO (se está na lista de itens válidos)
    class Validator implements AutoCompleteTextView.Validator
    {
        @Override
        public boolean isValid(CharSequence text)
        {
            //Log.v("tag", "Checking if local valid: " + text);
            Arrays.sort(locais);
            if (Arrays.binarySearch(locais, text.toString()) > 0)
            {
                return true;
            }
            return false;
        }
        //ARRUMADOR Se o item inputado for INVÁLIDO, oque fazer e oque retornar como input
        @Override
        public CharSequence fixText(CharSequence invalidText)
        {
            //Log.v("tag", "Returning hospital fixed text");
            tvPLLocalinv.setVisibility(View.VISIBLE);
            actvPLLocal.setText("");
            return "";
        }
    }

    //OUVINTE DO FOCO A verificação se o item é válido só ocorre quando o foco sai dele, essa classe verifica quando esse foco muda
    class FocusListener implements View.OnFocusChangeListener
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            //Log.v("tag", "Focus changed");
            if (v.getId() == R.id.actvPLLocal && !hasFocus)
            {
                //Log.v("tag", "Performing local validation");
                ((AutoCompleteTextView) v).performValidation();
            }
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag", "In activity_pesquisarlocais onStart");
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag", "In activity_pesquisarlocais onResume");
    }


    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag", "In activity_pesquisarlocais onPause");
    }


    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag", "In activity_pesquisarlocais onStop");
    }


    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag", "In activity_pesquisarlocais onDestroy");
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivPLArrowback:
                //  Log de onClick  //
                //Log.d("tag", "onClick no ArrowLeft");

                finish();
                break;


            case R.id.cvPLPesquisar:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvPLPesquisar");

                    //VALIDA se o local inputado é válido
                    tvPLLocalinv.setVisibility(View.GONE);
                    actvPLLocal.performValidation();

                    //OBTÉM o conteúdo dos filtros
                    String localF        = actvPLLocal.getText().toString().replaceAll("[ ]", "%20");
                    String poluicaoF     = selectedPoluicaoText.replaceAll("[ ]", "%20");
                    String transitoF     = selectedTransitoText.replaceAll("[ ]", "%20");
                    String alagamentoF   = selectedAlagamentoText.replaceAll("[ ]", "%20");
                    String inundacaoF    = selectedInundacoesText.replaceAll("[ ]", "%20");
                    String desmatamentoF = selectedDesmatamentoText.replaceAll("[ ]", "%20");

                    //LOG pra verificar valores finais
                    /*Log.d("tag", ", localF: " + localF
                            + ", poluicaoF: " + poluicaoF);*/


                    //SE NÃO TIVER NENHUM FILTRO ESPECIFICADO, AVISAR E NÃO BUSCAR:
                    if (localF.length() <= 3
                            && poluicaoF.equals("Selecione"))
                    {
                        Toast.makeText(activity_pesquisarlocais.this, R.string.strNadaSelecionado, Toast.LENGTH_SHORT).show();
                        actvPLLocal.requestFocus();
                    }
                    else
                    {
                        //SE LOCAL FOI ESPECIFICADO, e tiver mais filtros especificados além do local, avisar ao cliente que esses filtros extras serão ignorados.
                        if (localF.length() > 3
                                || !poluicaoF.equals("Selecione"))
                        {
                            Toast.makeText(activity_pesquisarlocais.this, R.string.strLocalEspecifico, Toast.LENGTH_SHORT).show();
                        }

                        Intent intentPLPesquisar = new Intent(activity_pesquisarlocais.this, activity_resultadopesquisa.class);
                        intentPLPesquisar.putExtra("localF", localF);
                        intentPLPesquisar.putExtra("poluicaoF", poluicaoF);
                        intentPLPesquisar.putExtra("tituloDaActivity", "Pesquisar Locais");
                        startActivity(intentPLPesquisar);
                    }
                break;
        }
    }
}
