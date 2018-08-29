package womm.quality_services.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import womm.quality_services.app.R;

public class activity_editperfil extends AppCompatActivity  implements View.OnClickListener
{
    EditText etEdtCEPValue, etEdtEmailValue;
    String cep, email;
    int erroc, erroe, altc, alte;
    ProgressBar pbEdtPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_editperfil onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_editperfil);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivEdtArrowback = findViewById(R.id.ivEdtArrowback);
        ImageView ivEdtHome      = findViewById(R.id.ivEdtHome);

        //  CardView's  //
        CardView cvEdtSalvar     = findViewById(R.id.cvEdtSalvar);

        //  EditText's  //
        etEdtCEPValue            = findViewById(R.id.etEdtCEPValue);
        etEdtEmailValue          = findViewById(R.id.etEdtEmailValue);

        //  ProgressBar's   //
        pbEdtPerfil              = findViewById(R.id.pbEdtPerfil);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivEdtArrowback.setOnClickListener((View.OnClickListener) this);
        cvEdtSalvar.setOnClickListener((View.OnClickListener) this);
        ivEdtHome.setOnClickListener((View.OnClickListener) this);


        //Aplicando Mask CEP ao que for inputado no EditText tvEdtCEPValue
        etEdtCEPValue.addTextChangedListener(MaskEdittextUtil.mask(etEdtCEPValue, MaskEdittextUtil.FORMAT_CEP));


    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_editperfil onStart");

    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_editperfil onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_editperfil onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_editperfil onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_editperfil onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivEdtArrowback :
                //  Log de onClick no ArrowLeft  //
                //Log.d("tag","onClick no ivEdtArrowback");

                finish();
                break;



            case R.id.cvEdtSalvar :
                //  Log de onClick no cvEdtSalvar  //
                //Log.d("tag","onClick no cvEdtSalvar");



                //////////////////////////////////////////
                // VALIDA se o CEP inputado é válido   //
                //////////////////////////////////////////

                if((etEdtCEPValue.getText().toString()).length() == 9)
                {
                    cep = etEdtCEPValue.getText().toString();
                    erroc = 0;
                }
                else if(etEdtCEPValue.getText().toString().length() == 0)
                {
                    cep = "";
                    erroc = 0;
                }
                else if ((etEdtCEPValue.getText().toString()).length() != 9 && etEdtCEPValue.getText().toString().length() != 0)
                {
                    Toast.makeText(activity_editperfil.this, R.string.cepinvalidoLabel, Toast.LENGTH_SHORT).show();
                    etEdtCEPValue.requestFocus();
                    erroc = 1;
                }



                //////////////////////////////////////////
                // VALIDA se o Email inputado é válido  //
                //////////////////////////////////////////

                if(etEdtEmailValue.getText().toString().matches("[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9._%+-]{2,100}[.]+[a-zA-Z0-9._%+-]{2,4}"))
                {
                    email = etEdtEmailValue.getText().toString();
                    erroe = 0;
                }
                else if(etEdtEmailValue.getText().toString().length() == 0)
                {
                    email = "";
                    erroe = 0;
                }
                else if(!etEdtEmailValue.getText().toString().matches("[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9._%+-]{2,100}[.]+[a-zA-Z0-9._%+-]{2,4}") && etEdtEmailValue.getText().toString().length() != 0)
                {
                    Toast.makeText(activity_editperfil.this, R.string.emailinvalidoLabel, Toast.LENGTH_SHORT).show();
                    etEdtEmailValue.requestFocus();
                    erroe = 1;
                }



                //////////////////////////////////////////
                // VERIFICA erros e da update no perfil //
                //////////////////////////////////////////

                if(erroc == 0 && erroe == 0)
                {

                    if(cep.length() != 0)
                    {
                        pbEdtPerfil.setVisibility(View.VISIBLE);

                        altc = 1;
                        String cepF = MaskEdittextUtil.unmask(cep);

                        SharedPreferences wmbPreference2;
                        wmbPreference2   = PreferenceManager.getDefaultSharedPreferences(activity_editperfil.this);
                        String idUsuario = wmbPreference2.getString("idUsuario", "");
                        //Log.d("TAG", "idUsuario: " + idUsuario);

                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(this);
                        String url ="http://dkvox.ngrok.io/qservices/mudarcep.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&cep="+cepF+"&idusuario="+idUsuario;

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    JSONObject jObject = new JSONObject(response);

                                    Intent intentEdt = new Intent(activity_editperfil.this, activity_perfil.class);
                                    intentEdt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    //Log.d("TAG","mudou cep");
                                    startActivity(intentEdt);
                                    pbEdtPerfil.setVisibility(View.GONE);
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
                                Toast.makeText(activity_editperfil.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();

                            }
                        });

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    }

                    if(email.length() != 0)
                    {
                        pbEdtPerfil.setVisibility(View.VISIBLE);

                        alte = 1;



                        ////////////////////////////////////////
                        //CONEXÃO VOLLEY COM O BANCO DE DADOS //
                        ////////////////////////////////////////

                        SharedPreferences wmbPreference2;
                        wmbPreference2   = PreferenceManager.getDefaultSharedPreferences(activity_editperfil.this);
                        String idUsuario = wmbPreference2.getString("idUsuario", "");
                        //Log.d("TAG", "idUsuario: " + idUsuario);

                        if (idUsuario.equals("0"))
                        {
                            Toast.makeText(this, "Sua conexão expirou.", Toast.LENGTH_SHORT).show();

                            //Log.d("tag","ID do usuario e 0.");

                            SharedPreferences wmbPreferencee;
                            SharedPreferences.Editor editorr;

                            wmbPreferencee = PreferenceManager.getDefaultSharedPreferences(activity_editperfil.this); //dura pra sempre
                            editorr = wmbPreferencee.edit();
                            editorr.putString("idUsuario", "0");
                            editorr.putString("isLogged", "N");
                            editorr.commit();
                            //Log.d("TAG", "idUsuario: " + "0" + ", isLogged: " + "N");

                            Intent intentSair = new Intent(activity_editperfil.this, activity_login.class);
                            intentSair.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentSair);
                            pbEdtPerfil.setVisibility(View.GONE);
                        }

                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(this);
                        String url ="http://dkvox.ngrok.io/qservices/mudaremail.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&email="+email+"&idusuario="+idUsuario;

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    JSONObject jObject = new JSONObject(response);

                                    Intent intentEdt = new Intent(activity_editperfil.this, activity_perfil.class);
                                    intentEdt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    //Log.d("TAG","mudou email");
                                    startActivity(intentEdt);
                                    pbEdtPerfil.setVisibility(View.GONE);
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
                                Toast.makeText(activity_editperfil.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();

                            }
                        });

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    }

                    if (cep.length() == 0 && email.length() == 0)
                    {
                        Toast.makeText(activity_editperfil.this, R.string.naoalterouLabel, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(altc == 1 && alte == 1)
                        {
                            Toast.makeText(activity_editperfil.this, R.string.alteroutdLabel, Toast.LENGTH_SHORT).show();
                        }
                        else if(altc == 1 && alte == 0)
                        {
                            Toast.makeText(activity_editperfil.this, R.string.alteroucLabel, Toast.LENGTH_SHORT).show();
                        }
                        else if(altc == 0 && alte == 1)
                        {
                            Toast.makeText(activity_editperfil.this, R.string.alteroueLabel, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;



            case R.id.ivEdtHome :
                //  Log de onClick no Home  //
                //Log.d("tag","onClick no ivEdtHome");

                Intent intentHome = new Intent(activity_editperfil.this, activity_principal.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
        }
    }

}

