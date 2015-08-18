package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.EAuthority;
import ee.esutoniagodesu.service.JasperService;
import ee.esutoniagodesu.util.FileResponse;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportResource {

    private static final Logger log = LoggerFactory.getLogger(ReportResource.class);

    @Inject
    private JasperService jasperService;

    @RequestMapping("/{report}.{format}")
    @ResponseBody
    public ResponseEntity<?> findAll(@PathVariable("report") String report, @PathVariable("format") String format) throws IOException, SQLException, JRException {
        log.debug("REST request to get report {} {}", report, format);
        Map.Entry<String, byte[]> result = jasperService.getReport(report, format);
        return FileResponse.jcresponseFile(format, result.getKey(), result.getValue());
    }

    @RequestMapping("/archive/rtk1.zip")
    @ResponseBody
    @RolesAllowed(EAuthority.role_admin)
    public ResponseEntity<?> heisig6(@RequestParam(required = false) Integer from,
                                     @RequestParam(required = false) Integer to) throws IOException, SQLException, JRException {
        log.debug("REST request to get RTK.zip");
        if (from == null && to == null) {
            from = 1;
            to = 2200;
        }
        byte[] result = jasperService.getHeisig6CustomAsArchive(from, to);
        return FileResponse.jcresponseFile("zip", "RTK1_" + from + "-" + to + ".zip", result);
    }
}
