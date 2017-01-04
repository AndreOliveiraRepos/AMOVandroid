package layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.security.KeyStore;

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
    public static final String PREFS_NAME = "prefsconfig";
    private String mParam1;
    private String mParam2;
    private Model model;
    //components
    private Button btnLogin;
    private Button btnRegister;
    private LinearLayout clickableLayout;
    private EditText username;
    private EditText password;
    private EditText confirmpassword;
    private TextView errorView;
    private ProgressDialog progressDialog;
    private SharedPreferences rememberLogin;
    private CheckBox checkBox;






    private int formMode;

    private a21200800isec.cmcticket2.OnFragmentInteractionListener mListener;


    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Code Clean up, SHAREDPREFERENCES
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        return v;
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
        checkBox = ((CheckBox) getView().findViewById(R.id.checkBox));
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
                        progressDialog = ProgressDialog.show(view.getContext(), view.getResources().getString(R.string.loginLabel), errorView.getText(), true, true);
                        model.doLogin(taskListener);
                    }else{
                        Log.d("Erro","pass e user");
                        errorView.setText("Password and User cant be empty!");
                    }

                }else if(mode == 3){
                    Log.d("CONFIRMA","REGISTO");
                    username = ((EditText)getView().findViewById(R.id.txtusername));
                    password = ((EditText)getView().findViewById(R.id.txtpassword));
                    confirmpassword = ((EditText)getView().findViewById(R.id.confPassText));
                    if(username.getText().length()> 0 && password.getText().length() > 0 && confirmpassword.getText().length() >0) {
                        if(password.getText().toString().equalsIgnoreCase(confirmpassword.getText().toString())){
                            model.setUser(username.getText().toString(),password.getText().toString());
                            progressDialog = ProgressDialog.show(view.getContext(), "", errorView.getText(), true, true);
                            model.doRegister(taskListener);
                        }else{
                            errorView.setText("Passwords and must be identical!");
                        }
                        /*model.setUser(username.getText().toString(),password.getText().toString());
                        progressDialog = ProgressDialog.show(view.getContext(), "", errorView.getText(), true, true);
                        model.doLogin(taskListener);*/
                    }else{
                        Log.d("Erro","pass e user");
                        errorView.setText("Password and User cant be empty!");
                    }
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
            if(formMode == 2){
                progressDialog.dismiss();
                mListener.onFragmentMessage(OnFragmentInteractionListener.FRAGMENT_TAG.LOGIN,"OK");
                model.setLogin(true);
                rememberLogin = getActivity().getSharedPreferences(PREFS_NAME, 0);
                if(checkBox.isChecked()){
                    SharedPreferences.Editor editor = rememberLogin.edit();
                    editor.putBoolean("auto", true);
                    editor.putString("username",model.getUser().getUserName());
                    editor.putString("password",model.getUser().getPassword());
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = rememberLogin.edit();
                    editor.putBoolean("auto", false);

                    editor.commit();

                }
            }else if(formMode == 3){
                progressDialog.dismiss();
                mListener.onFragmentMessage(OnFragmentInteractionListener.FRAGMENT_TAG.REGISTER,"OK");
                setFormMode(2);
            }
        }

        else if(progressDialog != null && !result){
            if(formMode == 2){
                progressDialog.dismiss();
                mListener.onFragmentMessage(OnFragmentInteractionListener.FRAGMENT_TAG.LOGIN,"NO");
            }else if(formMode == 3){
                progressDialog.dismiss();
                mListener.onFragmentMessage(OnFragmentInteractionListener.FRAGMENT_TAG.REGISTER,"NO");
            }

        }
    }
}
