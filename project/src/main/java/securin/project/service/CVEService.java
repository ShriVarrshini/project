package securin.project.service;

import securin.project.model.CVE;
import java.util.List;

public interface CVEService {

    void fetchandStoreCVE();

    List<CVE> getAllCVE();
    List<CVE> findlastNrecords(int n);
}
