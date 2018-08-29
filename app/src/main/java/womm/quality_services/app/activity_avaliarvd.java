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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import womm.quality_services.app.R;

public class activity_avaliarvd extends AppCompatActivity implements View.OnClickListener
{
    RatingBar rbAVDEspera, rbAVDEstacionamento, rbAVDRecepcao, rbAVDOrganizacao, rbAVDSinalizacao, rbAVDCordialidade, rbAVDLimpeza, rbAVDTriagem, rbAVDMedico, rbAVDEnfermaria, rbAVDExames, rbAVDInternacao;
    EditText etAVDData, etAVDObservacao;
    CheckBox cbAVDEstacionamento, cbAVDTriagem, cbAVDEnfermaria, cbAVDExames, cbAVDInternacao;
    String strDataAtualFormatada, hospitalSelecionado;
    TextView tvAVDHospitalVD;
    ProgressBar pbEnviandovd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_avaliarvd onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_avaliarvd);


        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivAVDArrowback =    findViewById(R.id.ivAVDArrowback);
        ImageView ivAVDHome      =    findViewById(R.id.ivAVDHome);

        //  EditText's    //
        etAVDData                =    findViewById(R.id.etAVDData);
        etAVDObservacao          =    findViewById(R.id.etAVDObservacao);

        //  CardView's  //
        CardView cvAVDEnviar     =    findViewById(R.id.cvAVDEnviar);

        //  RatingBar's  //
        rbAVDEspera              =    findViewById(R.id.rbAVDEspera);
        rbAVDEstacionamento      =    findViewById(R.id.rbAVDEstacionamento);
        rbAVDRecepcao            =    findViewById(R.id.rbAVDRecepcao);
        rbAVDOrganizacao         =    findViewById(R.id.rbAVDOrganizacao);
        rbAVDSinalizacao         =    findViewById(R.id.rbAVDSinalizacao);
        rbAVDCordialidade        =    findViewById(R.id.rbAVDCordialidade);
        rbAVDLimpeza             =    findViewById(R.id.rbAVDLimpeza);
        rbAVDTriagem             =    findViewById(R.id.rbAVDTriagem);
        rbAVDMedico              =    findViewById(R.id.rbAVDMedico);
        rbAVDEnfermaria          =    findViewById(R.id.rbAVDEnfermaria);
        rbAVDExames              =    findViewById(R.id.rbAVDExames);
        rbAVDInternacao          =    findViewById(R.id.rbAVDInternacao);

        //  CheckBox's  //
        cbAVDEstacionamento      =    findViewById(R.id.cbAVDEstacionamento);
        cbAVDTriagem             =    findViewById(R.id.cbAVDTriagem);
        cbAVDEnfermaria          =    findViewById(R.id.cbAVDEnfermaria);
        cbAVDExames              =    findViewById(R.id.cbAVDExames);
        cbAVDInternacao          =    findViewById(R.id.cbAVDInternacao);

        //  TextView's  //
        tvAVDHospitalVD          =    findViewById(R.id.tvAVDHospitalVD);

        //  ProgressBar's   //
        pbEnviandovd             = findViewById(R.id.pbEnviandovd);


        //Get intents
        Bundle bundle            = getIntent().getExtras();
        hospitalSelecionado      = bundle.getString("hospitalSelecionado");

        //Seta o hospital informado pelo VerDetalhes
        //Log.d("tag", "hospital: " + hospitalSelecionado);
        tvAVDHospitalVD.setText(hospitalSelecionado);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivAVDArrowback.setOnClickListener((View.OnClickListener) this);
        ivAVDHome.setOnClickListener((View.OnClickListener) this);
        cvAVDEnviar.setOnClickListener((View.OnClickListener) this);
        cbAVDEstacionamento.setOnClickListener((View.OnClickListener) this);
        cbAVDTriagem.setOnClickListener((View.OnClickListener) this);
        cbAVDEnfermaria.setOnClickListener((View.OnClickListener) this);
        cbAVDExames.setOnClickListener((View.OnClickListener) this);
        cbAVDInternacao.setOnClickListener((View.OnClickListener) this);



        /////////////////////
        //      DATA       //
        /////////////////////

        //Pega e Formata a Data atual
        SimpleDateFormat formatoDaData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date();
        strDataAtualFormatada = formatoDaData.format(dataAtual);

        //Data exemplo (Hint) do EditText etAVDData sempre ser o dia atual
        etAVDData.setHint("Ex.: " + strDataAtualFormatada);

        //Aplicando Mask (dd/mm/yyyy) ao que for inputado no EditText etAVDData
        etAVDData.addTextChangedListener(MaskEdittextUtil.mask(etAVDData, MaskEdittextUtil.FORMAT_DATE));


    }


    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_avaliarvd onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_avaliarvd onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_avaliarvd onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_avaliarvd onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_avaliarvd onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivAVDArrowback :
                //  Log de onClick  //
                //Log.d("tag","onClick no ArrowLeft");

                finish();
                break;

            case R.id.ivAVDHome :
                //  Log de onClick  //
                //Log.d("tag","onClick no Home");

                Intent intentHome = new Intent(activity_avaliarvd.this, activity_principal.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;

            case R.id.cbAVDEstacionamento :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAVDEstacionamento");

                // Is the view now checked?
                boolean checked1 = cbAVDEstacionamento.isChecked();
                if (checked1)
                {
                    rbAVDEstacionamento.setVisibility(View.GONE);
                }
                else
                {
                    rbAVDEstacionamento.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAVDTriagem :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaTriagem");

                // Is the view now checked?
                boolean checked2 = cbAVDTriagem.isChecked();
                if (checked2)
                {
                    rbAVDTriagem.setVisibility(View.GONE);
                }
                else
                {
                    rbAVDTriagem.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAVDEnfermaria :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaEnfermaria");

                // Is the view now checked?
                boolean checked3 = cbAVDEnfermaria.isChecked();
                if (checked3)
                {
                    rbAVDEnfermaria.setVisibility(View.GONE);
                }
                else
                {
                    rbAVDEnfermaria.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAVDExames :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaExames");

                // Is the view now checked?
                boolean checked4 = cbAVDExames.isChecked();
                if (checked4)
                {
                    rbAVDExames.setVisibility(View.GONE);
                }
                else
                {
                    rbAVDExames.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cbAVDInternacao :
                //  Log de onClick  //
                //Log.d("tag","onClick no cbAvaInternacao");

                // Is the view now checked?
                boolean checked5 = cbAVDInternacao.isChecked();
                if (checked5)
                {
                    rbAVDInternacao.setVisibility(View.GONE);
                }
                else
                {
                    rbAVDInternacao.setVisibility(View.VISIBLE);
                }
                break;


            case R.id.cvAVDEnviar :
                //  Log de onClick  //
                //Log.d("tag","onClick no cvAvaEnviar");

                //VALIDA se a Data inputada é válida
                if ((etAVDData.getText().toString()).length() == 10)
                {
                    //Validando data inputada pra ver se não está no futuro ou se não faz mais de 2 anos
                    String[] dataAtualSplitada = strDataAtualFormatada.split("/");
                    String[] dataInputSplitada = (etAVDData.getText().toString()).split("/");

                    //compara se ano inputado é > que ano atual
                    if (Integer.parseInt(dataInputSplitada[1]) > 12 || Integer.parseInt(dataInputSplitada[0]) > 31)
                    {
                        Toast.makeText(activity_avaliarvd.this, R.string.strDataInvalida, Toast.LENGTH_SHORT).show();
                        etAVDData.requestFocus();
                    }
                    else if (Integer.parseInt(dataInputSplitada[2]) > Integer.parseInt(dataAtualSplitada[2]))
                    {
                        Toast.makeText(activity_avaliarvd.this, R.string.strDataInvalidaF, Toast.LENGTH_SHORT).show();
                        etAVDData.requestFocus();
                    }
                    //compara se ano inputado é = o ano atual e o mês inputado é > que o mes atual
                    else if (Integer.parseInt(dataInputSplitada[2]) == Integer.parseInt(dataAtualSplitada[2]) && Integer.parseInt(dataInputSplitada[1]) > Integer.parseInt(dataAtualSplitada[1]))
                    {
                        Toast.makeText(activity_avaliarvd.this, R.string.strDataInvalidaF, Toast.LENGTH_SHORT).show();
                        etAVDData.requestFocus();
                    }
                    //compara se ano inputado é = o ano atual e o mês inputado é = o mes atual  e o dia inputado é > que o dia atual
                    else if (Integer.parseInt(dataInputSplitada[2]) == Integer.parseInt(dataAtualSplitada[2]) && Integer.parseInt(dataInputSplitada[1]) == Integer.parseInt(dataAtualSplitada[1]) && Integer.parseInt(dataInputSplitada[0]) > Integer.parseInt(dataAtualSplitada[0]))
                    {
                        Toast.makeText(activity_avaliarvd.this, R.string.strDataInvalidaF, Toast.LENGTH_SHORT).show();
                        etAVDData.requestFocus();
                    }
                    //compara se ano inputado é < que o ano atual -2
                    else if (Integer.parseInt(dataInputSplitada[2]) < (Integer.parseInt(dataAtualSplitada[2])) - 1)
                    {
                        Toast.makeText(activity_avaliarvd.this, R.string.strDataInvalidaA, Toast.LENGTH_SHORT).show();
                        etAVDData.requestFocus();
                    }
                    //data é valida
                    else
                    {
                        //TRATA as entradas
                        float espera        = rbAVDEspera.getRating();
                        float recepcao      = rbAVDRecepcao.getRating();
                        float organizacao   = rbAVDOrganizacao.getRating();
                        float sinalizacao   = rbAVDSinalizacao.getRating();
                        float cordialidade  = rbAVDCordialidade.getRating();
                        float limpeza       = rbAVDLimpeza.getRating();
                        float medico        = rbAVDMedico.getRating();
                        float estacionamento, triagem, enfermaria, exames, internacao;
                        if (cbAVDEstacionamento.isChecked())
                        {
                            estacionamento = 5.5f;
                        }
                        else
                        {
                            estacionamento = rbAVDEstacionamento.getRating();
                        }
                        if (cbAVDTriagem.isChecked())
                        {
                            triagem = 5.5f;
                        }
                        else
                        {
                            triagem = rbAVDTriagem.getRating();
                        }
                        if (cbAVDEnfermaria.isChecked())
                        {
                            enfermaria = 5.5f;
                        }
                        else
                        {
                            enfermaria = rbAVDEnfermaria.getRating();
                        }
                        if (cbAVDExames.isChecked())
                        {
                            exames = 5.5f;
                        }
                        else
                        {
                            exames = rbAVDExames.getRating();
                        }
                        if (cbAVDInternacao.isChecked())
                        {
                            internacao = 5.5f;
                        }
                        else
                        {
                            internacao = rbAVDInternacao.getRating();
                        }

                        //VARIAVEIS FINAIS pra jogar no BD. //Obs: SE A NOTA FOR "11", NÃO UTILIZAR NOS CÁLCULOS.
                        String hospitalSelecionado  = tvAVDHospitalVD.getText().toString().replaceAll("[ ]", "%20");
                        String dataF                = etAVDData.getText().toString();
                        int esperaF                 = (int) (espera * 2);
                        int recepcaoF               = (int) (recepcao * 2);
                        int organizacaoF            = (int) (organizacao * 2);
                        int sinalizacaoF            = (int) (sinalizacao * 2);
                        int cordialidadeF           = (int) (cordialidade * 2);
                        int limpezaF                = (int) (limpeza * 2);
                        int medicoF                 = (int) (medico * 2);
                        int estacionamentoF         = (int) (estacionamento * 2);
                        int triagemF                = (int) (triagem * 2);
                        int enfermariaF             = (int) (enfermaria * 2);
                        int examesF                 = (int) (exames * 2);
                        int internacaoF             = (int) (internacao * 2);
                        String observacaoF          = etAVDObservacao.getText().toString().replaceAll("[ ]", "%20");

                        //Log de teste para verificar se os valores das variáveis estão todos corretos.
                        String teste = hospitalSelecionado + "\n\r" + dataF + "\n\r" + esperaF + "\n\r" + recepcaoF + "\n\r" + organizacaoF + "\n\r" + sinalizacaoF + "\n\r" + cordialidadeF + "\n\r" + limpezaF + "\n\r" + medicoF + "\n\r" + estacionamentoF + "\n\r" + triagemF + "\n\r" + enfermariaF + "\n\r" + examesF + "\n\r" + internacaoF + "\n\r" + observacaoF;
                        //Log.d("tag",teste);


                        Toast.makeText(activity_avaliarvd.this, "Enviando...", Toast.LENGTH_SHORT).show();
                        pbEnviandovd.setVisibility(View.VISIBLE);

                        //Pega o ID do usuario
                        SharedPreferences wmbPreference;
                        wmbPreference = PreferenceManager.getDefaultSharedPreferences(activity_avaliarvd.this); //dura pra sempre
                        String idUsuario = wmbPreference.getString("idUsuario", "");
                        //Log.d("TAG","idUsuario: " + idUsuario);

                        if (idUsuario.equals("0"))
                        {
                            Toast.makeText(this, "Sua conexão expirou.", Toast.LENGTH_SHORT).show();

                            //Log.d("tag","ID do usuario e 0.");

                            SharedPreferences wmbPreferencee;
                            SharedPreferences.Editor editorr;

                            wmbPreferencee = PreferenceManager.getDefaultSharedPreferences(activity_avaliarvd.this); //dura pra sempre
                            editorr = wmbPreferencee.edit();
                            editorr.putString("idUsuario", "0");
                            editorr.putString("isLogged", "N");
                            editorr.commit();
                            //Log.d("TAG", "idUsuario: " + "0" + ", isLogged: " + "N");

                            Intent intentSair = new Intent(activity_avaliarvd.this, activity_login.class);
                            intentSair.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentSair);
                        }


                        //////////////////////////////////////////
                        //  CONEXÃO VOLLEY COM O BANCO DE DADOS //
                        //////////////////////////////////////////

                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(this);
                        String url ="http://dkvox.ngrok.io/qservices/avaliar.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&idusuario="+idUsuario+"&nomehospital="+hospitalSelecionado+"&tempoespera="+esperaF+"&estacionamento="+estacionamentoF+"&recepcao="+recepcaoF+"&organizacao="+organizacaoF+"&sinalizacao="+sinalizacaoF+"&limpeza="+limpezaF+"&triagem="+triagemF+"&medico="+medicoF+"&enfermaria="+enfermariaF+"&internacao="+internacaoF+"&cordialidade="+cordialidadeF+"&exames="+examesF+"&observacao="+observacaoF+"&datainsert="+dataF;

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
                                                Toast.makeText(activity_avaliarvd.this, R.string.toastAvaEnviadaLabel, Toast.LENGTH_SHORT).show();
                                                Intent intentEnviar = new Intent(activity_avaliarvd.this, activity_principal.class);
                                                intentEnviar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intentEnviar);
                                                pbEnviandovd.setVisibility(View.INVISIBLE);
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
                                Toast.makeText(activity_avaliarvd.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                            }
                        });

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);

                        ///////////////////////////////////////////////////
                        //  FINAL DA CONEXÃO VOLLEY COM O BANCO DE DADOS //
                        ///////////////////////////////////////////////////
                    }
                }

                //VERIFICA se data inputada é menor ou maior q o numero de caracteres correto
                else if ((etAVDData.getText().toString()).length() < 10 || (etAVDData.getText().toString()).length() > 10)
                {
                    Toast.makeText(activity_avaliarvd.this, R.string.strDataInvalida, Toast.LENGTH_SHORT).show();
                    etAVDData.requestFocus();
                }
                break;

        }
    }
}
