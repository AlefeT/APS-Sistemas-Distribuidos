package womm.quality_services.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.Random;

import womm.quality_services.app.R;

public class activity_cadastro2 extends AppCompatActivity implements View.OnClickListener
{
    EditText etCadCodigoVerif;
    TextView tvCad2Segs;
    int randomNum;
    AlertDialog adCad2Error;
    String etCad2Celular, etCad2CPF, etCad2Nome, etCad2Senha, etCad2Email, msg, digitado, podeReenviar;
    ProgressBar pbCadastrando2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_cadastro2 onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_cadastro2);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  EditText's    //
        etCadCodigoVerif            = findViewById(R.id.etCadCodigoVerif);

        //  TextView's    //
        tvCad2Segs                  = findViewById(R.id.tvCad2Segs);

        //  CardView's  //
        CardView cvCad2Confirmar    = findViewById(R.id.cvCad2Confirmar);
        CardView cvCad2Reenviar     = findViewById(R.id.cvCad2Reenviar);

        //  ProgressBar's   //
        pbCadastrando2              = findViewById(R.id.pbCadastrando2);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        cvCad2Confirmar.setOnClickListener((View.OnClickListener) this);
        cvCad2Reenviar.setOnClickListener((View.OnClickListener) this);



        //BUNDLE GET STRINGS DO PUT EXTRA
        Bundle bundle   = getIntent().getExtras();
        etCad2Celular   = bundle.getString("etCadCelular");
        etCad2CPF       = bundle.getString("etCadCPF");
        etCad2Senha     = bundle.getString("etCadSenha");
        etCad2Nome      = bundle.getString("etCadNome");
        etCad2Email     = bundle.getString("etCadEmail");



        //////////////////////////////////
        //  Gerando Número Aleatorio
        int min = 100000;
        int max = 999999;

        Random r = new Random();
        randomNum = r.nextInt(max - min + 1) + min;
        //Log.d("tag", "randomNum: " + String.valueOf(randomNum));


        //////////////////////////////////////
        //  Tela de Erro (Código Inválido)
        adCad2Error = new AlertDialog.Builder(activity_cadastro2.this).create();
        adCad2Error.setTitle("Código Inválido");
        adCad2Error.setMessage("O Código digitado não corresponde ao que foi enviado via SMS, por favor tente novamente.");
        adCad2Error.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //PEGA O PRIMEIRO NOME, SEM ACENTOS.
        String primeiroNome[] = etCad2Nome.split(" ");
        primeiroNome[0] = RemoverAcentosUtil.remover (primeiroNome[0]);



        ////////////////////////////////////////
        //CONEXÃO VOLLEY COM O BANCO DE DADOS //
        ////////////////////////////////////////

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        msg = "Ola%20" + primeiroNome[0] + ",%20seu%20Codigo%20de%20Verificacao%20do%20Quality%20Services%20e:%20";
        String url = "http://dkvox.ngrok.io/TestService/sendsms.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&telefone=" + etCad2Celular + "&mensagem=" + msg + randomNum;
        //Log.d("TAG", "Sending SMS URL: " + url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //Log.d("TAG", response);
                try
                {
                    JSONObject jObject = new JSONObject(response);
                    //Log.d("TAG", response);
                    //Log.d("TAG", "JSON was successfully received and converted.");
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
                //Log.e("TAG", "Volley connection miserably failed.");
                Toast.makeText(activity_cadastro2.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        //FINAL DA CONEXÃO VOLLEY COM O BANCO DE DADOS



        //////////////////////////
        //  TIMER pro Reenviar
        podeReenviar = "N";

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable()
        {
            int secs = 59;

            @Override
            public void run()
            {
                if (secs == 0)
                {
                    tvCad2Segs.setText("");
                    podeReenviar = "Y";
                }
                else
                {
                    secs = secs - 1;
                    tvCad2Segs.setText(secs+"s");
                    handler2.postDelayed(this, 1000);
                }
            }
        }, 1000);

    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_cadastro2 onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_cadastro2 onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_cadastro2 onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_cadastro2 onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_cadastro2 onDestroy");
    }



    @Override
    public void onBackPressed()
    {
        moveTaskToBack(false);
        Toast.makeText(this, "Por favor, digite o Código de Verificação e aperte Confirmar.", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cvCad2Confirmar:
                //  Log de onClick no cvCad2Confirmar  //
                //Log.d("tag", "onClick no cvCad2Confirmar");

                digitado = etCadCodigoVerif.getText().toString();

                if (digitado.length() == 0 || digitado == null)
                {
                    Toast.makeText(this, "Por favor digite o Código de Verificação.", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.valueOf(digitado) == randomNum)
                {
                    // Caso verdadeiro. Dar commit dos dados cadastrados no BD e passa o usuário para a proxima tela

                    pbCadastrando2.setVisibility(View.VISIBLE);

                    String nome = etCad2Nome.replaceAll("[ ]","%20");



                    //////////////////////////////////////////
                    //  CONEXÃO VOLLEY COM O BANCO DE DADOS //
                    //////////////////////////////////////////

                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url ="https://dkvox.ngrok.io/qservices/cadastrousuario?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&nome="+nome+"&cpf="+etCad2CPF+"&telefone="+etCad2Celular+"&email="+etCad2Email+"&senha="+etCad2Senha;

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            //Log.d("TAG", response);
                            try
                            {
                                JSONObject jObject = new JSONObject(response);
                                //Log.d("TAG","JSON was successfully received and converted.");
                                String idusuario = jObject.getString("idusuario");
                                //Log.d("TAG", "idusuario: " + idusuario);

                                //Declaring the SharedPreferences to store global variables
                                SharedPreferences wmbPreference;
                                SharedPreferences.Editor editor;

                                //Placing the idUsuario and isLogged in the DefaultSharePreferences
                                wmbPreference = PreferenceManager.getDefaultSharedPreferences(activity_cadastro2.this); //dura pra sempre
                                editor = wmbPreference.edit();
                                editor.putString("idUsuario", idusuario);
                                editor.putString("isLogged", "Y");
                                editor.commit();
                                //Log.d("TAG", "idUsuario: " + idusuario + ", isLogged: " + "Y");

                                if(!idusuario.equals("0"))
                                {
                                    Intent intentCad2Confirmar = new Intent(activity_cadastro2.this, activity_cadastro3.class);
                                    startActivity(intentCad2Confirmar);
                                    pbCadastrando2.setVisibility(View.GONE);
                                }
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
                            Toast.makeText(activity_cadastro2.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                    //FINAL DA CONEXÃO VOLLEY COM O BANCO DE DADOS

                }
                else
                {
                    // Caso falso. Printar "Código Inválido" na tela.
                    adCad2Error.show();
                    pbCadastrando2.setVisibility(View.GONE);
                }
                break;


            case R.id.cvCad2Reenviar :
                //  Log de onClick no intentCad2Reenviar  //
                //Log.d("tag","onClick no cvCad2Reenviar");

                if (podeReenviar == "Y")
                {
                    Intent intentCad2Reenviar = new Intent(activity_cadastro2.this, activity_cadastro2.class);
                    intentCad2Reenviar.putExtra("etCadNome", etCad2Nome);
                    intentCad2Reenviar.putExtra("etCadCPF", etCad2CPF);
                    intentCad2Reenviar.putExtra("etCadCelular", etCad2Celular);
                    intentCad2Reenviar.putExtra("etCadEmail", etCad2Email);
                    intentCad2Reenviar.putExtra("etCadSenha", etCad2Senha);
                    startActivity(intentCad2Reenviar);
                }
                else
                {
                    Toast.makeText(this, "Por favor, aguarde.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}




