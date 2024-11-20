import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

@Service
public class GitLabService {

    private final String GITLAB_API_URL = "https://gitlab.com/api/v4/projects/{projectId}/repository/files/{filePath}/raw";
    private final String ACCESS_TOKEN = "your_access_token";

    public InputStream fetchExcelFile(String projectId, String filePath, String branch) {
        RestTemplate restTemplate = new RestTemplate();

        // Build headers with authentication
        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", ACCESS_TOKEN);

        // Build the request URL
        String url = GITLAB_API_URL
            .replace("{projectId}", projectId)
            .replace("{filePath}", filePath);

        ResponseEntity<byte[]> response = restTemplate.exchange(
            url + "?ref=" + branch,
            HttpMethod.GET,
            null,
            byte[].class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new java.io.ByteArrayInputStream(response.getBody());
        } else {
            throw new RuntimeException("Failed to fetch the Excel file: " + response.getStatusCode());
        }
    }
}
