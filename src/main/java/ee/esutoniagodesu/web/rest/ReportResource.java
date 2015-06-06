package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.service.JasperService;
import ee.esutoniagodesu.util.FileResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportResource {

    @Inject
    private JasperService jasperService;

    @RequestMapping("/{report}.{format}")
    @ResponseBody
    public ResponseEntity<?> findAll(@PathVariable("report") String report, @PathVariable("format") String format) throws IOException, JRException, SQLException {
        Map.Entry<String, byte[]> result = jasperService.getReport(report, format);
        return FileResponse.jcresponseFile(format, result.getKey(), result.getValue());
    }
}
