package com.example.texttospeech1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements  TextToSpeech.OnInitListener{
    private  static  final  int TTS_ENGINE_REQUEST = 101;
    private  TextToSpeech textToSpeech;
    private EditText TextForSpeech;
    private Button Speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextForSpeech = findViewById(R.id.speech_text);
        Speech =findViewById(R.id.Speechbtn);
        Speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSpeech ();
            }
        });
    }
    public void  performSpeech(){
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TTS_ENGINE_REQUEST);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TTS_ENGINE_REQUEST && requestCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
            textToSpeech = new TextToSpeech(this, this);

        }
        else{
            Toast.makeText(getApplicationContext(), "Action Cannot take place",Toast.LENGTH_LONG).show();
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }


    }

    @Override
    public void onInit(int status) {
        if(status==TextToSpeech.SUCCESS){
            int languageStatus = textToSpeech.setLanguage(Locale.US);
            if(languageStatus==TextToSpeech.LANG_MISSING_DATA|| languageStatus==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(getApplicationContext(),"Language not Supported", Toast.LENGTH_SHORT).show();

            }else {
                String data = TextForSpeech.getText().toString();
            int  speechStatus =  textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                if (speechStatus==TextToSpeech.ERROR){
                    Toast.makeText(getApplicationContext(), "Error While Speech..", Toast.LENGTH_SHORT).show();
                }
            }

        }else{
            Toast.makeText(this, "Failed..", Toast.LENGTH_SHORT).show();
        }
    }
}
