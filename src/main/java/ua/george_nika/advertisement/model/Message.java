/**
 * Объект для хранения информации о категориях
 * Использует Jpa.
 * Использует Hibernate validator.
 * Created by george on 12.02.2016.
 */
package ua.george_nika.advertisement.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "message", schema = "public", catalog = "advertisement")
public class Message {

    private int idMessage;
    private Account account;
    private Category category;

    @NotNull(message = "title must be not empty")
    @Size(min = 10, max = 30, message = "title size must be between 10 and 30")
    private String title;

    @NotNull(message = "message must be not empty")
    @Size(min = 20, max = 400, message = "message size must be between 20 and 400")
    private String message;

    private LocalDateTime created = new LocalDateTime();
    private LocalDateTime updated = new LocalDateTime();

    @Id
    @Column(name = "id_message")
    @SequenceGenerator(name = "seq_message", sequenceName = "seq_message")
    @GeneratedValue(generator = "seq_message")
    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    @ManyToOne
    @JoinColumn(name = "id_account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account user) {
        this.account = user;
    }

    @ManyToOne
    @JoinColumn(name = "id_category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String header) {
        this.title = header;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "created")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Basic
    @Column(name = "updated")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", account=" + account +
                ", category=" + category +
                ", header='" + title + '\'' +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message that = (Message) o;

        return idMessage == that.idMessage;

    }

    @Override
    public int hashCode() {
        return idMessage;
    }
}
