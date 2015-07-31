package zero.easymvc;

import java.text.ParseException;

public class BeanParserException extends Exception {

    public BeanParserException(String message) {
        super(message);
    }

    public BeanParserException(ParseException e) {
        super(e);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 8692475102313582336L;

}
