package zero.easymvc;

public class EasyMVCException extends Exception {

    public EasyMVCException(Throwable throwable) {
        super(throwable);
    }

    public EasyMVCException(String message) {
        super(message);
    }

    public EasyMVCException(String message, Exception e) {
        super(message, e);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 2326630553813087797L;

}
