package layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import a21200800isec.cmcticket2.Assets.AsyncTaskCompleteListener;
import a21200800isec.cmcticket2.Model.Model;
import a21200800isec.cmcticket2.OnFragmentInteractionListener;
import a21200800isec.cmcticket2.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements AsyncTaskCompleteListener {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Model model;
    //components
    private Button btnLogin;
    private Button btnRegister;
    private LinearLayout clickableLayout;
    private EditText username;
    private EditText password;
    private TextView errorView;
    private ProgressDialog progressDialog;






    private int formMode;

    private a21200800isec.cmcticket2.OnFragmentInteractionListener mListener;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        formMode = 1;
        model = Model.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof a21200800isec.cmcticket2.OnFragmentInteractionListener) {
            mListener = (a21200800isec.cmcticket2.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        setViews();
        setControllers();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setViews(){
        clickableLayout = ((LinearLayout)getView().findViewById(R.id.mainContainer));
        btnLogin = ((Button)getView().findViewById(R.id.btnLogin));
        btnRegister = ((Button)getView().findViewById(R.id.btnRegister));
        errorView = ((TextView) getView().findViewById(R.id.errorView));
        if(this.formMode == 1){
            getView().findViewById(R.id.overlayLoginContainer).setVisibility(View.INVISIBLE);
        }else{
            getView().findViewById(R.id.overlayLoginContainer).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.confPassText).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.cfpwdView).setVisibility(View.INVISIBLE);
        }
    }
    private void setControllers(){

        clickableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("FAZ ", "Click");
                    if(formMode == 1) {
                        setFormMode(2);
                        getView().findViewById(R.id.overlayLoginContainer).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.logoLayout).setVisibility(View.INVISIBLE);
                    }
                }
            }
        );

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login
                final int mode = formMode;
                final AsyncTaskCompleteListener taskListener = LoginFragment.this;
                if(mode == 2) {
                    Log.d("FAZ ", "LOGIN");
                    username = ((EditText)getView().findViewById(R.id.txtusername));
                    password = ((EditText)getView().findViewById(R.id.txtpassword));
                    if(username.getText().length()> 0 && password.getText().length() > 0) {
                        model.setUser(username.getText().toString(),password.getText().toString());
                        progressDialog = ProgressDialog.show(view.getContext(), "", errorView.getText(), true, true);
                        model.doLogin(taskListener);
                    }else{
                        Log.d("Erro","pass e user");
                        errorView.setText("Password and User cant be empty!");
                    }

                }else if(mode == 3){
                    Log.d("CONFIRMA","REGISTO");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final int mode = formMode;
                if(mode == 2) {
                    setFormMode(3);
                    getView().findViewById(R.id.confPassText).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.cfpwdView).setVisibility(View.VISIBLE);
                    btnRegister.setText("Cancel");
                    btnLogin.setText("Register");


                }else if(mode == 3){
                    setFormMode(2);
                    getView().findViewById(R.id.confPassText).setVisibility(View.INVISIBLE);
                    getView().findViewById(R.id.cfpwdView).setVisibility(View.INVISIBLE);
                    btnRegister.setText("Register");
                    btnLogin.setText("Login");
                }
            }

        });


    }

    private void setFormMode(int m){
        this.formMode = m;
    }

    @Override
    public void onTaskComplete(boolean result) {
        if (progressDialog != null && result){
            progressDialog.dismiss();
            mListener.onFragmentMessage(OnFragmentInteractionListener.FRAGMENT_TAG.LOGIN,"OK");
        }
        else if(progressDialog != null && !result){
            progressDialog.dismiss();
            mListener.onFragmentMessage(OnFragmentInteractionListener.FRAGMENT_TAG.LOGIN,"NO");
        }
    }
}
