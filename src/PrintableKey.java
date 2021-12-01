//COMP 220 Final Project Method Stubs
//Katherine Bennett, Elsie Park, Carissa Hilscher

/**
 * A Key which corresponds to a printable char
 */
public class PrintableKey extends Key {
    /**
     * int corresponding to the ASCII code of the char of the Key
     */
    private int idASCII;

    /**
     * Constructs a PrintableKey representing a particular char
     * @param key char
     */
    public PrintableKey(char key) {
        idASCII = key;
    }

    /**
     * Returns unique key id
     * @return ASCII code id of printable char
     */
    @Override
    public int getIdentity() {
        return idASCII;
    }
}
