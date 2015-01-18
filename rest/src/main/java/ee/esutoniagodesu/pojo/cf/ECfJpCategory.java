package ee.esutoniagodesu.pojo.cf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Jaapanikeelsete sõnade sõnaliigid
 */
public enum ECfJpCategory {

    //lihtnimekiri
    N(65, "nimisõna", "n", ECfEtSonaliik.N),//noun (common) (futsuumeishi)
    VS(113, "nimisõna, mida saab kasutada koos verbiga suru", "n_", ECfEtSonaliik.N),//noun or participle which takes the aux. verb suru
    COMPD(185, "komposiitum e liitsõna", "compd", ECfEtSonaliik.N),//compd
    TEGUSONA_V1(186, "I grupi verb", "v1", ECfEtSonaliik.V),
    TEGUSONA_V2(187, "II grupi verb", "v2", ECfEtSonaliik.V),
    TEGUSONA_V3(188, "III grupi verb", "v3", ECfEtSonaliik.V),
    ADJ_I(22, "i-omadussõna", "adj1", ECfEtSonaliik.ADJ),//adjective (keiyoushi) adj1
    ADJ_NA(23, "na-omadussõna", "adj2", ECfEtSonaliik.ADJ),//adjectival nouns or quasi-adjectives (keiyodoshi) adj2
    PN(77, "pronomeen e asesõna", "pron", ECfEtSonaliik.ASES),//pronoun
    ADV(28, "adverb", "adv", ECfEtSonaliik.ADV),//adverb (fukushi)
    CONJ(40, "konjuktsioon e sidesõna", "conj", ECfEtSonaliik.SIDE),//conjunction
    PRT(82, "partikkel e abisõna", "part", ECfEtSonaliik.KAAS),//particle
    INT(56, "interjektsioon e hüüdsõna", "interj", ECfEtSonaliik.HUUD),//interjection (kandoushi)
    PREF(80, "prefiks e eesliide", "pref", ECfEtSonaliik.KAAS),//prefix
    SUF(87, "sufiks e järelliide", "suf", ECfEtSonaliik.KAAS),//suffix
    EXP(45, "fraas e väljend", "fr", ECfEtSonaliik.N),//Expressions (phrases, clauses, etc.)
    ATRIB(189, "atributiivne", "atrib", ECfEtSonaliik.N),//atrib
    KOHANIMI(190, "kohanimi", "kn", ECfEtSonaliik.N),//kn
    MEHENIMI(191, "mehe nimi", "mn", ECfEtSonaliik.N),//mn
    NAISENIMI(192, "naise nimi", "nn", ECfEtSonaliik.N),//nn
    PERENIMI(193, "perekonnanimi", "pn", ECfEtSonaliik.N),//pn
    SL(86, "släng", "släng", ECfEtSonaliik.N),//slang
    LING(59, "lingvistika", "ling", ECfEtSonaliik.N),//linguistics terminology
    MATH(63, "matemaatika", "mat", ECfEtSonaliik.N),//mathematics
    MED(176, "meditsiin", "med", ECfEtSonaliik.N),//medicine, etc. term
    SPORTS(179, "sport", "sport", ECfEtSonaliik.N),//sports term

    //täisnimekirja lisa
    MA(19, "martial arts term"),
    X(20, "rude or X-rated term (not displayed in educational software)"),
    ABBR(21, "abbreviation"),
    ADJ_NO(24, "nouns which may take the genitive case particle ´no´"),
    ADJ_PN(25, "pre-noun adjectival (rentaishi)"),
    ADJ_T(26, "´taru´ adjective"),
    ADJ_F(27, "noun or verb acting prenominally"),
    ADV_TO(29, "adverb taking the ´to´ particle"),
    ARCH(30, "archaism"),
    ATEJI(31, "ateji (phonetic) reading"),
    AUX(32, "auxiliary"),
    AUX_V(33, "auxiliary verb"),
    AUX_ADJ(34, "auxiliary adjective"),
    BUDDH(35, "Buddhist term"),
    CHEM(36, "chemistry term"),
    CHN(37, "children´s language"),
    COL(38, "colloquialism"),
    COMP(39, "computer terminology"),
    CTR(41, "counter"),
    DEROG(42, "derogatory"),
    EK_KANJI(43, "exclusively kanji"),
    EK_KANA(44, "exclusively kana"),
    FAM(46, "familiar language"),
    FEM(47, "female term or language"),
    FOOD(48, "food term"),
    GEOM(49, "geometry term"),
    GIKUN(50, "gikun (meaning as reading) or jukujikun (special kanji reading)"),
    HON(51, "honorific or respectful (sonkeigo) language"),
    HUM(52, "humble (kenjougo) language"),
    IK_KANJI(53, "word containing irregular kanji usage"),
    ID_EXP(54, "idiomatic expression"),
    IK_KANA(55, "word containing irregular kana usage"),
    IO(57, "irregular okurigana usage"),
    IV(58, "irregular verb"),
    M_SL(60, "manga slang"),
    MALE(61, "male term or language"),
    MALE_SL(62, "male slang"),
    MIL(64, "military"),
    N_ADV(66, "adverbial noun (fukushitekimeishi)"),
    N_SUF(67, "noun, used as a suffix"),
    N_PREF(68, "noun, used as a prefix"),
    N_T(69, "noun (temporal) (jisoumeishi)"),
    NUM(70, "numeric"),
    OK_KANJI(71, "word containing out-dated kanji"),
    OBS(72, "obsolete term"),
    OBSC(73, "obscure term"),
    OK_KANA(74, "out-dated or obsolete kana usage"),
    OIK(75, "old or irregular kana form"),
    ON_MIM(76, "onomatopoeic or mimetic word"),
    POET(78, "poetical term"),
    POL(79, "polite (teineigo) language"),
    PROVERB(81, "proverb"),
    PHYSICS(83, "physics terminology"),
    RARE(84, "rare"),
    SENS(85, "sensitive"),
    UK_KANJI(88, "word usually written using kanji alone"),
    UK_KANA(89, "word usually written using kana alone"),
    V1(90, "Ichidan verb"),
    V2A_S(91, "Nidan verb with ´u´ ending (archaic)"),
    V4H(92, "Yodan verb with ´hu/fu´ ending (archaic)"),
    V4R(93, "Yodan verb with ´ru´ ending (archaic)"),
    V5ARU(94, "Godan verb - -aru special class"),
    V5B(95, "Godan verb with ´bu´ ending"),
    V5G(96, "Godan verb with ´gu´ ending"),
    V5K(97, "Godan verb with ´ku´ ending"),
    V5K_S(98, "Godan verb - Iku/Yuku special class"),
    V5M(99, "Godan verb with ´mu´ ending"),
    V5N(100, "Godan verb with ´nu´ ending"),
    V5R(101, "Godan verb with ´ru´ ending"),
    V5R_I(102, "Godan verb with ´ru´ ending (irregular verb)"),
    V5S(103, "Godan verb with ´su´ ending"),
    V5T(104, "Godan verb with ´tsu´ ending"),
    V5U(105, "Godan verb with ´u´ ending"),
    V5U_S(106, "Godan verb with ´u´ ending (special class)"),
    V5URU(107, "Godan verb - Uru old class verb (old form of Eru)"),
    VZ(108, "Ichidan verb - zuru verb (alternative form of -jiru verbs)"),
    VI(109, "intransitive verb"),
    VK(110, "Kuru verb - special class"),
    VN(111, "irregular nu verb"),
    VR(112, "irregular ru verb, plain form ends with -ri"),
    VS_C(114, "su verb - precursor to the modern suru"),
    VS_S(115, "suru verb - special class"),
    VS_I(116, "suru verb - irregular"),
    KYB(117, "Kyoto-ben"),
    OSB(118, "Osaka-ben"),
    KSB(119, "Kansai-ben"),
    KTB(120, "Kantou-ben"),
    TSB(121, "Tosa-ben"),
    THB(122, "Touhoku-ben"),
    TSUG(123, "Tsugaru-ben"),
    KYU(124, "Kyuushuu-ben"),
    RKB(125, "Ryuukyuu-ben"),
    NAB(126, "Nagano-ben"),
    HOB(127, "Hokkaido-ben"),
    VT(128, "transitive verb"),
    VULG(129, "vulgar expression or word"),
    ADJ_KARI(130, "´kari´ adjective (archaic)"),
    ADJ_KU(131, "´ku´ adjective (archaic)"),
    ADJ_SHIKU(132, "´shiku´ adjective (archaic)"),
    ADJ_NARI(133, "archaic/formal form of na-adjective"),
    N_PR(134, "proper noun"),
    V_UNSPEC(135, "verb unspecified"),
    V4K(136, "Yodan verb with ´ku´ ending (archaic)"),
    V4G(137, "Yodan verb with ´gu´ ending (archaic)"),
    V4S(138, "Yodan verb with ´su´ ending (archaic)"),
    V4T(139, "Yodan verb with ´tsu´ ending (archaic)"),
    V4N(140, "Yodan verb with ´nu´ ending (archaic)"),
    V4B(141, "Yodan verb with ´bu´ ending (archaic)"),
    V4M(142, "Yodan verb with ´mu´ ending (archaic)"),
    V2K_K(143, "Nidan verb (upper class) with ´ku´ ending (archaic)"),
    V2G_K(144, "Nidan verb (upper class) with ´gu´ ending (archaic)"),
    V2T_K(145, "Nidan verb (upper class) with ´tsu´ ending (archaic)"),
    V2D_K(146, "Nidan verb (upper class) with ´dzu´ ending (archaic)"),
    V2H_K(147, "Nidan verb (upper class) with ´hu/fu´ ending (archaic)"),
    V2B_K(148, "Nidan verb (upper class) with ´bu´ ending (archaic)"),
    V2M_K(149, "Nidan verb (upper class) with ´mu´ ending (archaic)"),
    V2Y_K(150, "Nidan verb (upper class) with ´yu´ ending (archaic)"),
    V2R_K(151, "Nidan verb (upper class) with ´ru´ ending (archaic)"),
    V2K_S(152, "Nidan verb (lower class) with ´ku´ ending (archaic)"),
    V2G_S(153, "Nidan verb (lower class) with ´gu´ ending (archaic)"),
    V2S_S(154, "Nidan verb (lower class) with ´su´ ending (archaic)"),
    V2Z_S(155, "Nidan verb (lower class) with ´zu´ ending (archaic)"),
    V2T_S(156, "Nidan verb (lower class) with ´tsu´ ending (archaic)"),
    V2D_S(157, "Nidan verb (lower class) with ´dzu´ ending (archaic)"),
    V2N_S(158, "Nidan verb (lower class) with ´nu´ ending (archaic)"),
    V2H_S(159, "Nidan verb (lower class) with ´hu/fu´ ending (archaic)"),
    V2B_S(160, "Nidan verb (lower class) with ´bu´ ending (archaic)"),
    V2M_S(161, "Nidan verb (lower class) with ´mu´ ending (archaic)"),
    V2Y_S(162, "Nidan verb (lower class) with ´yu´ ending (archaic)"),
    V2R_S(163, "Nidan verb (lower class) with ´ru´ ending (archaic)"),
    V2W_S(164, "Nidan verb (lower class) with ´u´ ending and ´we´ conjugation (archaic)"),
    ARCHIT(165, "architecture term"),
    ASTRON(166, "astronomy, etc. term"),
    BASEB(167, "baseball term"),
    BIOL(168, "biology term"),
    BOT(169, "botany term"),
    BUS(170, "business term"),
    ECON(171, "economics term"),
    ENGR(172, "engineering term"),
    FINC(173, "finance term"),
    GEOL(174, "geology, etc. term"),
    LAW(175, "law, etc. term"),
    MUSIC(177, "music term"),
    SHINTO(178, "Shinto term"),
    SUMO(180, "sumo term"),
    ZOOL(181, "zoology term"),
    JOC(182, "jocular, humorous term"),
    ANAT(183, "anatomical term");

    public final int ID;
    public final String TITLE;
    public final String ABBREVIATION;
    public ECfEtSonaliik ET_RESP;

    /**
     * @return ILO04 tüübid sageduse järjekorras
     */
    public static final List<LI_Classifier> SIMPLIFIED_LIST = new ArrayList<>();

    static {
        {
            SIMPLIFIED_LIST.add(new LI_Classifier(N.ID, N.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(VS.ID, VS.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(COMPD.ID, COMPD.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(TEGUSONA_V1.ID, TEGUSONA_V1.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(TEGUSONA_V2.ID, TEGUSONA_V2.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(TEGUSONA_V3.ID, TEGUSONA_V3.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(ADJ_I.ID, ADJ_I.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(ADJ_NA.ID, ADJ_NA.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(PN.ID, PN.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(ADV.ID, ADV.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(CONJ.ID, CONJ.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(PRT.ID, PRT.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(INT.ID, INT.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(PREF.ID, PREF.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(SUF.ID, SUF.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(EXP.ID, EXP.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(ATRIB.ID, ATRIB.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(KOHANIMI.ID, KOHANIMI.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(MEHENIMI.ID, MEHENIMI.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(NAISENIMI.ID, NAISENIMI.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(PERENIMI.ID, PERENIMI.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(SL.ID, SL.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(LING.ID, LING.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(MATH.ID, MATH.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(MED.ID, MED.ABBREVIATION));
            SIMPLIFIED_LIST.add(new LI_Classifier(SPORTS.ID, SPORTS.ABBREVIATION));
        }
    }

    /**
     * @return JMDict tüübid tähestikulises järjestuses
     */
    public static final List<LI_Classifier> ADVANCED_LIST = new ArrayList<>();

    static {
        {
            ADVANCED_LIST.add(new LI_Classifier(MA.ID, MA.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(X.ID, X.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ABBR.ID, ABBR.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_I.ID, ADJ_I.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_NA.ID, ADJ_NA.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_NO.ID, ADJ_NO.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_PN.ID, ADJ_PN.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_T.ID, ADJ_T.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_F.ID, ADJ_F.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADV.ID, ADV.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADV_TO.ID, ADV_TO.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ARCH.ID, ARCH.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ATEJI.ID, ATEJI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(AUX.ID, AUX.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(AUX_V.ID, AUX_V.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(AUX_ADJ.ID, AUX_ADJ.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(BUDDH.ID, BUDDH.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(CHEM.ID, CHEM.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(CHN.ID, CHN.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(COL.ID, COL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(COMP.ID, COMP.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(CONJ.ID, CONJ.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(CTR.ID, CTR.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(DEROG.ID, DEROG.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(EK_KANJI.ID, EK_KANJI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(EK_KANA.ID, EK_KANA.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(EXP.ID, EXP.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(FAM.ID, FAM.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(FEM.ID, FEM.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(FOOD.ID, FOOD.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(GEOM.ID, GEOM.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(GIKUN.ID, GIKUN.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(HON.ID, HON.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(HUM.ID, HUM.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(IK_KANJI.ID, IK_KANJI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ID_EXP.ID, ID_EXP.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(IK_KANA.ID, IK_KANA.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(INT.ID, INT.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(IO.ID, IO.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(IV.ID, IV.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(LING.ID, LING.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(M_SL.ID, M_SL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(MALE.ID, MALE.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(MALE_SL.ID, MALE_SL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(MATH.ID, MATH.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(MIL.ID, MIL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(N.ID, N.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(N_ADV.ID, N_ADV.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(N_SUF.ID, N_SUF.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(N_PREF.ID, N_PREF.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(N_T.ID, N_T.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(NUM.ID, NUM.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(OK_KANJI.ID, OK_KANJI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(OBS.ID, OBS.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(OBSC.ID, OBSC.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(OK_KANA.ID, OK_KANA.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(OIK.ID, OIK.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ON_MIM.ID, ON_MIM.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(PN.ID, PN.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(POET.ID, POET.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(POL.ID, POL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(PREF.ID, PREF.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(PROVERB.ID, PROVERB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(PRT.ID, PRT.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(PHYSICS.ID, PHYSICS.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(RARE.ID, RARE.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(SENS.ID, SENS.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(SL.ID, SL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(SUF.ID, SUF.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(UK_KANJI.ID, UK_KANJI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(UK_KANA.ID, UK_KANA.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V1.ID, V1.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2A_S.ID, V2A_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4H.ID, V4H.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4R.ID, V4R.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5ARU.ID, V5ARU.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5B.ID, V5B.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5G.ID, V5G.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5K.ID, V5K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5K_S.ID, V5K_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5M.ID, V5M.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5N.ID, V5N.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5R.ID, V5R.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5R_I.ID, V5R_I.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5S.ID, V5S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5T.ID, V5T.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5U.ID, V5U.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5U_S.ID, V5U_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V5URU.ID, V5URU.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VZ.ID, VZ.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VI.ID, VI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VK.ID, VK.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VN.ID, VN.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VR.ID, VR.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VS.ID, VS.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VS_C.ID, VS_C.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VS_S.ID, VS_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VS_I.ID, VS_I.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(KYB.ID, KYB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(OSB.ID, OSB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(KSB.ID, KSB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(KTB.ID, KTB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(TSB.ID, TSB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(THB.ID, THB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(TSUG.ID, TSUG.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(KYU.ID, KYU.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(RKB.ID, RKB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(NAB.ID, NAB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(HOB.ID, HOB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VT.ID, VT.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(VULG.ID, VULG.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_KARI.ID, ADJ_KARI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_KU.ID, ADJ_KU.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_SHIKU.ID, ADJ_SHIKU.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ADJ_NARI.ID, ADJ_NARI.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(N_PR.ID, N_PR.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V_UNSPEC.ID, V_UNSPEC.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4K.ID, V4K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4G.ID, V4G.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4S.ID, V4S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4T.ID, V4T.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4N.ID, V4N.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4B.ID, V4B.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V4M.ID, V4M.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2K_K.ID, V2K_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2G_K.ID, V2G_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2T_K.ID, V2T_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2D_K.ID, V2D_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2H_K.ID, V2H_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2B_K.ID, V2B_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2M_K.ID, V2M_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2Y_K.ID, V2Y_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2R_K.ID, V2R_K.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2K_S.ID, V2K_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2G_S.ID, V2G_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2S_S.ID, V2S_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2Z_S.ID, V2Z_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2T_S.ID, V2T_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2D_S.ID, V2D_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2N_S.ID, V2N_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2H_S.ID, V2H_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2B_S.ID, V2B_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2M_S.ID, V2M_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2Y_S.ID, V2Y_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2R_S.ID, V2R_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(V2W_S.ID, V2W_S.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ARCHIT.ID, ARCHIT.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ASTRON.ID, ASTRON.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(BASEB.ID, BASEB.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(BIOL.ID, BIOL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(BOT.ID, BOT.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(BUS.ID, BUS.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ECON.ID, ECON.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ENGR.ID, ENGR.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(FINC.ID, FINC.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(GEOL.ID, GEOL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(LAW.ID, LAW.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(MED.ID, MED.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(MUSIC.ID, MUSIC.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(SHINTO.ID, SHINTO.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(SPORTS.ID, SPORTS.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(SUMO.ID, SUMO.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ZOOL.ID, ZOOL.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(JOC.ID, JOC.TITLE));
            ADVANCED_LIST.add(new LI_Classifier(ANAT.ID, ANAT.TITLE));
        }
    }

    ECfJpCategory(int id, String title) {
        ID = id;
        TITLE = title;
        ABBREVIATION = null;
    }

    ECfJpCategory(int id, String title, String abbr) {
        ID = id;
        TITLE = title;
        ABBREVIATION = abbr;
    }

    ECfJpCategory(int id, String title, String abbr, ECfEtSonaliik cfEtSonaliik) {
        ID = id;
        TITLE = title;
        ABBREVIATION = abbr;
        ET_RESP = cfEtSonaliik;
    }

    public static List<ECfJpCategory> asList() {
        return Arrays.asList(values());
    }

    public static ECfJpCategory findById(int needle) {
        for (ECfJpCategory p : values()) {
            if (p.ID == needle) return p;
        }
        return null;
    }

    public static ECfJpCategory findByTitle(String needle) {
        for (ECfJpCategory p : values()) {
            if (p.TITLE.equalsIgnoreCase(needle)) return p;
        }
        return null;
    }

    public static ECfJpCategory findByAbbreviation(String needle) {
        for (ECfJpCategory p : values()) {
            if (p.ABBREVIATION.equalsIgnoreCase(needle)) return p;
        }
        return null;
    }

    public static List<ECfJpCategory> getSimplifiedList() {
        return Arrays.asList(N, VS, COMPD, TEGUSONA_V1, TEGUSONA_V2, TEGUSONA_V3,
            ADJ_I, ADJ_NA, PN, ADV, CONJ, PRT, INT, PREF, SUF, EXP,
            ATRIB, KOHANIMI, MEHENIMI, NAISENIMI, PERENIMI,
            SL, LING, MATH, MED, SPORTS);
    }

    /**
     * @return JMDict tüübid tähestikulises järjestuses
     */
    public static List<ECfJpCategory> getAdvancedList() {
        return Arrays.asList(MA, X, ABBR, ADJ_I, ADJ_NA, ADJ_NO, ADJ_PN, ADJ_T,
            ADJ_F, ADV, ADV_TO,
            ARCH, ATEJI, AUX, AUX_V, AUX_ADJ, BUDDH, CHEM, CHN, COL, COMP, CONJ,
            CTR, DEROG, EK_KANJI, EK_KANA, EXP, FAM, FEM, FOOD, GEOM, GIKUN, HON,
            HUM, IK_KANJI, ID_EXP, IK_KANA, INT, IO, IV, LING, M_SL, MALE,
            MALE_SL, MATH, MIL, N, N_ADV, N_SUF, N_PREF, N_T, NUM, OK_KANJI, OBS,
            OBSC, OK_KANA, OIK, ON_MIM, PN, POET, POL, PREF, PROVERB, PRT, PHYSICS,
            RARE, SENS, SL, SUF, UK_KANJI, UK_KANA, V1, V2A_S, V4H, V4R, V5ARU, V5B,
            V5G, V5K, V5K_S, V5M, V5N, V5R, V5R_I, V5S, V5T, V5U, V5U_S, V5URU, VZ, VI,
            VK, VN, VR, VS, VS_C, VS_S, VS_I, KYB, OSB, KSB, KTB, TSB, THB, TSUG, KYU,
            RKB, NAB, HOB, VT, VULG, ADJ_KARI, ADJ_KU, ADJ_SHIKU, ADJ_NARI, N_PR,
            V_UNSPEC, V4K, V4G, V4S, V4T, V4N, V4B, V4M, V2K_K, V2G_K, V2T_K, V2D_K,
            V2H_K, V2B_K, V2M_K, V2Y_K, V2R_K, V2K_S, V2G_S, V2S_S, V2Z_S, V2T_S,
            V2D_S, V2N_S, V2H_S, V2B_S, V2M_S, V2Y_S, V2R_S, V2W_S, ARCHIT,
            ASTRON, BASEB, BIOL, BOT, BUS, ECON, ENGR, FINC, GEOL, LAW, MED,
            MUSIC, SHINTO, SPORTS, SUMO, ZOOL, JOC, ANAT);
    }

    public int getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public String toString() {
        return ABBREVIATION;
    }
}
