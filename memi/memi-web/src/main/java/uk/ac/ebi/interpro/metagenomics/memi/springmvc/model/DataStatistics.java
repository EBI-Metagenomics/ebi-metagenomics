package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.RunStatisticsVO;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.SampleStatisticsVO;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.StudyStatisticsVO;

/**
 * Created by maxim on 07/07/16.
 */
public class DataStatistics {

    private StudyStatisticsVO studyStatistics;

    private SampleStatisticsVO sampleStatistics;

    private RunStatisticsVO runStatistics;

    public DataStatistics(StudyStatisticsVO studyStatistics, SampleStatisticsVO sampleStatistics, RunStatisticsVO runStatistics) {
        this.studyStatistics = studyStatistics;
        this.sampleStatistics = sampleStatistics;
        this.runStatistics = runStatistics;
    }

    public StudyStatisticsVO getStudyStatistics() {
        return studyStatistics;
    }

    public void setStudyStatistics(StudyStatisticsVO studyStatistics) {
        this.studyStatistics = studyStatistics;
    }

    public RunStatisticsVO getRunStatistics() {
        return runStatistics;
    }

    public void setRunStatistics(RunStatisticsVO runStatistics) {
        this.runStatistics = runStatistics;
    }

    public SampleStatisticsVO getSampleStatistics() {
        return sampleStatistics;
    }

    public void setSampleStatistics(SampleStatisticsVO sampleStatistics) {
        this.sampleStatistics = sampleStatistics;
    }
}