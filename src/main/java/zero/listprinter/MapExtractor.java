package zero.listprinter;

import java.util.Map;

public class MapExtractor implements DataExtractor {

    private Object key;

    public MapExtractor(Object key) {
        this.key = key;
    }

    @Override
    public Object extract(Object data) throws ListPrinterException {
        Map<?, ?> map = (Map<?, ?>) data;

        return map.get(key);
    }

}
