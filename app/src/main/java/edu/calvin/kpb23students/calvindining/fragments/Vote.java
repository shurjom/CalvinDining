package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import java.util.Observable;
import java.util.Observer;

import edu.calvin.kpb23students.calvindining.JavaService;
import edu.calvin.kpb23students.calvindining.MyApplication;
import edu.calvin.kpb23students.calvindining.R;


/**
 * <p>
 *     This fragment allows you to vote
 * </p>
 */
public class Vote extends Fragment {
    private JavaService javaService;
    private Observer javaServiceObserver;
    JavaService.Poll commonsPoll;
    JavaService.Poll knollPoll;

    public Vote() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_vote, container, false);

        // display who is logged in
        final TextView userName = (TextView) layout.findViewById(R.id.userName);

        // get everything for commons
        final TextView commonsQuestion = (TextView) layout.findViewById(R.id.commonsQuestion);
        // radio buttons
        final RadioButton commons1 = (RadioButton) layout.findViewById(R.id.commons1);
        final RadioButton commons2 = (RadioButton) layout.findViewById(R.id.commons2);
        final RadioButton commons3 = (RadioButton) layout.findViewById(R.id.commons3);
        final RadioButton commons4 = (RadioButton) layout.findViewById(R.id.commons4);


        // get everything for knoll
        final TextView knollQuestion = (TextView) layout.findViewById(R.id.knollQuestion);
        // radio buttons
        final RadioButton knoll1 = (RadioButton) layout.findViewById(R.id.knoll1);
        final RadioButton knoll2 = (RadioButton) layout.findViewById(R.id.knoll2);
        final RadioButton knoll3 = (RadioButton) layout.findViewById(R.id.knoll3);
        final RadioButton knoll4 = (RadioButton) layout.findViewById(R.id.knoll4);
        
        // submit button
        final Button commonsButton = (Button) layout.findViewById(R.id.commonsButton);

        javaServiceObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                // only load initially when loading view to avoid creating loops
                if (javaService.getUser() != null) {
                    userName.setText("Logged in as: " + javaService.getUser().getUserName());
                }

                commonsPoll = javaService.getCommonsPoll();
                knollPoll = javaService.getKnollPoll();

                commonsQuestion.setText(commonsPoll.getQuestion());
                commons1.setText(commonsPoll.getOption1());
                commons2.setText(commonsPoll.getOption2());
                commons3.setText(commonsPoll.getOption3());
                commons4.setText(commonsPoll.getOption4());

                knollQuestion.setText(knollPoll.getQuestion());
                knoll1.setText(knollPoll.getOption1());
                knoll2.setText(knollPoll.getOption2());
                knoll3.setText(knollPoll.getOption3());
                knoll4.setText(knollPoll.getOption4());
            }
        };

        javaService = MyApplication.getMyApplication().getJavaService();
        javaService.addObserver(javaServiceObserver);
        javaServiceObserver.update(null, null);
        javaService.check();

        // Inflate the layout for this fragment
        return layout;
    }


    @Override
    public void onDestroy() {
        javaService.deleteObserver(javaServiceObserver);
        super.onDestroy();
    }
}
