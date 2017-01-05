package a21200800isec.cmcticket2;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import a21200800isec.cmcticket2.Assets.AsyncTaskCompleteListener;
import a21200800isec.cmcticket2.Assets.Tasks.LoginTask;
import a21200800isec.cmcticket2.Model.Model;
import layout.CameraFragment;
import layout.MyMapFragment;
import layout.LoginFragment;
import layout.TicketForm;


//TODO:Languages, Socket, puts, dels from api? Code clean up
public class Principal extends FragmentActivity implements OnMapReadyCallback, OnFragmentInteractionListener,AsyncTaskCompleteListener {
    //property
    public static final String PREFS_NAME = "prefsconfig";
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    //owned fragments
    MyMapFragment sMapFragment;
    LoginFragment loginFrag;
    TicketForm ticketForm;
    CameraFragment cameraFragment;
    ArrayList<Integer> listFragments;
    Fragment currentFrag;
    //components
    ArrayList<String> sideMenuText;
    ListView mSideListView;
    DrawerLayout drawerLayout;
    ImageButton btnAddTicket;
    Toast toast;
    ProgressDialog progressDialog;
    //model
    Model model;

    //debugger
    int debug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(a21200800isec.cmcticket2.R.layout.activity_principal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 19);
        }
        //model = new Model();
        model= Model.getInstance();
        model.setContext(this);
        fragmentManager = this.getSupportFragmentManager();
        listFragments = new ArrayList<>();
        debug=0;
        setLayout();
        setControllers();


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
        btnAddTicket = (ImageButton) findViewById(R.id.btnTicket);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        boolean autoLogin = prefs.getBoolean("auto", false);

        if(debug == 1) {//debug login scree
            prefs = getSharedPreferences(PREFS_NAME, 0);
            prefs.edit().clear().commit();
            this.setCurrentFragment(LoginFragment.newInstance("",""));
        }else if(debug == 2){//map screen
            sMapFragment = sMapFragment.newInstance();
            sMapFragment.getMapAsync(this);
            setCurrentFragment(sMapFragment);
        }else if(debug == 3){  //camera screen
            this.setCurrentFragment(CameraFragment.newInstance("",""));
        }else if (debug == 0){
            if(autoLogin){
                //this.setCurrentFragment(LoginFragment.newInstance("",""));
                model.getUser().setEmail(prefs.getString("email",""));
                model.getUser().setPassword(prefs.getString("password",""));
                new LoginTask(this).execute();
                progressDialog = ProgressDialog.show(this, getResources().getString(R.string.loginLabel), "", true, true);
            }
            this.setCurrentFragment(LoginFragment.newInstance("",""));
        }


        //TO-DO: desligar aqui
       // this.setCurrentFragment(loginFrag.newInstance("",""));
    }

    public void setControllers(){
        mSideListView.setOnItemClickListener(new DrawerItemClickListener());
        ticketForm = TicketForm.newInstance("","");
        btnAddTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG","CLICKEI");
                findViewById(R.id.btnTicket).setVisibility(View.INVISIBLE);
                setCurrentFragment(ticketForm);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.model.setMap(googleMap);
        this.model.setTracker();
        Log.d("LAT",String.valueOf(model.getCurrentLatitude()));
        Log.d("LON",String.valueOf(model.getCurrentLongitude()));
        LatLng local = new LatLng(model.getCurrentLatitude(), model.getCurrentLongitude());
        this.model.getMap().addMarker(new MarkerOptions().position(local).title("HERE"));
        this.model.getMap().moveCamera(CameraUpdateFactory.newLatLng(local));
        findViewById(R.id.btnTicket).setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentMessage(FRAGMENT_TAG TAG, String msg) {
        switch (TAG){
            case ABOUT:
                break;
            case LOGIN:
                if(msg.equalsIgnoreCase("OK")) {
                    //this.model.setMap();

                    sMapFragment = new MyMapFragment();
                    sMapFragment.getMapAsync(this);
                    setCurrentFragment(sMapFragment);
                }else{
                    setToast("Try Again!");
                }
                break;
            case SEND:
                if(msg.equalsIgnoreCase("OK"))
                    //setCurrentFragment(sMapFragment);
                break;
            case TICKET:
                if(msg.equalsIgnoreCase("OK"))
                    setToast("Ticket sent!");
                else{
                    setToast("Ticket not sent, try again later!");
                }
                    //setCurrentFragment(sMapFragment);
                break;
            case CAMERA:
                setCurrentFragment(CameraFragment.newInstance("",""));
                break;
            case REGISTER:
                if(msg.equalsIgnoreCase("OK"))
                    setToast("Account created. Please login now.");
                else{
                    setToast("Account not created, try again later!");
                }
                break;

        }
    }

    @Override
    public void onTaskComplete(boolean result) {
        //enter
        if (progressDialog != null && result){
            progressDialog.dismiss();
            sMapFragment = sMapFragment.newInstance();
            sMapFragment.getMapAsync(this);
            setCurrentFragment(sMapFragment);
        }else if(!result){
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            progressDialog.dismiss();
            //getSupportFragmentManager().beginTransaction().remove(loginFrag).commit();
            loginFrag = LoginFragment.newInstance("","");
            setCurrentFragment(loginFrag);
        }
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //onclick get position change fragments
            Log.d("D","Click");
        }
    }

    private void setCurrentFragment(Fragment frag) {



        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragContainer, frag);
        currentFrag = frag;




        transaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();

        //toast.show();

        //map frag code
        /*sMapFragment.getMapAsync(this);
        fragmentManager = this.getSupportFragmentManager();

        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragContainer,sMapFragment);

        transaction.commit();*/
        /*HttpClient httpClient = new HttpClient(this.model);
        httpClient.execute("/token" , HttpClient.RequestType.POST.toString());*/

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {

        //kill app our change to map

        if(currentFrag instanceof LoginFragment) {
            super.onBackPressed();
        }else if(currentFrag instanceof CameraFragment){
            setCurrentFragment(ticketForm);
        }else if(currentFrag instanceof TicketForm){
            sMapFragment = sMapFragment.newInstance();
            sMapFragment.getMapAsync(this);
            setCurrentFragment(sMapFragment);
        }

    }

    public void setToast(String message){
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);

        toast.show();
    }

}
