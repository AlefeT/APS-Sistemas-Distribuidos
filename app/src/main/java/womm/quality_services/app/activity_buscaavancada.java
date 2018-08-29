package womm.quality_services.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;

import womm.quality_services.app.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class activity_buscaavancada extends AppCompatActivity implements View.OnClickListener
{
    LocationListener ll;
    LocationManager lm;
    AutoCompleteTextView actvBAvHospital;
    TextView tvBAvHospinv;
    View vBAvDistancia;
    boolean podepassar     = true;
    double latitude, longitude;
    ProgressBar pbBuscando, pbCarregandolista;
    Spinner spBAvEspera, spBAvEspecialidade, spBAvDistancia, spBAvConvenio, spBAvNotageral, spBAvEstacionamento, spBAvRecepcao, spBAvOrganizacao, spBAvSinalizacao, spBAvCordialidade, spBAvLimpeza, spBAvTriagem, spBAvMedico, spBAvEnfermaria, spBAvExames, spBAvInternacao;
    String  hospitais[], selectedEsperaText, selectedEspecialidadeText, selectedDistanciaText, selectedConvenioText, selectedNotageralText, selectedEstacionamentoText, selectedRecepcaoText, selectedOrganizacaoText, selectedSinalizacaoText, selectedCordialidadeText, selectedLimpezaText, selectedTriagemText, selectedMedicoText, selectedEnfermariaText, selectedExamesText, selectedInternacaoText, posicao;
    String[] tempoespera   = new String[]{"Selecione", "Até 30min", "Até 1 hora", "Até 1 hora e 30min", "Mais de 1 hora e 30min"};
    String[] especialidade = new String[]{"Selecione", "Alergia e Imunologia", "Anestesiologia", "Cardiologia", "Cirurgia Plástica", "Clínico Geral", "Dermatologia", "Endocrinologia", "Gastroenterologia", "Geriatria", "Ginecologia", "Hematologia", "Infectologia", "Mastologia", "Neurologia", "Obstetrícia", "Oftalmologia", "Oncologia", "Ortopedia", "Otorrinolaringologia", "Pediatria", "Psiquiatria", "Radioterapia", "Reumatologia", "Terapia Intensiva", "Transplantes", "Urologia"};
    String[] distancia     = new String[]{"Selecione", "Até 5km", "Até 10km", "Até 20km", "Mais de 20km"};
    String[] convenios     = new String[]{"Selecione", "Ameplan - Executivo", "Ameplan - Master", "Amil", "Amil 200 Regional", "Amil 400", "Amil 700", "Biosaúde Básico", "Biosaúde Especial", "Blue Med", "Blue Med Premium Standard", "Bradesco Seguro Saúde - Nacional Flex", "Bradesco Seguro Saúde - Top Nacional", "Classes Laboriosas Individual Enfermaria Prime", "Dix Saúde Individual 100 QC", "Garantia Adventista"};
    String[] notas         = new String[]{"Selecione a Nota", "Menor que 2,5", "Entre 2,5 e 5,0", "Entre 5,1 e 7,5", "Maior que 7,5"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_buscaavancada onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_buscaavancada);


        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's //
        ImageView ivBAvArrowback = findViewById(R.id.ivBAvArrowback);

        //  Spinner's //
        spBAvEspera              = findViewById(R.id.spBAvEspera);
        spBAvEspecialidade       = findViewById(R.id.spBAvEspecialidade);
        spBAvDistancia           = findViewById(R.id.spBAvDistancia);
        spBAvConvenio            = findViewById(R.id.spBAvConvenio);
        spBAvNotageral           = findViewById(R.id.spBAvNotageral);
        spBAvEstacionamento      = findViewById(R.id.spBAvEstacionamento);
        spBAvRecepcao            = findViewById(R.id.spBAvRecepcao);
        spBAvOrganizacao         = findViewById(R.id.spBAvOrganizacao);
        spBAvSinalizacao         = findViewById(R.id.spBAvSinalizacao);
        spBAvCordialidade        = findViewById(R.id.spBAvCordialidade);
        spBAvLimpeza             = findViewById(R.id.spBAvLimpeza);
        spBAvTriagem             = findViewById(R.id.spBAvTriagem);
        spBAvMedico              = findViewById(R.id.spBAvMedico);
        spBAvEnfermaria          = findViewById(R.id.spBAvEnfermaria);
        spBAvExames              = findViewById(R.id.spBAvExames);
        spBAvInternacao          = findViewById(R.id.spBAvInternacao);

        //  AutoCompleteTextView's //
        actvBAvHospital          = findViewById(R.id.actvBAvHospital);

        //  CardView's  //
        CardView cvBAvBuscar     = findViewById(R.id.cvBAvBuscar);

        //  TextView's  //
        tvBAvHospinv             = findViewById(R.id.tvBAvHospinv);

        //  View's  //
        vBAvDistancia            = findViewById(R.id.vBAvDistancia);

        //  ProgressBar's   //
        pbBuscando               = findViewById(R.id.pbBuscando);
        pbCarregandolista        = findViewById(R.id.pbCarregandolista);

        pbCarregandolista.setVisibility(View.VISIBLE);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivBAvArrowback.setOnClickListener((View.OnClickListener) this);
        cvBAvBuscar.setOnClickListener((View.OnClickListener) this);
        vBAvDistancia.setOnClickListener((View.OnClickListener) this);



        ///////////////////////////////////////////
        //    AutoCompleteTextView: Hospital     //
        ///////////////////////////////////////////

        //CONEXÃO VOLLEY COMEÇA AQUI
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
                actvBAvHospital.setAdapter(new ArrayAdapter<String>(activity_buscaavancada.this, android.R.layout.simple_dropdown_item_1line, hospitais));
                actvBAvHospital.setValidator(new activity_buscaavancada.Validator());
                actvBAvHospital.setOnFocusChangeListener(new activity_buscaavancada.FocusListener());

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



        ///////////////////////////////////
        //          SPINNERS             //
        ///////////////////////////////////

        //////////////////////////
        //  Tempos de Espera
        //
        ArrayAdapter<String> adapterEspera = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tempoespera);
        adapterEspera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvEspera.setAdapter(adapterEspera);
        spBAvEspera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedEsperaText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedEsperaText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedEsperaText = "Selecione";
                //Log.d("tag", "Selecionado: " + selectedEsperaText);
            }
        });


        //////////////////////////
        //  Especialidade
        //
        ArrayAdapter<String> adapterEspecialidade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, especialidade);
        adapterEspecialidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvEspecialidade.setAdapter(adapterEspecialidade);
        spBAvEspecialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedEspecialidadeText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedEspecialidadeText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedEspecialidadeText = "Selecione";
                //Log.d("tag", "Selecionado: " + selectedEspecialidadeText);
            }
        });


        //////////////////////////
        //  Distancia
        //
        ArrayAdapter<String> adapterDistancia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, distancia);
        adapterDistancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvDistancia.setAdapter(adapterDistancia);
        spBAvDistancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedDistanciaText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedDistanciaText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedDistanciaText = "Selecione";
                //Log.d("tag", "Selecionado: " + selectedDistanciaText);
            }
        });


        //////////////////////////
        //  Convenio
        //
        ArrayAdapter<String> adapterConvenio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, convenios);
        adapterConvenio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvConvenio.setAdapter(adapterConvenio);
        spBAvConvenio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedConvenioText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedConvenioText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedConvenioText = "Selecione";
                //Log.d("tag", "Selecionado: " + selectedConvenioText);
            }
        });


        //////////////////////////
        //  Nota Geral
        //
        ArrayAdapter<String> adapterNotageral = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterNotageral.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvNotageral.setAdapter(adapterNotageral);
        spBAvNotageral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedNotageralText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedNotageralText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedNotageralText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedNotageralText);
            }
        });


        //////////////////////////
        //  Estacionamento
        //
        ArrayAdapter<String> adapterEstacionamento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterEstacionamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvEstacionamento.setAdapter(adapterEstacionamento);
        spBAvEstacionamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedEstacionamentoText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedEstacionamentoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedEstacionamentoText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedEstacionamentoText);
            }
        });


        //////////////////////////
        //  Recepcao
        //
        ArrayAdapter<String> adapterRecepcao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterRecepcao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvRecepcao.setAdapter(adapterRecepcao);
        spBAvRecepcao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedRecepcaoText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedRecepcaoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedRecepcaoText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedRecepcaoText);
            }
        });


        //////////////////////////
        //  Organizacao
        //
        ArrayAdapter<String> adapterOrganizacao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterOrganizacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvOrganizacao.setAdapter(adapterOrganizacao);
        spBAvOrganizacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedOrganizacaoText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedOrganizacaoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedOrganizacaoText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedOrganizacaoText);
            }
        });


        //////////////////////////
        //  Sinalizacao
        //
        ArrayAdapter<String> adapterSinalizacao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterSinalizacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvSinalizacao.setAdapter(adapterSinalizacao);
        spBAvSinalizacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedSinalizacaoText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedSinalizacaoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedSinalizacaoText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedSinalizacaoText);
            }
        });


        //////////////////////////
        //  Cordialidade
        //
        ArrayAdapter<String> adapterCordialidade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterCordialidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvCordialidade.setAdapter(adapterCordialidade);
        spBAvCordialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedCordialidadeText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedCordialidadeText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedCordialidadeText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedCordialidadeText);
            }
        });


        //////////////////////////
        //  Limpeza
        //
        ArrayAdapter<String> adapterLimpeza = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterLimpeza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvLimpeza.setAdapter(adapterLimpeza);
        spBAvLimpeza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedLimpezaText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedLimpezaText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedLimpezaText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedLimpezaText);
            }
        });


        //////////////////////////
        //  Triagem
        //
        ArrayAdapter<String> adapterTriagem = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterTriagem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvTriagem.setAdapter(adapterTriagem);
        spBAvTriagem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedTriagemText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedTriagemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedTriagemText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedTriagemText);
            }
        });


        //////////////////////////
        //  Medico
        //
        ArrayAdapter<String> adapterMedico = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterMedico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvMedico.setAdapter(adapterMedico);
        spBAvMedico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedMedicoText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedMedicoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedMedicoText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedMedicoText);
            }
        });


        //////////////////////////
        //  Enfermaria
        //
        ArrayAdapter<String> adapterEnfermaria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterEnfermaria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvEnfermaria.setAdapter(adapterEnfermaria);
        spBAvEnfermaria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedEnfermariaText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedEnfermariaText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedEnfermariaText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedEnfermariaText);
            }
        });


        //////////////////////////
        //  Exames
        //
        ArrayAdapter<String> adapterExames = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterExames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvExames.setAdapter(adapterExames);
        spBAvExames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedExamesText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedExamesText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedExamesText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedExamesText);
            }
        });


        //////////////////////////
        //  Internacao
        //
        ArrayAdapter<String> adapterInternacao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notas);
        adapterInternacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBAvInternacao.setAdapter(adapterInternacao);
        spBAvInternacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedInternacaoText = (String) parent.getItemAtPosition(position);
                //Log.d("tag", "Selecionado: " + selectedInternacaoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedInternacaoText = "Selecione a Nota";
                //Log.d("tag", "Selecionado: " + selectedInternacaoText);
            }
        });


    }

    //VALIDADOR Verifica se o Hospital inputado é VÁLIDO (se está na lista de itens válidos)
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
        //ARRUMADOR Se o item inputado for INVÁLIDO, oque fazer e oque retornar como input
        @Override
        public CharSequence fixText(CharSequence invalidText)
        {
            //Log.v("tag", "Returning hospital fixed text");
            tvBAvHospinv.setVisibility(View.VISIBLE);
            actvBAvHospital.setText("");
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
            if (v.getId() == R.id.actvBAvHospital && !hasFocus)
            {
                //Log.v("tag", "Performing hospital validation");
                ((AutoCompleteTextView)v).performValidation();
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
        //Log.d("tag","In activity_buscaavancada onStart");
    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_buscaavancada onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_buscaavancada onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_buscaavancada onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_buscaavancada onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivBAvArrowback:
                //  Log de onClick  //
                //Log.d("tag", "onClick no ArrowLeft");

                finish();
                break;


            case R.id.cvBAvBuscar:
                //  Log de onClick  //
                //Log.d("tag", "onClick no cvBAvBuscar");

                if (podepassar == true)
                {
                    //Log.d("tag", "click no buscar e Podepassar = true");

                    //VALIDA se o Hospital inputado é válido
                    tvBAvHospinv.setVisibility(View.GONE);
                    actvBAvHospital.performValidation();

                    //OBTÉM o conteúdo dos filtros
                    String hospitalF        = actvBAvHospital.getText().toString().replaceAll("[ ]", "%20");
                    String tempoesperaF     = selectedEsperaText.replaceAll("[ ]", "%20");
                    String especialidadeF   = selectedEspecialidadeText.replaceAll("[ ]", "%20");
                    String distanciaF       = selectedDistanciaText.replaceAll("[ ]", "%20");
                    String convenioF        = selectedConvenioText.replaceAll("[ ]", "%20");
                    String notageralF       = selectedNotageralText.replaceAll("[ ]", "%20");
                    String estacionamentoF  = selectedEstacionamentoText.replaceAll("[ ]", "%20");
                    String recepcaoF        = selectedRecepcaoText.replaceAll("[ ]", "%20");
                    String organizacaoF     = selectedOrganizacaoText.replaceAll("[ ]", "%20");
                    String sinalizacaoF     = selectedSinalizacaoText.replaceAll("[ ]", "%20");
                    String cordialidadeF    = selectedCordialidadeText.replaceAll("[ ]", "%20");
                    String limpezaF         = selectedLimpezaText.replaceAll("[ ]", "%20");
                    String triagemF         = selectedTriagemText.replaceAll("[ ]", "%20");
                    String medicoF          = selectedMedicoText.replaceAll("[ ]", "%20");
                    String enfermariaF      = selectedEnfermariaText.replaceAll("[ ]", "%20");
                    String examesF          = selectedExamesText.replaceAll("[ ]", "%20");
                    String internacaoF      = selectedInternacaoText.replaceAll("[ ]", "%20");

                    //LOG pra verificar valores finais
                    /*Log.d("tag", "posicaoF: " + posicao
                            + ", hospitalF: " + hospitalF
                            + ", convenioF: " + convenioF
                            + ", distanciaF: " + distanciaF
                            + ", tempoesperaF: " + tempoesperaF
                            + ", especialidadeF: " + especialidadeF
                            + ", notageralF: " + notageralF
                            + ", estacionamentoF: " + estacionamentoF
                            + ", recepcaoF: " + recepcaoF
                            + ", organizacaoF: " + organizacaoF
                            + ", sinalizacaoF: " + sinalizacaoF
                            + ", cordialidadeF: " + cordialidadeF
                            + ", limpezaF: " + limpezaF
                            + ", triagemF: " + triagemF
                            + ", medicoF: " + medicoF
                            + ", enfermariaF: " + enfermariaF
                            + ", examesF: " + examesF
                            + ", internacaoF: " + internacaoF);*/

                    //SE NÃO TIVER NENHUM FILTRO ESPECIFICADO, AVISAR E NÃO BUSCAR:
                    if (hospitalF.length() <= 3
                            && tempoesperaF.equals("Selecione")
                            && especialidadeF.equals("Selecione")
                            && distanciaF.equals("Selecione")
                            && convenioF.equals("Selecione")
                            && notageralF.equals("Selecione%20a%20Nota")
                            && estacionamentoF.equals("Selecione%20a%20Nota")
                            && recepcaoF.equals("Selecione%20a%20Nota")
                            && organizacaoF.equals("Selecione%20a%20Nota")
                            && sinalizacaoF.equals("Selecione%20a%20Nota")
                            && cordialidadeF.equals("Selecione%20a%20Nota")
                            && limpezaF.equals("Selecione%20a%20Nota")
                            && triagemF.equals("Selecione%20a%20Nota")
                            && medicoF.equals("Selecione%20a%20Nota")
                            && enfermariaF.equals("Selecione%20a%20Nota")
                            && examesF.equals("Selecione%20a%20Nota")
                            && internacaoF.equals("Selecione%20a%20Nota"))
                    {
                        Toast.makeText(activity_buscaavancada.this, R.string.strNadaSelecionado, Toast.LENGTH_SHORT).show();
                        actvBAvHospital.requestFocus();
                    }
                    else
                    {
                        //SE HOSPITAL FOI ESPECIFICADO, e tiver mais filtros especificados além do hospital, avisar ao cliente que esses filtros extras serão ignorados.
                        if (hospitalF.length() > 3
                                && (!tempoesperaF.equals("Selecione")
                                || !especialidadeF.equals("Selecione")
                                || !distanciaF.equals("Selecione")
                                || !convenioF.equals("Selecione")
                                || !notageralF.equals("Selecione%20a%20Nota")
                                || !estacionamentoF.equals("Selecione%20a%20Nota")
                                || !recepcaoF.equals("Selecione%20a%20Nota")
                                || !organizacaoF.equals("Selecione%20a%20Nota")
                                || !sinalizacaoF.equals("Selecione%20a%20Nota")
                                || !cordialidadeF.equals("Selecione%20a%20Nota")
                                || !limpezaF.equals("Selecione%20a%20Nota")
                                || !triagemF.equals("Selecione%20a%20Nota")
                                || !medicoF.equals("Selecione%20a%20Nota")
                                || !enfermariaF.equals("Selecione%20a%20Nota")
                                || !examesF.equals("Selecione%20a%20Nota")
                                || !internacaoF.equals("Selecione%20a%20Nota")))
                        {
                            Toast.makeText(activity_buscaavancada.this, R.string.strHospitalEspecifico, Toast.LENGTH_SHORT).show();
                        }

                        Intent intentBAvBuscar = new Intent(activity_buscaavancada.this, activity_resultadobusca.class);
                        intentBAvBuscar.putExtra("hospitalF", hospitalF);
                        intentBAvBuscar.putExtra("tempoesperaF", tempoesperaF);
                        intentBAvBuscar.putExtra("especialidadeF", especialidadeF);
                        intentBAvBuscar.putExtra("distanciaF", distanciaF);
                        intentBAvBuscar.putExtra("convenioF", convenioF);
                        intentBAvBuscar.putExtra("notageralF", notageralF);
                        intentBAvBuscar.putExtra("estacionamentoF", estacionamentoF);
                        intentBAvBuscar.putExtra("recepcaoF", recepcaoF);
                        intentBAvBuscar.putExtra("organizacaoF", organizacaoF);
                        intentBAvBuscar.putExtra("sinalizacaoF", sinalizacaoF);
                        intentBAvBuscar.putExtra("cordialidadeF", cordialidadeF);
                        intentBAvBuscar.putExtra("limpezaF", limpezaF);
                        intentBAvBuscar.putExtra("triagemF", triagemF);
                        intentBAvBuscar.putExtra("medicoF", medicoF);
                        intentBAvBuscar.putExtra("enfermariaF", enfermariaF);
                        intentBAvBuscar.putExtra("examesF", examesF);
                        intentBAvBuscar.putExtra("internacaoF", internacaoF);
                        intentBAvBuscar.putExtra("posicaoF", posicao);
                        intentBAvBuscar.putExtra("tituloDaActivity", "Busca Avançada");
                        startActivity(intentBAvBuscar);
                    }
                }
                else
                {
                    //Log.d("tag", "click no buscar e Podepassar = false");

                    //VALIDA se o Hospital inputado é válido
                    tvBAvHospinv.setVisibility(View.GONE);
                    actvBAvHospital.performValidation();

                    //OBTÉM o conteúdo dos filtros
                    final String hospitalF       = actvBAvHospital.getText().toString().replaceAll("[ ]", "%20");
                    final String tempoesperaF    = selectedEsperaText.replaceAll("[ ]", "%20");
                    final String especialidadeF  = selectedEspecialidadeText.replaceAll("[ ]", "%20");
                    final String distanciaF      = selectedDistanciaText.replaceAll("[ ]", "%20");
                    final String convenioF       = selectedConvenioText.replaceAll("[ ]", "%20");
                    final String notageralF      = selectedNotageralText.replaceAll("[ ]", "%20");
                    final String estacionamentoF = selectedEstacionamentoText.replaceAll("[ ]", "%20");
                    final String recepcaoF       = selectedRecepcaoText.replaceAll("[ ]", "%20");
                    final String organizacaoF    = selectedOrganizacaoText.replaceAll("[ ]", "%20");
                    final String sinalizacaoF    = selectedSinalizacaoText.replaceAll("[ ]", "%20");
                    final String cordialidadeF   = selectedCordialidadeText.replaceAll("[ ]", "%20");
                    final String limpezaF        = selectedLimpezaText.replaceAll("[ ]", "%20");
                    final String triagemF        = selectedTriagemText.replaceAll("[ ]", "%20");
                    final String medicoF         = selectedMedicoText.replaceAll("[ ]", "%20");
                    final String enfermariaF     = selectedEnfermariaText.replaceAll("[ ]", "%20");
                    final String examesF         = selectedExamesText.replaceAll("[ ]", "%20");
                    final String internacaoF     = selectedInternacaoText.replaceAll("[ ]", "%20");

                    //LOG pra verificar valores finais
                    /*Log.d("tag", "posicaoF: " + posicao
                            + ", hospitalF: " + hospitalF
                            + ", convenioF: " + convenioF
                            + ", distanciaF: " + distanciaF
                            + ", tempoesperaF: " + tempoesperaF
                            + ", especialidadeF: " + especialidadeF
                            + ", notageralF: " + notageralF
                            + ", estacionamentoF: " + estacionamentoF
                            + ", recepcaoF: " + recepcaoF
                            + ", organizacaoF: " + organizacaoF
                            + ", sinalizacaoF: " + sinalizacaoF
                            + ", cordialidadeF: " + cordialidadeF
                            + ", limpezaF: " + limpezaF
                            + ", triagemF: " + triagemF
                            + ", medicoF: " + medicoF
                            + ", enfermariaF: " + enfermariaF
                            + ", examesF: " + examesF
                            + ", internacaoF: " + internacaoF);*/

                    //SE NÃO TIVER NENHUM FILTRO ESPECIFICADO, AVISAR E NÃO BUSCAR:
                    if (hospitalF.length() <= 3
                            && tempoesperaF.equals("Selecione")
                            && especialidadeF.equals("Selecione")
                            && distanciaF.equals("Selecione")
                            && convenioF.equals("Selecione")
                            && notageralF.equals("Selecione%20a%20Nota")
                            && estacionamentoF.equals("Selecione%20a%20Nota")
                            && recepcaoF.equals("Selecione%20a%20Nota")
                            && organizacaoF.equals("Selecione%20a%20Nota")
                            && sinalizacaoF.equals("Selecione%20a%20Nota")
                            && cordialidadeF.equals("Selecione%20a%20Nota")
                            && limpezaF.equals("Selecione%20a%20Nota")
                            && triagemF.equals("Selecione%20a%20Nota")
                            && medicoF.equals("Selecione%20a%20Nota")
                            && enfermariaF.equals("Selecione%20a%20Nota")
                            && examesF.equals("Selecione%20a%20Nota")
                            && internacaoF.equals("Selecione%20a%20Nota"))
                    {
                        Toast.makeText(activity_buscaavancada.this, R.string.strNadaSelecionado, Toast.LENGTH_SHORT).show();
                        actvBAvHospital.requestFocus();
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
                                            && (!tempoesperaF.equals("Selecione")
                                            || !especialidadeF.equals("Selecione")
                                            || !distanciaF.equals("Selecione")
                                            || !convenioF.equals("Selecione")
                                            || !notageralF.equals("Selecione%20a%20Nota")
                                            || !estacionamentoF.equals("Selecione%20a%20Nota")
                                            || !recepcaoF.equals("Selecione%20a%20Nota")
                                            || !organizacaoF.equals("Selecione%20a%20Nota")
                                            || !sinalizacaoF.equals("Selecione%20a%20Nota")
                                            || !cordialidadeF.equals("Selecione%20a%20Nota")
                                            || !limpezaF.equals("Selecione%20a%20Nota")
                                            || !triagemF.equals("Selecione%20a%20Nota")
                                            || !medicoF.equals("Selecione%20a%20Nota")
                                            || !enfermariaF.equals("Selecione%20a%20Nota")
                                            || !examesF.equals("Selecione%20a%20Nota")
                                            || !internacaoF.equals("Selecione%20a%20Nota")))
                                    {
                                        Toast.makeText(activity_buscaavancada.this, R.string.strHospitalEspecifico, Toast.LENGTH_SHORT).show();
                                    }

                                    //Log.d("tag", "click no buscar, e Podepassar = false, e latlong != 0");
                                    Intent intentBAvBuscar = new Intent(activity_buscaavancada.this, activity_resultadobusca.class);
                                    intentBAvBuscar.putExtra("hospitalF", hospitalF);
                                    intentBAvBuscar.putExtra("tempoesperaF", tempoesperaF);
                                    intentBAvBuscar.putExtra("especialidadeF", especialidadeF);
                                    intentBAvBuscar.putExtra("distanciaF", distanciaF);
                                    intentBAvBuscar.putExtra("convenioF", convenioF);
                                    intentBAvBuscar.putExtra("notageralF", notageralF);
                                    intentBAvBuscar.putExtra("estacionamentoF", estacionamentoF);
                                    intentBAvBuscar.putExtra("recepcaoF", recepcaoF);
                                    intentBAvBuscar.putExtra("organizacaoF", organizacaoF);
                                    intentBAvBuscar.putExtra("sinalizacaoF", sinalizacaoF);
                                    intentBAvBuscar.putExtra("cordialidadeF", cordialidadeF);
                                    intentBAvBuscar.putExtra("limpezaF", limpezaF);
                                    intentBAvBuscar.putExtra("triagemF", triagemF);
                                    intentBAvBuscar.putExtra("medicoF", medicoF);
                                    intentBAvBuscar.putExtra("enfermariaF", enfermariaF);
                                    intentBAvBuscar.putExtra("examesF", examesF);
                                    intentBAvBuscar.putExtra("internacaoF", internacaoF);
                                    intentBAvBuscar.putExtra("posicaoF", posicao);
                                    intentBAvBuscar.putExtra("tituloDaActivity", "Busca Avançada");
                                    startActivity(intentBAvBuscar);
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


            case R.id.vBAvDistancia:
                //  Log de onClick  //
                //Log.d("tag", "onClick no vBAvDistancia");

                boolean gps_enabled = false;
                lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);


                //SE a versao do android for maior que 23, pede permissao do uso da localizacao na activity
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    //request Permissions
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);

                    //If permission to ACCESS_FINE_LOCATION is NOT granted
                    if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        int permissionCheck = ContextCompat.checkSelfPermission(activity_buscaavancada.this, Manifest.permission.ACCESS_FINE_LOCATION);
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
                            latitude = location.getLatitude();
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

                    vBAvDistancia.setVisibility(View.GONE);
                }
                break;
        }
    }
}
