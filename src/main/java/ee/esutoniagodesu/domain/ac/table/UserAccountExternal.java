package ee.esutoniagodesu.domain.ac.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An externally managed account that is associated with an internal user.  For example, an
 * account with Google or Facebook.
 */
@Entity
@Table(schema = "ac", name = "user_account_external")
public class UserAccountExternal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = 20, nullable = false, insertable = true, updatable = false)
    private ExternalProvider provider;

    @Column(name = "identifier", length = 50, nullable = false, insertable = true, updatable = false)
    private String identifier;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public UserAccountExternal() {
    }

    public UserAccountExternal(ExternalProvider provider, String identifier, User user) {
        this.provider = provider;
        this.identifier = identifier;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExternalProvider getProvider() {
        return provider;
    }

    public void setProvider(ExternalProvider provider) {
        this.provider = provider;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountExternal that = (UserAccountExternal) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (provider != that.provider) return false;
        return !(identifier != null ? !identifier.equals(that.identifier) : that.identifier != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAccountExternal{" +
            "id=" + id +
            ", provider=" + provider +
            ", identifier='" + identifier + '\'' +
            '}';
    }
}
