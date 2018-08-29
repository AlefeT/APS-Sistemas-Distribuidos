package womm.quality_services.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import womm.quality_services.app.R;

public class activity_faleconosco extends AppCompatActivity  implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //  Log de entrada no onCreate  //
        //Log.d("tag", "In activity_faleconosco onCreate");

        //  Set Content View  //
        setContentView(R.layout.layout_faleconosco);



        //////////////////////////////////
        //  Declaração das variáveis    //
        //////////////////////////////////

        //  ImageView's  //
        ImageView ivFCArrowback  = findViewById(R.id.ivFCArrowback);
        ImageView ivFCTelefone   = findViewById(R.id.ivFCTelefone);
        ImageView ivFCEmail      = findViewById(R.id.ivFCEmail);
        ImageView ivFCWhats      = findViewById(R.id.ivFCWhats);
        ImageView ivFCFace       = findViewById(R.id.ivFCFace);
        ImageView ivFCSite       = findViewById(R.id.ivFCSite);
        ImageView ivFCChat       = findViewById(R.id.ivFCChat);



        //////////////////////////////////
        //  Set's dos Click Listeners   //
        //////////////////////////////////

        ivFCArrowback.setOnClickListener((View.OnClickListener) this);
        ivFCTelefone.setOnClickListener((View.OnClickListener) this);
        ivFCEmail.setOnClickListener((View.OnClickListener) this);
        ivFCWhats.setOnClickListener((View.OnClickListener) this);
        ivFCFace.setOnClickListener((View.OnClickListener) this);
        ivFCSite.setOnClickListener((View.OnClickListener) this);
        ivFCChat.setOnClickListener((View.OnClickListener) this);

    }



    /////////////////
    //  WHATSAPP   //
    /////////////////

    private void openWhatsApp()
    {
        if(whatsappInstalledOrNot("com.whatsapp"))
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("whatsapp://send?text=Ola&phone=5511989904536"));
            startActivity(browserIntent);
        }
        else
        {
            Toast.makeText(this, "Seu celular não possui o aplicativo WhatsApp instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean whatsappInstalledOrNot(String uri)
    {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try
        {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //  Log de entrada no onStart  //
        //Log.d("tag","In activity_faleconosco onStart");

    }



    @Override
    protected void onResume()
    {
        super.onResume();

        //  Log de entrada no onResume  //
        //Log.d("tag","In activity_faleconosco onResume");
    }



    @Override
    protected void onPause()
    {
        super.onPause();

        //  Log de entrada no onPause  //
        //Log.d("tag","In activity_faleconosco onPause");
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        //  Log de entrada no onStop  //
        //Log.d("tag","In activity_faleconosco onStop");
    }



    protected void onDestroy()
    {
        super.onDestroy();

        //  Log de entrada no onDestroy  //
        //Log.d("tag","In activity_faleconosco onDestroy");
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ivFCArrowback :
                //  Log de onClick //
                //Log.d("tag","onClick no ivFCArrowback");

                finish();
                break;



            case R.id.ivFCTelefone :
                //  Log de onClick //
                //Log.d("tag","onClick no ivFCTelefone");

                Intent intentTelefone = new Intent(Intent.ACTION_DIAL);
                intentTelefone.setData(Uri.parse("tel:+551131294248"));
                startActivity(intentTelefone);
                break;



            case R.id.ivFCEmail :
                //  Log de onClick //
                //Log.d("tag","onClick no ivFCEmail");

                Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                intentEmail.setData(Uri.parse("mailto:info@qualityservices.net.br"));
                startActivity(intentEmail);
                break;



            case R.id.ivFCWhats :
                //  Log de onClick //
                //Log.d("tag","onClick no ivFCWhats");

                openWhatsApp();
                break;



            case R.id.ivFCFace :
                //  Log de onClick //
                //Log.d("tag","onClick no ivFCFace");

                Intent intentFace = new Intent(Intent.ACTION_VIEW);
                intentFace.setData(Uri.parse("https://www.facebook.com/qualityservices.net/"));
                startActivity(intentFace);
                break;



            case R.id.ivFCSite :
                //  Log de onClick //
                //Log.d("tag","onClick no ivFCSite");

                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                intentSite.setData(Uri.parse("https://www.qualityservices.net.br/"));
                startActivity(intentSite);
                break;



            case R.id.ivFCChat :
                //  Log de onClick //
                //Log.d("tag","onClick no ivFCChat");

                Intent intentChat = new Intent(Intent.ACTION_VIEW);
                intentChat.setData(Uri.parse("https://dkvox.com.br/chatbots/chat/qualityservices/"));
                startActivity(intentChat);
                break;
        }
    }

}

