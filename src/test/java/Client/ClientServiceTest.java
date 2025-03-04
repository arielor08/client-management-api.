package Client;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Models.Client;
import Repository.ClientRepository;
import Service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setIdentification("40212022541");
        client.setFirstName("Ariel");
        client.setLastName("Ortiz");
        client.setEmail("ariel@example.com");
        client.setPhone("1234567890");
        client.setCountry("DO");
    }

    @Test
    public void testCreateClient() {
        when(clientRepository.findByIdentification(client.getIdentification())).thenReturn(Optional.empty());

        Client createdClient = clientService.createClient(client);

        assertNotNull(createdClient);
        assertEquals("40212022541", createdClient.getIdentification());
    }

    @Test
    public void testUpdateClient_Success() {
        when(clientRepository.findByIdentification(client.getIdentification())).thenReturn(Optional.of(client));

        client.setEmail("newemail@example.com");
        Optional<Client> updatedClient = clientService.updateClient(client.getIdentification(), client);

        assertTrue(updatedClient.isPresent());
        assertEquals("newemail@example.com", updatedClient.get().getEmail());
    }

    @Test
    public void testDeleteClient_Success() {
        when(clientRepository.findByIdentification(client.getIdentification())).thenReturn(Optional.of(client));

        String clientId=  client.getIdentification();
        boolean result = clientService.deleteClient(client.getIdentification());

        assertTrue(result);
    }

    @Test
    public void testDeleteClient_NotFound() {
        when(clientRepository.findByIdentification(client.getIdentification())).thenReturn(Optional.empty());

        boolean result = clientService.deleteClient(client.getIdentification());

        assertFalse(result);
    }

    @Test
    public void testGetClientById_Found() {
        when(clientRepository.findByIdentification(client.getIdentification())).thenReturn(Optional.of(client));

        Optional<Client> foundClient = clientService.getClientById(client.getIdentification());

        assertTrue(foundClient.isPresent());
        assertEquals(client.getIdentification(), foundClient.get().getIdentification());
    }

    @Test
    public void testGetClientById_NotFound() {
        when(clientRepository.findByIdentification(client.getIdentification())).thenReturn(Optional.empty());

        Optional<Client> foundClient = clientService.getClientById(client.getIdentification());

        assertFalse(foundClient.isPresent());
    }
}
