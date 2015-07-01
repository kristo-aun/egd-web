package ee.esutoniagodesu.domain.ac.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.esutoniagodesu.domain.AbstractAuditingEntity;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.util.lang.ISO6391;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A user.
 */
@Entity
@Table(schema = "ac", name = "user")
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    @NotNull
    @Pattern(regexp = "^[a-z0-9]*$")
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean activated = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang_key")
    private ISO6391 langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "reset_date", nullable = true)
    private DateTime resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        schema = "ac",
        name = "user_authority",
        joinColumns = {@JoinColumn(name = "login", referencedColumnName = "login")},
        inverseJoinColumns = {@JoinColumn(name = "name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<PersistentToken> persistentTokens = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<ExternalAccount> externalAccounts = new HashSet<>();

    public Set<ExternalAccount> getExternalAccounts() {
        return externalAccounts;
    }

    public void setExternalAccounts(Set<ExternalAccount> externalAccountIds) {
        this.externalAccounts = externalAccountIds;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public DateTime getResetDate() {
        return resetDate;
    }

    public void setResetDate(DateTime resetDate) {
        this.resetDate = resetDate;
    }

    public ISO6391 getLangKey() {
        return langKey;
    }

    public void setLangKey(ISO6391 langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<PersistentToken> getPersistentTokens() {
        return persistentTokens;
    }

    public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
        this.persistentTokens = persistentTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!login.equals(user.login)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }

    @Transient
    private boolean hasRole(String authority) {
        for (Authority p : authorities) {
            if (p.getName().equals(authority)) return true;
        }
        return false;
    }

    @Transient
    public boolean hasRoleAdmin() {
        return hasRole(AuthoritiesConstants.ADMIN);
    }

    @Transient
    public boolean hasRoleSensei() {
        return hasRole(AuthoritiesConstants.SENSEI);
    }

    @Transient
    public boolean hasRoleUser() {
        return hasRole(AuthoritiesConstants.USER);
    }
}
