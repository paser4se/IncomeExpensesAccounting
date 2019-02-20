package at.htl.iea.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@Entity
@XmlRootElement
@NamedQueries(
        @NamedQuery(name = "Category.getSortedCategories", query = "select c from Category c order by c.name asc")
)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    private Category parentCategory;

    @OneToMany
    private Set<Category> subcategories = new LinkedHashSet<>();

    // region Constructor
    public Category(){}
    // endregion

    // region Getter & setter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Category> subcategories) {
        this.subcategories = subcategories;
    }
    // endregion

}
