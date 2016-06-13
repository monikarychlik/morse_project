package project.pl.morseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] alpha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "0", " " };

        String[] dottie = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
                "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
                "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--..", ".----", "..---", "...--", "....-", ".....",
                "-....", "--...", "---..", "----.", "-----", "|" };

        final EditText normalEditText = (EditText) findViewById(R.id.normalEditText);
        final EditText morseEditText = (EditText) findViewById(R.id.morseEditText);
        final Button button = (Button) findViewById(R.id.button);

        if (normalEditText == null || morseEditText == null || button == null) return;

        normalEditText.addTextChangedListener(getTextWatcher(true));
        morseEditText.addTextChangedListener(getTextWatcher(false));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalEditText.setEnabled(!normalEditText.isEnabled());
                morseEditText.setEnabled(!morseEditText.isEnabled());

                if (button.getText().equals("Kodowanie")) {
                    button.setText("Dekodowanie");
                } else {
                    button.setText("Kodowanie");
                }
            }
        });
    }

    public TextWatcher getTextWatcher(final boolean isNormalText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isNormalText) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
