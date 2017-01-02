package layout;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import a21200800isec.cmcticket2.Model.Model;
import a21200800isec.cmcticket2.OnFragmentInteractionListener;
import a21200800isec.cmcticket2.R;

public class TicketForm extends Fragment {
    //property
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    //components
    private EditText txtLocation;
    private EditText txtDate;
    private EditText txtDescription;
    private Image imageToSend;
    private ImageButton btnCamera;
    private ImageButton btnSend;

    //model
    private Model model;

    private a21200800isec.cmcticket2.OnFragmentInteractionListener mListener;

    public TicketForm() {
        // Required empty public constructor
        this.model = model.getInstance();
    }


    public static TicketForm newInstance(String param1, String param2) {
        TicketForm fragment = new TicketForm();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_ticket_form, container, false);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setViews();
        setControllers();
    }

    public void setViews(){
        txtLocation = ((EditText) getView().findViewById(R.id.textLocation));
        txtDate = ((EditText) getView().findViewById(R.id.textDate));
        txtDescription = ((EditText) getView().findViewById(R.id.textDescription));
        //imageToSend;
        btnCamera=((ImageButton) getView().findViewById(R.id.btnCamera));
        btnSend=((ImageButton) getView().findViewById(R.id.btnSend));

    }

    public void setControllers(){
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentMessage(OnFragmentInteractionListener.FRAGMENT_TAG.CAMERA, "OK");
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDate.getText()!= null && txtDescription != null && txtLocation.getText() != null){
                    Log.d("DEBUG", "ENVIA ticket");
                }else{
                    Log.d("DEBUG", "Campos vazios");
                }
            }
        });
    }
}
