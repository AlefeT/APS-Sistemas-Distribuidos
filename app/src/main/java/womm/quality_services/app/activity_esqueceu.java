package womm.quality_services.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class activity_esqueceu extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar pbEsqceu;
    EditText etEsqEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag","In activity_esqueceu onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_esqueceu);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  EditText's    //
        etEsqEmail               = findViewById(R.id.etEsqEmail);

        //  CardView's  //
        CardView cvEsqConfirmar  = findViewById(R.id.cvEsqConfirmar);
        CardView cvEsqVoltar     = findViewById(R.id.cvEsqVoltar);

        //  ProgressBar's   //
        pbEsqceu                 = findViewById(R.id.pbEsqceu);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        cvEsqConfirmar.setOnClickListener((View.OnClickListener) this);
        cvEsqVoltar.setOnClickListener((View.OnClickListener) this);

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_esqueceu onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_esqueceu onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_esqueceu onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_esqueceu onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_esqueceu onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cvEsqConfirmar:
                //  Log de onClick no cvEsqConfirmar  //
                //Log.d("tag", "onClick no cvEsqConfirmar");

                if(etEsqEmail.getText().toString().matches("[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9._%+-]{2,100}[.]+[a-zA-Z0-9._%+-]{2,4}"))
                {
                    pbEsqceu.setVisibility(View.VISIBLE);

                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url ="http://dkvox.ngrok.io/qservices/passresend.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&email="+etEsqEmail.getText().toString();

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

                                Toast.makeText(activity_esqueceu.this, "Senha enviada para seu E-Mail com sucesso.", Toast.LENGTH_SHORT).show();
                                pbEsqceu.setVisibility(View.GONE);
                                finish();
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
                            Toast.makeText(activity_esqueceu.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();

                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
                else
                {
                    Toast.makeText(this, "E-mail Inválido.", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.cvEsqVoltar :
                //  Log de onClick no cvEsqVoltar  //
                //Log.d("tag","onClick no cvEsqVoltar");
                pbEsqceu.setVisibility(View.VISIBLE);

                finish();
                pbEsqceu.setVisibility(View.GONE);
                break;
        }
    }

}

