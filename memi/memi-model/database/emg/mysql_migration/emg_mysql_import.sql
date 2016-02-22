-- EMG schema extension to support monitoring of pipeline jobs and pipeline versioning

-- Author: Maxim Scheremetjew, EMBL-EBI, InterPro

-- Drop table statements
-- drop table emg.ANALYSIS_JOB;
-- drop table emg.ANALYSIS_STATUS;
-- drop table emg.EXPERIMENT_TYPE;
-- drop table emg.PIPELINE_RELEASE_TOOL;
-- drop table emg.PIPELINE_RELEASE;
-- drop table emg.PIPELINE_TOOL;
-- -- 
-- drop table SAMPLE_PUBLICATION;
-- drop table STUDY_PUBLICATION;
-- drop table PUBLICATION;
-- --  
-- drop table emg.SAMPLE_ANN;
-- drop table emg.sample;
-- drop table emg.GSC_CV_CV; 
-- drop table emg.VARIABLE_NAMES;
-- -- 
-- drop table emg.STUDY;
-- drop table emg.BIOME_HIERARCHY_TREE;

-- Create table statements 
CREATE TABLE `PIPELINE_RELEASE` (
    `PIPELINE_ID` TINYINT AUTO_INCREMENT,
    `DESCRIPTION` TEXT,
    `CHANGES` TEXT NOT NULL,
    `RELEASE_VERSION` VARCHAR(20) NOT NULL,
    `RELEASE_DATE` DATE NOT NULL,
    PRIMARY KEY (`PIPELINE_ID`)
);

 CREATE TABLE `PIPELINE_TOOL` (
    `TOOL_ID` SMALLINT AUTO_INCREMENT,
    `TOOL_NAME` VARCHAR(30) NOT NULL,
    `DESCRIPTION` TEXT NOT NULL,
    `WEB_LINK` VARCHAR(500),
    `VERSION` VARCHAR(30) NOT NULL,
    `EXE_COMMAND` VARCHAR(500) NOT NULL,
    `INSTALLATION_DIR` VARCHAR(200),
    `CONFIGURATION_FILE` LONGTEXT,
    `NOTES` TEXT,
    PRIMARY KEY (`TOOL_ID`)
);

CREATE TABLE `PIPELINE_RELEASE_TOOL` (
    `PIPELINE_ID` TINYINT NOT NULL,
    `TOOL_ID` SMALLINT NOT NULL,
    `TOOL_GROUP_ID` DECIMAL(6 , 3 ) NOT NULL,
    `HOW_TOOL_USED_DESC` TEXT NOT NULL COMMENT 'Text description on how this version of the tool is used in this version of the pipeline.',
    PRIMARY KEY (`PIPELINE_ID` , `TOOL_ID`),
    FOREIGN KEY (`PIPELINE_ID`)
        REFERENCES `PIPELINE_RELEASE` (`PIPELINE_ID`),
    FOREIGN KEY (`TOOL_ID`)
        REFERENCES `PIPELINE_TOOL` (`TOOL_ID`)
);

CREATE TABLE `ANALYSIS_STATUS` (
    `ANALYSIS_STATUS_ID` TINYINT AUTO_INCREMENT,
    `ANALYSIS_STATUS` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`ANALYSIS_STATUS_ID`)
);

CREATE TABLE `BIOME_HIERARCHY_TREE` (
    `BIOME_ID` SMALLINT,
    `BIOME_NAME` VARCHAR(60) NOT NULL,
    `LFT` SMALLINT NOT NULL,
    `RGT` SMALLINT NOT NULL,
    `DEPTH` TINYINT NOT NULL,
    `LINEAGE` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`BIOME_ID`)
);

-- Changes:
-- Remove column STUDY_LINKOUT (deprecated)
-- Remove column SUBMITTER_ID (deprecated)
-- Remove column STUDY_TYPE (deprecated)
CREATE TABLE `STUDY` (
    `STUDY_ID` INT NOT NULL AUTO_INCREMENT,
    `CENTRE_NAME` VARCHAR(255) COMMENT 'The center_name used by SRA, it must be in the SRA schema, table CV_CENTER_NAME, which also should contain the description of that acronym (but doesn\'t always!)',
    `EXPERIMENTAL_FACTOR` VARCHAR(255) COMMENT 'This is metadata about the study, its to give an easy look up for time series studies or things where the study wass designed to test a particular variable, e.g. time, depth, disease etc...',
    `IS_PUBLIC` TINYINT COMMENT '1 for public, 0 for private (As of Aug2012 this is set manually in both Production and Web databases)',
    `NCBI_PROJECT_ID` INT COMMENT 'This is for an X-ref to the NCBI projects ID, which is now BioProjects, not used very much and should be moved to an xref table',
    `PUBLIC_RELEASE_DATE` DATE COMMENT 'The date originally specified by the submitter of when their data should be released to public, can be changed by submitter in SRA and we should sync with SRA.',
    `STUDY_ABSTRACT` LONGTEXT COMMENT 'The submitter provided description of the project/study.',
    `EXT_STUDY_ID` VARCHAR(18) NOT NULL COMMENT 'This is the external (non-EMG) ID for the study, i.e. its the SRA study ID which always starts ERP or SRP (or DRP)',
    `STUDY_NAME` VARCHAR(255) COMMENT 'The human readable name of the study, as presented on EMG web pages, can be submitter provided or curated from details provided in submission',
    `STUDY_STATUS` VARCHAR(30) COMMENT 'not used, should be deprecated',
    `DATA_ORIGINATION` VARCHAR(20) COMMENT 'Where did the data come from, this could be HARVESTED for stuff taken from SRA, or SUBMITTED for stuff that is brokered to SRA through EMG',
    `AUTHOR_EMAIL` VARCHAR(100) COMMENT 'Email address of contact person for study, WILL be shown publicly on Study page',
    `AUTHOR_NAME` VARCHAR(100) COMMENT 'Name of contact person for study, WILL be shown publicly on Study page',
    `LAST_UPDATE` DATETIME DEFAULT NOW() NOT NULL COMMENT 'The date any update was made to the row, this is auto-updated in PROD by a trigger, but not in any others (e.g. web, test or dev)',
    `SUBMISSION_ACCOUNT_ID` VARCHAR(15) COMMENT 'Defines which users do have permission to access that sample/study. It is a reference to ERAPRO\'s submission_account table',
    `BIOME_ID` SMALLINT COMMENT 'Links to an entry in the biome hierarchy table, which is a controlled vocabulary.',
    `RESULT_DIRECTORY` VARCHAR(100) COMMENT 'Path to the results directory for this study',
    `FIRST_CREATED` DATETIME DEFAULT NOW() NOT NULL COMMENT 'The date when the study has been created in EMG for the first time. Usually happens when a new study is loaded from ENA into EMG using the webuploader tool.',
    PRIMARY KEY (`STUDY_ID`),
    FOREIGN KEY (`BIOME_ID`)
        REFERENCES `BIOME_HIERARCHY_TREE` (`BIOME_ID`)
);

-- Changes:
-- Remove column DTYPE (deprecated)
-- Remove column HOST_SEX (deprecated)
-- Remove column SAMPLE_CLASSIFICATION (deprecated)
-- Remove column MISC (never used)
-- Remove column PHENOTYPE (deprecated)
-- Remove column LAT_LON (deprecated)
-- Remove column SUBMITTER_ID (deprecated)
CREATE TABLE `SAMPLE` (
    `SAMPLE_ID` INT NOT NULL AUTO_INCREMENT COMMENT 'The unique identifier assigned by a trigger on the database that assignes the next number in the series. This is effectively an EMG accession number, but we have no intention of ever discolsing this to external users.',
    `ANALYSIS_COMPLETED` DATE COMMENT 'This is the date that analysis was (last) completed on. It is the trigger used in the current web-app to display the analysis results page, if this is null there will never be the button on the sample page to be able to show the analsyis results.',
    `COLLECTION_DATE` DATE COMMENT 'The date the sample was collected, this value is now also present in the sample_ann table, and so can be deleted from this table AFTER the web-app has been changed to get the date from the sample_ann table instead.',
    `GEO_LOC_NAME` VARCHAR(255) COMMENT 'The (country) name of the location the sample was collected from, this value is now also present in the sample_ann table, and so can be deleted from this table AFTER the web-app has been changed to get the data from the sample_ann table instead.',
    `IS_PUBLIC` TINYINT,
    `METADATA_RECEIVED` DATETIME DEFAULT NOW(),
    `SAMPLE_DESC` LONGTEXT,
    `SEQUENCEDATA_ARCHIVED` DATETIME,
    `SEQUENCEDATA_RECEIVED` DATETIME,
    `ENVIRONMENT_BIOME` VARCHAR(255),
    `ENVIRONMENT_FEATURE` VARCHAR(255),
    `ENVIRONMENT_MATERIAL` VARCHAR(255),
    `STUDY_ID` INT,
    `SAMPLE_NAME` VARCHAR(255),
    `SAMPLE_ALIAS` VARCHAR(255),
    `HOST_TAX_ID` BIGINT,
    `EXT_SAMPLE_ID` VARCHAR(15),
    `SPECIES` VARCHAR(255),
    `LATITUDE` DECIMAL(7 , 4 ),
    `LONGITUDE` DECIMAL(7 , 4 ),
    `LAST_UPDATE` DATETIME DEFAULT NOW() NOT NULL,
    `SUBMISSION_ACCOUNT_ID` VARCHAR(15) COMMENT 'Defines which users do have permission to access that sample/study. It is a reference to ERAPRO\'s submission_account table',
    `BIOME_ID` SMALLINT,
    PRIMARY KEY (`SAMPLE_ID`),
    FOREIGN KEY (`STUDY_ID`)
        REFERENCES `STUDY` (`STUDY_ID`),
    FOREIGN KEY (`BIOME_ID`)
        REFERENCES `BIOME_HIERARCHY_TREE` (`BIOME_ID`)
);

CREATE TABLE `VARIABLE_NAMES` (
    `VAR_ID` SMALLINT NOT NULL AUTO_INCREMENT COMMENT ' variable identifier, unique sequenctial number auto generated',
    `VAR_NAME` VARCHAR(50) NOT NULL COMMENT 'Unique human readable name as given by GSC (or other authority)',
    `DEFINITION` LONGTEXT COMMENT 'Definition of variable, as given by GSC (or other authority)',
    `VALUE_SYNTAX` VARCHAR(250) COMMENT 'how the GSC (or other authority) has defined the value for the term should be given',
    `ALIAS` VARCHAR(30) COMMENT 'Short name, or INSDC name given by GSC, should be less than 20char and contain no spaces',
    `AUTHORITY` VARCHAR(30) COMMENT 'person or organisation that created/defined the variable (usualy GSC)',
    `SRA_XML_ATTRIBUTE` VARCHAR(30) COMMENT 'Where the ATTRIBUTE should be in the SRA XML schema, (NB currently (Aug2012) almost everything goes in SRA.SAMPLE which is technically wrong!)',
    `REQUIRED_FOR_MIMARKS_COMPLIANC` VARCHAR(1) COMMENT 'If a value for the variable is required for GSC MIMARKS compliance (as of Aug2012)',
    `REQUIRED_FOR_MIMS_COMPLIANCE` VARCHAR(1) COMMENT 'Is a value required for GSC MIMS compliance (as of Aug 2012)',
    `GSC_ENV_PACKAGES` VARCHAR(250) COMMENT 'which (if any) of the GSC environmental packages is this variable part of',
    `COMMENTS` VARCHAR(250),
    PRIMARY KEY (`VAR_ID` , `VAR_NAME`),
    UNIQUE (`VAR_NAME`),
    UNIQUE (`VAR_ID`)
);
   
CREATE TABLE `GSC_CV_CV` (
    `VAR_NAME` VARCHAR(50),
    `VAR_VAL_CV` VARCHAR(60),
    PRIMARY KEY (`VAR_VAL_CV`),
    FOREIGN KEY (`VAR_NAME`)
        REFERENCES `VARIABLE_NAMES` (`VAR_NAME`)
);

CREATE TABLE `SAMPLE_ANN` (
    `SAMPLE_ID` INT NOT NULL COMMENT 'Internal sample ID from SAMPLE table',
    `VAR_VAL_CV` VARCHAR(60) COMMENT 'The value of the variable defined in VAR_ID where that variable must use a controlled vocabulary, this value must be in GSC_CV_CV',
    `UNITS` VARCHAR(25) COMMENT 'The UNITS of the value given in VAR_VAL_UCV',
    `VAR_ID` SMALLINT NOT NULL COMMENT 'The variable ID from the VARIABLE_NAMES table',
    `VAR_VAL_UCV` VARCHAR(4000) COMMENT 'The value for the varible defined by VAR_ID',
    PRIMARY KEY (`SAMPLE_ID` , `VAR_ID`),
    FOREIGN KEY (`VAR_ID`)
        REFERENCES `VARIABLE_NAMES` (`VAR_ID`),
    FOREIGN KEY (`SAMPLE_ID`)
        REFERENCES `SAMPLE` (`SAMPLE_ID`),
    FOREIGN KEY (`VAR_VAL_CV`)
        REFERENCES `GSC_CV_CV` (`VAR_VAL_CV`)
);

CREATE TABLE `EXPERIMENT_TYPE` (
    `EXPERIMENT_TYPE_ID` TINYINT AUTO_INCREMENT,
    `EXPERIMENT_TYPE` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`EXPERIMENT_TYPE_ID`)
);

-- Changes:
-- Added foreign key to experiment_type table (was missing)
-- Remove column experiment_type (deprecated)
CREATE TABLE `ANALYSIS_JOB` (
    `JOB_ID` BIGINT NOT NULL AUTO_INCREMENT,
    `JOB_OPERATOR` VARCHAR(15) NOT NULL,
    `PIPELINE_ID` TINYINT NOT NULL,
    `SUBMIT_TIME` DATETIME(0) DEFAULT NOW() NOT NULL,
    `COMPLETE_TIME` DATETIME(0),
    `ANALYSIS_STATUS_ID` TINYINT NOT NULL,
    `RE_RUN_COUNT` TINYINT DEFAULT 0,
    `INPUT_FILE_NAME` VARCHAR(50) NOT NULL,
    `RESULT_DIRECTORY` VARCHAR(100) NOT NULL,
    `EXTERNAL_RUN_IDS` VARCHAR(100),
    `SAMPLE_ID` INT,
    `IS_PRODUCTION_RUN` TINYINT,
    `EXPERIMENT_TYPE_ID` TINYINT,
    PRIMARY KEY (`JOB_ID`),
    FOREIGN KEY (`PIPELINE_ID`)
        REFERENCES `PIPELINE_RELEASE` (`PIPELINE_ID`),
    FOREIGN KEY (`ANALYSIS_STATUS_ID`)
        REFERENCES `ANALYSIS_STATUS` (`ANALYSIS_STATUS_ID`),
    FOREIGN KEY (`EXPERIMENT_TYPE_ID`)
        REFERENCES `EXPERIMENT_TYPE` (`EXPERIMENT_TYPE_ID`),
    FOREIGN KEY (`SAMPLE_ID`)
        REFERENCES `SAMPLE` (`SAMPLE_ID`)
);

CREATE TABLE `PUBLICATION` (
    `PUB_ID` INT NOT NULL AUTO_INCREMENT,
    `AUTHORS` VARCHAR(4000),
    `DOI` VARCHAR(1500),
    `ISBN` VARCHAR(10),
    `ISO_JOURNAL` VARCHAR(255),
    `ISSUE` VARCHAR(55),
    `MEDLINE_JOURNAL` VARCHAR(255),
    `PUB_ABSTRACT` LONGTEXT,
    `PUBMED_CENTRAL_ID` BIGINT,
    `PUBMED_ID` BIGINT,
    `PUB_TITLE` VARCHAR(740) NOT NULL,
    `RAW_PAGES` VARCHAR(30),
    `URL` VARCHAR(740),
    `VOLUME` VARCHAR(55),
    `PUBLISHED_YEAR` BIGINT,
    `PUB_TYPE` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`PUB_ID`)
);

-- Changes:
-- Changed column names 
CREATE TABLE `STUDY_PUBLICATION` (
    `STUDY_ID` INT NOT NULL COMMENT 'the study id of the study with this publication', 
	`PUB_ID` INT NOT NULL COMMENT 'publication ID from the publication table', 
    PRIMARY KEY (`STUDY_ID` , `PUB_ID`),
    FOREIGN KEY (`PUB_ID`)
        REFERENCES `PUBLICATION` (`PUB_ID`),
    FOREIGN KEY (`STUDY_ID`)
        REFERENCES `STUDY` (`STUDY_ID`)
);


-- Changes:
-- Changed column names
CREATE TABLE `SAMPLE_PUBLICATION` (
    `SAMPLE_ID` INT NOT NULL COMMENT 'sample_id from the sample table, of the sample associated with this publication', 
	`PUB_ID` INT NOT NULL COMMENT 'publication ID from publication table', 	
    PRIMARY KEY (`SAMPLE_ID` , `PUB_ID`),
    FOREIGN KEY (`SAMPLE_ID`)
        REFERENCES `SAMPLE` (`SAMPLE_ID`),
    FOREIGN KEY (`PUB_ID`)
        REFERENCES `PUBLICATION` (`PUB_ID`)
);

ALTER TABLE `ANALYSIS_JOB`  COMMENT 'Table to track all analysis runs in production.';

CREATE INDEX `ANALYSIS_JOB_E_TYPE_ID_IDX` ON `ANALYSIS_JOB` (`EXPERIMENT_TYPE_ID`);

CREATE UNIQUE INDEX `GSC_CV_CV_PK` ON `GSC_CV_CV` (`VAR_VAL_CV`);

CREATE UNIQUE INDEX `GSC_CV_CV_U1` ON `GSC_CV_CV` (`VAR_NAME`, `VAR_VAL_CV`);

CREATE UNIQUE INDEX `pipeline_tool_group_uqidx` ON `PIPELINE_RELEASE_TOOL` (`PIPELINE_ID`, `TOOL_GROUP_ID`);

CREATE UNIQUE INDEX `SAMPLE_ANN_PK` ON `SAMPLE_ANN` (`SAMPLE_ID`, `VAR_ID`);

CREATE INDEX `STUDY_BIOME_ID_IDX` ON `STUDY` (`BIOME_ID`);

CREATE UNIQUE INDEX `VARIABLE_NAMES_PK` ON `VARIABLE_NAMES` (`VAR_ID`, `VAR_NAME`);

-- Commented because of duplication warning
-- CREATE UNIQUE INDEX `VARIABLE_NAMES_U1` ON `VARIABLE_NAMES` (`VAR_ID`);

-- Commented because of duplication warning
-- CREATE UNIQUE INDEX `VARIABLE_NAMES_U2` ON `VARIABLE_NAMES` (`VAR_NAME`);
  
-- CREATE INDEX VARIABLE_NAMES_FBI1 ON VARIABLE_NAMES (UPPER(VAR_NAME));