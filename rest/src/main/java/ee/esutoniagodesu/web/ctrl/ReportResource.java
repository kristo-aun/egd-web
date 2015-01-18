package ee.esutoniagodesu.web.ctrl;

import com.jc.spring.FileResponse;
import ee.esutoniagodesu.service.JasperService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportResource {

    @Inject
    private JasperService jasperService;

	@RequestMapping("/download/{report}.{format}")
	@ResponseBody
	public ResponseEntity<?> findAll(@PathVariable("report") String report, @PathVariable("format") String format) throws IOException, JRException, SQLException {
		Map.Entry<String, byte[]> result = jasperService.getReport(report, format);
		return FileResponse.jcresponseFile(format, result.getKey(), result.getValue());
	}
}
