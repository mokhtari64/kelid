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



import ir.mehdi.kelid.R;
import ir.mehdi.kelid.ui.StepFragmentDelegate;


public class UserPhoneFragment extends Fragment {
    StepFragmentDelegate delegate;

    public void setDelegate(StepFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    //    private FanoosActivity activity;
    public EditText phoneEditText;
    public String string;
    Button send;
    public boolean validation() {
        return phoneEditText.getText()!=null && phoneEditText.getText().toString().length()>9;

    }

    public void setPhoneEditText(String phoneEditText) {
        string= phoneEditText;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_phone_layout,null);
        phoneEditText= (EditText) view.findViewById(R.id.phone);
        phoneEditText.setText(string);
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>10)
                {
                    send.setEnabled(true);
                    send.setTextColor(Color.WHITE);
                }else
                {
                    send.setEnabled(false);
                    send.setTextColor(Color.GRAY);
                }

            }
        });
        send= (Button) view.findViewById(R.id.send);
        if(phoneEditText.getText().length()>10)
        {
            send.setEnabled(true);
            send.setTextColor(Color.WHITE);
        }else {
            send.setEnabled(false);
            send.setTextColor(Color.GRAY);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.doneClicked();
            }
        });
        return view;
    }


    public String getPhone() {
        return phoneEditText.getText().toString();
    }
}
