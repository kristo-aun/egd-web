package ee.esutoniagodesu.util.jasperreports;

import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.export.OdsExporterConfiguration;
import net.sf.jasperreports.export.SimpleOdsExporterConfiguration;

public final class ODSGenerator {
    private final OdsExporterConfiguration _conf;
    private final JROdsExporter _exporter;

    public ODSGenerator() {
        _conf = new SimpleOdsExporterConfiguration();
        _exporter = new JROdsExporter();
        _exporter.setConfiguration(_conf);
    }

    public OdsExporterConfiguration getConfiguration() {
        return _conf;
    }

    public JROdsExporter getExporter() {
        return _exporter;
    }
}
