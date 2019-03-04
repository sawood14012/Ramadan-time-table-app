package com.example.sawood.cardvie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Dev_info extends AppCompatActivity {

    TextView meer,ameen,feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info);
        this.setTitle("Devloper's Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        meer = (TextView) findViewById(R.id.meer_insta);
        ameen = (TextView) findViewById(R.id.ameen_insta);
        feed=(TextView)findViewById(R.id.textView6);

        String meerinstagram = "<a href='https://www.instagram.com/sawoodrocks/'>@sawoodrocks</a>";
        String ameeninstagram = "<a href='https://www.instagram.com/ameenfarooqi/'>@ameenfarooqi</a>";
        String feedback = "<a href='https://goo.gl/forms/aH5sCPWnKV86E0ZE2'>Give us feed back</a>";

        meer.setText(Html.fromHtml(meerinstagram));

        meer.setMovementMethod(LinkMovementMethod.getInstance());
        ameen.setText(Html.fromHtml(ameeninstagram));
        ameen.setMovementMethod(LinkMovementMethod.getInstance());
        feed.setText(Html.fromHtml(feedback));
        feed.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
