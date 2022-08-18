package com.example.texttospeech;

import static android.speech.tts.TextToSpeech.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btnSpk;
    EditText editText;
    SeekBar pitch,rate;
    TextToSpeech textToSpeech;
    TextView PV,RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pitch= findViewById(R.id.pitch);
        rate=findViewById(R.id.rate);
        PV=findViewById(R.id.show_pitch);
        RV=findViewById(R.id.show_rate);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS){
                int result = textToSpeech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getApplicationContext(), "This language is not supported!",
                            Toast.LENGTH_SHORT);
                } else {
                    btnSpk.setEnabled(true);
                        pitch=findViewById(R.id.pitch);
                        rate=findViewById(R.id.rate);
                        float tpitch,trate;
                        tpitch= pitch.getProgress();
                        if(tpitch<0.1)
                        tpitch=0.1f;

                        textToSpeech.setPitch(tpitch);

                        trate=rate.getProgress();

                        if(trate<0.1)
                            trate=0.1f;
                        textToSpeech.setSpeechRate(trate);

                    speak();
                }
        }
        });
        btnSpk = (Button) findViewById(R.id.b1);
        editText = (EditText) findViewById(R.id.texttospeech);

        btnSpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }
    private void speak() {
        String text = editText.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}