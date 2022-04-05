package com.example.secretmessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    AppCompatButton buttonFacebook;
    AppCompatButton buttonLinkedIn;
    ImageView imageCopyText;
    TextView textViewMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        buttonFacebook = findViewById(R.id.buttonFacebook);
        buttonLinkedIn = findViewById(R.id.buttonLinkedIn);
        imageCopyText = findViewById(R.id.imageViewCopy);
        textViewMail = findViewById(R.id.textViewMail);

        buttonFacebook.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SRECOOO/"))));
        buttonLinkedIn.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/sreten-banovic-b71a38208/"))));
        imageCopyText.setOnClickListener(view -> setClipboard(textViewMail.getText().toString()));  // kopiranje teksta iz TextView-a
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