package securin.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import securin.project.model.CVE;
import securin.project.repository.cverepository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class CVEServiceImpl implements CVEService {

    @Autowired
    private cverepository cveRepository;

    private static final String url = "https://services.nvd.nist.gov/rest/json/cves/2.0?resultsPerpage={result}&startIndex={startIndex}";
    @Override
    public void fetchandStoreCVE() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            int startIndex = 0;
            int result = 20;
            int totalResults = 256739;


            while (startIndex <= totalResults){
                String apiUrl = url.replace("{result}", String.valueOf(result)).replace("{startIndex}", String.valueOf(startIndex));
                ResponseEntity<Map<String, Object>> response = restTemplate.getForEntity(apiUrl, (Class<Map<String, Object>>) (Object) Map.class);

                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> vulnerabilities = (List<Map<String, Object>>) responseBody.get("vulnerabilities");

                for (Map<String, Object> vulnerabilityData : vulnerabilities) {
                    Map<String, Object> cveData = (Map<String, Object>) vulnerabilityData.get("cve");
                    String cveId = (String) cveData.get("id");

                    List<Map<String, String>> descriptions = (List<Map<String, String>>) cveData.get("descriptions");
                    String description = descriptions.stream()
                            .filter(desc -> "en".equals(desc.get("lang")))
                            .findFirst()
                            .map(desc -> desc.get("value"))
                            .orElse("No description available");

                    String cveStatus = (String) cveData.get("vulnStatus");

                    String lastmodified = (String) cveData.get("lastModified");
                    int indexofdot = lastmodified.indexOf('.');
                    if (indexofdot > 0) {
                        lastmodified = lastmodified.substring(0, indexofdot);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Timestamp timestamp = null;
                    try {
                        Date parsedDate = sdf.parse(lastmodified);
                        timestamp = new Timestamp(parsedDate.getTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    CVE cve = new CVE();
                    cve.setCveId(cveId);
                    cve.setDescription(description);
                    cve.setCveStatus(cveStatus);
                    cve.setLastmodified(timestamp);
                    cveRepository.save(cve);
                }

                Thread.sleep(2000);
                startIndex += 20;
                result=20 ;


            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public List<CVE> getAllCVE() {
        return cveRepository.findAll();
    }

    @Override
    public List<CVE> findlastNrecords(int n) {
        return cveRepository.findlastNrecords(n);
    }


}


