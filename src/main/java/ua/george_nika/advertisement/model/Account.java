/**
 * Объект для хранения информации о пользователе
 * Использует Jpa.
 * Использует Hibernate validator.
 */

package ua.george_nika.advertisement.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account", schema = "public", catalog = "advertisement")
public class Account {
    private int idAccount;

    @NotNull(message = "login must be not empty")
    @Size(min = 4, max = 50, message = "login size must be between 4 and 50")
    private String login;

    @NotNull(message = "password must be not empty")
    @Size(min = 4, max = 50, message = "password size must be between 4 and 50")
    private String password;

    private LocalDateTime created;

    public Account(){
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
        this.created = new LocalDateTime();
    }

    @Id
    @Column(name = "id_account")
    @SequenceGenerator(name="seq_account", sequenceName="seq_account")
    @GeneratedValue(generator="seq_account")
    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idUser) {
        this.idAccount = idUser;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "created")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idAccount=" + idAccount +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account that = (Account) o;

        return idAccount == that.idAccount;

    }

    @Override
    public int hashCode() {
        return idAccount;
    }
}
