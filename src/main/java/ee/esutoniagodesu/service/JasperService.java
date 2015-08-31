package ee.esutoniagodesu.service;

import ee.esutoniagodesu.bean.EGDAssets;
import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.heisig.view.VHeisig6Custom;
import ee.esutoniagodesu.pojo.cf.ECfReportType;
import ee.esutoniagodesu.repository.domain.kanjidic2.KanjiRepository;
import ee.esutoniagodesu.repository.project.KanjiDB;
import ee.esutoniagodesu.repository.project.ReportDB;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.util.JCDateTime;
import ee.esutoniagodesu.util.JCString;
import ee.esutoniagodesu.util.commons.JCIOUtils;
import ee.esutoniagodesu.util.commons.JCText;
import ee.esutoniagodesu.util.jasperreports.CSVGenerator;
import ee.esutoniagodesu.util.jasperreports.JSGeneratorType;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class JasperService {

    private static final Logger log = LoggerFactory.getLogger(JasperService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private KanjiDB kanjiDB;

    @Inject
    private KanjiRepository kanjiRepository;

    @Inject
    private ReportDB reportDB;

    private static final CSVGenerator _csvGenerator = new CSVGenerator("\t");

    private int _exampleWordsCount = 20;

    private List<VHeisig6Custom> findAllVHeisig6Custom() throws UnsupportedEncodingException {
        return findVHeisig6Custom(1, 2200);
    }

    /**
     * 1) Leiab kõik 2200 Heisig 6 kaarti
     * 2) Lisab näidissõnad
     * 3) Koostab html'i
     */
    private List<VHeisig6Custom> findVHeisig6Custom(int from, int to) throws UnsupportedEncodingException {
        Assert.isTrue(from > 0 && to > from && to <= 2200);
        List<VHeisig6Custom> result = dao.findAll(VHeisig6Custom.class);
        Assert.isTrue(2200 == result.size());
        result = result.subList(from - 1, to);

        StringBuilder words = new StringBuilder();
        for (VHeisig6Custom p : result) {

            kanjiRepository.findByLiteral(p.getKanji()).ifPresent(item -> {
                p.setJlpt(item.getJlpt());
                if (item.getJouyou() != null) {
                    p.setJouyou(item.getJouyou().getJouyouId());
                }
            });

            if (_exampleWordsCount > 0) {
                p.setExampleWords(kanjiDB.getExampleWords(p.getKanji(), _exampleWordsCount));
            }

            if (p.getImageSha() != null) {
                String diagram = "<img src=\"" + JCText.toHex(p.getKanji()).toUpperCase() + ".png" + "\" />";
                p.setStrokeImageHtml(diagram);
            }

            if (p.getJpWordAudioFileName() != null) {
                p.setJpWordAudioHtml("[sound:" + jpFilename(p) + "]");
            }

            p.setKeywordEnAudioFileName("[sound:" + enFilename(p) + "]");

            if (p.getExampleWords() != null) {
                words.setLength(0);
                for (String[] q : p.getExampleWords()) {
                    if (words.length() > 0) words.append("<br/>");
                    words.append(q[1]).append("【").append(q[0]).append("】").append(q[2]);
                }
                p.setExampleWordsHtml(words.length() > 0 ? words.toString() : null);
            }
        }

        return result;
    }

    private List<Map<String, ?>> findAllVHeisig4() {
        return reportDB.findAllVHeisig4();
    }

    private List<Map<String, ?>> findAllVKanji() {
        return reportDB.findAllVKanji();
    }

    private List<Map<String, ?>> findTofuTranslated() {
        return reportDB.getTofuTranslatedByUser(SecurityUtils.getUserUuid());
    }

    private List<?> getData(ECfReportType reportType) throws UnsupportedEncodingException {
        List data = null;

        switch (reportType) {
            case HEISIG6_CUSTOM: {
                data = findAllVHeisig6Custom();
                break;
            }
            case HEISIG4: {
                data = findAllVHeisig4();
                break;
            }
            case KANJI: {
                data = findAllVKanji();
                break;
            }
            case TOFU_TRANSLATIONS: {
                data = findTofuTranslated();
                break;
            }
        }

        if (data == null) throw new IllegalStateException("getData: data == null");

        return data;
    }

    private byte[] compileToBytes(ECfReportType reportType, JSGeneratorType format, Collection data) throws IOException, JRException, IllegalArgumentException, IllegalStateException, SQLException {
        if (reportType == null || format == null)
            throw new IllegalArgumentException("reportType == null || format == null");

        log.info("getFileAsBytes: reportType=" + reportType + ", format=" + format);
        InputStream istream = null;

        JRDataSource dataSource = null;
        Map<String, Object> staticParams = new HashMap<>();

        switch (reportType) {
            case HEISIG6_CUSTOM: {
                dataSource = new JRBeanCollectionDataSource(data);
                istream = assets.getVHeisig6Custom().getInputStream();
                break;
            }
            case HEISIG4: {
                dataSource = new JRBeanCollectionDataSource(data);
                staticParams.put("dtnow", JCDateTime.now());
                staticParams.put("elementCount", data.size());
                istream = assets.getVHeisig4().getInputStream();
                break;
            }
            case KANJI: {
                dataSource = new JRBeanCollectionDataSource(data);
                staticParams.put("dtnow", JCDateTime.now());
                staticParams.put("elementCount", data.size());
                istream = assets.getVKanji().getInputStream();
                break;
            }
            case TOFU_TRANSLATIONS: {
                dataSource = new JRBeanCollectionDataSource(data);
                istream = assets.getTofuSentenceTranslations().getInputStream();
                break;
            }
        }

        if (istream == null) throw new IllegalStateException("istream == null || dataSource == null");

        //paneb ka istreami ise kinni
        byte[] result;
        if (format == JSGeneratorType.CSV) {
            result = JSGeneratorType.export(istream, dataSource, staticParams, _csvGenerator.getExporter());
        } else {
            result = format.export(istream, dataSource, staticParams);
        }

        log.info("getFileAsBytes: result.length=" + result.length);

        return result;
    }

    public Map.Entry<String, byte[]> getReport(String entityName, String formatName) throws IOException, JRException, SQLException {
        ECfReportType reportType = ECfReportType.valueOf(entityName.toUpperCase());
        JSGeneratorType format = JSGeneratorType.findByExtension(formatName);
        return getReport(reportType, format);
    }

    public Map.Entry<String, byte[]> getReport(ECfReportType reportType, JSGeneratorType format) throws IOException, JRException, SQLException {
        List data = getData(reportType);
        return getReport(reportType, format, data);
    }

    private static String toFileName(ECfReportType reportType, JSGeneratorType format) {
        String stamp = JCDateTime.nowIncludingSec();
        return reportType.name().toLowerCase() + " " + stamp + "." + format.extension();
    }

    private Map.Entry<String, byte[]> getReport(ECfReportType reportType, JSGeneratorType format, Collection data) throws IOException, JRException, SQLException {
        StringBuilder msg = new StringBuilder("getReport: reportType=" + reportType + ", format=" + format);

        byte[] fileAsBytes = compileToBytes(reportType, format, data);
        if (fileAsBytes == null) throw new IllegalStateException(msg.append(", fileAsBytes == null").toString());

        String fileName = toFileName(reportType, format);
        if (fileName == null) throw new IllegalStateException(msg.append(", fileName == null").toString());

        return new AbstractMap.SimpleEntry<>(fileName, fileAsBytes);
    }

    @Inject
    protected EGDAssets assets;

    //------------------------------ cache management ------------------------------

    private static String jpFilename(VHeisig6Custom item) {
        String ext = JCString.getExtension(item.getJpWordAudioFileName());
        if (ext == null || ext.length() < 1) ext = ".mp3";
        return "RTK1_keyword_jp_" + item.getId() + "." + ext;
    }

    private static String enFilename(VHeisig6Custom item) {
        return "[sound:RTK1_keyword_en_" + item.getId() + ".mp3" + "]";
    }

    public byte[] getHeisig6CustomAsArchive(int from, int to) throws IOException, JRException, SQLException {
        List<VHeisig6Custom> data = findVHeisig6Custom(from, to);
        Assert.notNull(data.get(0).getExampleWordsHtml());

        Map.Entry<String, byte[]> report = getReport(ECfReportType.HEISIG6_CUSTOM, JSGeneratorType.CSV, data);

        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(ostream);

        String name = "RTK1_" + from + "-" + to + ".csv";
        Map.Entry<String, byte[]> report2 = new AbstractMap.SimpleEntry<>(name, report.getValue());

        JCIOUtils.addToZipFile(report2, zos);

        for (VHeisig6Custom p : data) {
            if (p.getJpWordAudioFileName() != null && p.getJpWordAudio() != null && p.getJpWordAudio().length > 0) {
                JCIOUtils.addToZipFile("keyword_jp/" + jpFilename(p), p.getJpWordAudio(), zos);
            }

            //if (p.getStrokeImage() != null && p.getStrokeImage().length > 0) {
            //    JCIOUtils.addToZipFile("stroke_image/" + p.getKanji() + ".svg", p.getStrokeImage(), zos);
            //}
        }

        zos.close();
        ostream.close();

        return ostream.toByteArray();
    }
}
