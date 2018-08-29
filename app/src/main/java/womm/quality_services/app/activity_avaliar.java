package womm.quality_services.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import womm.quality_services.app.R;

public class activity_avaliar extends AppCompatActivity implements View.OnClickListener
{
    RatingBar rbAvaEspera, rbAvaEstacionamento, rbAvaRecepcao, rbAvaOrganizacao, rbAvaSinalizacao, rbAvaCordialidade, rbAvaLimpeza, rbAvaTriagem, rbAvaMedico, rbAvaEnfermaria, rbAvaExames, rbAvaInternacao;
    EditText etAvaData, etAvaObservacao;
    CheckBox cbAvaEstacionamento, cbAvaTriagem, cbAvaEnfermaria, cbAvaExames, cbAvaInternacao;
    String[] hospitais;
    AutoCompleteTextView actvAvaHospital;
    String strDataAtualFormatada;
    ProgressBar pbEnviando, pbCarregandolista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_avaliar onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_avaliar);


        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivAvaArrowback =    findViewById(R.id.ivAvaArrowback);

        //  EditText's    //
        etAvaData                =    findViewById(R.id.etAvaData);
        etAvaObservacao          =    findViewById(R.id.etAvaObservacao);

        //  CardView's  //
        CardView cvAvaEnviar     =    findViewById(R.id.cvAvaEnviar);

        //  RatingBar's  //
        rbAvaEspera              =    findViewById(R.id.rbAvaEspera);
        rbAvaEstacionamento      =    findViewById(R.id.rbAvaEstacionamento);
        rbAvaRecepcao            =    findViewById(R.id.rbAvaRecepcao);
        rbAvaOrganizacao         =    findViewById(R.id.rbAvaOrganizacao);
        rbAvaSinalizacao         =    findViewById(R.id.rbAvaSinalizacao);
        rbAvaCordialidade        =    findViewById(R.id.rbAvaCordialidade);
        rbAvaLimpeza             =    findViewById(R.id.rbAvaLimpeza);
        rbAvaTriagem             =    findViewById(R.id.rbAvaTriagem);
        rbAvaMedico              =    findViewById(R.id.rbAvaMedico);
        rbAvaEnfermaria          =    findViewById(R.id.rbAvaEnfermaria);
        rbAvaExames              =    findViewById(R.id.rbAvaExames);
        rbAvaInternacao          =    findViewById(R.id.rbAvaInternacao);

        //  CheckBox's  //
        cbAvaEstacionamento      =    findViewById(R.id.cbAvaEstacionamento);
        cbAvaTriagem             =    findViewById(R.id.cbAvaTriagem);
        cbAvaEnfermaria          =    findViewById(R.id.cbAvaEnfermaria);
        cbAvaExames              =    findViewById(R.id.cbAvaExames);
        cbAvaInternacao          =    findViewById(R.id.cbAvaInternacao);

        //  ProgressBar's   //
        pbEnviando               = findViewById(R.id.pbEnviando);
        pbCarregandolista        = findViewById(R.id.pbCarregandolista);

        pbCarregandolista.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivAvaArrowback.setOnClickListener((View.OnClickListener) this);
        cvAvaEnviar.setOnClickListener((View.OnClickListener) this);
        cbAvaEstacionamento.setOnClickListener((View.OnClickListener) this);
        cbAvaTriagem.setOnClickListener((View.OnClickListener) this);
        cbAvaEnfermaria.setOnClickListener((View.OnClickListener) this);
        cbAvaExames.setOnClickListener((View.OnClickListener) this);
        cbAvaInternacao.setOnClickListener((View.OnClickListener) this);



        ///////////////////////////////////////////
        //    AutoCompleteTextView: HOSPITAL     //
        ///////////////////////////////////////////

        //CONEXÃO VOLLEY COMEÇA AQUI
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        //Link pra puxar a STRING com todos os hospitais. Esse link será sempre atualizado, retornando os hospitais atuais do BD, então sempre puxar a lista por ele.:
        String url ="https://dkvox.ngrok.io/qservices/hospitallist.json";
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
                actvAvaHospital = findViewById(R.id.actvAvaHospital);
                actvAvaHospital.setAdapter(new ArrayAdapter<String>(activity_avaliar.this, android.R.layout.simple_dropdown_item_1line, hospitais));
                actvAvaHospital.setValidator(new activity_avaliar.Validator());
                actvAvaHospital.setOnFocusChangeListener(new activity_avaliar.FocusListener());

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



        /////////////////////
        //      DATA       //
        /////////////////////

        //Pega e Formata a Data atual
        SimpleDateFormat formatoDaData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date();
        strDataAtualFormatada = formatoDaData.format(dataAtual);

        //Data exemplo (Hint) do EditText etAvaData sempre ser o dia atual
        etAvaData.setHint("Ex.: " + strDataAtualFormatada);

        //Aplicando Mask (dd/mm/yyyy) ao que for inputado no EditText etAvaData
        etAvaData.addTextChangedListener(MaskEdittextUtil.mask(etAvaData, MaskEdittextUtil.FORMAT_DATE));



    }


    //Verifica se o item inputado é valido (se está na lista de itens válidos)
    class Validator implements AutoCompleteTextView.Validator
    {
        @Override
        public boolean isValid(CharSequence text)
        {
            //Log.v("tag", "Checking if hospital valid: "+ text);
            Arrays.sort(hospitais);
            if (Arrays.binarySearch(hospitais, text.toString()) > 0)
            {
                return true;
            }
            return false;
        }
        //Se o item inputado for inválido, oque fazer e oque retornar como input
        @Override
        public CharSequence fixText(CharSequence invalidText)
        {
            //Log.v("tag", "Returning hospital fixed text");

            Toast.makeText(activity_avaliar.this, R.string.strHospitalInvalido, Toast.LENGTH_LONG).show();
            actvAvaHospital.setText("");
            return "";
        }
    }


    //A verificação se o item é válido só ocorre quando o foco sai dele, essa classe verifica quando esse foco muda
    class FocusListener implements View.OnFocusChangeListener
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            //Log.v("tag", "Focus changed");
            if (v.getId() == R.id.actvAvaHospital && !hasFocus)
            {
                //Log.v("tag", "Performing hospital validation");
                ((AutoCompleteTextView)v).performValidation();
            }
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_avaliar onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_avaliar onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_avaliar onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_avaliar onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_avaliar onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivAvaArrowback :
                //  Log de onClick  //
                //Log.d("tag","onClick no ArrowLeft");

                finish();
                break;


            case R.id.cbAvaEstacionamento :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaEstacionamento");

                // Is the view now checked?
                boolean checked1 = cbAvaEstacionamento.isChecked();
                if (checked1)
                {
                    rbAvaEstacionamento.setVisibility(View.GONE);
                }
                else
                {
                    rbAvaEstacionamento.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAvaTriagem :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaTriagem");

                // Is the view now checked?
                boolean checked2 = cbAvaTriagem.isChecked();
                if (checked2)
                {
                    rbAvaTriagem.setVisibility(View.GONE);
                }
                else
                {
                    rbAvaTriagem.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAvaEnfermaria :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaEnfermaria");

                // Is the view now checked?
                boolean checked3 = cbAvaEnfermaria.isChecked();
                if (checked3)
                {
                    rbAvaEnfermaria.setVisibility(View.GONE);
                }
                else
                {
                    rbAvaEnfermaria.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAvaExames :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaExames");

                // Is the view now checked?
                boolean checked4 = cbAvaExames.isChecked();
                if (checked4)
                {
                    rbAvaExames.setVisibility(View.GONE);
                }
                else
                {
                    rbAvaExames.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAvaInternacao :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaInternacao");

                // Is the view now checked?
                boolean checked5 = cbAvaInternacao.isChecked();
                if (checked5)
                {
                    rbAvaInternacao.setVisibility(View.GONE);
                }
                else
                {
                    rbAvaInternacao.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cvAvaEnviar :
                //  Log de onClick  //
                //Log.d("tag","onClick no cvAvaEnviar");

                //VALIDA se o Hospital inputado é válido
                actvAvaHospital.performValidation();

                //VALIDA se a Data inputada é válida
                if ((etAvaData.getText().toString()).length() == 10)
                {
                    //Validando data inputada pra ver se não está no futuro ou se não faz mais de 2 anos
                    String[] dataAtualSplitada = strDataAtualFormatada.split("/");
                    String[] dataInputSplitada = (etAvaData.getText().toString()).split("/");

                    //compara se ano inputado é > que ano atual
                    if (Integer.parseInt(dataInputSplitada[1]) > 12 || Integer.parseInt(dataInputSplitada[0]) > 31)
                    {
                        Toast.makeText(activity_avaliar.this, R.string.strDataInvalida, Toast.LENGTH_LONG).show();
                        etAvaData.requestFocus();
                    }
                    else if (Integer.parseInt(dataInputSplitada[2]) > Integer.parseInt(dataAtualSplitada[2]))
                    {
                        Toast.makeText(activity_avaliar.this, R.string.strDataInvalidaF, Toast.LENGTH_LONG).show();
                        etAvaData.requestFocus();
                    }
                    //compara se ano inputado é = o ano atual e o mês inputado é > que o mes atual
                    else if (Integer.parseInt(dataInputSplitada[2]) == Integer.parseInt(dataAtualSplitada[2]) && Integer.parseInt(dataInputSplitada[1]) > Integer.parseInt(dataAtualSplitada[1]))
                    {
                        Toast.makeText(activity_avaliar.this, R.string.strDataInvalidaF, Toast.LENGTH_LONG).show();
                        etAvaData.requestFocus();
                    }
                    //compara se ano inputado é = o ano atual e o mês inputado é = o mes atual  e o dia inputado é > que o dia atual
                    else if (Integer.parseInt(dataInputSplitada[2]) == Integer.parseInt(dataAtualSplitada[2]) && Integer.parseInt(dataInputSplitada[1]) == Integer.parseInt(dataAtualSplitada[1]) && Integer.parseInt(dataInputSplitada[0]) > Integer.parseInt(dataAtualSplitada[0]))
                    {
                        Toast.makeText(activity_avaliar.this, R.string.strDataInvalidaF, Toast.LENGTH_LONG).show();
                        etAvaData.requestFocus();
                    }
                    //compara se ano inputado é < que o ano atual -2
                    else if (Integer.parseInt(dataInputSplitada[2]) < (Integer.parseInt(dataAtualSplitada[2])) - 1)
                    {
                        Toast.makeText(activity_avaliar.this, R.string.strDataInvalidaA, Toast.LENGTH_LONG).show();
                        etAvaData.requestFocus();
                    }
                    //data é valida
                    else
                    {
                        //TRATA as entradas
                        float espera        = rbAvaEspera.getRating();
                        float recepcao      = rbAvaRecepcao.getRating();
                        float organizacao   = rbAvaOrganizacao.getRating();
                        float sinalizacao   = rbAvaSinalizacao.getRating();
                        float cordialidade  = rbAvaCordialidade.getRating();
                        float limpeza       = rbAvaLimpeza.getRating();
                        float medico        = rbAvaMedico.getRating();
                        float estacionamento, triagem, enfermaria, exames, internacao;
                        if (cbAvaEstacionamento.isChecked())
                        {
                            estacionamento = 5.5f;
                        }
                        else
                        {
                            estacionamento = rbAvaEstacionamento.getRating();
                        }
                        if (cbAvaTriagem.isChecked())
                        {
                            triagem = 5.5f;
                        }
                        else
                        {
                            triagem = rbAvaTriagem.getRating();
                        }
                        if (cbAvaEnfermaria.isChecked())
                        {
                            enfermaria = 5.5f;
                        }
                        else
                        {
                            enfermaria = rbAvaEnfermaria.getRating();
                        }
                        if (cbAvaExames.isChecked())
                        {
                            exames = 5.5f;
                        }
                        else
                        {
                            exames = rbAvaExames.getRating();
                        }
                        if (cbAvaInternacao.isChecked())
                        {
                            internacao = 5.5f;
                        }
                        else
                        {
                            internacao = rbAvaInternacao.getRating();
                        }

                        //VARIAVEIS FINAIS pra jogar no BD. //Obs: SE A NOTA FOR "11", NÃO UTILIZAR NOS CÁLCULOS.
                        String hospitalF    = actvAvaHospital.getText().toString().replaceAll("[ ]", "%20");
                        String dataF        = etAvaData.getText().toString();
                        int esperaF         = (int) (espera * 2);
                        int recepcaoF       = (int) (recepcao * 2);
                        int organizacaoF    = (int) (organizacao * 2);
                        int sinalizacaoF    = (int) (sinalizacao * 2);
                        int cordialidadeF   = (int) (cordialidade * 2);
                        int limpezaF        = (int) (limpeza * 2);
                        int medicoF         = (int) (medico * 2);
                        int estacionamentoF = (int) (estacionamento * 2);
                        int triagemF        = (int) (triagem * 2);
                        int enfermariaF     = (int) (enfermaria * 2);
                        int examesF         = (int) (exames * 2);
                        int internacaoF     = (int) (internacao * 2);
                        String observacaoF  = etAvaObservacao.getText().toString().replaceAll("[ ]", "%20");

                        //Log de teste para verificar se os valores das variáveis estão todos corretos.
                        String teste = hospitalF + "\n\r" + dataF + "\n\r" + esperaF + "\n\r" + recepcaoF + "\n\r" + organizacaoF + "\n\r" + sinalizacaoF + "\n\r" + cordialidadeF + "\n\r" + limpezaF + "\n\r" + medicoF + "\n\r" + estacionamentoF + "\n\r" + triagemF + "\n\r" + enfermariaF + "\n\r" + examesF + "\n\r" + internacaoF + "\n\r" + observacaoF;
                        //Log.d("tag",teste);

                        //VERIFICA se caracteres do hospital inputado nao é menor ou igual a 3
                        if (hospitalF.length() <= 3)
                        {
                            Toast.makeText(activity_avaliar.this, R.string.strHospitalInvalido, Toast.LENGTH_LONG).show();
                            actvAvaHospital.requestFocus();
                        }
                        else if (hospitalF.length() > 3 && dataF.length() == 10)
                        {
                            Toast.makeText(activity_avaliar.this, "Enviando...", Toast.LENGTH_SHORT).show();
                            pbEnviando.setVisibility(View.VISIBLE);

                            //Pega o ID do usuario
                            SharedPreferences wmbPreference;
                            wmbPreference = PreferenceManager.getDefaultSharedPreferences(activity_avaliar.this); //dura pra sempre
                            String idUsuario = wmbPreference.getString("idUsuario", "");
                            //Log.d("TAG","idUsuario: " + idUsuario);

                            if (idUsuario.equals("0"))
                            {
                                Toast.makeText(this, "Sua conexão expirou.", Toast.LENGTH_SHORT).show();

                                //Log.d("tag","ID do usuario e 0.");

                                SharedPreferences wmbPreferencee;
                                SharedPreferences.Editor editorr;

                                wmbPreferencee = PreferenceManager.getDefaultSharedPreferences(activity_avaliar.this); //dura pra sempre
                                editorr = wmbPreferencee.edit();
                                editorr.putString("idUsuario", "0");
                                editorr.putString("isLogged", "N");
                                editorr.commit();
                                //Log.d("TAG", "idUsuario: " + "0" + ", isLogged: " + "N");

                                Intent intentSair = new Intent(activity_avaliar.this, activity_login.class);
                                intentSair.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentSair);
                            }

                            //////////////////////////////////////////
                            //  CONEXÃO VOLLEY COM O BANCO DE DADOS //
                            //////////////////////////////////////////

                            // Instantiate the RequestQueue.
                            RequestQueue queue = Volley.newRequestQueue(this);
                            //Log.d("TAG", "HOSPITAL ENVIADO: " + hospitalF);
                            String url ="http://dkvox.ngrok.io/qservices/avaliar.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&idusuario="+idUsuario+"&nomehospital="+hospitalF+"&tempoespera="+esperaF+"&estacionamento="+estacionamentoF+"&recepcao="+recepcaoF+"&organizacao="+organizacaoF+"&sinalizacao="+sinalizacaoF+"&limpeza="+limpezaF+"&triagem="+triagemF+"&medico="+medicoF+"&enfermaria="+enfermariaF+"&internacao="+internacaoF+"&cordialidade="+cordialidadeF+"&exames="+examesF+"&observacao="+observacaoF+"&datainsert="+dataF;
                            //Log.d("TAG", url);

                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    try
                                    {
                                        JSONObject jObject = new JSONObject(response);
                                        final String resp = jObject.getString("resposta");
                                        //Log.d("TAG", "resp: " + resp);

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                //Log.d("TAG", "resp 2: " + resp);
                                                if (resp.equals("Inserido com sucesso"))
                                                {
                                                    //Confirmação de envio da avaliação.
                                                    Toast.makeText(activity_avaliar.this, R.string.toastAvaEnviadaLabel, Toast.LENGTH_SHORT).show();
                                                    Intent intentEnviar = new Intent(activity_avaliar.this, activity_principal.class);
                                                    intentEnviar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intentEnviar);
                                                    pbEnviando.setVisibility(View.INVISIBLE);
                                                }
                                                else
                                                {
                                                    handler.postDelayed(this, 1000);
                                                }
                                            }
                                        }, 1000);
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
                                    Toast.makeText(activity_avaliar.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                                }
                            });

                            // Add the request to the RequestQueue.
                            queue.add(stringRequest);

                            ///////////////////////////////////////////////////
                            //  FINAL DA CONEXÃO VOLLEY COM O BANCO DE DADOS //
                            ///////////////////////////////////////////////////

                        }
                    }
                }
                //VERIFICA se data inputada é menor ou maior q o numero de caracteres correto
                else if ((etAvaData.getText().toString()).length() < 10 || (etAvaData.getText().toString()).length() > 10)
                {
                    Toast.makeText(activity_avaliar.this, R.string.strDataInvalida, Toast.LENGTH_LONG).show();
                    etAvaData.requestFocus();
                }
                break;

        }
    }
}
