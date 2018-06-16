package rcod.com.pingme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



public class PingFragment extends Fragment {
    private int gametype;
    public static String adress, game, pais;
    private int strs;
    private String[] games = new String[]{"League of Legends", "Dota 2", "CS:GO","PUBG"};
    private String[] servidoreslol = new String[]{"NA","EUW","EUNE","OCE","BR","LAN"};
    private String[] ipslol = new String[]{"104.160.131.1","104.160.141.3","104.160.142.3","104.160.156.1","104.160.152.3"/*/"8.23.24.100"/*/,"104.160.136.3"};
    private String[] servidorescs = new String[]{
            "US West (Los Angeles)",
            "US East (Washington D.C)",
            "Europe West (Madrid)",
            "Europe West (Berlin)",
            "Europe East (Warsaw)",
            "Europe East (Vienna)",
            "Russia (Moscow)",
            "Russia 2 (Moscow)",
            "Australia (Sydney)",
            "Brazil (São Paulo)",
            "South America (Lima)",
            "South America (Santiago)",
            "Asia Pacific (Dubai)",
            "Asia Pacific (Mumbai)",
            "Asia Pacific (Hong Kong)",
            "Asia Pacific (Singapore)",
            "Asia Pacific (Beijing)",
            "Asia Pacific (Tokyo)"};
    private String[] ipsvalve = new String[]{
            "162.254.194.1",//Los Angeles
            "208.78.164.1",//Washington D.C
            "155.133.247.1",//Madrid
            "146.66.152.2",//Berlin
            "155.133.231.1",//Warsaw
            "146.66.155.1",//Vienna
            "146.66.156.2",//Moscow
            "185.25.180.1",//Moscow
            "155.133.227.2",//Sydney
            "155.133.224.1",//Sao Paulo
            "143.137.146.1",//Lima
            "155.133.249.1",//Santiago
            "185.25.183.1",//Dubai
            "155.133.233.2",//Mumbai
            "155.133.244.1",//Hong Kong
            "45.121.184.1",//Singapore
            "125.39.181.4",//Beijing
            "45.121.186.1"};//Tokyo

    private String[] servidorespub = new String[]{
            "US East (Virginia)",
            "US East (Ohio)",
            "US West (California)",
            "US West (Oregon)",
            "Canada (Central)",
            "Europe (Ireland)",
            "Europe (London)",
            "Europe (Frankfurt)",
            "South America (São Paulo)",
            "Australia (Sydney)",
            "Asia Pacific (Mumbai)",
            "Asia Pacific (Seoul)",
            "Asia Pacific (Singapore)",
            "Asia Pacific (Tokyo)"};
    private String[] ipspub = new String[]{
            "23.23.255.255",
            "13.58.0.253",
            "13.56.63.251",
            "34.208.63.251",
            "35.182.0.251",
            "34.248.60.213",
            "35.176.0.252",
            "35.156.63.252",
            "52.67.255.254",
            "13.54.63.252",
            "13.126.0.252",
            "13.124.63.251",
            "13.228.0.251",
            "13.112.63.251"};

    public PingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ping, container, false);
        // Inflate the layout for this fragment

        ArrayAdapter<String> gamesadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, games);
        gamesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> lolserveradapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, servidoreslol);
        lolserveradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> csserveradapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, servidorescs);
        csserveradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> pubserveradapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, servidorespub);
        pubserveradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ImageView imagem = v.findViewById(R.id.gameimage);

        Spinner sp1 = v.findViewById(R.id.spinner1);
        sp1.setAdapter(gamesadapter);

        final Spinner sp2 = v.findViewById(R.id.spinner2);
        sp2.setAdapter(lolserveradapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                game = games[position];
                gametype = position;
                switch (position){
                    case 0: imagem.setImageResource(R.drawable.ashe);sp2.setAdapter(lolserveradapter); break;
                    case 1: imagem.setImageResource(R.drawable.dota2);sp2.setAdapter(csserveradapter); break;
                    case 2: imagem.setImageResource(R.drawable.csgo);sp2.setAdapter(csserveradapter); break;
                    case 3: imagem.setImageResource(R.drawable.pubg);sp2.setAdapter(pubserveradapter);break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(gametype){
                    case 0: adress = ipslol[position]; pais = servidoreslol[position]; break;
                    case 1: adress = ipsvalve[position]; pais = servidorescs[position]; break;
                    case 2: adress = ipsvalve[position]; pais = servidorescs[position]; break;
                    case 3: adress = ipspub[position]; pais = servidorespub[position]; break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button pingtest = v.findViewById(R.id.PingTest);
        pingtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.VerificarConexão(getContext())){

                    MainActivity.aux = 1;
                    MainActivity.callresults.start();
                    MainActivity.results(adress,getActivity());
                }else {
                    Toast.makeText(getContext(), "Wi-Fi connection not detected, please verify and try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}
