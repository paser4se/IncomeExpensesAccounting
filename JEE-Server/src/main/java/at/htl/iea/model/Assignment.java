package at.htl.iea.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "Assignment.getAll", query = "select a from Assignment a")
})
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords = new HashSet<>();

    @OneToOne
    private Category category;

    //region Constructors
    public Assignment() {
    }

    public Assignment(Category category) {
        this.category = category;
    }
    //endregion

    //region Getter and Setter
    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }
    //endregion
}
