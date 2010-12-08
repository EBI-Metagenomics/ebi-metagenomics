package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a first version of a sample (just a simple version).
 * TODO: Ask for a detailed specification of a sample object
 * TODO: Implement the JUni test for this class
 * TODO: Add Hibernate annotations
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class Sample implements Serializable {

    private long sampleId;

    private String sampleName;

    private Date collectionDate;

    private Map<String, Object> samplePropertyMap;

    public Sample() {
        this.collectionDate = new Date();
        samplePropertyMap = new HashMap<String, Object>();
        //geographic location (country and/or sea,region) 
        samplePropertyMap.put("geo_loc_name", "country");
        samplePropertyMap.put("geo_area", "Hinxton");
        //geographic location (latitude and longitude)
        samplePropertyMap.put("lat_lon", "52.086864,0.181189");
        samplePropertyMap.put("biome", "N/A");
        samplePropertyMap.put("feature", "N/A");
        samplePropertyMap.put("material", "N/A");
//        altitude - hight above sea level for air samples
        samplePropertyMap.put("alt", "N/A");
        samplePropertyMap.put("depth", "vertical distance beneath surface of object");
        samplePropertyMap.put("elev", "elevation - hight above/below sea level for land samples");
        samplePropertyMap.put("host_common_name", "N/A");

        samplePropertyMap.put("host_taxid", "a valid NCBI taxonomic node ID");
        samplePropertyMap.put("host_subject_id", "N/A");
        samplePropertyMap.put("samp_store_temp", "sample storage temperature");
        samplePropertyMap.put("samp_store_dur", "sample storage duration");
        samplePropertyMap.put("samp_store_loc", "sample storage location");
        samplePropertyMap.put("ph", "1014 mb");
        samplePropertyMap.put("Humidity", "75 %");
        samplePropertyMap.put("Wind", "14 mph");
        samplePropertyMap.put("Temperature", "32C");
    }

    public Sample(long sampleId, String sampleName) {
        this();
        this.sampleId = sampleId;
        this.sampleName = sampleName;
    }


    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getFormattedCollectionDate() {
        Format formatter = new SimpleDateFormat("dd.MMM.yy");
        return formatter.format(collectionDate);
    }

    public List<String> getSampleProperties() {
        List<String> result = new ArrayList<String>();
        for (Object value : samplePropertyMap.values()) {
            result.add((String) value);
        }
        return result;
    }

    public Map<String, Object> getPropertyMap() {
        return samplePropertyMap;
    }
}