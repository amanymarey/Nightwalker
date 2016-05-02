package epeden.nightwalker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SleepingActivity extends AppCompatActivity {

    private TextView snoozeCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);
    }

    public void backButtonPushed(View v) {
        Intent main_intent = new Intent(this,MainActivity.class);
        startActivity(main_intent);
    }

    public void updatePhoneCount(int count) {
        snoozeCountTextView = (TextView) findViewById(R.id.snoozeLimitTextView);
        snoozeCountTextView.setText("You picked up your phone "+count+" times.");

    }
}
