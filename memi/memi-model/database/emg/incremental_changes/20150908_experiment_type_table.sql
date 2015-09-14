-- EMG schema extension to support enumerated experiment types.
-- Login as emg schema owner and run the following statements

-- Author: Maxim Scheremetjew, EMBL-EBI, InterPro

-- Table Drop statements
DROP TABLE experiment cascade constraints;
DROP TABLE experiment_type cascade constraints;

--Alter table statements
ALTER TABLE analysis_job DROP COLUMN experiment_type_id;
ALTER TABLE analysis_job ADD experiment_type_id NUMBER(2);

-- DDL statements for the table experiment_type
CREATE TABLE experiment_type (
         experiment_type_id NUMBER(2)  PRIMARY KEY,
         experiment_type    VARCHAR2(30 CHAR)  NOT NULL
         );

-- Set user permissions

GRANT SELECT ON experiment_type TO "EMGAPP";
GRANT SELECT ON experiment_type TO "EMG_USER";
GRANT ALTER ON experiment_type TO "EMG_USER";
GRANT SELECT ON experiment_type TO "EMG_READ";
GRANT DELETE ON experiment_type TO "EMG_USER";

-- Set index on ANALYSIS_JOB table

CREATE INDEX ANALYSIS_JOB_E_TYPE_ID_IDX ON ANALYSIS_JOB(experiment_type_id) TABLESPACE EMG_IND;

-- Populate experiment_type table

INSERT INTO experiment_type (experiment_type_id, experiment_type) values (1,'metatranscriptomic');
INSERT INTO experiment_type (experiment_type_id, experiment_type) values (2,'metagenomic');
INSERT INTO experiment_type (experiment_type_id, experiment_type) values (3,'amplicon');
INSERT INTO experiment_type (experiment_type_id, experiment_type) values (4,'assembly');

-- Populate analysis_job table

UPDATE analysis_job
SET experiment_type_id = (SELECT experiment_type_id
                 FROM experiment_type
                 WHERE experiment_type.experiment_type = analysis_job.experiment_type);

-- Final alter table statement to remove the obsolete column
-- ALTER TABLE analysis_job DROP COLUMN experiment_type;

COMMIT;