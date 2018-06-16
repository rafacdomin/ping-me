package rcod.com.pingme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import static java.lang.Thread.sleep;


public class LoadingFragment extends Fragment {

    public LoadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_loading, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    do {
                        sleep(1000);
                    }while(MainActivity.threadping.isAlive());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        return v;
    }
}
