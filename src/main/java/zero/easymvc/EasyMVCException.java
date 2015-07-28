package zero.easymvc;

public class EasyMVCException extends Exception {

    public EasyMVCException(Exception e) {
        super(e);
    }

    public EasyMVCException(String message) {
        super(message);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 2326630553813087797L;

}
