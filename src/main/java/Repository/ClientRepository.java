package Repository;

import Models.Client;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    public List<Client> findByCountry(String country) {
        return find("country", country).list();
    }

    public Optional<Client> findByIdentification(String identification) {
        return find("identification", identification).firstResultOptional();
    }

    public boolean deleteByIdIfExists(Long id) {
        return deleteById(id);
    }

    public boolean deleteByIdentification(String identification) {
        return delete("identification", identification) > 0;
    }
}
