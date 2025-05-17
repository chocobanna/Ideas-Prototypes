// src/main/java/terminal/CommandHandler.java
package terminal;

import java.util.*;

public class CommandHandler {
    private final DirectoryNode root;
    private DirectoryNode current;

    public CommandHandler() {
        root = new DirectoryNode("");
        current = root;
        initSolarSystem();
    }

    private void initSolarSystem() {
        String[] paths = {
            // Mercury
            "Mercury/Alpha_Base",
            "Mercury/Solar_Farm",
            "Mercury/Research_Post",
            "Mercury/Helios_Station",
            "Mercury/Observation_Platform",

            // Venus
            "Venus/Cloud_City",
            "Venus/Venusian_Pier",
            "Venus/Acid_Rain_Fortress",
            "Venus/Greenhouse_Alpha",
            "Venus/Orbital_Dock",

            // Earth
            "Earth/New_York",
            "Earth/Los_Angeles",
            "Earth/London",
            "Earth/Tokyo",
            "Earth/Sydney",
            "Earth/Mumbai",
            "Earth/Berlin",
            "Earth/Paris",
            "Earth/Cairo",
            "Earth/Sao_Paulo",

            // Moon
            "Earth/Moon/Luna_Colony",
            "Earth/Moon/Sea_Base",
            "Earth/Moon/Tranquility_Outpost",
            "Earth/Moon/Rover_Camp",

            // Mars
            "Mars/Mars_Colony",
            "Mars/Valles_Marineris_Base",
            "Mars/Olympus_Mons_Research",
            "Mars/Polar_Station",
            "Mars/Cydonia_Outpost",

            // Phobos
            "Mars/Phobos/Phobos_Outpost",
            "Mars/Phobos/Red_Rock_Camp",
            "Mars/Phobos/Stellar_Sight_Station",

            // Deimos
            "Mars/Deimos/Deimos_Station",
            "Mars/Deimos/Deep_Space_Lab",

            // Jupiter & Moons
            "Jupiter/Io/Io_Station",
            "Jupiter/Io/Vulcan_Platform",
            "Jupiter/Europa/Europa_Lab",
            "Jupiter/Europa/Ice_Station",
            "Jupiter/Ganymede/Ganymede_Settlement",
            "Jupiter/Ganymede/Jupiter_Gateway",
            "Jupiter/Callisto/Callisto_Hub",
            "Jupiter/Callisto/Mining_Outpost",

            // Saturn & Moons
            "Saturn/Titan/Titan_Base",
            "Saturn/Titan/Helium_Silo",
            "Saturn/Enceladus/Enceladus_Outpost",
            "Saturn/Enceladus/Cryo_Station",

            // Uranus & Moons
            "Uranus/Miranda/Miranda_Station",
            "Uranus/Ariel/Ariel_Outpost",
            "Uranus/Umbriel/Umbriel_Base",
            "Uranus/Titania/Titania_Research_Hub",
            "Uranus/Oberon/Oberon_Station",

            // Neptune & Moon
            "Neptune/Triton/Triton_Colony",
            "Neptune/Triton/Frost_Lab",

            // Pluto & Charon
            "Pluto/Charon/Charon_Station",
            "Pluto/Charon/Ice_Dock",

            // Belts
            "Asteroid_Belt/Ceres_Outpost",
            "Asteroid_Belt/Vesta_Station",
            "Asteroid_Belt/Pallas_Outpost",
            "Asteroid_Belt/Hygiea_Station",
            "Kuiper_Belt/Eris_Outpost",
            "Kuiper_Belt/Makemake_Station",
            "Kuiper_Belt/Haumea_Outpost",
            "Kuiper_Belt/Quaoar_Station"
        };

        for (String p : paths) {
            addPath(p);
        }
    }

    private void addPath(String path) {
        String[] parts = path.split("/");
        DirectoryNode node = root;
        for (String part : parts) {
            DirectoryNode child = node.children.get(part);
            if (child == null) {
                child = new DirectoryNode(part, node);
                node.children.put(part, child);
            }
            node = child;
        }
    }

    public String handle(String cmd) {
        String[] parts = cmd.split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) return null;
        switch (parts[0]) {
            case "nav":
                if (parts.length < 2) return "Usage: nav <dir> or nav ..";
                if ("..".equals(parts[1])) {
                    if (current.parent != null) current = current.parent;
                    return null;
                }
                DirectoryNode nxt = current.children.get(parts[1]);
                if (nxt != null) { current = nxt; return null; }
                return "Directory not found: " + parts[1];
            case "list":
                if (current.children.isEmpty()) return "(no subdirectories)";
                List<String> names = new ArrayList<>(current.children.keySet());
                Collections.sort(names);
                return String.join("\n", names);
            default:
                return "Unknown command: " + cmd;
        }
    }

    public String getCurrentDirPath() {
        List<String> names = new ArrayList<>();
        DirectoryNode node = current;
        while (node != null && !node.name.isEmpty()) {
            names.add(node.name);
            node = node.parent;
        }
        Collections.reverse(names);
        return "/" + String.join("/", names) + "/";
    }

    private static class DirectoryNode {
        final String name;
        final DirectoryNode parent;
        final Map<String, DirectoryNode> children = new HashMap<>();
        DirectoryNode(String name) { this(name, null); }
        DirectoryNode(String name, DirectoryNode parent) {
            this.name = name;
            this.parent = parent;
        }
    }
}
