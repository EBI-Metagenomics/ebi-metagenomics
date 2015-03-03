package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.mockup;

import au.com.bytecode.opencsv.CSVReader;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class DatabaseMockuper {
    public static void main(String[] args) {
        //Instantiate date creator
        DateCreator dateCreator = new DateCreator();
        //create publications
        List<Publication> pubs = new ArrayList<Publication>();

        Publication p1 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p1.setAuthors("Kristen M. DeAngelis, John M. Gladden, Martin Allgaier");
        p1.setYear(2010);
        p1.setPubTitle("Strategies for Enhancing the Effectiveness of Metagenomic-based Enzyme Discovery in Lignocellulolytic Microbial Communities");
        p1.setVolume("BioEnergy Research, Volume 3, Number 2, 146-158");
        p1.setDoi("10.1007/s12155-010-9089-z");
        pubs.add(p1);

        Publication p2 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p2.setAuthors("Turnbaugh PJ, Hamady M, Yatsunenko T");
        p2.setYear(2009);
        p2.setPubTitle("A core gut microbiome in obese and lean twins.");
        p2.setVolume("Nature. 2009 Jan 22;457(7228):480-4. Epub 2008 Nov 30.");
        p2.setPubMedId(19043404);
        p2.setDoi("10.1038/nature07540");
        pubs.add(p2);

        Publication p3 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p3.setAuthors("Folker Meyer, Lynn Schriml, Ian R Joint");
        p3.setYear(2010);
        p3.setPubTitle("Metagenomes and metatranscriptomes from the L4 long-term coastal monitoring station in the Western English Channel");
        p3.setVolume("Standards in Genomic Sciences, Vol 3, No 2 (2010)");
        p3.setDoi("10.4056/sigs.1202536");
        pubs.add(p3);

        Publication p4 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p4.setPubTitle("The seasonal structure of microbial communities in the Western English Channel");
        p4.setDoi("10.1111/j.1462-2920.2009.02017.x");
        p4.setAuthors("Jack A. Gilbert, Dawn Field, Paul Swift");
        p4.setYear(2009);
        pubs.add(p4);

        Publication p5 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p5.setPubTitle("Detection of Large Numbers of Novel Sequences in the Metatranscriptomes of Complex Marine Microbial Communities");
        p5.setDoi("10.1371/journal.pone.0003042");
        p5.setAuthors("Jack A. Gilbert");
        p5.setYear(2008);
        pubs.add(p5);

        Publication p6 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p6.setPubTitle("The Visualization and Analysis of Microbial Population Structures");
        p6.setUrl("http://vamps.mbl.edu");
        pubs.add(p6);

        Publication p7 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p7.setPubTitle("International census of marine microbes");
        p7.setUrl("http://icomm.mbl.edu");
        pubs.add(p7);

        Publication p8 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2010, "title1", "authors1", "doi1");
        p8.setAuthors("Carola Simon, Arnim Wiezer, Axel W. Strittmatter");
        p8.setYear(2009);
        p8.setPubTitle("Phylogenetic Diversity and Metabolic Potential Revealed in a Glacier Ice Metagenome");
        p8.setVolume("Nature 457, 480-484");
        p8.setDoi("10.1128/AEM.00946-09");
        pubs.add(p8);

        //persist publications
        for (Publication pub : pubs) {
            createObject(pub);
        }

        List<Study> publicStudies = parseStudies("EMG_STUDY.csv");
        for (Study publicStudy : publicStudies) {
            String studyId = publicStudy.getStudyId();
            if (studyId.equals("SRP001743")) {
                publicStudy.addPublication(p1);
            } else if (studyId.equals("ERP000118")) {
                publicStudy.addPublication(p3);
            } else if (studyId.equals("SRP000319")) {
                publicStudy.addPublication(p2);
            } else if (studyId.equals("SRP000240")) {
                publicStudy.addPublication(p8);
            } else if (studyId.equals("study_placeholder1")) {
                publicStudy.setSubmissionAccountId("Webin-10");
            }
            publicStudy.setLastMetadataReceived(dateCreator.getNextDate());
            createObject(publicStudy);
        }

        mockupPrivateSamples(dateCreator);
        mockupPrivateStudies(dateCreator);

        Map<String, Set<Sample>> sampleMap = parseSamples("EMG_SAMPLE.csv");
        for (String studyId : sampleMap.keySet()) {
            for (Sample sample : sampleMap.get(studyId)) {
                String sampleId = sample.getSampleId();
                if (sampleId.equals("Sample_place_holder1")) {
                    sample.setSubmissionAccountId("Webin-10");
                }
                createObject(sample);
            }
        }

        for (Study study : publicStudies) {
            Set<Sample> samples = sampleMap.get(study.getStudyId());
            for (Sample sample : samples) {
                sample.setStudy(study);
                if (study.getStudyId().equals("ERP000118")) {
                    sample.addPublication(p6);
                    sample.addPublication(p7);
                }
                createObject(sample);
            }
        }
        SessionFactoryUtil.close();
    }

    private static void mockupPrivateStudies(DateCreator dateCreator) {
        List<Study> privateStudies = parseStudies("PRIVATE_STUDIES.csv");
        for (Study study : privateStudies) {
            study.setSubmissionAccountId("Webin-10");
            study.setStudyStatus(getRandomStudyStatus());
            study.setLastMetadataReceived(dateCreator.getNextDate());
            if (study.getStudyId().equals("SRP001111")) {
                study.addSample(getSample("SRS009999"));
                study.setPublic(true);
            }
            createObject(study);
        }
    }

    private static void mockupPrivateSamples(DateCreator dateCreator) {
        Map<String, Set<Sample>> sampleMap = parseSamples("PRIVATE_SAMPLES.csv");
        for (String studyId : sampleMap.keySet()) {
            for (Sample sample : sampleMap.get(studyId)) {
                sample.setMetadataReceived(dateCreator.getNextDate());
                sample.setPublic(false);
                sample.setSubmissionAccountId("Webin-10");
                createObject(sample);
            }
        }
    }

    /**
     * Returns a map between study id and samples.
     */
    private static Map<String, Set<Sample>> parseSamples(String fileName) {
        Map<String, Set<Sample>> result = new HashMap<String, Set<Sample>>();
        try {
            URL url = DatabaseMockuper.class.getResource(fileName);
            URI uri = new URI(url.toString());

            if (uri != null) {

                CSVReader reader = new CSVReader(new FileReader(new File(uri)), ',');
                if (reader != null) {
                    List<String[]> rows = reader.readAll();
                    rows = rows.subList(1, rows.size());

                    for (String[] row : rows) {
                        Sample s;
                        String type = row[3];
                        if (type.startsWith("Environmental")) {
                            s = new EnvironmentSample();
                            ((EnvironmentSample) s).setLatLon(row[5]);
                            ((EnvironmentSample) s).setEnvironmentalBiome(row[11]);
                            ((EnvironmentSample) s).setEnvironmentalFeature(row[12]);
                            ((EnvironmentSample) s).setEnvironmentalMaterial(row[13]);

                        } else {
                            s = new HostSample();
                            ((HostSample) s).setHostSex((row[41].equals("female") ? HostSample.HostSex.FEMALE : HostSample.HostSex.MALE));
                            String taxonId = row[17];
                            if (taxonId != null && taxonId.trim().length() > 0) {
                                ((HostSample) s).setHostTaxonomyId(new Integer(Integer.parseInt(taxonId)));
                            }
                            ((HostSample) s).setPhenotype(row[47]);
                        }
                        s.setSampleId(row[0]);
                        String studyId = row[1];
//                        s.setSampleTitle(row[2]);
                        s.setSampleClassification(row[3]);
                        s.setGeoLocName(row[4]);

                        //set dates using different date formatters
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String collectionDate = row[6];
                            if (collectionDate != null && collectionDate.startsWith("200")) {
                                s.setCollectionDate(df.parse(collectionDate));
                            }

                            df = new SimpleDateFormat("dd/MM/yyyy");
                            String metaDate = row[7];
                            if (metaDate != null && metaDate.trim().length() > 0) {
                                s.setMetadataReceived(df.parse(metaDate));
                            }

                            String seqReceivedDate = row[8];
                            if (seqReceivedDate != null && seqReceivedDate.trim().length() > 0) {
                                s.setSequenceDataReceived(df.parse(seqReceivedDate));
                            }

                            String seqArchivedDate = row[9];
                            if (seqArchivedDate != null && seqArchivedDate.trim().length() > 0) {
                                s.setSequenceDataArchived(df.parse(seqArchivedDate));
                            }

                            String completeDate = row[10];
                            if (completeDate != null && completeDate.trim().length() > 0) {
                                s.setAnalysisCompleted(df.parse(completeDate));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        s.setMiscellaneous(row[56]);
                        s.setSampleDescription(row[57]);
                        s.setPublic((row[58].equals("TRUE") ? true : false));
                        Set<Sample> samples = result.get(studyId);
                        if (samples == null) {
                            samples = new HashSet<Sample>();
                        }
                        samples.add(s);
                        result.put(studyId, samples);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static List<Study> parseStudies(String fileName) {
        List<Study> result = new ArrayList<Study>();
        try {
            URL url = DatabaseMockuper.class.getResource(fileName);
            URI uri = new URI(url.toString());

            if (uri != null) {

                CSVReader reader = new CSVReader(new FileReader(new File(uri)), ',');
                if (reader != null) {
                    List<String[]> rows = reader.readAll();
                    rows = rows.subList(1, rows.size());

                    for (String[] row : rows) {
                        Study s = new Study();
                        s.setStudyId(row[0]);
                        s.setNcbiProjectId(Long.parseLong(row[1]));
                        s.setStudyName(row[2]);
                        s.setCentreName(row[5]);
//                        setStudyType(row[6], s);
                        s.setExperimentalFactor(row[8]);
                        //set public release date                        
                        try {
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String releaseDate = row[11];
                            if (releaseDate != null && releaseDate.trim().length() > 0) {
                                s.setPublicReleaseDate(df.parse(releaseDate));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        s.setStudyAbstract(row[14]);
                        s.setPublic((row[15].equals("TRUE") ? true : false));
                        s.setStudyStatus(getRandomStudyStatus());

                        result.add(s);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

//    private static void setStudyType(String content, Study s) {
//        if (content.startsWith("Environ")) {
//            s.setStudyType(Study.StudyType.ENVIRONMENTAL);
//        } else if (content.startsWith("Host")) {
//            s.setStudyType(Study.StudyType.HOST_ASSOCIATED);
//        } else {
//            s.setStudyType(Study.StudyType.UNDEFINED);
//        }
//    }

    private static Study.StudyStatus getRandomStudyStatus() {
        int num = new Random().nextInt(2);
        switch (num) {
            case 1:
                return Study.StudyStatus.FINISHED;
            case 2:
                return Study.StudyStatus.IN_PROGRESS;
            default:
                return Study.StudyStatus.UNDEFINED;
        }
    }

    private static void listStudies() {
        Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
            tx = session.beginTransaction();
            List studies = session.createCriteria(Study.class).list();
//            List honeys = new ArrayList();
            for (Iterator iter = studies.iterator(); iter.hasNext(); ) {
                Study element = (Study) iter.next();
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                try {
// Second try catch as the rollback could fail as well
                    tx.rollback();
                } catch (HibernateException e1) {
                }
// throw again the first exception
                throw e;
            }


        }
    }


    private static void createObject(Object obj) {
        Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                try {
// Second try catch as the rollback could fail as well
                    tx.rollback();
                } catch (HibernateException e1) {
                    e1.printStackTrace();
                }
// throw again the first exception
                throw e;
            }
        }
    }

    private static Sample getSample(String sampleId) {
        Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
            tx = session.beginTransaction();
            Criteria crit = session.createCriteria(Sample.class);
            crit.add(Restrictions.eq("sampleId", sampleId));
            List<Sample> samples = crit.list();
            if (samples.size() > 0) {
                return samples.get(0);
            }
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (HibernateException e1) {
                    e1.printStackTrace();
                }
                throw e;
            }
        }
        return null;
    }

    static class DateCreator {
        private int counter;

        private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        private String[] dates = {"01/01/2012", "02/02/2010", "03/03/2010", "04/04/2010",
                "01/01/2009", "02/02/2011", "03/03/2009", "04/04/2009"};

        public DateCreator() {
            this.counter = 0;
        }

        public Date getNextDate() {
            if (counter >= dates.length) {
                //reset counter
                counter = 0;
            }
            Date result = null;
            try {
                result = df.parse(dates[counter]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            counter++;
            return result;
        }
    }
}