package ee.esutoniagodesu.domain.ac.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An externally managed account that is associated with an internal user.  For example, an
 * account with Google or Facebook.
 */
@Entity
@Table(schema = "ac", name = "userconnection")
public class ExternalAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "providerid", length = 20, nullable = false)
    private ExternalAccountProvider externalProvider;

    @Column(name = "providerUserId", length = 50, nullable = false)
    private String externalIdentifier;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "userid", referencedColumnName = "login", nullable = false)
    private User user;

    public ExternalAccount() {
    }

    public ExternalAccount(ExternalAccountProvider externalProvider, String externalIdentifier) {
        this.externalProvider = externalProvider;
        this.externalIdentifier = externalIdentifier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExternalAccountProvider getExternalProvider() {
        return externalProvider;
    }

    public void setExternalProvider(ExternalAccountProvider externalProvider) {
        this.externalProvider = externalProvider;
    }

    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((externalIdentifier == null) ? 0 : externalIdentifier.hashCode());
        result = prime
                * result
                + ((externalProvider == null) ? 0 : externalProvider.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        ExternalAccount other = (ExternalAccount) obj;
        if (externalIdentifier == null) {
            if (other.externalIdentifier != null)
                return false;
        } else if (!externalIdentifier.equals(other.externalIdentifier))
            return false;
        if (externalProvider != other.externalProvider)
            return false;
        return true;
    }

    public String toString() {
        return "ExternalAccount{" +
            "id=" + id +
            ", externalProvider=" + externalProvider +
            ", externalIdentifier='" + externalIdentifier + '\'' +
            '}';
    }
}
