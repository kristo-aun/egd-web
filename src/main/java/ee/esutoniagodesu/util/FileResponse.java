package ee.esutoniagodesu.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class FileResponse {
    public static ResponseEntity<byte[]> jcresponseFile(String fileExtension, String fileName, byte[] fileAsBytes) throws IllegalArgumentException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", HeaderContentType.findByName(fileExtension).getContentType());
        headers.set("Content-disposition", "attachment;charset=utf-8; filename=" + fileName);
        headers.setContentLength(fileAsBytes.length);
        return new ResponseEntity<>(fileAsBytes, headers, HttpStatus.OK);
    }
}
