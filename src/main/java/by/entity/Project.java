package by.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
public class Project implements Comparable<Project>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Task> taskList;
    @ManyToMany
    @JoinTable(name = "project_user",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> userList = new LinkedHashSet<>();

    @Override
    public int compareTo(Project o) {
        return this.id - o.id;
    }
}