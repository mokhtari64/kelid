package ir.mehdi.kelid.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ir.mehdi.kelid.R;
import ir.mehdi.kelid.ui.DotsProgressBar;
import ir.mehdi.kelid.ui.StepFragmentDelegate;


//import org.apache.commons.io.IOUtils;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Iman on 8/9/2016.
 */

public class ActivationCodeFragment extends Fragment {
    int minute;
    int second;

    //    CreateJobActivity activity;
    EditText phoneEditText;
    StepFragmentDelegate delegate;
    private String phone;
    Button button;
    TextView timerTextView, textView, resend, change;
    String timerText;
    String timerText2, s;

    Timer timer;
    DotsProgressBar dotsProgressBar;

    public void setDelegate(StepFragmentDelegate delegate) {
        this.delegate = delegate;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_activation_code_layout, null);
        textView = (TextView) view.findViewById(R.id.textView10);
        resend = (TextView) view.findViewById(R.id.resend);

        change = (TextView) view.findViewById(R.id.change_phone);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.back();
            }
        });
        dotsProgressBar = (DotsProgressBar) view.findViewById(R.id.dotprogress);
        dotsProgressBar.setVisibility(View.VISIBLE);
        textView.setTypeface(FanoosApplication.BYEKAN_NORMAL);
        s = FanoosApplication.applicationContext.getString(R.string.activation_code);
        s = String.format(s, phone);

        refresh() ;


        phoneEditText = (EditText) view.findViewById(R.id.activation);
        button = (Button) view.findViewById(R.id.send);
        button.setEnabled(false);
        button.setTextColor(Color.GRAY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.doneClicked();
            }
        });
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 5) {
                    button.setEnabled(true);
                    button.setTextColor(Color.WHITE);
                } else {
                    button.setEnabled(false);
                    button.setTextColor(Color.GRAY);
                }

            }
        });

        return view;
    }

    public String getActivationCode() {
        return phoneEditText.getText().toString();
    }

//    public void setActivity(CreateJobActivity activity) {
//        this.activity = activity;
//    }


    public boolean validation() {
        return phoneEditText.getText() != null && phoneEditText.getText().toString().length() == 5;
    }

    public void setCode(String code) {
        phoneEditText.setText(code);
        timer.cancel();

    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void refresh() {
        if(timer!=null)
            timer.cancel();
        resend.setTextColor(Color.GRAY);
        resend.setClickable(false);
        resend.setOnClickListener(null);
        minute = 3;
        second = 0;
        if (timerText2 == null) {
            timerText2 = getString(R.string.wait);
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                second--;
                if (second < 0) {
                    minute--;
                    second = 59;
                }


                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (minute == 0 && second == 0) {
                            timerText = minute + ":" + ((second < 10) ? "0" + second : second);
                            dotsProgressBar.stop();
                            dotsProgressBar.setVisibility(View.GONE);
                            resend.setTextColor(Color.BLACK);
                            resend.setClickable(true);
                            timer.cancel();

                            resend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delegate.tryAgain();
                                }
                            });
                            textView.setText(s + "." );
                            return;
                        } else {
                            timerText = minute + ":" + ((second < 10) ? "0" + second : second);
                            textView.setText(s + "." + timerText2 + " (" + timerText + ")");
                        }
//                        timerTextView.setText(timerText2+" "+timerText);
                    }
                });


            }
        }, 100, 1000);
    }
}
