package securin.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CVE
{
    @Id
    private String cveId;
    @Column(length=2000)
    private String description;
    private String cveStatus;


    private Timestamp lastmodified;
    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCveStatus() {
        return cveStatus;
    }

    public void setCveStatus(String cveStatus) {
        this.cveStatus = cveStatus;
    }
    public Timestamp getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Timestamp lastmodified) {
        this.lastmodified = lastmodified;
    }


}
