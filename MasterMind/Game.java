package MasterMind;

import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author fsancheztemprano
 */
public class Game {

    private int tries;
    private String tryLog;
    private Code code;
    private Code[] decodes;

    public Game() {
        tries = 0;
        tryLog = "";
        decodes = new Code[Rules.getMaxTries() + 1];
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public void incTries() {
        tries++;
    }

    public void updateLog(String str) {
        tryLog += str;
    }

    public String getTryLog() {
        return tryLog;
    }

    public void setTryLog(String tryLog) {
        this.tryLog = tryLog;
    }

    public void startGame() {
        if (Menu.pickRival()) {
            code = new Code(insertCode());
        } else {
            code = new Code();
        }

        do {
            Code deCode = new Code(insertDeCode(), code);
            incTries();
            decodes[tries] = deCode;
            updateLog(getTries() + "/" + Rules.getMaxTries() + " " + deCode.getCode() + " " + deCode.getPerfMatches() + " " + deCode.getSemiMatches() + "\n");
            if (deCode.getPerfMatches() >= Rules.getCodeLenght()) {
                break;
            }
        } while (tries < Rules.getMaxTries());

        gameOver(decodes[tries]);
    }

    public boolean codeCheck(String cod) {
        int codeCheck = 0;
        char[] ar = Rules.getOptionsArray();
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < cod.length(); j++) {
                if (cod.charAt(j) == ar[i]) {
                    codeCheck++;
                }
            }
        }
        return cod.length() == Rules.getCodeLenght() && codeCheck == Rules.getCodeLenght();
    }

    public String insertCode() {
        String label = "";
        String codeIn = "";
        do {
            codeIn = JOptionPane.showInputDialog(null,
                    label
                    + Loc.code1 + "\n"
                    + Arrays.toString(Rules.getOptionsArray()) + "\n"
                    + Loc.code2 + Rules.getOptionsStr(),
                    "MasterMind", 3);
            label = Loc.err1;
        } while (!codeCheck(codeIn));

        return codeIn;
    }

    public String insertDeCode() {

        String label = "";
        String deCodeIn = "";
        do {
            deCodeIn = JOptionPane.showInputDialog(null,
                    label
                    + Loc.decode1 + "\n"
                    + Loc.code2 + Rules.getOptionsStr() + "\n"
                    + Arrays.toString(Rules.getOptionsArray()) + "\n"
                    + Loc.decode2 + (tries + 1) + " / " + Rules.getMaxTries() + " !\n\n"
                    + Loc.decode2 + Rules.getOptionsStr() + " (P) (S)\n"
                    + tryLog,
                    "MasterMind", 3);
            label = Loc.err1;
        } while (!codeCheck(deCodeIn));
        return deCodeIn;
    }

    public void gameOver(Code deCod) {
        String finalLog;
        if (deCod.getPerfMatches() == Rules.getCodeLenght()) {
            finalLog = Loc.over1 + "\n\n" + Loc.over2 + "! (" + code.getCode() + ")\n";
        } else {
            finalLog = Loc.over1 + "\n\n" + Loc.over3 + "! (" + code.getCode() + ")\n";
        }
        JOptionPane.showMessageDialog(null,
                finalLog + "\n\n"
                + Loc.decode2 + Rules.getOptionsStr() + " (P) (S)\n"
                + tryLog,
                "MasterMind", 3);
    }
}
