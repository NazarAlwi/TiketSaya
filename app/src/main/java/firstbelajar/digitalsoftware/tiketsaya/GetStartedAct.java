package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetStartedAct extends AppCompatActivity {

    Button button_sign_in, btn_new_account_on_getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        button_sign_in = findViewById(R.id.button_sign_in);
        btn_new_account_on_getStarted= findViewById(R.id.btn_new_account_on_getStarted);
        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSign = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(goToSign);
            }
        });
        btn_new_account_on_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterOne = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(goToRegisterOne);
            }
        });
    }
}
