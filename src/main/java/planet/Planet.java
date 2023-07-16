package planet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "planet")
public class Planet {
    @Id
    private String id;

    @Column(name = "name", length = 500)
    private String name;
}
