package securin.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import securin.project.model.CVE;

import java.util.List;

@EnableJpaRepositories
public interface cverepository extends JpaRepository<CVE, String> {
    @Query(value="SELECT * FROM cve ORDER BY lastmodified DESC LIMIT :n", nativeQuery=true)
    List<CVE> findlastNrecords(int n);
}
