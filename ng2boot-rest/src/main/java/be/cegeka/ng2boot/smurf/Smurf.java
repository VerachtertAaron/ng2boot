package be.cegeka.ng2boot.smurf;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Smurf {

    /**
     * Default Constructor for hibernate
     */
    protected Smurf() {
    }

    @Id
    @SequenceGenerator(name="id_sequence", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="id_sequence")
    @Column(name = "ID")
    private long id;

    @Lob
    @Column(name = "MESSAGE", length = -1)
    private String name;

    @Column(name = "CREATIONDATE")
    private LocalDate creationDate;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Smurf smurf = (Smurf) o;

        if (id != smurf.id) return false;
        if (name != null ? !name.equals(smurf.name) : smurf.name != null) return false;
        return creationDate != null ? creationDate.equals(smurf.creationDate) : smurf.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    public static class SmurfBuilder {

        private Smurf smurf;

        private SmurfBuilder() {
            smurf = new Smurf();
        }

        public static SmurfBuilder aSmurf() {
            return new SmurfBuilder();
        }

        public Smurf build() {
            return smurf;
        }

        public SmurfBuilder withName(String message) {
            smurf.name = message;
            return this;
        }

        public SmurfBuilder withCreationDate(LocalDate creationDate) {
            smurf.creationDate = creationDate;
            return this;
        }
    }
}
