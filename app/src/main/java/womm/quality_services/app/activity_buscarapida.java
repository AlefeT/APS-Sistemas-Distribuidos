package womm.quality_services.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;

import womm.quality_services.app.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class activity_buscarapida extends AppCompatActivity implements View.OnClickListener
{
    LocationListener ll;
    LocationManager lm;
    AutoCompleteTextView actvBraHospital;
    TextView tvBraHospinv;
    View vBRaDistancia;
    boolean podepassar   = true;
    double latitude, longitude;
    ProgressBar pbBuscando, pbCarregandolista;
    Spinner spBraConvenio, spBraDistancia, spBraTempoespera;
    String  hospitais[], selectedConvenioText, selectedDistanciaText, selectedTempoesperaText, posicao;
    String[] distancia   = new String[]{"Selecione", "Até 5km", "Até 10km", "Até 20km", "Mais de 20km"};
    String[] tempoespera = new String[]{"Selecione", "Até 30min", "Até 1 hora", "Até 1 hora e 30min", "Mais de 1 hora e 30min"};
    String[] convenios   = new String[]{"Selecione", "Ameplan - Executivo", "Ameplan - Master", "Amil", "Amil 200 Regional", "Amil 400", "Amil 700", "Biosaúde Básico", "Biosaúde Especial", "Blue Med", "Blue Med Premium Standard", "Bradesco Seguro Saúde - Nacional Flex", "Bradesco Seguro Saúde - Top Nacional", "Classes Laboriosas Individual Enfermaria Prime", "Dix Saúde Individual 100 QC", "Garantia Adventista"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_buscarapida onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_buscarapida);


        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivBRaArrowback = findViewById(R.id.ivBRaArrowback);

        //  Spinner's //
        spBraConvenio            = findViewById(R.id.spBraConvenio);
        spBraDistancia           = findViewById(R.id.spBraDistancia);
        spBraTempoespera         = findViewById(R.id.spBraTempoespera);

        //  AutoCompleteTextView's //
        actvBraHospital          = findViewById(R.id.actvBraHospital);

        //  CardView's  //
        CardView cvBRaBuscar     = findViewById(R.id.cvBRaBuscar);

        //  TextView's  //
        tvBraHospinv             = findViewById(R.id.tvBraHospinv);

        //  View's  //
        vBRaDistancia            = findViewById(R.id.vBRaDistancia);

        //  ProgressBar's   //
        pbBuscando               = findViewById(R.id.pbBuscando);
        pbCarregandolista        = findViewById(R.id.pbCarregandolista);

        pbCarregandolista.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivBRaArrowback.setOnClickListener((View.OnClickListener) this);
        cvBRaBuscar.setOnClickListener((View.OnClickListener) this);
        vBRaDistancia.setOnClickListener((View.OnClickListener) this);



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
                actvBraHospital.setAdapter(new ArrayAdapter<String>(activity_buscarapida.this, android.R.layout.simple_dropdown_item_1line, hospitais));
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

        //  Convenios    //
        ArrayAdapter<String> adapterConvenio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, convenios);
        adapterConvenio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBraConvenio.setAdapter(adapterConvenio);
        spBraConvenio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedConvenioText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "Convenio Selected: " + selectedConvenioText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedConvenioText = "Selecione";
                //Log.d("tag", "Convenio Selected: " + selectedConvenioText);
            }
        });


        //  Distancias    //
        ArrayAdapter<String> adapterDistancia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, distancia);
        adapterDistancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBraDistancia.setAdapter(adapterDistancia);
        spBraDistancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedDistanciaText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Log.d("tag", "Distancias Selected: " + selectedDistanciaText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedDistanciaText = "Selecione";
                //Log.d("tag", "Distancias Selected: " + selectedDistanciaText);
            }
        });


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
            if (v.getId() == R.id.actvBraHospital && !hasFocus)
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
        //Log.d("tag", "In activity_buscarapida onStart");
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag", "In activity_buscarapida onResume");
    }


    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag", "In activity_buscarapida onPause");
    }


    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag", "In activity_buscarapida onStop");
    }


    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag", "In activity_buscarapida onDestroy");
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivBRaArrowback:
                //  Log de onClick  //
                //Log.d("tag", "onClick no ArrowLeft");

                finish();
                break;


            case R.id.cvBRaBuscar:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvBRaBuscar");

                if (podepassar == true)
                {
                    //Log.d("tag", "click no buscar e Podepassar = true");

                    //VALIDA se o Hospital inputado é válido
                    tvBraHospinv.setVisibility(View.GONE);
                    actvBraHospital.performValidation();

                    //OBTÉM o conteúdo dos filtros
                    String hospitalF    = actvBraHospital.getText().toString().replaceAll("[ ]", "%20");
                    String convenioF    = selectedConvenioText.replaceAll("[ ]", "%20");
                    String distanciaF   = selectedDistanciaText.replaceAll("[ ]", "%20");
                    String tempoesperaF = selectedTempoesperaText.replaceAll("[ ]", "%20");

                    //LOG pra verificar valores finais
                    /*Log.d("tag", "posicao: " + posicao
                            + ", hospitalF: " + hospitalF
                            + ", convenioF: " + convenioF
                            + ", distanciaF: " + distanciaF
                            + ", tempoesperaF: " + tempoesperaF);*/


                    //SE NÃO TIVER NENHUM FILTRO ESPECIFICADO, AVISAR E NÃO BUSCAR:
                    if (hospitalF.length() <= 3
                            && convenioF.equals("Selecione")
                            && distanciaF.equals("Selecione")
                            && tempoesperaF.equals("Selecione"))
                    {
                        Toast.makeText(activity_buscarapida.this, R.string.strNadaSelecionado, Toast.LENGTH_SHORT).show();
                        actvBraHospital.requestFocus();
                    }
                    else
                    {
                        //SE HOSPITAL FOI ESPECIFICADO, e tiver mais filtros especificados além do hospital, avisar ao cliente que esses filtros extras serão ignorados.
                        if (hospitalF.length() > 3
                                && (!convenioF.equals("Selecione")
                                || !distanciaF.equals("Selecione")
                                || !tempoesperaF.equals("Selecione")))
                        {
                            Toast.makeText(activity_buscarapida.this, R.string.strHospitalEspecifico, Toast.LENGTH_SHORT).show();
                        }

                        Intent intentBRaBuscar = new Intent(activity_buscarapida.this, activity_resultadobusca.class);
                        intentBRaBuscar.putExtra("hospitalF", hospitalF);
                        intentBRaBuscar.putExtra("convenioF", convenioF);
                        intentBRaBuscar.putExtra("distanciaF", distanciaF);
                        intentBRaBuscar.putExtra("tempoesperaF", tempoesperaF);
                        intentBRaBuscar.putExtra("posicaoF", posicao);
                        intentBRaBuscar.putExtra("tituloDaActivity", "Busca Rápida - Pronto Atendimento");
                        startActivity(intentBRaBuscar);
                    }
                }
                else
                {
                    //Log.d("tag", "click no buscar e Podepassar = false");

                    //VALIDA se o Hospital inputado é válido
                    tvBraHospinv.setVisibility(View.GONE);
                    actvBraHospital.performValidation();

                    //OBTÉM o conteúdo dos filtros
                    final String hospitalF      = actvBraHospital.getText().toString().replaceAll("[ ]", "%20");
                    final String convenioF      = selectedConvenioText.replaceAll("[ ]", "%20");
                    final String distanciaF     = selectedDistanciaText.replaceAll("[ ]", "%20");
                    final String tempoesperaF   = selectedTempoesperaText.replaceAll("[ ]", "%20");

                    //LOG pra verificar valores finais
                    /*Log.d("tag", "posicao: " + posicao
                            + ", hospitalF: " + hospitalF
                            + ", convenioF: " + convenioF
                            + ", distanciaF: " + distanciaF
                            + ", tempoesperaF: " + tempoesperaF);*/

                    //SE NÃO TIVER NENHUM FILTRO ESPECIFICADO, AVISAR E NÃO BUSCAR:
                    if (hospitalF.length() <= 3
                            && convenioF.equals("Selecione")
                            && distanciaF.equals("Selecione")
                            && tempoesperaF.equals("Selecione"))
                    {
                        Toast.makeText(activity_buscarapida.this, R.string.strNadaSelecionado, Toast.LENGTH_SHORT).show();
                        actvBraHospital.requestFocus();
                    }
                    else
                    {
                        Toast.makeText(this, "Obtendo localização...", Toast.LENGTH_LONG).show();
                        pbBuscando.setVisibility(View.VISIBLE);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (latitude != 0.0 && longitude != 0.0 && posicao != null)
                                {
                                    //SE HOSPITAL FOI ESPECIFICADO, e tiver mais filtros especificados além do hospital, avisar ao cliente que esses filtros extras serão ignorados.
                                    if (hospitalF.length() > 3
                                            && (!convenioF.equals("Selecione")
                                            || !distanciaF.equals("Selecione")
                                            || !tempoesperaF.equals("Selecione")))
                                    {
                                        Toast.makeText(activity_buscarapida.this, R.string.strHospitalEspecifico, Toast.LENGTH_SHORT).show();
                                    }

                                    //Log.d("tag", "click no buscar, e Podepassar = false, e latlong != 0");
                                    Intent intentBRaBuscar = new Intent(activity_buscarapida.this, activity_resultadobusca.class);
                                    intentBRaBuscar.putExtra("hospitalF", hospitalF);
                                    intentBRaBuscar.putExtra("convenioF", convenioF);
                                    intentBRaBuscar.putExtra("distanciaF", distanciaF);
                                    intentBRaBuscar.putExtra("tempoesperaF", tempoesperaF);
                                    intentBRaBuscar.putExtra("especialidadeF", "Selecione");
                                    intentBRaBuscar.putExtra("notageralF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("estacionamentoF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("recepcaoF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("organizacaoF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("sinalizacaoF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("cordialidadeF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("limpezaF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("triagemF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("medicoF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("enfermariaF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("examesF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("internacaoF", "Selecione%20a%20Nota");
                                    intentBRaBuscar.putExtra("posicaoF", posicao);
                                    intentBRaBuscar.putExtra("tituloDaActivity", "Busca Rápida - Pronto Atendimento");
                                    startActivity(intentBRaBuscar);
                                    pbBuscando.setVisibility(View.GONE);
                                }
                                else
                                {
                                    handler.postDelayed(this, 1000);
                                }
                            }
                        }, 1000);
                    }
                }
                break;

            case R.id.vBRaDistancia:
                //  Log de onClick  //
                //Log.d("tag", "onClick no vBRaDistancia");

                boolean gps_enabled = false;
                lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);

                //SE a versao do android for maior que 23, pede permissao do uso da localizacao na activity
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    //request Permissions
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);

                    //If permission to ACCESS_FINE_LOCATION is NOT granted
                    if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        int permissionCheck = ContextCompat.checkSelfPermission(activity_buscarapida.this, Manifest.permission.ACCESS_FINE_LOCATION);
                        //Log.d("TAG", "Permission check for ACCESS_FINE_LOCATION IS: " + String.valueOf(permissionCheck));

                        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(this, "Para utilizar o filtro Distância, é necessário que nos dê permissão para acessar sua localização.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                //SENAO, permissao ja foi dada ao instalar o app.
                else
                {

                }

                //If permission to ACCESS_FINE_LOCATION is granted
                if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    //  Log do status do GPS  //
                    //Log.d("TAG", "GPS ENABLED: " + String.valueOf(gps_enabled));

                    if (!gps_enabled)
                    {
                        Toast.makeText(this, "Para utilizar o filtro Distância é necessário ativar sua localização.", Toast.LENGTH_LONG).show();
                        break;
                    }

                    if (gps_enabled)
                    {
                        podepassar = false;
                        //Log.d("TAG", "click no distancia, e tem permissao e podepassar: " + podepassar);
                    }

                    // Define a listener that responds to location updates
                    ll = new LocationListener()
                    {
                        public void onLocationChanged(Location location)
                        {
                            // Called when a new location is found by the network location provider.
                            latitude  = location.getLatitude();
                            longitude = location.getLongitude();
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras)
                        {

                        }

                        public void onProviderEnabled(String provider)
                        {

                        }

                        public void onProviderDisabled(String provider)
                        {

                        }
                    };

                    // Register the listener with the Location Manager to receive location updates
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, ll);

                    final Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            //Log.d("TAG", "Current Latitude: " + String.valueOf(latitude) + ", Current Longitude: " + String.valueOf(longitude));

                            if (latitude != 0.0 && longitude != 0.0)
                            {
                                //Log.d("TAG", "Current Latitude: " + String.valueOf(latitude) + ", Current Longitude: " + String.valueOf(longitude));
                                posicao = String.valueOf(latitude) + "," + String.valueOf(longitude);
                                //Log.d("TAG", "posicao: " + posicao);
                                lm.removeUpdates(ll);
                                ll = null;
                                podepassar = true;
                                //Log.d("TAG", "podepassar: " + podepassar + " e posicao: " + posicao);
                            }
                            else
                            {
                                handler2.postDelayed(this, 1000);
                            }
                        }
                    }, 1000);

                    vBRaDistancia.setVisibility(View.GONE);
                }
                break;
        }
    }
}
