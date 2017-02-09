package com.example.passwordinputview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.password.lib.PasswordInput;

public class MainActivity extends AppCompatActivity {
    private PasswordInput passwordInputFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordInputFirst = (PasswordInput) findViewById(R.id.passwordInput_first);
        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwdFirst = passwordInputFirst.getText().toString();
                Toast.makeText(MainActivity.this, "密码是:" + pwdFirst, Toast.LENGTH_SHORT).show();
            }
        });

        //设置Text长度改变监听
        passwordInputFirst.setTextLenChangeListen(new PasswordInput.TextLenChangeListen() {
            @Override
            public void onTextLenChange(CharSequence text, int len) {
                if (len == 6) {
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        passwordInputFirst.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(passwordInputFirst, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 300);
    }
}
