package ee.esutoniagodesu.pojo.assets;

import java.io.Serializable;

public class EGDAssetsImpl implements EGDAssets, Serializable {

    private static final long serialVersionUID = 9104913148019174831L;

    public final String prefix;

    public final String JapEst;
    public final String VHeisig6;
    public final String VHeisig4;
    public final String VKanji;
    public final String VHeisig6Custom;
    public final String Article;

    public EGDAssetsImpl() {
        this("/WEB-INF/jasperreports/");
    }

    public EGDAssetsImpl(String prefix) {
        this.prefix = prefix;

        JapEst = prefix + "JapEst.jrxml";
        VHeisig6 = prefix + "VHeisig6.jrxml";
        VHeisig4 = prefix + "VHeisig4.jrxml";
        VKanji = prefix + "VKanji.jrxml";
        VHeisig6Custom = prefix + "VHeisig6Custom.jrxml";
        Article = prefix + "Article.jrxml";
    }

    public String getJapEst() {
        return JapEst;
    }

    public String getVHeisig6() {
        return VHeisig6;
    }

    public String getVHeisig6Custom() {
        return VHeisig6Custom;
    }

    public String getVHeisig4() {
        return VHeisig4;
    }

    public String getVKanji() {
        return VKanji;
    }

    public String getArticle() {
        return Article;
    }
}
