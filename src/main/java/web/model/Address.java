package web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "adresses")
@Getter @Setter @ToString
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String street;
    private int building;

    @OneToMany(mappedBy = "address")
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }
}
