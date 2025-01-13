@Entity
public class Secao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoSeccao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
