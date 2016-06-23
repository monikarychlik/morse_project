package project.pl.morseproject;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final long TIME_DELAY_SHORT = 400;
    private static final long TIME_DELAY_LONG = 12000;
    private MorseUtil morseUtil = new MorseUtil();
    private boolean textToMorse = true;
    private Button button;
    private Button buttonTransmit;
    private EditText inputEditText;
    private EditText outputEditText;
    private TextView textView;
    private Camera camera;
    private String morseMessage = "";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (initView()) return;
        initCameraFlash();
        handler = new Handler();
        setListeners();
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
                if (!textToMorse) {
                    transmit();
                }
            }
        });
    }

    private void onButtonClick() {
        textToMorse = !textToMorse;
        clearText();
        if (button.getText().equals("Kodowanie")) {
            button.setText("Dekodowanie");
            textView.setText("Kodowanie:");
        } else {
            button.setText("Kodowanie");
            textView.setText("Dekodowanie");
        }
    }

    private void initCameraFlash() {
        this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void turnFlashlightOn(){
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
    }


    private void turnFlashlightOff(){
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
    }

    private void clearText() {
        inputEditText.setText("");
        outputEditText.setText("");
    }

    private void transmit() {
        if (textToMorse) {
            for (Character character : morseMessage.toCharArray()) {
                if (character.compareTo('.') == 0) {
                    turnFlashlightOn();
                    //delayTurningFlashlightOff(TIME_DELAY_SHORT);
                } else if (character.compareTo('-') == 0) {
                    turnFlashlightOn();
                    //delayTurningFlashlightOff(TIME_DELAY_LONG);
                } else if (character.compareTo(' ') == 0) {
                    //delayTurningFlashlightOff(TIME_DELAY_SHORT);
                } else if (character.compareTo('|') == 0) {
                    //delayTurningFlashlightOff(TIME_DELAY_LONG);
                } else {
                    continue;
                }
                //delayTurningFlashlightOff(TIME_DELAY_SHORT);
            }
        }
    }

    private void delayTurningFlashlightOff(long timeDelay) {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            public void run() {
                turnFlashlightOff();
            }
        }, timeDelay);
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
                outputEditText.setText(morseUtil.code(s));
            } else {
                morseMessage = morseUtil.decode(s);
                outputEditText.setText(morseMessage);
            }
        }
    }
}
