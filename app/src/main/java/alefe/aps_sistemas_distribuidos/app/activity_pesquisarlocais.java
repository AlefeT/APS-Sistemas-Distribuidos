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
    AutoCompleteTextView actvBraHospital;
    TextView tvBraHospinv;
    ProgressBar pbBuscando, pbCarregandolista;
    Spinner spBraTempoespera;
    String  hospitais[], selectedTempoesperaText;
    String[] tempoespera = new String[]{"Selecione", "Até 30min", "Até 1 hora", "Até 1 hora e 30min", "Mais de 1 hora e 30min"};

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
        ImageView ivBRaArrowback = findViewById(R.id.ivPLArrowback);

        //  Spinner's //
        spBraTempoespera         = findViewById(R.id.spPLAlagamento);

        //  AutoCompleteTextView's //
        actvBraHospital          = findViewById(R.id.actvPLLocal);

        //  CardView's  //
        CardView cvBRaBuscar     = findViewById(R.id.cvBRaBuscar);

        //  TextView's  //
        tvBraHospinv             = findViewById(R.id.tvPLLocalinv);

        //  ProgressBar's   //
        pbBuscando               = findViewById(R.id.pbBuscando);
        pbCarregandolista        = findViewById(R.id.pbCarregandolista);

        pbCarregandolista.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivBRaArrowback.setOnClickListener((View.OnClickListener) this);
        cvBRaBuscar.setOnClickListener((View.OnClickListener) this);



        ///////////////////////////////////////////
        //    AutoCompleteTextView: Hospital     //
        ///////////////////////////////////////////

        //CONEXÃO VOLLEY COMEÇA AQUI
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        //Link pra puxar a STRING com todos os hospitais. Esse link será sempre atualizado, retornando os hospitais atuais do BD, então sempre puxar a lista por ele.:
        String url ="http://dkvox.ngrok.io/qservices/hospitallist.json";
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
                hospitais = response.split(",");

                //AutoCompleteTextView: linka, puxa lista de itens válidos, seta o validador.
                actvBraHospital.setAdapter(new ArrayAdapter<String>(activity_pesquisarlocais.this, android.R.layout.simple_dropdown_item_1line, hospitais));
                actvBraHospital.setValidator(new Validator());
                actvBraHospital.setOnFocusChangeListener(new FocusListener());

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
        //    Spinners: Convenio, Distancia, Tempo de Espera     //
        ///////////////////////////////////////////////////////////

        //  Tempos de Espera    //
        ArrayAdapter<String> adapterTempoespera = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tempoespera);
        adapterTempoespera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBraTempoespera.setAdapter(adapterTempoespera);
        spBraTempoespera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedTempoesperaText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "TempoEspera Selected: " + selectedTempoesperaText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedTempoesperaText = "Selecione";
                //Log.d("tag", "TempoEspera Selected: " + selectedTempoesperaText);
            }
        });

    }


    //VALIDADOR Verifica se o Hospital inputado é VÁLIDO (se está na lista de itens válidos)
    class Validator implements AutoCompleteTextView.Validator
    {
        @Override
        public boolean isValid(CharSequence text)
        {
            //Log.v("tag", "Checking if hospital valid: " + text);
            Arrays.sort(hospitais);
            if (Arrays.binarySearch(hospitais, text.toString()) > 0)
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
            tvBraHospinv.setVisibility(View.VISIBLE);
            actvBraHospital.setText("");
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
                //Log.v("tag", "Performing hospital validation");
                ((AutoCompleteTextView) v).performValidation();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request
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


            case R.id.cvBRaBuscar:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvBRaBuscar");

                    //Log.d("tag", "click no buscar e Podepassar = true");

                    //VALIDA se o Hospital inputado é válido
                    tvBraHospinv.setVisibility(View.GONE);
                    actvBraHospital.performValidation();

                    //OBTÉM o conteúdo dos filtros
                    String hospitalF    = actvBraHospital.getText().toString().replaceAll("[ ]", "%20");
                    String tempoesperaF = selectedTempoesperaText.replaceAll("[ ]", "%20");

                    //LOG pra verificar valores finais
                    /*Log.d("tag", "posicao: " + posicao
                            + ", hospitalF: " + hospitalF
                            + ", convenioF: " + convenioF
                            + ", distanciaF: " + distanciaF
                            + ", tempoesperaF: " + tempoesperaF);*/


                    //SE NÃO TIVER NENHUM FILTRO ESPECIFICADO, AVISAR E NÃO BUSCAR:
                    if (hospitalF.length() <= 3
                            && tempoesperaF.equals("Selecione"))
                    {
                        Toast.makeText(activity_pesquisarlocais.this, R.string.strNadaSelecionado, Toast.LENGTH_SHORT).show();
                        actvBraHospital.requestFocus();
                    }
                    else
                    {
                        //SE HOSPITAL FOI ESPECIFICADO, e tiver mais filtros especificados além do hospital, avisar ao cliente que esses filtros extras serão ignorados.
                        if (hospitalF.length() > 3
                                || !tempoesperaF.equals("Selecione"))
                        {
                            Toast.makeText(activity_pesquisarlocais.this, R.string.strLocalEspecifico, Toast.LENGTH_SHORT).show();
                        }

                        Intent intentBRaBuscar = new Intent(activity_pesquisarlocais.this, activity_resultadopesquisa.class);
                        intentBRaBuscar.putExtra("hospitalF", hospitalF);
                        intentBRaBuscar.putExtra("tempoesperaF", tempoesperaF);
                        intentBRaBuscar.putExtra("tituloDaActivity", "Busca Rápida - Pronto Atendimento");
                        startActivity(intentBRaBuscar);
                    }
                break;
        }
    }
}
