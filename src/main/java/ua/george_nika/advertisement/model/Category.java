/**
 * Объект для хранения информации о категориях
 * Использует Jpa.
 */

package ua.george_nika.advertisement.model;

import javax.persistence.*;

@Entity
@Table(name = "category", schema = "public", catalog = "advertisement")
public class Category {
    private int idCategory;
    private String name;

    @Id
    @Column(name = "id_category")
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category that = (Category) o;

        return idCategory == that.idCategory;

    }

    @Override
    public int hashCode() {
        return idCategory;
    }
}
