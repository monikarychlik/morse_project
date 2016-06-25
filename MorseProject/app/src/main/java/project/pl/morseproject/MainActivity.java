package project.pl.morseproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final long TIME_DELAY_SHORT = 50;
    private static final long TIME_DELAY_MEDIUM = 300;
    private static final long TIME_DELAY_LONG = 1000;

    private boolean textToMorse = true;
    private boolean blockTransmitButton = false;
    private String morseMessage = "";
    private Button button;
    private Button buttonTransmit;
    private EditText inputEditText;
    private EditText outputEditText;
    private TextView textView;
    private MorseUtil morseUtil;
    private Thread thread;
    private CameraFlashlightUtil cameraFlashlightUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (initView()) return;
        initUtils();
        setListeners();
    }

    private void initUtils() {
        morseUtil = new MorseUtil();
        cameraFlashlightUtil = new CameraFlashlightUtil(this);
    }

    private void setListeners() {
        inputEditText.addTextChangedListener(getTextWatcher());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        buttonTransmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!blockTransmitButton) {
                    blockTransmitButton = true;
                    transmit();
                }
            }
        });
    }

    private void onButtonClick() {
        textToMorse = !textToMorse;
        clearText();
        if (button.getText().equals(getString(R.string.coding))) {
            button.setText(R.string.decoding);
            textView.setText(R.string.coding);
        } else {
            button.setText(R.string.coding);
            textView.setText(R.string.decoding);
        }
    }

    private void clearText() {
        inputEditText.setText("");
        outputEditText.setText("");
    }

    private void transmit() {
        if (morseMessage.length() == 0) {
            blockTransmitButton = false;
            return;
        }

        tryToJoinThread();
        thread = new Thread(getRunnable());
        thread.run();
    }

    @NonNull
    private Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                for (Character character : morseMessage.toCharArray()) {
                    if (character.compareTo('.') == 0) {
                        cameraFlashlightUtil.turnFlashlightOn();
                        delay(TIME_DELAY_SHORT);
                        cameraFlashlightUtil.turnFlashlightOff();
                    } else if (character.compareTo('-') == 0) {
                        cameraFlashlightUtil.turnFlashlightOn();
                        delay(TIME_DELAY_LONG);
                        cameraFlashlightUtil.turnFlashlightOff();
                    } else if (character.compareTo(' ') == 0) {
                        delay(TIME_DELAY_MEDIUM);
                    } else if (character.compareTo('|') == 0) {
                        delay(TIME_DELAY_LONG);
                    } else {
                        continue;
                    }
                    delay(TIME_DELAY_SHORT);
                }
                blockTransmitButton = false;
            }

            private void delay(long timeDelay) {
                try {
                    synchronized (this) {
                        wait(timeDelay);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void tryToJoinThread() {
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean initView() {
        button = (Button) findViewById(R.id.button);
        buttonTransmit = (Button) findViewById(R.id.buttonTransmit);
        inputEditText = (EditText) findViewById(R.id.inputEditText);
        outputEditText = (EditText) findViewById(R.id.outputEditText);
        textView = (TextView) findViewById(R.id.textView);

        return inputEditText == null
                || outputEditText == null
                || button == null
                || buttonTransmit == null
                || textView == null;
    }

    @Override
    protected void onDestroy() {
        cameraFlashlightUtil.releaseCamera();
        tryToJoinThread();
        super.onDestroy();
    }

    public TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                translate(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private void translate(CharSequence s) {
        if (outputEditText != null) {
            if (textToMorse) {
                morseMessage = morseUtil.code(s);
                outputEditText.setText(morseMessage);
            } else {
                final String tempMessage = morseUtil.decode(s);
                outputEditText.setText(tempMessage);
                morseMessage = morseUtil.code(tempMessage);
            }
        }
    }
}
