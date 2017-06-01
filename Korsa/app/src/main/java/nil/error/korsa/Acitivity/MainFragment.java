package nil.error.korsa.Acitivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nil.error.korsa.R;


public class MainFragment extends Fragment {

    View view;

    Button btn_offer_ride, btn_view_ride;
    private CardView cardoffer, cardview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mainfragment, container, false);

        this.view = view;

        cardoffer = (CardView) view.findViewById(R.id.card_offer);
        cardview = (CardView) view.findViewById(R.id.card_view);


        cardoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new OfferRIde();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frghomeholder, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().setTitle("Offer Ride");
            }
        });

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewRide();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frghomeholder, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().setTitle("View Ride");
            }
        });

        return view;
    }

}
