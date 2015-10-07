package uk.ac.ebi.interpro.metagenomics.memi.forms;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 10/7/15
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Biome {
    ALL("All", "root:root"),
    AIR("Air", "root:Environmental:Air"),
    ENGINEERED("Engineered", "root:Engineered"),
    FOREST_SOIL("Forest", "root:Environmental:Terrestrial:Soil:Loam:Forest soil", "root:Environmental:Terrestrial:Soil:Sand:Forest soil",
            "root:Environmental:Terrestrial:Soil:Forest soil", "root:Environmental:Terrestrial:Soil:Boreal forest", "root:Environmental:Terrestrial:Soil:Tropical rainforest"),
    FRESHWATER("Freshwater", "root:Environmental:Aquatic:Freshwater"),
    GRASSLAND("Grassland", "root:Environmental:Terrestrial:Soil:Grasslands", "root:Environmental:Terrestrial:Soil:Sand:Grasslands",
            "root:Environmental:Terrestrial:Soil:Loam:Grasslands", "root:Environmental:Terrestrial:Soil:Clay:Grasslands"),
    HUMAN_GUT("Human gut", "root:Host-associated:Human:Digestive system:Large intestine"),
    HUMAN_HOST("Human", "root:Host-associated:Human"),
    HOST_ASSOCIATED("Host-associated", "root:Host-associated"),
    NON_HUMAN_HOST("Non-human", "root:Host-associated:Algae","root:Host-associated:Amphibia","root:Host-associated:Animal","root:Host-associated:Annelida",
            "root:Host-associated:Arthropoda", "root:Host-associated:Birds", "root:Host-associated:Cnidaria", "root:Host-associated:Echinodermata",
            "root:Host-associated:Endosymbionts", "root:Host-associated:Fish", "root:Host-associated:Fungi", "root:Host-associated:Insecta", "root:Host-associated:Invertebrates",
            "root:Host-associated:Mammals", "root:Host-associated:Microbial", "root:Host-associated:Mollusca", "root:Host-associated:Plants", "root:Host-associated:Porifera",
            "root:Host-associated:Protists", "root:Host-associated:Protozoa", "root:Host-associated:Reptile", "root:Host-associated:Spiralia", "root:Host-associated:Tunicates"),
    MARINE("Marine", "root:Environmental:Aquatic:Marine"),
    SOIL("Soil", "root:Environmental:Terrestrial:Soil"),
    WASTEWATER("Wastewater", "root:Engineered:Wastewater");

    private String name;

    private String[] lineages;

    private Biome(String name, String... lineages) {
        this.name = name;
        this.lineages = lineages;
    }

//        @Override
//        public String toString() {
//            return name;
//        }

    public String getUpperCaseString() {
        String result = name.replace(" ", "_");
        result = result.replace("-", "");
        return result.toUpperCase();
    }

    public String[] getLineages() {
        return lineages;
    }
}
