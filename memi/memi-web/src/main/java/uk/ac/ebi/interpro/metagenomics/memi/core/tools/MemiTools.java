package uk.ac.ebi.interpro.metagenomics.memi.core.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.BiomeEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Simple tool class which provides general used methods.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MemiTools {
    private final static Log log = LogFactory.getLog(MemiTools.class);

    public static final Map<Integer, String> biomeIconCSSMap = new HashMap<Integer, String>();

    public static final Map<Integer, String> biomeIconTitleMap = new HashMap<Integer, String>();

    public static void assignBiomeIconCSSClass(final BiomeEntity biomeEntity, final BiomeDAO biomeDAO) {
        if (biomeIconCSSMap.isEmpty()) {
            buildBiomeIconMap(biomeDAO);
        }
        Biome biome = biomeEntity.getBiome();
        if (biome != null) {
            biomeEntity.setBiomeIconCSSClass(getBiomeIconCSSClass(biome, biomeDAO));
        }
        if (biomeEntity.getBiomeIconCSSClass() == null) {
            // Set the default icon class
            biomeEntity.setBiomeIconCSSClass(MemiTools.biomeIconCSSMap.get(0));
        }
    }

    public static String getBiomeIconCSSClass(final Biome biome, final BiomeDAO biomeDAO) {
        if (biomeIconCSSMap.isEmpty()) {
            buildBiomeIconMap(biomeDAO);
        }
        // Look up CSS class
        int biomeId = biome.getBiomeId();
        if (MemiTools.biomeIconCSSMap.containsKey(biomeId)) {
            return MemiTools.biomeIconCSSMap.get(biomeId);
        }
        // no CSS class entry exists for the biome entry
        // traverse up the tree for all ancestors
        else {
            List<Biome> ancestors = biomeDAO.getAllAncestorsInDescOrder(biome);
            for (Biome ancestor : ancestors) {
                if (MemiTools.biomeIconCSSMap.containsKey(ancestor.getBiomeId())) {
                    return MemiTools.biomeIconCSSMap.get(ancestor.getBiomeId());
                }
            }
        }
        return null;
    }

    public static void assignBiomeIconTitle(final BiomeEntity biomeEntity, final BiomeDAO biomeDAO) {
        if (biomeIconTitleMap.isEmpty()) {
            buildBiomeIconMap(biomeDAO);
        }
        Biome biome = biomeEntity.getBiome();
        if (biome != null) {
            biomeEntity.setBiomeIconTitle(getBiomeIconTitle(biome, biomeDAO));
        }
        if (biomeEntity.getBiomeIconTitle() == null) {
            // Set the default icon class
            biomeEntity.setBiomeIconTitle(MemiTools.biomeIconTitleMap.get(0));
        }
    }

    public static String getBiomeIconTitle(final Biome biome, final BiomeDAO biomeDAO) {
        if (biomeIconTitleMap.isEmpty()) {
            buildBiomeIconMap(biomeDAO);
        }
            // Look up biome icon title
        int biomeId = biome.getBiomeId();
        if (MemiTools.biomeIconTitleMap.containsKey(biomeId)) {
            return MemiTools.biomeIconTitleMap.get(biomeId);
        }
        // no title entry exists for the biome entry
        // traverse up the tree for all ancestors
        else {
            List<Biome> ancestors = biomeDAO.getAllAncestorsInDescOrder(biome);
            for (Biome ancestor : ancestors) {
                if (MemiTools.biomeIconTitleMap.containsKey(ancestor.getBiomeId())) {
                    return MemiTools.biomeIconTitleMap.get(ancestor.getBiomeId());
                }
            }
        }
        return null;
    }

    private static void buildBiomeIconMap(final BiomeDAO biomeDAO) {
        // add default icon class
        MemiTools.biomeIconCSSMap.put(0, "default_b");
        MemiTools.biomeIconTitleMap.put(0, "Undefined");

        // add 1:1 mapping biomes
        List<Biome> biomes = biomeDAO.readByLineages("root:Environmental:Terrestrial:Soil", "root:Environmental:Air", "root:Engineered",
                "root:Environmental:Aquatic:Freshwater", "root:Host-associated:Human:Digestive system:Large intestine",
                "root:Environmental:Aquatic:Marine", "root:Engineered:Wastewater", "root:Host-associated:Human", "root:Host-associated",
                "root:Host-associated:Plants", "root:Environmental:Terrestrial:Volcanic", "root:Environmental:Aquatic:Thermal springs",
                "root:Environmental:Terrestrial:Rock-dwelling (subaerial biofilm)", "root:Environmental:Aquatic:Freshwater:Groundwater:Cave water");
        for (Biome biome : biomes) {
            String biomeName = biome.getBiomeName();
            if (biomeName.equalsIgnoreCase("Air")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "air_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Air");
            } else if (biomeName.equalsIgnoreCase("Engineered")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "engineered_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Engineered");
            } else if (biomeName.equalsIgnoreCase("Freshwater")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "freshwater_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Freshwater");
//            } else if (biomeName.equalsIgnoreCase("Host-associated")) {
//                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "non_human_host_b");
//                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Non-human host");
            } else if (biomeName.equalsIgnoreCase("Human")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "human_host_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Host-associated human");
            } else if (biomeName.equalsIgnoreCase("Large intestine")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "human_gut_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Human gut (Large intestine)");
            } else if (biomeName.equalsIgnoreCase("Marine")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "marine_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Marine");
            } else if (biomeName.equalsIgnoreCase("Soil")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "soil_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Soil");
            } else if (biomeName.equalsIgnoreCase("Wastewater")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "wastewater_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Wastewater");
            } else if (biomeName.equalsIgnoreCase("Plants")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "plant_host_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Host-associated plant");
            } else if (biomeName.equalsIgnoreCase("Volcanic")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "vulcano_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Volcanic");
            } else if (biomeName.equalsIgnoreCase("Thermal springs")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "hotspring_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Thermal springs");
            } else if (biomeName.equalsIgnoreCase("Cave water")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "cave_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Cave water");
            } else if (biomeName.equalsIgnoreCase("Rock-dwelling (subaerial biofilm)")) {
                MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "cave_b");
                MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Rock-dwelling (subaerial biofilm)");
            }
        }
        // add grassland biomes
        biomes = biomeDAO.readByLineages(uk.ac.ebi.interpro.metagenomics.memi.forms.Biome.GRASSLAND.getLineages());
        for (Biome biome : biomes) {
            MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "grassland_b");
            MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Grassland");
        }

        // add forest soil biomes
        biomes = biomeDAO.readByLineages(uk.ac.ebi.interpro.metagenomics.memi.forms.Biome.FOREST_SOIL.getLineages());
        for (Biome biome : biomes) {
            MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "forest_b");
            MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Forest soil");
        }
        //add Host-associated mammals biome
        Biome biome = biomeDAO.readByLineage("root:Host-associated:Mammals");
        MemiTools.biomeIconCSSMap.put(biome.getBiomeId(), "non_human_host_b");
        MemiTools.biomeIconTitleMap.put(biome.getBiomeId(), "Host-associated mammals");

    }


    public static Set<String> getSampleIds(Collection<Sample> samples) {
        log.info("Getting sample IDs from the specified sample list...");
        Set<String> result = new HashSet<String>();
        for (Sample sample : samples) {
            result.add(sample.getSampleId());
        }
        return result;
    }

    /**
     * Determines the generic type of specified collection. The items of the collection should have sub types,
     * that is why it returns the type of the first selected entry.
     */
    public static Class getTypeOfGenericSet(Collection<Sample> samples) {
        for (Sample sample : samples) {
            return sample.getClass();
        }
        return Object.class;
    }

    public static String createFileName(final String fileName) {
        log.info("Creating file name in the following format - FILENAME_yyyyMMdd.csv");
        final String suffix = ".csv";
        Format dateFormatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        final String infix = dateFormatter.format(date);
        return fileName + infix + suffix;
    }

    public static void addIndex(StringBuilder indexes, int index) {
        if (indexes.length() > 0) {
            indexes.append(",");
        }
        indexes.append(index);
    }

    private static List<String> readLines(File file) {
        final List<String> result = new ArrayList<String>();
        BufferedReader br = null;

        try {
            String currentLine;
            br = new BufferedReader(new FileReader(file));

            while ((currentLine = br.readLine()) != null) {
                result.add(currentLine);
            }
        } catch (IOException e) {
            log.warn("Could not read the following file: " + file.getAbsolutePath(), e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                log.warn("Could not close the buffered reader!", ex);
            }
        }
        return result;
    }

    /**
     * @return Returns a list of absolute file paths.
     */
    public static List<String> getListOfChunkedResultFiles(File file) {
        return readLines(file);
    }
    public static String formatLineage(String lineage) {
        return lineage
                .replaceAll("root:","")
                .replaceAll(":"," - ");
    }

}
