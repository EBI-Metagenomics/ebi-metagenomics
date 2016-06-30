-- MySQL create and insert statements for blacklisting studies

-- Author: Maxim Scheremetjew, EMBL-EBI, InterPro

-- Create table statements
CREATE TABLE `CV_STUDY_ISSUE` (
    `ISSUE_ID` TINYINT,
    `ISSUE_TYPE` VARCHAR(50) NOT NULL COMMENT 'Represents the name of the issue.',
    `DESCRIPTION` TEXT NOT NULL,
    PRIMARY KEY (`ISSUE_ID`),
);

CREATE TABLE `BLACKLISTED_STUDY` (
    `EXT_STUDY_ID` VARCHAR(18) NOT NULL COMMENT 'This is the external (non-EMG) ID for the study, i.e. its the SRA study ID which always starts ERP or SRP (or DRP)',
    `ISSUE_ID` TINYINT NOT NULL,
    PRIMARY KEY (`EXT_STUDY_ID`),
    FOREIGN KEY (`ISSUE_ID`)
        REFERENCES `CV_STUDY_ISSUE` (`ISSUE_ID`)
);

-- Insert statements
INSERT INTO CV_STUDY_ISSUE (issue_id,issue_type,description) VALUES (1,'RAW_DATA_DOWNLOAD_ERROR','Unable to download the raw data from the FTP server.');
INSERT INTO CV_STUDY_ISSUE (issue_id,issue_type,description) VALUES (2,'RAW_DATA_FILE_CORRUPT','The downloaded raw sequence files are corrupt.');
INSERT INTO CV_STUDY_ISSUE (issue_id,issue_type,description) VALUES (3,'DATA_PREPARATION_ERROR','Merging the pair-end seqences failed.');
INSERT INTO CV_STUDY_ISSUE (issue_id,issue_type,description) VALUES (4,'ANALYSIS_QC_NOT_PASSED','None of the raw sequence files passed QC.');
INSERT INTO CV_STUDY_ISSUE (issue_id,issue_type,description) VALUES (5,'DATABASE_UPLOAD_FAILED','The database upload of study and sample meta data failed.');

INSERT INTO BLACKLISTED_STUDY (EXT_STUDY_ID,ISSUE_ID) VALUES ('ERP0001',1);