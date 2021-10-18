package com.example.vigenere_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.content.ClipData;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    EditText output, input, chaveField;
    Button cifrar, decifrar, copyInput, copyOutput, clearInput, clearOutPut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instanceLayout();
        cifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(cifrarV).start();
            }
        });
        decifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(decifrarV).start();
            }
        });

        copyInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label",input.getText());
                clipboard.setPrimaryClip(clip);
            }
        });

        copyOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label",output.getText());
                clipboard.setPrimaryClip(clip);
            }
        });

        clearInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText("");
            }
        });

        clearOutPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("");
            }
        });

    }

    private void instanceLayout () {
        output = findViewById(R.id.outputEditText);
        input = findViewById(R.id.inputEditText);
        chaveField = findViewById(R.id.chaveEditText);
        cifrar = findViewById(R.id.cifrar);
        decifrar = findViewById(R.id.decifrar);
        copyInput = findViewById(R.id.copyInput);
        copyOutput = findViewById(R.id.copyOutput);
        clearInput = findViewById(R.id.clearInput);
        clearOutPut = findViewById(R.id.clearOutput);
    }

    private final Runnable cifrarV = new Runnable() {
        @Override
        public void run() {
            String texto = input.getText().toString();
            String chave = chaveField.getText().toString();
            int ascii = 0, contKey = 0, sizeKey = chave.length() - 1, sizeChar = texto.length();
            output.setText("");
            if (!chave.equals("")) {
                for (int i = 0; i < sizeChar; i++) {
                    ascii = (int) texto.charAt(i);
                    ascii += (int) chave.charAt(contKey);
                    try {
                        output.setText(output.getText().toString() + ((char) ascii));
                    } catch (Exception ex) {

                    }
                    if (contKey != sizeKey) {
                        contKey++;
                    } else {
                        contKey = 0;
                    }
                }
            }
        }
    };

    private final Runnable decifrarV = new Runnable() {
        @Override
        public void run() {
            String texto = input.getText().toString();
            String chave = chaveField.getText().toString();
            int ascii = 0, contKey = 0, sizeKey = chave.length() - 1, sizeChar = texto.length();
            output.setText("");
            if (!chave.equals("")) {
                for (int i = 0; i < sizeChar; i++) {
                    ascii = (int) texto.charAt(i);
                    ascii -= (int) chave.charAt(contKey);
                    try {
                        output.setText(output.getText().toString() + ((char) ascii));
                    } catch (Exception ex) {

                    }
                    if (contKey != sizeKey) {
                        contKey++;
                    } else {
                        contKey = 0;
                    }
                }
            }
        }
    };
}