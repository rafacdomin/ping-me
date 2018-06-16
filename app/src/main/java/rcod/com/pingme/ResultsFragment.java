package rcod.com.pingme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class ResultsFragment extends Fragment {

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_results, container, false);

        TextView avgres = v.findViewById(R.id.avgres);
        TextView maxres = v.findViewById(R.id.maxres);
        TextView game = v.findViewById(R.id.game);
        TextView server = v.findViewById(R.id.server);
        Button checkagain = v.findViewById(R.id.PingAgain);
        Button changeserver = v.findViewById(R.id.ChangeServer);

        try {
            avgres.setText(MainActivity.avg[0]);
            maxres.setText(MainActivity.max[0]);
            game.setText(PingFragment.game);
            server.setText(PingFragment.pais);
        }catch (Exception e){
            Toast.makeText(getContext(),"Network error, please verify your connection and try again",Toast.LENGTH_LONG).show();
            MainActivity.callping.start();
        }

        checkagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.aux = 1;
                MainActivity.callresults.start();
                MainActivity.results(PingFragment.adress,getActivity());
            }
        });

        changeserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.callping.start();
            }
        });

        return v;
    }
}
