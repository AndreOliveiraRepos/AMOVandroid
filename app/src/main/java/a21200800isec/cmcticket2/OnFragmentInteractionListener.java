package a21200800isec.cmcticket2;

import android.net.Uri;

/**
 * Created by red_f on 30/12/2016.
 */


public interface OnFragmentInteractionListener {
    public enum FRAGMENT_TAG{
        ABOUT,LOGIN,SEND,TICKET;
    }

    public void onFragmentMessage(FRAGMENT_TAG TAG, String msg);
}

