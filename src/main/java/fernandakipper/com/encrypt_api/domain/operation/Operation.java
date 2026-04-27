package fernandakipper.com.encrypt_api.domain.operation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "userdocument", nullable = false)
    private String userDocument;

    @Column(name = "creditcardtoken", nullable = false)
    private String creditCardToken;

    @Column(name = "operationvalue", nullable = false)
    private Long operationValue;
}