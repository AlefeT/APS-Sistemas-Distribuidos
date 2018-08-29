package womm.quality_services.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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

import java.util.InputMismatchException;

import womm.quality_services.app.R;

public class activity_cadastro1 extends AppCompatActivity implements View.OnClickListener
{
    EditText etCadNome, etCadCPF, etCadCelular, etCadEmail, etCadSenha;
    AlertDialog adCadErroCPF, adCadErroNome, adCadErroEmail, adCadErroSenha, adCadErroCelular, adCadErroEmailExist, adCadErroCPFExist;
    ProgressBar pbCadastrando1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_cadastro1 onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_cadastro1);


        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  EditText's    //
        etCadCPF                     = findViewById(R.id.etCadCPF);
        etCadCelular                 = findViewById(R.id.etCadCelular);
        etCadEmail                   = findViewById(R.id.etCadEmail);
        etCadSenha                   = findViewById(R.id.etCadSenha);
        etCadNome                    = findViewById(R.id.etCadNome);

        //  TextView's  //
        TextView tvCadTermosPolitica = findViewById(R.id.tvCadTermosPolitica);

        //  CardView's  //
        CardView cvCadCadastrar      = findViewById(R.id.cvCadCadastrar);

        //  ProgressBar's   //
        pbCadastrando1               = findViewById(R.id.pbCadastrando1);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        cvCadCadastrar.setOnClickListener((View.OnClickListener) this);



        //////////////////////////////////////////////////////////////////////////////////////////////////////
        //  Frase clicável do "Ao cadastrar-se, você concorda com nossos Termos e Política de Privacidade." //
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        tvCadLinkTermosPolitica(tvCadTermosPolitica);



        //////////////////////////////////////
        //  Tela de Erro (Nome)
        adCadErroNome = new AlertDialog.Builder(activity_cadastro1.this).create();
        adCadErroNome.setTitle("Nome Inválido");
        adCadErroNome.setMessage("O nome digitado contém números ou caracteres especiais. Por favor digite um nome válido.");
        adCadErroNome.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //////////////////////////////////////
        //  Tela de Erro (CPF)
        adCadErroCPF = new AlertDialog.Builder(activity_cadastro1.this).create();
        adCadErroCPF.setTitle("CPF Inválido");
        adCadErroCPF.setMessage("O CPF digitado está inválido. Certifique-se de que ele não contém caracteres especiais e digite-o novamente.");
        adCadErroCPF.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //////////////////////////////////////
        //  Tela de Erro (E-Mail)
        adCadErroEmail = new AlertDialog.Builder(activity_cadastro1.this).create();
        adCadErroEmail.setTitle("E-Mail Inválido");
        adCadErroEmail.setMessage("O E-Mail digitado não pôde ser validado. Por favor certifique-se e tente novamente.");
        adCadErroEmail.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //////////////////////////////////////
        //  Tela de Erro (Senha)
        adCadErroSenha = new AlertDialog.Builder(activity_cadastro1.this).create();
        adCadErroSenha.setTitle("Senha Inválida");
        adCadErroSenha.setMessage("A senha digitada é inválida. Por favor certifique-se de que ela esteja entre 6 e 15 caracteres.");
        adCadErroSenha.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //////////////////////////////////////
        //  Tela de Erro (Celular)
        adCadErroCelular = new AlertDialog.Builder(activity_cadastro1.this).create();
        adCadErroCelular.setTitle("Número Inválido");
        adCadErroCelular.setMessage("O número de celular digitado é inválido, Por favor certifique-se e tente novamente.");
        adCadErroCelular.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //////////////////////////////////////
        //  Tela de Erro (E-Mail Existe)
        adCadErroEmailExist = new AlertDialog.Builder(activity_cadastro1.this).create();
        adCadErroEmailExist.setTitle("E-Mail Já Cadastrado");
        adCadErroEmailExist.setMessage("Esse E-Email já está cadastrado em nosso sistema.");
        adCadErroEmailExist.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //////////////////////////////////////
        //  Tela de Erro (CPF Existe)
        adCadErroCPFExist = new AlertDialog.Builder(activity_cadastro1.this).create();
        adCadErroCPFExist.setTitle("CPF Já Cadastrado");
        adCadErroCPFExist.setMessage("Esse CPF já está cadastrado em nosso sistema.");
        adCadErroCPFExist.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });


        //////////////////////////////////////
        //Aplicando Mask CEP ao que for inputado no EditText tvEdtCEPValue
        etCadCPF.addTextChangedListener(MaskEdittextUtil.mask(etCadCPF, MaskEdittextUtil.FORMAT_CPF));
        etCadCelular.addTextChangedListener(MaskEdittextUtil.mask(etCadCelular, MaskEdittextUtil.FORMAT_CEL));

    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  Método para fazer a Frase clicável do "Ao cadastrar-se, você concorda com nossos Termos e Política de Privacidade." //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void tvCadLinkTermosPolitica(TextView tvCadTermPoli)
    {
        SpannableStringBuilder ssbCadTermosPolitica = new SpannableStringBuilder("Ao cadastrar-se, você concorda com nossos ");
        ssbCadTermosPolitica.append("Termos de Uso.");
        ssbCadTermosPolitica.setSpan(new ClickableSpan()
                                     {
                                         @Override
                                         public void onClick(View widget)
                                         {
                                             Intent intentCadTermos = new Intent(activity_cadastro1.this, activity_termos.class);
                                             startActivity(intentCadTermos);
                                         }
                                     },
                ssbCadTermosPolitica.length() - "Termos de Uso.".length(), ssbCadTermosPolitica.length(), 0);
        tvCadTermPoli.setMovementMethod(LinkMovementMethod.getInstance());
        tvCadTermPoli.setText(ssbCadTermosPolitica, TextView.BufferType.SPANNABLE);
    }




    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_cadastro1 onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_cadastro1 onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_cadastro1 onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_cadastro1 onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_cadastro1 onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cvCadCadastrar :
                //  Log de onClick no cvCadCadastrar  //
                //Log.d("tag","onClick no cvCadCadastrar");
                int errorFlag = 0;

                if(!etCadEmail.getText().toString().matches("[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9._%+-]{2,100}[.]+[a-zA-Z0-9._%+-]{2,4}"))
                {
                    adCadErroEmail.show();
                    errorFlag = 1;
                }

                if(etCadNome.getText().toString().matches("[0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]"))
                {
                    adCadErroNome.show();
                    errorFlag = 1;
                    //Log.d("TAG","Nome contem caractere invalido.");
                }

                int s = etCadNome.getText().toString().length();
                int s2 = etCadNome.getText().toString().replaceAll(" ", "").length();
                if(s - s2 == 0)
                {
                    adCadErroNome.show();
                    errorFlag = 1;
                }

                if(!etCadSenha.getText().toString().matches("[0-9A-Za-z_!@#$%&]{6,15}"))
                {
                    adCadErroSenha.show();
                    errorFlag = 1;
                }

                //VALIDA CEL
                String celSemMask = MaskEdittextUtil.unmask(etCadCelular.getText().toString());
                if(!celSemMask.matches("[0-9]{11}"))
                {
                    adCadErroCelular.show();
                    errorFlag = 1;
                }

                //VALIDA CPF
                String cpfSemMask = MaskEdittextUtil.unmask(etCadCPF.getText().toString());
                if(cpfSemMask.length() != 11)
                {
                    adCadErroCPF.show();
                    errorFlag = 1;
                }
                else
                {
                    String CPF = cpfSemMask;

                    char dig10, dig11;
                    int sm, i, r, num, peso;

                    // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
                    try
                    {
                        // Calculo do 1o. Digito Verificador
                        sm = 0;
                        peso = 10;
                        for (i=0; i<9; i++)
                        {
                            // converte o i-esimo caractere do CPF em um numero:
                            // por exemplo, transforma o caractere '0' no inteiro 0
                            // (48 eh a posicao de '0' na tabela ASCII)
                            num = (int)(CPF.charAt(i) - 48);
                            sm = sm + (num * peso);
                            peso = peso - 1;
                        }

                        r = 11 - (sm % 11);
                        if ((r == 10) || (r == 11))
                            dig10 = '0';
                        else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

                        // Calculo do 2o. Digito Verificador
                        sm = 0;
                        peso = 11;
                        for(i=0; i<10; i++)
                        {
                            num = (int)(CPF.charAt(i) - 48);
                            sm = sm + (num * peso);
                            peso = peso - 1;
                        }

                        r = 11 - (sm % 11);
                        if ((r == 10) || (r == 11))
                            dig11 = '0';
                        else dig11 = (char)(r + 48);

                        // Verifica se os digitos calculados conferem com os digitos informados.
                        if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                        {
                            //ALL GREEN
                        }
                        else
                        {
                            adCadErroCPF.show();
                            errorFlag = 1;
                        }
                    }
                    catch (InputMismatchException erro)
                    {

                    }
                }

                if(errorFlag == 0)
                {
                    pbCadastrando1.setVisibility(View.VISIBLE);

                    //VOLLEY TO CHECK EMAIL
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url ="http://dkvox.ngrok.io/qservices/signupcheckemail.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&email="+etCadEmail.getText().toString();

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
                                String resposta = jObject.getString("idusuario");
                                //Log.d("TAG", resposta);

                                //IF EMAIL DOES NOT EXIST
                                if(jObject.getString("idusuario").equals("N"))
                                {
                                    //VOLLEY TO CHECK CPF
                                    RequestQueue queue2 = Volley.newRequestQueue(activity_cadastro1.this);
                                    String url2 ="http://dkvox.ngrok.io/qservices/signupcheckcpf.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&cpf="+MaskEdittextUtil.unmask(etCadCPF.getText().toString());

                                    // Request a string response from the provided URL.
                                    StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response)
                                        {
                                            //Log.d("TAG", response);
                                            try
                                            {
                                                JSONObject jObject2 = new JSONObject(response);
                                                //Log.d("TAG","JSON was successfully received and converted.");
                                                String resposta = jObject2.getString("idusuario");
                                                //Log.d("TAG", resposta);

                                                //IF CPF DOES NOT EXIST
                                                if(jObject2.getString("idusuario").equals("N"))
                                                {
                                                    String cpfSemMask2 = MaskEdittextUtil.unmask(etCadCPF.getText().toString());
                                                    String celSemMask2 = MaskEdittextUtil.unmask(etCadCelular.getText().toString());

                                                    Intent intentCadCadastrar = new Intent(activity_cadastro1.this, activity_cadastro2.class);
                                                    intentCadCadastrar.putExtra("etCadNome", etCadNome.getText().toString());
                                                    intentCadCadastrar.putExtra("etCadCPF", cpfSemMask2);
                                                    intentCadCadastrar.putExtra("etCadCelular", celSemMask2);
                                                    intentCadCadastrar.putExtra("etCadEmail", etCadEmail.getText().toString());
                                                    intentCadCadastrar.putExtra("etCadSenha", etCadSenha.getText().toString());
                                                    startActivity(intentCadCadastrar);
                                                    pbCadastrando1.setVisibility(View.GONE);
                                                }
                                                else if(jObject2.getString("idusuario").equals("Y"))
                                                {
                                                    adCadErroCPFExist.show();

                                                    pbCadastrando1.setVisibility(View.GONE);
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
                                            Toast.makeText(activity_cadastro1.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    // Add the request to the RequestQueue.
                                    queue2.add(stringRequest2);
                                }
                                else if(jObject.getString("idusuario").equals("Y"))
                                {
                                    adCadErroEmailExist.show();

                                    pbCadastrando1.setVisibility(View.GONE);
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
                            Toast.makeText(activity_cadastro1.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
                break;
        }
    }



}




