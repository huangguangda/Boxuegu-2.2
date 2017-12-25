package cn.edu.gdmec.android.boxuegu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Boxuegu extends AppCompatActivity {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_boxuegu );

        text = (TextView) findViewById ( R.id.text );

        String name = "GD阿達";

        text.setText ( name );
    }
}
