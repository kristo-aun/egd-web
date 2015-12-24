package ee.esutoniagodesu.util.commons;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.Map;

public final class JCAudio {

    private static final Logger log = LoggerFactory.getLogger(JCAudio.class);

    private static Charset _charset = Charsets.UTF_8;

    public static Map.Entry<String, byte[]> googleTTSBytes(String word, String locale) throws IOException {
        return googleTTSBytes(word, locale, _charset);
    }

    /**
     * Downloads audio file from google tts service
     */
    public static Map.Entry<String, byte[]> googleTTSBytes(String word, String locale, Charset charset) throws IOException {
        StringBuilder msg = new StringBuilder("googleTTSBytes");
        long ms = System.currentTimeMillis();
        word = java.net.URLEncoder.encode(word, charset.displayName());

        StringBuilder surl = new StringBuilder("http://translate.google.com/translate_tts?");
        surl.append("ie=").append(charset.displayName());
        surl.append("&tl=").append(locale);
        surl.append("&q=").append(word);
        msg.append(": url=").append(surl.toString());

        URL url = new URL(surl.toString());
        byte[] bytes = retrieveBytes(url);

        msg.append(", bytes.length=").append(bytes.length);
        msg.append(", time=").append(System.currentTimeMillis() - ms);
        log.debug(msg.toString());

        return new AbstractMap.SimpleEntry<>(surl.toString(), bytes);
    }

    public static Map.Entry<String, byte[]> romajidesuTTS(String word) throws IOException {
        return romajidesuTTS(word, _charset);
    }

    public static Map.Entry<String, byte[]> romajidesuTTS(String word, Charset charset) throws IOException {
        StringBuilder msg = new StringBuilder("romajidesuTTSBytes");
        long ms = System.currentTimeMillis();
        word = java.net.URLEncoder.encode(word, charset.displayName());

        StringBuilder surl = new StringBuilder("http://www.romajidesu.com/sound/");
        surl.append(word).append(".mp3");
        msg.append(": url=").append(surl.toString());

        URL url = new URL(surl.toString());
        byte[] bytes = retrieveBytes(url);

        msg.append(", bytes.length=").append(bytes.length);
        msg.append(", time=").append(System.currentTimeMillis() - ms);
        log.debug(msg.toString());

        return new AbstractMap.SimpleEntry<>(surl.toString(), bytes);
    }

    public static byte[] retrieveBytes(URL url) throws IOException {
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        InputStream audioSrc = urlConn.getInputStream();
        DataInputStream read = new DataInputStream(audioSrc);

        return IOUtils.toByteArray(read);
    }
}
