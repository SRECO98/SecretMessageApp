package com.example.secretmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TranslaterActivity extends AppCompatActivity {

    TextView naslovPrvi, naslovDrugi;
    EditText textPrvi, textDrugi;
    Button translate;
    FloatingActionButton buttonCopyOne, buttonCopyTwo, buttonDeleteOne, buttonDeleteTwo;

    TranslatorClass translatorClass;
    private long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translater);

        naslovPrvi = findViewById(R.id.textViewCaption1);
        naslovDrugi = findViewById(R.id.textViewCaption2);
        textPrvi = findViewById(R.id.editText1);
        textDrugi = findViewById(R.id.editText2);
        translate = findViewById(R.id.translater);
        buttonCopyOne = findViewById(R.id.floatingButtonCopyTextOne);
        buttonCopyTwo = findViewById(R.id.floatingButtonCopyTextTwo);
        buttonDeleteOne = findViewById(R.id.floatingButtonDeleteTextOne);
        buttonDeleteTwo = findViewById(R.id.floatingButtonDeleteTextTwo);

        translatorClass = new TranslatorClass();

        translate.setOnClickListener(view -> {
            if(textDrugi.getText().toString().isEmpty() && !(textPrvi.getText().toString().isEmpty())){
                currentTime = System.currentTimeMillis();
                String message = translatorClass.startCreatingSecretMessage(textPrvi.getText().toString(), currentTime);
                textDrugi.setText(message);
            }else if(textPrvi.getText().toString().isEmpty() && !(textDrugi.getText().toString().isEmpty())){
                String message = translatorClass.startReadingSecretMessage(textDrugi.getText().toString());
                textPrvi.setText(message);
            }else if(textPrvi.getText().toString().isEmpty() && textDrugi.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Enter text in at least one box.", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getApplicationContext(), "One of the boxes must be empty!", Toast.LENGTH_SHORT).show();
            }
        });
        //Kopiranje tekstra iz textView
        buttonCopyOne.setOnClickListener(view -> {
            //mjenjanje ikonice na buttonu
            buttonCopyOne.setImageResource(R.drawable.checked);
            buttonCopyTwo.setImageResource(R.drawable.ic_baseline_content_copy_24);
            setClipboard(textPrvi.getText().toString());
        });
        buttonCopyTwo.setOnClickListener(view -> {
                buttonCopyTwo.setImageResource(R.drawable.checked);
                buttonCopyOne.setImageResource(R.drawable.ic_baseline_content_copy_24);
                setClipboard(textDrugi.getText().toString());

        });

        buttonDeleteOne.setOnClickListener(view -> textPrvi.getText().clear());
        buttonDeleteTwo.setOnClickListener(view -> textDrugi.getText().clear());
    }

    //Kopiranje teksta iz EditText-a na telefonu.
    private void setClipboard(CharSequence text){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("copy", text);
        if(clipboardManager != null){
            clipboardManager.setPrimaryClip(data);
            Toast.makeText(getApplicationContext(), "Text copied", Toast.LENGTH_SHORT).show();
        }
    }
}