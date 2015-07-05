package ee.esutoniagodesu.domain.ac.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.esutoniagodesu.util.lang.ISO6391;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A user.
 */
@Entity
@Table(schema = "ac", name = "user")
public class User implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @Size(min = 13, max = 13)
    @Column(name = "uuid", length = 13, unique = true)
    private String uuid;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean activated = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang_key", nullable = false)
    private ISO6391 langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @CollectionTable(
        schema = "ac",
        name = "user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @ElementCollection(targetClass = Authority.class)
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Authority> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<PersistentToken> persistentTokens = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private UserAccountForm accountForm;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<UserAccountExternal> accountExternals = new HashSet<>();

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "created_date", nullable = false)
    protected DateTime createdDate = DateTime.now();

    public User() {
    }

    public User(String uuid) {
        this.uuid = uuid;
    }

    public User(String firstName, String lastName, String email, ExternalProvider externalProvider, String externalUserId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountExternals.add(new UserAccountExternal(externalProvider, externalUserId));
    }

    public User(String login, String password, String firstName, String lastName, String email, String langKey) {
        this.accountForm = new UserAccountForm(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.langKey = ISO6391.valueOf(langKey);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public ISO6391 getLangKey() {
        return langKey;
    }

    public void setLangKey(ISO6391 langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Set<PersistentToken> getPersistentTokens() {
        return persistentTokens;
    }

    public UserAccountForm getAccountForm() {
        return accountForm;
    }

    public void setAccountForm(UserAccountForm accountForm) {
        this.accountForm = accountForm;
    }

    public Set<UserAccountExternal> getAccountExternals() {
        return accountExternals;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Authority> getRoles() {
        return roles;
    }

    public void setRoles(Set<Authority> roles) {
        this.roles = roles;
    }

    public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
        this.persistentTokens = persistentTokens;
    }

    public void setAccountExternals(Set<UserAccountExternal> accountExternals) {
        this.accountExternals = accountExternals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey=" + langKey +
            ", activationKey='" + activationKey + '\'' +
            ", roles=" + roles +
            ", persistentTokens.size=" + persistentTokens.size() +
            ", accountForm=" + accountForm +
            ", accountExternals=" + accountExternals +
            ", createdDate=" + createdDate +
            '}';
    }
}
