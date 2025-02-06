package com.example.ValorantAPI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api") // Prefix all endpoints with /api
public class ValorantApiController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String url = "https://valorant-api.com/v1/agents";

    // GET ALL PLAYABLE AGENTS
    @GetMapping("/agents")
    public Object getAgents() {
        try {

            // Fetch response as String
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonResponse);

            List<ValorantAgent> agentList = new ArrayList<>();

            // The API response contains a "data" array
            JsonNode agentsArray = root.get("data");
            for (JsonNode agentNode : agentsArray) {
                // Extract only playable agents
                if (!agentNode.get("isPlayableCharacter").asBoolean()) {
                    continue;
                }

                // Extract relevant agent information
                String uuid = agentNode.get("uuid").asText();
                String name = agentNode.get("displayName").asText();
                String description = agentNode.get("description").asText();
                String role = agentNode.has("role") ? agentNode.get("role").get("displayName").asText() : "Unknown";

                // Extract abilities
                List<Abilities> abilities = new ArrayList<>();
                JsonNode abilitiesArray = agentNode.get("abilities");
                if (abilitiesArray != null && abilitiesArray.isArray()) {
                    for (JsonNode abilityNode : abilitiesArray) {
                        String slot = abilityNode.get("slot").asText();
                        String abilityName = abilityNode.get("displayName").asText();
                        String abilityDescription = abilityNode.get("description").asText();

                        abilities.add(new Abilities(slot, abilityName, abilityDescription));
                    }
                }

                // Create Agent object
                ValorantAgent agent = new ValorantAgent(uuid, name, description, role, abilities);
                agentList.add(agent);
            }

            return agentList;

        } catch (Exception ex) {
            Logger.getLogger(ValorantApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "Error fetching Valorant agents.";
        }
    }


    // SEARCH AGENT BY NAME
    @GetMapping("/agent")
    public Object getAgentByName(@RequestParam String name) {
        try {

            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode agentsArray = root.get("data");

            for (JsonNode agentNode : agentsArray) {
                if (agentNode.get("displayName").asText().equalsIgnoreCase(name) &&
                        agentNode.get("isPlayableCharacter").asBoolean()) {
                    return extractAgent(agentNode);
                }
            }

            return "Agent not found.";
        } catch (Exception ex) {
            Logger.getLogger(ValorantApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "Error fetching agent data.";
        }
    }

    // HELPER METHOD TO EXTRACT AGENT DETAILS (INCLUDING ABILITIES)
    private ValorantAgent extractAgent(JsonNode agentNode) {
        String uuid = agentNode.get("uuid").asText();
        String name = agentNode.get("displayName").asText();
        String description = agentNode.get("description").asText();
        String role = agentNode.has("role") ? agentNode.get("role").get("displayName").asText() : "Unknown";

        // Extract abilities
        List<Abilities> abilities = new ArrayList<>();
        if (agentNode.has("abilities")) {
            for (JsonNode abilityNode : agentNode.get("abilities")) {
                String slot = abilityNode.get("slot").asText();
                String abilityName = abilityNode.get("displayName").asText();
                String abilityDesc = abilityNode.get("description").asText();

                abilities.add(new Abilities(slot, abilityName, abilityDesc));
            }
        }

        return new ValorantAgent(uuid, name, description, role, abilities);

    }

}
