package nl.quintor.solitaire.Helpers;

public class Data {
    /**
     * This method checks if the value is an integer
     *
     * @param value String
     * @return boolean
     */
    public boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method multiples string
     *
     * I got this code from:
     * https://stackoverflow.com/questions/2255500/can-i-multiply-strings-in-java-to-repeat-sequences
     * @param amount        int
     * @param toReplaceWith String
     * @return String
     */
    public String multiplyString(int amount, String toReplaceWith) {
        amount = amount < 0 ? 0 : amount;
        return new String(new char[amount]).replace("\0", toReplaceWith);
    }
}
