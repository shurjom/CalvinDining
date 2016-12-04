package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import edu.calvin.kpb23students.calvindining.JavaService;
import edu.calvin.kpb23students.calvindining.MyApplication;
import edu.calvin.kpb23students.calvindining.R;

/**
 *
 **/
public class Login extends Fragment {
    private JavaService javaService;
    private Observer javaServiceObserver;
    private boolean loggingIn = false;
    private boolean creatingUser = false;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_login, container, false);

        final EditText username = (EditText) frameLayout.findViewById(R.id.username);
        final EditText password = (EditText) frameLayout.findViewById(R.id.password);
        final TextView displayUserName = (TextView) frameLayout.findViewById(R.id.displayUserName);

        javaServiceObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (loggingIn) {
                    if (javaService.getUser() != null) {
                        makeToast("Succesfully Logged in", getContext());
                    } else {
                        makeToast("Failed to Log in", getContext());
                    }
                    loggingIn = false;
                }
                if (creatingUser) {
                    if (javaService.getUser() != null) {
                        makeToast("Created new User", getContext());
                    } else {
                        makeToast("Failed to create new User", getContext());
                    }
                    creatingUser = false;
                }
                if (javaService.getUser() != null) {
                    displayUserName.setText("Logged in as: " + javaService.getUser().getUserName());
                } else {
                    displayUserName.setText("Logged out");
                }
            }
        };


        javaService = MyApplication.getMyApplication().getJavaService();
        javaService.addObserver(javaServiceObserver);
        javaServiceObserver.update(null, null);

        Button signUpButton = (Button) frameLayout.findViewById(R.id.signUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatingUser = true;
                javaService.createUser(username.getText().toString(), password.getText().toString());
            }
        });

        Button loginButton = (Button) frameLayout.findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggingIn = true;
                javaService.setLogin(username.getText().toString(), password.getText().toString());
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    loggingIn = true;
                    javaService.setLogin(username.getText().toString(), password.getText().toString());
                }
                return false;
            }
        });

        Button logOutButton = (Button) frameLayout.findViewById(R.id.logOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                javaService.setLogin(null, null);
            }
        });

        // Inflate the layout for this fragment
        return frameLayout;
    }

    @Override
    public void onDestroy() {
        javaService.deleteObserver(javaServiceObserver);
        super.onDestroy();
    }

    private void makeToast(String toastMessage, Context context) {
        Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,700);
        toast.show();
    }
}
