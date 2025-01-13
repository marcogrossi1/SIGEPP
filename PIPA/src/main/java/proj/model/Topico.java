import javax.persistence.*;

@Entity
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "secao_id")
    private Secao secao;

    // Getters e setters
}
