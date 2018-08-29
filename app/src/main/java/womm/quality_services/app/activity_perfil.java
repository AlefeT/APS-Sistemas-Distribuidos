package womm.quality_services.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import womm.quality_services.app.R;

public class activity_perfil extends AppCompatActivity  implements View.OnClickListener
{
    ProgressBar pbPerfil;
    TextView tvPerNomeValue, tvPerCPFValue, tvPerCelValue, tvPerCEPValue, tvPerEmailValue;
    String CEP, CPF;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_perfil onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_perfil);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivPerArrowback = findViewById(R.id.ivPerArrowback);
        ImageView ivPerExit      = findViewById(R.id.ivPerExit);

        //  CardView's  //
        CardView cvPerEditar     = findViewById(R.id.cvPerEditar);

        //  TextView's  //
        tvPerNomeValue           = findViewById(R.id.tvPerNomeValue);
        tvPerCPFValue            = findViewById(R.id.tvPerCPFValue);
        tvPerCelValue            = findViewById(R.id.tvPerCelValue);
        tvPerCEPValue            = findViewById(R.id.tvPerCEPValue);
        tvPerEmailValue          = findViewById(R.id.tvPerEmailValue);
        TextView tvPerSair       = findViewById(R.id.tvPerSair);

        //  ProgressBar's   //
        pbPerfil                 = findViewById(R.id.pbPerfil);

        pbPerfil.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivPerArrowback.setOnClickListener((View.OnClickListener) this);
        cvPerEditar.setOnClickListener((View.OnClickListener) this);
        ivPerExit.setOnClickListener((View.OnClickListener) this);
        tvPerSair.setOnClickListener((View.OnClickListener) this);



        ////////////////////////////////////
        //  Setar informações do perfil   //
        ////////////////////////////////////

        SharedPreferences wmbPreference2;
        wmbPreference2   = PreferenceManager.getDefaultSharedPreferences(activity_perfil.this);
        String idUsuario = wmbPreference2.getString("idUsuario", "");
        //Log.d("TAG", "idUsuario: " + idUsuario);

        if (idUsuario.equals("0"))
        {
            Toast.makeText(this, "Sua conexão expirou.", Toast.LENGTH_SHORT).show();

            //Log.d("tag","ID do usuario e 0.");

            SharedPreferences wmbPreferencee;
            SharedPreferences.Editor editorr;

            wmbPreferencee = PreferenceManager.getDefaultSharedPreferences(activity_perfil.this); //dura pra sempre
            editorr = wmbPreferencee.edit();
            editorr.putString("idUsuario", "0");
            editorr.putString("isLogged", "N");
            editorr.commit();
            //Log.d("TAG", "idUsuario: " + "0" + ", isLogged: " + "N");

            Intent intentSair = new Intent(activity_perfil.this, activity_login.class);
            intentSair.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pbPerfil.setVisibility(View.GONE);
            startActivity(intentSair);
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://dkvox.ngrok.io/qservices/perfil.php?token=5Ywp8rCXwv7p0dWhis09GohiLetnpJuJ&idusuario="+idUsuario;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jObject = new JSONObject(response);
                    //Log.d("TAG","" + jObject);

                    //trata CEL
                    String firstnumbers = jObject.getString("telefone").substring(0,2);//Primeiros dois digitos
                    String midnumbers = jObject.getString("telefone").substring(2,7);//meio dos digitos
                    String restnumbers = jObject.getString("telefone").substring(7);//Resto dos digitos
                    String telefone = "(" + firstnumbers + ") " + midnumbers + "-" + restnumbers;

                    //trata CEP
                    String cep = jObject.getString("cep");

                    if (cep.equals("0"))
                    {
                        CEP = "";
                    }
                    else
                    {
                        String firsthalfCEP  = cep.substring(0, 5);//Primeiros dois digitos
                        String secondhalfCEP = cep.substring(5);//Resto dos digitos
                        CEP = firsthalfCEP + "-" + secondhalfCEP;
                    }

                    //trata CPF
                    String cpfpt1 = jObject.getString("cpf").substring(0,3);//Parte 1
                    String cpfpt2 = jObject.getString("cpf").substring(3,6);//Parte 2
                    String cpfpt3 = jObject.getString("cpf").substring(6,9);//Parte 3
                    String cpfpt4 = jObject.getString("cpf").substring(9);//Parte 4
                    CPF = cpfpt1 + "." + cpfpt2 + "." + cpfpt3 + "-" + cpfpt4;


                    //Sets
                    tvPerNomeValue.setText(jObject.getString("nome").replaceAll("[\u00e3]","ã").replaceAll("[\u00f5]","õ").replaceAll("[\u00f1]","ñ").replaceAll("[\u00e1]","á").replaceAll("[\u00e9]","é").replaceAll("[\u00ed]","í").replaceAll("[\u00f3]","ó").replaceAll("[\u00fa]","ú").replaceAll("[\u00e2]","â").replaceAll("[\u00ea]","ê").replaceAll("[\u00ee]","î").replaceAll("[\u00f4]","ô").replaceAll("[\u00fb]","û"));
                    tvPerCelValue.setText(telefone);
                    tvPerEmailValue.setText(jObject.getString("email"));
                    tvPerCEPValue.setText(CEP);
                    tvPerCPFValue.setText(CPF);

                    pbPerfil.setVisibility(View.GONE);
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
                Toast.makeText(activity_perfil.this, "Erro ao se conectar com o servidor.\nPor favor verifique sua conexão ou tente novamente mais tarde.", Toast.LENGTH_LONG).show();

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
        //Log.d("tag","In activity_perfil onStart");

    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_perfil onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_perfil onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_perfil onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_perfil onDestroy");
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivPerArrowback :
                //  Log de onClick no ArrowLeft  //
                //Log.d("tag","onClick no ivPerArrowback");

                finish();
                break;



            case R.id.cvPerEditar :
                //  Log de onClick no cvPerEditar  //
                //Log.d("tag","onClick no cvPerEditar");

                Intent intentEditar = new Intent(activity_perfil.this, activity_editperfil.class);
                startActivity(intentEditar);
                break;



            case R.id.tvPerSair :
                //  Log de onClick no tvPerSair  //
                //Log.d("tag","onClick no tvPerSair");

                pbPerfil.setVisibility(View.VISIBLE);



                //////////////////////////////////
                //         isLogged set         //
                //////////////////////////////////

                SharedPreferences wmbPreference;
                SharedPreferences.Editor editor;

                wmbPreference = PreferenceManager.getDefaultSharedPreferences(activity_perfil.this); //dura pra sempre
                editor = wmbPreference.edit();
                editor.putString("idUsuario", "0");
                editor.putString("isLogged", "N");
                editor.commit();
                //Log.d("TAG", "idUsuario: " + "0" + ", isLogged: " + "N");

                Intent intentSair = new Intent(activity_perfil.this, activity_login.class);
                intentSair.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pbPerfil.setVisibility(View.GONE);
                startActivity(intentSair);
                break;



            case R.id.ivPerExit :
                //  Log de onClick no ivPerExit  //
                //Log.d("tag","onClick no ivPerExit");

                pbPerfil.setVisibility(View.VISIBLE);

                //////////////////////////////////
                //         isLogged set         //
                //////////////////////////////////

                SharedPreferences wmbPreference1;
                SharedPreferences.Editor editor1;

                wmbPreference1 = PreferenceManager.getDefaultSharedPreferences(activity_perfil.this);
                editor1 = wmbPreference1.edit();
                editor1.putString("idUsuario", "0");
                editor1.putString("isLogged", "N");
                editor1.commit();
                //Log.d("TAG", "idUsuario: " + "0" + ", isLogged: " + "N");

                Intent intentExit = new Intent(activity_perfil.this, activity_login.class);
                intentExit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pbPerfil.setVisibility(View.GONE);
                startActivity(intentExit);
                break;
        }
    }

}

