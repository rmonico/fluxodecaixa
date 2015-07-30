package zero.easymvc;

public interface BeanParser {

    Object parse(Object value) throws BeanParserException;

}
