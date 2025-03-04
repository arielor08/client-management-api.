package Controller;

import Models.Client;
import Service.ClientService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Client Management", description = "Operations related to client management")
public class ClientController {

    @Inject
    ClientService clientService;

    @GET
    @Operation(summary = "Get all clients", description = "Returns a list of all registered clients.")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GET
    @Path("/{identification}")
    @Operation(summary = "Get client by ID", description = "Fetches a client by its unique identifier.")
    public Response getClientById(@PathParam("identification") String identification) {
        Optional<Client> client = clientService.getClientById(identification);
        return client.map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/country/{country}")
    @Operation(summary = "Get clients by country", description = "Retrieves all clients from a specific country.")
    public Response getClientsByCountry(@PathParam("country") String country) {
        List<Client> clients = clientService.getClientsByCountry(country);

        if (clients.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "No clients found for this country"))
                    .build();
        }

        return Response.ok(clients).build();
    }

    @POST
    @Operation(summary = "Create a new client", description = "Registers a new client in the system.")
    public Response createClient(@Valid Client client) {
        Client createdClient = clientService.createClient(client);
        return Response.status(Response.Status.CREATED).entity(createdClient).build();
    }

    @PUT
    @Path("/{identification}")
    @Operation(summary = "Update an existing client", description = "Updates the email, phone, and country of an existing client.")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("identification") String identification, @Valid Client client) {
        Optional<Client> updatedClient = clientService.updateClient(identification, client);

        if (updatedClient.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Client updated successfully");
            response.put("identification", updatedClient.get().getIdentification());

            return Response.ok(response).build();
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Client not found");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }
    }

    @DELETE
    @Path("/{identification}")
    @Operation(summary = "Delete a client", description = "Deletes a client by its unique identifier.")
    public Response deleteClient(@PathParam("identification") String identification) {
        boolean deleted = clientService.deleteClient(identification);

        if (deleted) {
            return Response.ok(Map.of("message", "Client deleted successfully")).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Client not found"))
                    .build();
        }
    }
}