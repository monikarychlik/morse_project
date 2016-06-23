package project.pl.morseproject;

import java.util.HashMap;
import java.util.Map;

public class MorseUtil {

    private Map<String, String> morseTab = new HashMap<>();

    public MorseUtil() {
        morseTab.put("a", ".-");
        morseTab.put("b", "-...");
        morseTab.put("c", "-.-.");
        morseTab.put("d", "-..");
        morseTab.put("e", ".");
        morseTab.put("f", "..-.");
        morseTab.put("g", "--.");
        morseTab.put("h", "....");
        morseTab.put("i", "..");
        morseTab.put("j", ".---");
        morseTab.put("k", "-.-");
        morseTab.put("l", ".-..");
        morseTab.put("m", "--");
        morseTab.put("n", "-.");
        morseTab.put("o", "---");
        morseTab.put("p", ".--.");
        morseTab.put("q", "--.-");
        morseTab.put("r", ".-.");
        morseTab.put("s", "...");
        morseTab.put("t", "-");
        morseTab.put("u", "..-");
        morseTab.put("v", "...-");
        morseTab.put("w", ".--");
        morseTab.put("x", "-..-");
        morseTab.put("y", "-.--");
        morseTab.put("z", "--..");
        morseTab.put("1", ".----");
        morseTab.put("2", "..---");
        morseTab.put("3", "...--");
        morseTab.put("4", "....-");
        morseTab.put("5", ".....");
        morseTab.put("6", "-....");
        morseTab.put("7", "--...");
        morseTab.put("8", "---..");
        morseTab.put("9", "----.");
        morseTab.put("0", "-----");
        morseTab.put(" ", "|");
    }

    public String code(CharSequence charSequence) {
        String finalText = "";

        for (int i = 0; i < charSequence.length(); i++) {
            final String text = morseTab.get(("" + charSequence.charAt(i)).toLowerCase());
            if (text != null) {
                finalText += text + " ";
            }
        }

        return finalText;
    }

    public String decode(CharSequence charSequence) {
        final String[] split = charSequence.toString().split(" ");
        String finalText = "";

        for (String part : split) {
            if (part.equals("\n")) {
                finalText += " ";
            } else {
                final String text = getKeyForValue(part);
                if (text != null) {
                    finalText += text;
                }
            }
        }

        return finalText;
    }

    private String getKeyForValue(String searchedValue) {
        for (Map.Entry<String, String> entry : morseTab.entrySet()) {
            if (entry.getValue().equals(searchedValue)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
