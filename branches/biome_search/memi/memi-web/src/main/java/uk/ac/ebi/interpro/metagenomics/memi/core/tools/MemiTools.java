package uk.ac.ebi.interpro.metagenomics.memi.core.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static final Map<Integer, String> biomeIconMap = new HashMap<Integer, String>();

    public static void assignBiomeIconCSSClass(final Study study, final BiomeDAO biomeDAO) {
        if (biomeIconMap.isEmpty()) {
            buildBiomeIconMap(biomeDAO);
        }
        Biome studyBiome = study.getBiome();
        if (studyBiome != null) {
            // Look up CSS class
            int biomeId = study.getBiome().getBiomeId();
            if (MemiTools.biomeIconMap.containsKey(biomeId)) {
                study.setBiomeIconCSSClass(MemiTools.biomeIconMap.get(biomeId));
            }
            // no CSS class entry exists for the study biome
            // traverse up the tree for all ancestors
            else {
                List<Biome> ancestors = biomeDAO.getAllAncestorsInDescOrder(study.getBiome());
                for (Biome ancestor : ancestors) {
                    if (MemiTools.biomeIconMap.containsKey(ancestor.getBiomeId())) {
                        study.setBiomeIconCSSClass(MemiTools.biomeIconMap.get(ancestor.getBiomeId()));
                        break;
                    }
                }
            }
        }
        if (study.getBiomeIconCSSClass() == null) {
            // Set the default icon class
            study.setBiomeIconCSSClass(MemiTools.biomeIconMap.get(0));
        }
    }

    private static void buildBiomeIconMap(final BiomeDAO biomeDAO) {
        // add default icon class
        MemiTools.biomeIconMap.put(0, "default_b");

        // add 1:1 mapping biomes
        List<Biome> biomes = biomeDAO.readByLineages("root:Environmental:Terrestrial:Soil", "root:Environmental:Air", "root:Engineered", "root:Environmental:Aquatic:Freshwater", "root:Host-associated:Human:Digestive system:Large intestine"
                , "root:Environmental:Aquatic:Marine", "root:Engineered:Wastewater", "root:Host-associated:Human", "root:Host-associated");
        for (Biome biome : biomes) {
            String biomeName = biome.getBiomeName();
            if (biomeName.equalsIgnoreCase("Air")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "air_b");
            } else if (biomeName.equalsIgnoreCase("Engineered")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "engineered_b");
            } else if (biomeName.equalsIgnoreCase("Freshwater")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "freshwater_b");
            } else if (biomeName.equalsIgnoreCase("Host-associated")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "non_human_host_b");
            } else if (biomeName.equalsIgnoreCase("Human")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "human_host_b");
            } else if (biomeName.equalsIgnoreCase("Large intestine")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "human_gut_b");
            } else if (biomeName.equalsIgnoreCase("Marine")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "marine_b");
            } else if (biomeName.equalsIgnoreCase("Soil")) {
                MemiTools.biomeIconMap.put(biome.getBiomeId(), "soil_b");
            } else if (biomeName.equalsIgnoreCase("wastewater_b")) {

            }
        }
        // add grassland biomes
        biomes = biomeDAO.readByLineages("root:Environmental:Terrestrial:Soil:Grasslands", "root:Environmental:Terrestrial:Soil:Sand:Grasslands",
                "root:Environmental:Terrestrial:Soil:Loam:Grasslands", "root:Environmental:Terrestrial:Soil:Clay:Grasslands");
        for (Biome biome : biomes) {
            MemiTools.biomeIconMap.put(biome.getBiomeId(), "grassland_b");
        }

        // add forest soil biomes
        biomes = biomeDAO.readByLineages("root:Environmental:Terrestrial:Soil:Loam:Forest Soil", "root:Environmental:Terrestrial:Soil:Sand:Forest soil",
                "root:Environmental:Terrestrial:Soil:Forest Soil");
        for (Biome biome : biomes) {
            MemiTools.biomeIconMap.put(biome.getBiomeId(), "forest_b");
        }
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

    public static List<String> getArchivedSeqs(EmgLogFileInfoDAO fileInfoDAO, Sample sample) {
        List<String> result = new ArrayList<String>();
        if (fileInfoDAO != null && sample != null) {
            result = fileInfoDAO.getSraIDs(sample.getId());
        }
        return result;
    }

    /**
     * Source code from http://www.exampledepot.com/egs/java.io/file2bytearray.html.
     *
     * @param file
     * @return Byte array representation.
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }


    public static void addIndex(StringBuilder indexes, int index) {
        if (indexes.length() > 0) {
            indexes.append(",");
        }
        indexes.append(index);
    }
}
