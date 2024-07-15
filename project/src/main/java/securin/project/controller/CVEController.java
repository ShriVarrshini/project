package securin.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import securin.project.model.CVE;
import securin.project.repository.cverepository;
import securin.project.service.CVEService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cve")
public class CVEController {
    @Autowired
    private CVEService cveService;

    @Autowired
    private cverepository cverepo;

    @GetMapping("/fetch")
    public ResponseEntity<String> fetchAndStoreCVEs() {
        cveService.fetchandStoreCVE();
        return ResponseEntity.ok("CVE details fetched and stored successfully.");
    }

    @GetMapping
    public List<CVE> getAllCVEs() {
        List<CVE> cves = cveService.getAllCVE();
        return cves;
    }
    @GetMapping("/{cveId}")
    public ResponseEntity<Optional<CVE>> getCve(@PathVariable String cveId){
        Optional<CVE> cve=cverepo.findById(cveId);
        if(cve.isPresent()) {
            return ResponseEntity.ok(cve);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/records")
    public List<CVE> findlastNrecords(@RequestParam int n){
        return cveService.findlastNrecords(n);
    }

}
