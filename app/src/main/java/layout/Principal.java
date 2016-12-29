package layout;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import a21200800isec.cmcticket2.Model.Model;
import a21200800isec.cmcticket2.R;

public class Principal extends FragmentActivity implements OnMapReadyCallback{
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment ticketForm;
    MyMapFragment sMapFragment;
    ArrayList<String> sideMenuText;
    ListView mSideListView;
    DrawerLayout drawerLayout;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(a21200800isec.cmcticket2.R.layout.activity_principal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 19);
        }
        //model = new Model();
        setLayout();
        setControllers();
        sMapFragment = new MyMapFragment();
        //ticketForm = new TicketForm();
        //ticketForm = this.getFragmentManager().findFragmentById(R.id.formTicket);

        model = new Model(this);


    }

    public void setLayout(){
        sideMenuText= new ArrayList<>();
        sideMenuText.add("Sent");
        sideMenuText.add("Beacon?");
        sideMenuText.add("About");
        sideMenuText.add("Quit");
        drawerLayout = (DrawerLayout) findViewById(R.id.side_menu_layout);
        mSideListView = (ListView) findViewById(R.id.side_menu_view);
        mSideListView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_text_view, sideMenuText));

    }

    public void setControllers(){
        mSideListView.setOnItemClickListener(new DrawerItemClickListener());

    }
    public void switchFragment(){
        /*
         FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                   .replace(R.id.content_frame, fragment)
                   .commit();
        * */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.model.setMap(googleMap);
        Log.d("LAT",String.valueOf(model.getCurrentLatitude()));
        Log.d("LON",String.valueOf(model.getCurrentLongitude()));
        LatLng local = new LatLng(model.getCurrentLatitude(), model.getCurrentLongitude());
        this.model.getMap().addMarker(new MarkerOptions().position(local).title("HERE"));
        this.model.getMap().moveCamera(CameraUpdateFactory.newLatLng(local));
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //onclick get position change fragments
            Log.d("D","Click");
        }
    }

    private void changeFragment(Fragment frag, boolean saveInBackstack, boolean animate) {
        /*String backStateName = ((Object) frag).getClass().getName();

        try {
            fragmentManager = this.getFragmentManager();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

            if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null) {
                //fragment not in back stack, create it.
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (animate) {
                    //Log.d(TAG, "Change Fragment: animate");
                   // transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                }

                transaction.replace(R.id.fragContainer, frag, backStateName);

                if (saveInBackstack) {
                    //Log.d(TAG, "Change Fragment: addToBackTack " + backStateName);
                    transaction.addToBackStack(backStateName);
                }/* else {
                    Log.d(TAG, "Change Fragment: NO addToBackTack");
                }*/

                /*transaction.commit();
            } else {
                // custom effect if fragment is already instanciated
            }
        } catch (IllegalStateException exception) {
            Log.w("e", "Unable to commit fragment, could be activity as been killed in background. " + exception.toString());
        }*/
    }


    public void onClick(View v) {
        //TextView txt2 = (TextView)findViewById(R.id.text2);
        //txt2.setText("teste");
        //changeFragment(ticketForm,false,false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sMapFragment.getMapAsync(this);
        fragmentManager = this.getFragmentManager();

        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragContainer,sMapFragment);

        transaction.commit();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
