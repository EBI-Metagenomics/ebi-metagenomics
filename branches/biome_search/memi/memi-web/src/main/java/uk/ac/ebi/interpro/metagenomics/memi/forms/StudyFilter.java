package uk.ac.ebi.interpro.metagenomics.memi.forms;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

/**
 * Represents a filter form, which is used within the study list page.
 * Use this object to filter studies by type or status.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudyFilter {

    public final static String MODEL_ATTR_NAME = "studyFilter";

    private String searchTerm;

    private Study.StudyStatus studyStatus;

    private StudyVisibility studyVisibility;

    private Biome biome;

    public StudyFilter() {
        //set default values
        this.studyVisibility = StudyVisibility.ALL_PUBLISHED_PROJECTS;
        this.biome = Biome.ALL;
    }


    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Study.StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(Study.StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }

    public StudyVisibility getStudyVisibility() {
        return studyVisibility;
    }

    public void setStudyVisibility(StudyVisibility studyVisibility) {
        this.studyVisibility = studyVisibility;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    /**
     * ALL_PROJECTS: All published and my pre-published studies
     * ALL_PUBLISHED_PROJECTS: All published studies
     * MY_PROJECTS: All my published and my pre-published studies
     * MY_PUBLISHED_PROJECTS: All my published studies
     * MY_PRE-PUBLISHED_PROJECTS: All my pre-published studies
     */
    public enum StudyVisibility {
        ALL_PROJECTS("All projects"),
        ALL_PUBLISHED_PROJECTS("All published projects"),
        MY_PROJECTS("My projects"),
        MY_PUBLISHED_PROJECTS("My published projects"),
        MY_PREPUBLISHED_PROJECTS("My pre-published projects");

        private String name;

        private StudyVisibility(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getUpperCaseString() {
            String result = name.replace(" ", "_");
            result = result.replace("-", "");
            return result.toUpperCase();
        }
    }

    public enum Biome {
        ALL("All", "root:root"),
        AIR("Air", "root:Environmental:Air"),
        ENGINEERED("Engineered", "root:Engineered"),
        FOREST_SOIL("Forest", "root:Environmental:Terrestrial:Soil:Loam:Forest Soil", "root:Environmental:Terrestrial:Soil:Sand:Forest soil",
                "root:Environmental:Terrestrial:Soil:Forest Soil"),
        FRESHWATER("Freshwater", "root:Environmental:Aquatic:Freshwater"),
        GRASSLAND("Grassland", "root:Environmental:Terrestrial:Soil:Grasslands", "root:Environmental:Terrestrial:Soil:Sand:Grasslands",
                "root:Environmental:Terrestrial:Soil:Loam:Grasslands", "root:Environmental:Terrestrial:Soil:Clay:Grasslands"),
        HUMAN_GUT("Human gut", "root:Host-associated:Human:Digestive system:Large intestine"),
        HUMAN_HOST("Human", "root:Host-associated:Human"),
        HOST_ASSOCIATED("Host-associated", "root:Host-associated"),
        MARINE("Marine", "root:Environmental:Aquatic:Marine"),
        SOIL("Soil", "root:Environmental:Terrestrial:Soil"),
        WASTEWATER("Wastewater", "root:Engineered:Wastewater");

        private String name;

        private String[] lineages;

        private Biome(String name, String... lineages) {
            this.name = name;
            this.lineages = lineages;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getUpperCaseString() {
            String result = name.replace(" ", "_");
            result = result.replace("-", "");
            return result.toUpperCase();
        }

        public String[] getLineages() {
            return lineages;
        }
    }
}