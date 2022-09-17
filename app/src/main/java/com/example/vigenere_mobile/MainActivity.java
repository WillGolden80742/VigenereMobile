package com.example.vigenere_mobile;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.content.ClipData;
import android.widget.EditText;
import java.util.Base64;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encode64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decode64(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode("");
        try {
            decodedBytes = Base64.getDecoder().decode(input);
        } catch (IllegalArgumentException ex) {
            output.setText("Decodificação falha");
        }
        return new String(decodedBytes);
    }

    private final Runnable cifrarV = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            String texto = input.getText().toString();
            String textoCifrado = "";
            String chave = chaveField.getText().toString();
            int ascii = 0, contKey = 0, sizeKey = chave.length() - 1, sizeChar = texto.length();
            output.setText("");
            if (!chave.equals("")) {
                for (int i = 0; i < sizeChar; i++) {
                    ascii = (int) texto.charAt(i);
                    ascii += (int) chave.charAt(contKey);
                    try {
                        textoCifrado+=output.getText().toString() + ((char) ascii);
                    } catch (Exception ex) {

                    }
                    if (contKey != sizeKey) {
                        contKey++;
                    } else {
                        contKey = 0;
                    }
                }
                output.setText(encode64(textoCifrado));
            }
        }
    };

    private final Runnable decifrarV = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            String texto = decode64(input.getText().toString());
            String chave = chaveField.getText().toString();
            String textoDecifrado = "";
            int ascii = 0, contKey = 0, sizeKey = chave.length() - 1, sizeChar = texto.length();
            output.setText("");
            if (!chave.equals("")) {
                for (int i = 0; i < sizeChar; i++) {
                    ascii = (int) texto.charAt(i);
                    ascii -= (int) chave.charAt(contKey);
                    try {
                        textoDecifrado+=output.getText().toString() + ((char) ascii);
                    } catch (Exception ex) {

                    }
                    if (contKey != sizeKey) {
                        contKey++;
                    } else {
                        contKey = 0;
                    }
                }
                output.setText(textoDecifrado);
            }
        }
    };
}