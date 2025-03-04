package Service;

import Repository.ClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import Models.Client;


import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final CountryService countryService;// Service to fetch nationality

    @Transactional
    public Client createClient(Client client) {
     try{
         client.setNationality(countryService.getNationalityByCountryCode(client.getCountry())); // Fetch nationality
         clientRepository.persist(client);
         return client;
     } catch (Exception e) {
         throw new WebApplicationException("Error creating the client", 500);
     }
    }

    public List<Client> getAllClients() {
        return clientRepository.listAll();
    }

    public List<Client> getClientsByCountry(String country) {
        return clientRepository.findByCountry(country);
    }

    public Optional<Client> getClientById(String id) {
        return clientRepository.findByIdentification(id);
    }

    @Transactional
    public Optional<Client> updateClient(String id, Client updatedClient) {
        try {
            return clientRepository.findByIdentification(id).map(existingClient -> {
                existingClient.setEmail(updatedClient.getEmail());
                existingClient.setAddress(updatedClient.getAddress());
                existingClient.setPhone(updatedClient.getPhone());
                existingClient.setCountry(updatedClient.getCountry());
                existingClient.setNationality(countryService.getNationalityByCountryCode(updatedClient.getCountry())); // Update nationality
                return existingClient;
            });
        } catch (Exception e){
            throw new WebApplicationException("Error updating the client", e);
        }

    }

    @Transactional
    public boolean deleteClient(String identification) {
        return clientRepository.deleteByIdentification(identification);
    }
}
