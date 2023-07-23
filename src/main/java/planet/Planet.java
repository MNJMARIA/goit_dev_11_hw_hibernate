package planet;

import jakarta.persistence.*;
import lombok.Data;
import ticket.Ticket;

import java.util.Set;

@Data
@Entity
@Table(name = "planet")
public class Planet {
    @Id
    private String id;

    @Column(name = "name", length = 500)
    private String name;
/*
    @OneToMany(mappedBy = "planet")
    private Set<Ticket> tickets;*/
}
