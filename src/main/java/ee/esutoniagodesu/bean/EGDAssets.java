package ee.esutoniagodesu.bean;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;

@Component
public class EGDAssets implements Serializable {

    private static final long serialVersionUID = 9104913148019174831L;

    private String path(String name) {
        return "jasperreports" + File.separator + name + ".jrxml";
    }

    private ClassPathResource resource(String name) {
        return new ClassPathResource(path(name));
    }

    public Resource getVHeisig6() {
        return resource("VHeisig6");
    }

    public Resource getVHeisig4() {
        return resource("VHeisig4");
    }

    public Resource getVHeisig6Custom() {
        return resource("VHeisig6Custom");
    }

    public Resource getTofuSentenceTranslations() {
        return resource("TofuSentenceTranslations");
    }

    public Resource getVKanji() {
        return resource("VKanji");
    }
}
