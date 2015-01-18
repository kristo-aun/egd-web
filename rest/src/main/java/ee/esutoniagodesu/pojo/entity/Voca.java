package ee.esutoniagodesu.pojo.entity;

import com.jc.structure.pojo.IntID;
import ee.esutoniagodesu.pojo.cf.ECfEtSonaliik;
import ee.esutoniagodesu.pojo.cf.ECfJpCategory;
import ee.esutoniagodesu.pojo.cf.ECfOrigin;
import ee.esutoniagodesu.pojo.cf.ECfVocaTransQuality;

import java.io.Serializable;

public final class Voca implements IntID, Serializable {

    private static final long serialVersionUID = -6172683092110349371L;

    //phrase_jp
    private int phraseJpId;
    private String jp;
    private String kana;
    private String romaji;
    private String romajiHepburn;
    private int jpAudioId;
    private ECfOrigin cfOriginJp;

    //voca_jp
    private int vocaJpId;
    private String descrJp;
    private ECfJpCategory cfJpCategory;

    //mtm_voca
    private int mtmVocaId;
    private ECfVocaTransQuality cfVocaTransQuality;

    //phrase_et
    private int phraseEtId;
    private String et;
    private ECfOrigin cfOriginEt;
    private int etAudioId;

    //voca_et
    private int vocaEtId;
    private ECfEtSonaliik cfEtSonaliik;
    private String descrEt;

    //abi
    private String cfJpCategories;
    private String cfEtSonaliigid;

    private int uvote;
    private int dvote;

    public int getPhraseJpId() {
        return phraseJpId;
    }

    public void setPhraseJpId(int phraseJpId) {
        this.phraseJpId = phraseJpId;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getRomajiHepburn() {
        return romajiHepburn;
    }

    public void setRomajiHepburn(String romajiHepburn) {
        this.romajiHepburn = romajiHepburn;
    }

    public int getJpAudioId() {
        return jpAudioId;
    }

    public void setJpAudioId(int jpAudioId) {
        this.jpAudioId = jpAudioId;
    }

    public ECfOrigin getCfOriginJp() {
        return cfOriginJp;
    }

    public void setCfOriginJp(ECfOrigin cfOriginJp) {
        this.cfOriginJp = cfOriginJp;
    }

    public int getVocaJpId() {
        return vocaJpId;
    }

    public void setVocaJpId(int vocaJpId) {
        this.vocaJpId = vocaJpId;
    }

    public String getDescrJp() {
        return descrJp;
    }

    public void setDescrJp(String descrJp) {
        this.descrJp = descrJp;
    }

    public ECfJpCategory getCfJpCategory() {
        return cfJpCategory;
    }

    public void setCfJpCategory(ECfJpCategory cfJpCategory) {
        this.cfJpCategory = cfJpCategory;
    }

    public int getMtmVocaId() {
        return mtmVocaId;
    }

    public void setMtmVocaId(int mtmVocaId) {
        this.mtmVocaId = mtmVocaId;
    }

    public ECfVocaTransQuality getCfVocaTransQuality() {
        return cfVocaTransQuality;
    }

    public void setCfVocaTransQuality(ECfVocaTransQuality cfVocaTransQuality) {
        this.cfVocaTransQuality = cfVocaTransQuality;
    }

    public int getPhraseEtId() {
        return phraseEtId;
    }

    public void setPhraseEtId(int phraseEtId) {
        this.phraseEtId = phraseEtId;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public ECfOrigin getCfOriginEt() {
        return cfOriginEt;
    }

    public void setCfOriginEt(ECfOrigin cfOriginEt) {
        this.cfOriginEt = cfOriginEt;
    }

    public int getEtAudioId() {
        return etAudioId;
    }

    public void setEtAudioId(int etAudioId) {
        this.etAudioId = etAudioId;
    }

    public int getVocaEtId() {
        return vocaEtId;
    }

    public void setVocaEtId(int vocaEtId) {
        this.vocaEtId = vocaEtId;
    }

    public ECfEtSonaliik getCfEtSonaliik() {
        return cfEtSonaliik;
    }

    public void setCfEtSonaliik(ECfEtSonaliik cfEtSonaliik) {
        this.cfEtSonaliik = cfEtSonaliik;
    }

    public String getDescrEt() {
        return descrEt;
    }

    public void setDescrEt(String descrEt) {
        this.descrEt = descrEt;
    }

    public String getCfJpCategories() {
        return cfJpCategories;
    }

    public void setCfJpCategories(String cfJpCategories) {
        this.cfJpCategories = cfJpCategories;
    }

    public String getCfEtSonaliigid() {
        return cfEtSonaliigid;
    }

    public void setCfEtSonaliigid(String cfEtSonaliigid) {
        this.cfEtSonaliigid = cfEtSonaliigid;
    }

    public int getUvote() {
        return uvote;
    }

    public void setUvote(int uvote) {
        this.uvote = uvote;
    }

    public int getDvote() {
        return dvote;
    }

    public void setDvote(int dvote) {
        this.dvote = dvote;
    }

    private transient int id;//abi gridi ehitamisel

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Voca{" +
            "phraseJpId=" + phraseJpId +
            ", jp='" + jp + '\'' +
            ", kana='" + kana + '\'' +
            ", romaji='" + romaji + '\'' +
            ", romajiHepburn='" + romajiHepburn + '\'' +
            ", jpAudioId=" + jpAudioId +
            ", cfOriginJp=" + cfOriginJp +
            ", vocaJpId=" + vocaJpId +
            ", descrJp='" + descrJp + '\'' +
            ", cfJpCategory=" + cfJpCategory +
            ", mtmVocaId=" + mtmVocaId +
            ", cfVocaTransQuality=" + cfVocaTransQuality +
            ", phraseEtId=" + phraseEtId +
            ", et='" + et + '\'' +
            ", cfOriginEt=" + cfOriginEt +
            ", etAudioId=" + etAudioId +
            ", vocaEtId=" + vocaEtId +
            ", cfEtSonaliik=" + cfEtSonaliik +
            ", descrEt='" + descrEt + '\'' +
            ", cfJpCategories='" + cfJpCategories + '\'' +
            ", cfEtSonaliigid='" + cfEtSonaliigid + '\'' +
            ", uvote=" + uvote +
            ", dvote=" + dvote +
            ", id=" + id +
            '}';
    }
}
