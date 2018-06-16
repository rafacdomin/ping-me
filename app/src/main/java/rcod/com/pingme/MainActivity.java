package rcod.com.pingme;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static String ShowOrHideWebViewInitialUse = "show";
    private Button pingtab, abouttab, speedtab;
    private AdView mAdView;
    public static Thread threadping, callresults, callping;
    public static String[] avg = new String[1];
    public static String[] max = new String[1];
    public static final String[] teste = new String[1];
    public static int aux = 0;
    private final SpeedFragment speedfrag = new SpeedFragment();
    private final AboutFragment aboutfrag = new AboutFragment();
    private final PingFragment pingfrag = new PingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();//remover titlebar
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-7625686165906035~3299698996");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        pingtab = findViewById(R.id.pingtab);
        speedtab = findViewById(R.id.speedtab);
        abouttab = findViewById(R.id.abouttab);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, pingfrag).commit();
        ShowOrHideWebViewInitialUse = "show";

        new Thread(new Runnable() {
            @Override
            public void run() {
                pingtab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!getSupportFragmentManager().equals(pingfrag)){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,pingfrag).commit();
                            ShowOrHideWebViewInitialUse = "show";
                        }
                    }
                });

                speedtab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!getSupportFragmentManager().equals(speedfrag)){
                            if(VerificarConexão(getApplicationContext())){
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,speedfrag).commit();
                                ShowOrHideWebViewInitialUse = "show";
                            }else {
                                Toast.makeText(getApplicationContext(), "Wi-Fi connection not detected, please verify and try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

                abouttab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!getSupportFragmentManager().equals(aboutfrag)){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,aboutfrag).commit();
                            ShowOrHideWebViewInitialUse = "show";
                        }
                    }
                });
            }
        }).start();

        callresults = new Thread(new Runnable() {
            @Override
            public void run() {
                int out = 1;
                while(out==1){
                    if(aux == 1){
                        out=0;
                        aux = 0;
                        resultscreen();
                    }
                }
            }
        });
        callping = new Thread(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,pingfrag).commit();
                ShowOrHideWebViewInitialUse = "show";
            }
        });
    }


    public static void results(final String IpAdress, final Activity activity){

        //final String[] teste = new String[1];
        final Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                Toast.makeText(activity.getApplicationContext(),"Network Error, please check your internet connection",Toast.LENGTH_LONG).show();
            }
        };
        threadping = new Thread(new Runnable() {
            @Override
            public void run() {
                teste[0] = ping(IpAdress);
            }
        });
        threadping.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int out = 1;
                    do {
                        if (!threadping.isAlive()) {
                            try {
                                String[] rtt = teste[0].split("mdev = ");
                                if (rtt.length > 1) {
                                    String[] result = rtt[1].split("/");
                                    String[] min = result[0].split("[.]");
                                    max = result[2].split("[.]");
                                    avg = result[1].split("[.]");
                                    String[] mdev = result[3].split("[.]");
                                    Log.i("Ping Test", teste[0]);
                                } else throw new RuntimeException("Error ping server");
                            }catch (Exception e){
                                MainActivity.callping.start();
                                Message message = mHandler.obtainMessage();
                                message.sendToTarget();

                            }
                            out = 0;
                        }
                    } while (out == 1);
                }
            }).start();
    }

    public static String ping(String ip) {
        String str = "";
        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/ping -c 10 " + ip);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuilder output = new StringBuilder();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();

            //body.append(output.toString()+"\n");
            str = output.toString();
            // Log.d(TAG, str);
        } catch (IOException e) {
            callping.start();
            // body.append("Error\n");
            e.printStackTrace();
        }
        return str;
    }

    public void resultscreen(){
        final LoadingFragment loadingfrag = new LoadingFragment();
        final ResultsFragment resultsfrag = new ResultsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,loadingfrag).commit();
        ShowOrHideWebViewInitialUse = "show";

        new Thread(new Runnable() {
            @Override
            public void run() {
                int out = 1;
                while(out==1) {
                    if(!threadping.isAlive()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultsfrag).commit();
                        ShowOrHideWebViewInitialUse = "show";
                        out = 0;
                    }
                }
            }
        }).start();
    }

    public static boolean VerificarConexão(Context c){
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        isConnected = cm.getNetworkInfo(1).isConnected();

        return isConnected;
    }
}

