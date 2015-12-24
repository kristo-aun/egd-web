package ee.esutoniagodesu.web.rest.errors;

/**
 * Custom, parameterized exception, which can be translated on the client side.
 * For example:
 *
 * <pre>
 * throw new CustomParameterizedException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
 * </pre>
 *
 * Can be translated with:
 *
 * <pre>
 * "error.myCustomError" :  "The server says {{params[0]}} to {{params[1]}}"
 * </pre>
 */
public class CustomLocalizedException extends RuntimeException {

    private static final long serialVersionUID = -8718882648241844610L;

    private final String code;

    public CustomLocalizedException(String code) {
        super(code);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
