package at.htl.iea.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Category.getSortedCategories", query = "select c from Category c where c.parentCategory is null order by c.name asc"),
        @NamedQuery(name = "Category.getByName", query = "select c from Category c where c.name = ?1")
})
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    @JsonIgnore
    private Category parentCategory;

    private Long parentCategoryId;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Category> subcategories = new LinkedHashSet<>();

    // region Constructor
    public Category(){}

    public Category(String name) {
        this.name = name;
    }
    // endregion

    // region Getter & setter
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentCategoryId() {
        if (this.parentCategory != null) {
            this.parentCategoryId = this.parentCategory.id;
        }
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
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

    public List<Category> getSubcategories() {
        return this.subcategories.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
    }

    public void addSubcategory(Category subcategory) {
        subcategory.setParentCategory(this);
        subcategories.add(subcategory);
    }
    // endregion
}
