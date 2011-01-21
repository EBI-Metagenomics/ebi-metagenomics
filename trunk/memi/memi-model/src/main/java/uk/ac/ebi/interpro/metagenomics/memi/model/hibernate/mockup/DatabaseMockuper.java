package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.mockup;

import au.com.bytecode.opencsv.CSVReader;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class DatabaseMockuper {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //create publications
        List<Publication> pubs = new ArrayList<Publication>();
        Publication p1 = new Publication();
        p1.setViewType(Publication.ViewType.DOI);
        p1.setDoi("10.1007/s12155-010-9089-z");
        pubs.add(p1);

        Publication p2 = new Publication();
        p2.setViewType(Publication.ViewType.PMID);
        p2.setPubMedId(19043404);
        pubs.add(p2);

        Publication p3 = new Publication();
        p3.setViewType(Publication.ViewType.DOI);
        p3.setAuthors("Folker Meyer, Lynn Schriml, Ian R Joint, Martin Mühling, Dawn Field, Jack A. Gilbert");
        p3.setPubTitle("Metagenomes and metatranscriptomes from the L4 long-term coastal monitoring station in the Western English Channel");
        p3.setYear(2010);
        p3.setVolume("Vol 3, No 2");
        p3.setDoi("10.4056/sigs.1202536");
        pubs.add(p3);

        Publication p4 = new Publication();
        p4.setViewType(Publication.ViewType.DOI);
        p4.setPubTitle("The seasonal structure of microbial communities in the Western English Channel");
        p4.setDoi("10.1111/j.1462-2920.2009.02017.x");
        p4.setAuthors("Jack A. Gilbert, Dawn Field, Paul Swift, Lindsay Newbold, Anna Oliver, " +
                "Tim Smyth1, Paul J. Somerfield1, Sue Huse, Ian Joint");
        p4.setYear(2009);
        pubs.add(p4);

        Publication p5 = new Publication();
        p5.setViewType(Publication.ViewType.DOI);
        p5.setPubTitle("Detection of Large Numbers of Novel Sequences in the Metatranscriptomes of Complex Marine Microbial Communities");
        p5.setDoi("10.1371/journal.pone.0003042");
        p5.setAuthors("Jack A. Gilbert");
        p5.setYear(2008);
        pubs.add(p5);

        Publication p6 = new Publication();
        p6.setViewType(Publication.ViewType.URL);
        p6.setPubTitle("The Visualization and Analysis of Microbial Population Structures");
        p6.setUrl("http://vamps.mbl.edu");
        pubs.add(p6);

        Publication p7 = new Publication();
        p7.setViewType(Publication.ViewType.URL);
        p7.setPubTitle("INTERNATIONAL CENSUS OF MARINE MICROBES");
        p7.setUrl("http://icomm.mbl.edu");
        pubs.add(p7);

        //persist publications
        for (Publication pub : pubs) {
            createObject(pub);
        }

        List<Study> studies = parseStudies();
        for (Study study : studies) {
            String studyId = study.getStudyId();
            if (studyId.equals("SRP001743")) {
                study.addPublication(p1);
            } else if (studyId.equals("ERP000118")) {
                study.addPublication(p3);
            } else if (studyId.equals("SRP000319")) {
                study.addPublication(p2);
            }
            createObject(study);
        }

        Map<String, Set<Sample>> sampleMap = parseSamples();
        for (String studyId : sampleMap.keySet()) {
            for (Sample sample : sampleMap.get(studyId)) {
                createObject(sample);
            }
        }
        for (Study study : studies) {
            Set<Sample> samples = sampleMap.get(study.getStudyId());
            study.setSamples(samples);
            createObject(study);
        }
    }

    private static Map<String, Set<Sample>> parseSamples() {
        Map<String, Set<Sample>> result = new HashMap<String, Set<Sample>>();
        try {
            CSVReader reader = new CSVReader(new FileReader("EMG_SAMPLE.csv"), ',');
            if (reader != null) {
                List<String[]> rows = reader.readAll();
                rows = rows.subList(1, rows.size());

                for (String[] row : rows) {
                    Sample s;
                    String type = row[3];
                    String studyId = row[1];
                    if (type.startsWith("Environmental")) {
                        s = new EnvironmentSample();
                        ((EnvironmentSample) s).setLatLon(row[5]);
                    } else {
                        s = new HostSample();
                        ((HostSample) s).setHostSex(row[37]);
                        ((HostSample) s).setHostTaxonId(Integer.parseInt(row[13]));
                    }
                    s.setSampleId(row[0]);
                    s.setSampleTitle(row[2]);
                    s.setSampleDescription(row[53]);
                    s.setSampleClassification(row[3]);
                    s.setGeoLocName(row[4]);
                    s.setHabitatType(row[9]);
                    Set<Sample> samples = result.get(studyId);
                    if (samples == null) {
                        samples = new HashSet<Sample>();
                    }
                    samples.add(s);
                    result.put(studyId, samples);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    private static List<Study> parseStudies() {
        List<Study> result = new ArrayList<Study>();
        try {
            CSVReader reader = new CSVReader(new FileReader("EMG_STUDY.csv"), ',');
            if (reader != null) {
                List<String[]> rows = reader.readAll();
                rows = rows.subList(1, rows.size());

                for (String[] row : rows) {
                    Study s = new Study();
                    s.setStudyId(row[0]);
                    s.setNcbiProjectId(Integer.parseInt(row[1]));
                    s.setStudyName(row[2]);
                    s.setCentreName(row[5]);
                    s.setExperimentalFactor(row[8]);
                    s.setStudyAbstract(row[14]);
                    result.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    private static void listStudies() {
        Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
            tx = session.beginTransaction();
            List studies = session.createCriteria(Study.class).list();
//            List honeys = new ArrayList();
            for (Iterator iter = studies.iterator(); iter.hasNext();) {
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

}