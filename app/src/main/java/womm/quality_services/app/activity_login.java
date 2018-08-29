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

import java.util.Objects;

import womm.quality_services.app.R;

public class activity_login extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar pbLogin;
    EditText etLogEmail, etLogSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_login onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_login);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  EditText's    //
        etLogEmail              = findViewById(R.id.etLogEmail);
        etLogSenha              = findViewById(R.id.etLogSenha);

        //  TextView's  //
        TextView tvLogEsqueceu  = findViewById(R.id.tvLogEsqueceu);

        //  CardView's  //
        CardView cvLogLogin     = findViewById(R.id.cvLogLogin);
        CardView cvLogCadastrar = findViewById(R.id.cvLogCadastrar);

        //  ProgressBar's   //
        pbLogin                 = findViewById(R.id.pbLogin);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        cvLogLogin.setOnClickListener((View.OnClickListener) this);
        cvLogCadastrar.setOnClickListener((View.OnClickListener) this);
        tvLogEsqueceu.setOnClickListener((View.OnClickListener) this);

    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_login onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_login onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_login onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_login onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_login onDestroy");
    }


    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cvLogLogin :
                //  Log de onClick no cvLogin  //
                //Log.d("tag","onClick no cvLogLogin");

                if((etLogEmail.getText().toString().matches("[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9._%+-]{2,100}[.]+[a-zA-Z0-9._%+-]{2,4}")) && etLogSenha.length() >= 6)
                {
                    pbLogin.setVisibility(View.VISIBLE);

                    String emailInputado = etLogEmail.getText().toString();
                    String senhaInputado = etLogSenha.getText().toString();



                    ////////////////////////////////////////
                    //CONEXÃO VOLLEY COM O BANCO DE DADOS //
                    ////////////////////////////////////////

                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url ="https://dkvox.ngrok.io/qservices/logincheck?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&email="+emailInputado+"&senha="+senhaInputado;

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
                                String idUsuario   = jObject.getString("idusuario");
                                //Log.d("TAG", idUsuario);

                                if(Objects.equals(jObject.getString("idusuario"), "0"))
                                {
                                    Toast.makeText(activity_login.this, "E-mail ou Senha inválidos.", Toast.LENGTH_SHORT).show();
                                    //Log.d("TAG", "Usuário Inválido");
                                    pbLogin.setVisibility(View.GONE);
                                }
                                else
                                {
                                    SharedPreferences wmbPreference;
                                    SharedPreferences.Editor editor;

                                    wmbPreference = PreferenceManager.getDefaultSharedPreferences(activity_login.this); //dura pra sempre
                                    editor = wmbPreference.edit();
                                    editor.putString("idUsuario", idUsuario);
                                    editor.putString("isLogged", "Y");
                                    editor.commit();
                                    //Log.d("TAG", "idUsuario: " + idUsuario + ", isLogged: " + "Y");

                                    Intent intentLogLogin = new Intent(activity_login.this, activity_principal.class);
                                    startActivity(intentLogLogin);
                                    pbLogin.setVisibility(View.GONE);
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
                            Toast.makeText(activity_login.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                    //FINAL DA CONEXÃO VOLLEY COM O BANCO DE DADOS

                }
                else
                {
                    Toast.makeText(this, "E-mail ou Senha inválidos.", Toast.LENGTH_SHORT).show();
                }
                break;



            case R.id.cvLogCadastrar :
                //  Log de onClick no cvLogin  //
                //Log.d("tag","onClick no cvLogCadastrar");

                Intent intentLogCadastrar  = new Intent(activity_login.this, activity_cadastro1.class);
                startActivity(intentLogCadastrar );
                break;



            case R.id.tvLogEsqueceu :
                //  Log de onClick no tvLogEsqueceu  //
                //Log.d("tag","onClick no tvLogEsqueceu");

                Intent intentLogEsqueceu = new Intent(activity_login.this, activity_esqueceu.class);
                startActivity(intentLogEsqueceu);
                break;
        }
    }

}