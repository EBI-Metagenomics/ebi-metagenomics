--EMG schema extension to support monitoring of pipeline jobs and pipeline versioning

--Author: Maxim Scheremetjew, EMBL-EBI, InterPro

--Table Drop statements
DROP TABLE "EMG"."analysis_run_job" cascade constraints;
DROP TABLE "EMG"."analysis_status" cascade constraints;
DROP TABLE "EMG"."analysis_run_step" cascade constraints;

DROP TABLE "EMG"."pipeline_release" cascade constraints;
DROP TABLE "EMG"."pipeline_tool" cascade constraints;
DROP TABLE "EMG"."pipeline_release_tool" cascade constraints;

--Sequence drop statements
DROP SEQUENCE "EMG"."ANALYSIS_RUN_JOB_SEQ";
DROP SEQUENCE "EMG"."ANALYSIS_RUN_STEP_SEQ";
DROP SEQUENCE "EMG"."PIPELINE_RELEASE_SEQ";
DROP SEQUENCE "EMG"."PIPELINE_TOOL_SEQ";

--DDL statements for tables analysis_run_job and pipeline release and pipeline tool
--Login as emg schema owner and run the following statements

CREATE TABLE analysis_run_job (
         job_id             NUMBER(10)  PRIMARY KEY,
         submit_time        TIMESTAMP(0)  DEFAULT CURRENT_TIMESTAMP NOT NULL,
         complete_time      TIMESTAMP(0),
         job_operator       VARCHAR2(10)  NOT NULL,
         result_directory   VARCHAR2(100) NOT NULL,
         is_production_run  NUMBER(1),
         run_id             NUMBER(19)
                            CONSTRAINT run_id_fk REFERENCES sample (sample_id),
         pipeline_id        NUMBER(4)  NOT NULL
                            CONSTRAINT pipeline_id_fk2 REFERENCES pipeline_release (pipeline_id),
         analysis_status_id NUMBER(2)  NOT NULL
                            CONSTRAINT analysis_status_fk REFERENCES analysis_status (analysis_status_id)
         );

COMMENT ON TABLE analysis_run IS 'Table to track all analysis runs in production.';

GRANT SELECT ON analysis_run_job TO "EMGAPP";
GRANT SELECT ON analysis_run_job TO "EMG_USER";
GRANT ALTER ON analysis_run_job TO "EMG_USER";
GRANT SELECT ON analysis_run_job TO "EMG_READ";

CREATE SEQUENCE  ANALYSIS_RUN_JOB_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON ANALYSIS_RUN_JOB_SEQ TO "EMGAPP";
GRANT SELECT ON ANALYSIS_RUN_JOB_SEQ TO "EMG_USER";
GRANT ALTER ON ANALYSIS_RUN_JOB_SEQ TO "EMG_USER";
GRANT SELECT ON ANALYSIS_RUN_JOB_SEQ TO "EMG_READ";

-- triggers the usage of the PIPELINE_RELEASE_SEQ sequence to be used for new entries
create or replace
trigger ANALYSIS_RUN_JOB_TRIGGER
BEFORE INSERT ON analysis_run_job
FOR EACH ROW
BEGIN
if :new.job_id is null then
  select ANALYSIS_RUN_JOB_SEQ.nextval into :new.job_id
  from dual;
END IF;
END;

CREATE TABLE analysis_status (
         analysis_status_id NUMBER(2)  PRIMARY KEY,
         analysis_status    VARCHAR2(15)  NOT NULL
         );

GRANT SELECT ON analysis_status TO "EMGAPP";
GRANT SELECT ON analysis_status TO "EMG_USER";
GRANT ALTER ON analysis_status TO "EMG_USER";
GRANT SELECT ON analysis_status TO "EMG_READ";


CREATE TABLE analysis_run_step (
         step_id            NUMBER(10)  PRIMARY KEY,
         step_name          VARCHAR2(35)  NOT NULL,
         submit_time        TIMESTAMP(0)  DEFAULT CURRENT_TIMESTAMP,
         complete_time      TIMESTAMP(0),
         succeeded          NUMBER(1) DEFAULT 0,
         rerun_count        NUMBER(1)    DEFAULT 0,
         tool_id            NUMBER(7)
                            CONSTRAINT tool_id_fk REFERENCES pipeline_tool (tool_id),
         job_id             NUMBER(7) NOT NULL
                            CONSTRAINT job_id_fk REFERENCES analysis_run_job (job_id)
         );

GRANT SELECT ON analysis_run_step TO "EMGAPP";
GRANT SELECT ON analysis_run_step TO "EMG_USER";
GRANT ALTER ON analysis_run_step TO "EMG_USER";
GRANT SELECT ON analysis_run_step TO "EMG_READ";

CREATE SEQUENCE  ANALYSIS_RUN_STEP_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON ANALYSIS_RUN_STEP_SEQ TO "EMGAPP";
GRANT SELECT ON ANALYSIS_RUN_STEP_SEQ TO "EMG_USER";
GRANT ALTER ON ANALYSIS_RUN_STEP_SEQ TO "EMG_USER";
GRANT SELECT ON ANALYSIS_RUN_STEP_SEQ TO "EMG_READ";

-- triggers the usage of the PIPELINE_RELEASE_SEQ sequence to be used for new entries
create or replace
trigger ANALYSIS_RUN_STEP_TRIGGER
BEFORE INSERT ON analysis_run_step
FOR EACH ROW
BEGIN
if :new.step_id is null then
  select ANALYSIS_RUN_STEP_SEQ.nextval into :new.step_id
  from dual;
END IF;
END;

--Statements for pipeline release table (including create table, CREATE SEQUENCE, GRANT statements and triggers)

CREATE TABLE pipeline_release (
         pipeline_id      NUMBER(4)  PRIMARY KEY,
         description      CLOB,
         changes          CLOB  NOT NULL,
         release_version  VARCHAR2(20)  NOT NULL,
         release_date     DATE  NOT NULL
         );

GRANT SELECT ON pipeline_release TO "EMGAPP";
GRANT SELECT ON pipeline_release TO "EMG_USER";
GRANT ALTER ON pipeline_release TO "EMG_USER";
GRANT SELECT ON pipeline_release TO "EMG_READ";

CREATE SEQUENCE  PIPELINE_RELEASE_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON PIPELINE_RELEASE_SEQ TO "EMGAPP";
GRANT SELECT ON PIPELINE_RELEASE_SEQ TO "EMG_USER";
GRANT ALTER ON PIPELINE_RELEASE_SEQ TO "EMG_USER";
GRANT SELECT ON PIPELINE_RELEASE_SEQ TO "EMG_READ";

-- triggers the usage of the PIPELINE_RELEASE_SEQ sequence to be used for new entries
create or replace
trigger PIPELINE_RELEASE_TRIGGER
BEFORE INSERT ON pipeline_release
FOR EACH ROW
BEGIN
if :new.pipeline_id is null then
  select PIPELINE_RELEASE_SEQ.nextval into :new.pipeline_id
  from dual;
END IF;
END;

--Statements for pipeline_tool table (including create table, CREATE SEQUENCE, GRANT statements and triggers)

CREATE TABLE pipeline_tool (
         tool_id            NUMBER(7)  PRIMARY KEY,
         tool_name          VARCHAR2(30)  NOT NULL,
         description        VARCHAR2(1000)  NOT NULL,
         web_link           VARCHAR2(500),
         version            VARCHAR2(30)  NOT NULL,
         exe_command        VARCHAR2(500)  NOT NULL,
         installation_dir   VARCHAR2(100),
         configuration_file CLOB,
         notes              CLOB
         );

GRANT SELECT ON pipeline_tool TO "EMGAPP";
GRANT SELECT ON pipeline_tool TO "EMG_USER";
GRANT ALTER ON pipeline_tool TO "EMG_USER";
GRANT SELECT ON pipeline_tool TO "EMG_READ";

CREATE SEQUENCE  PIPELINE_TOOL_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON PIPELINE_TOOL_SEQ TO "EMGAPP";
GRANT SELECT ON PIPELINE_TOOL_SEQ TO "EMG_USER";
GRANT ALTER ON PIPELINE_TOOL_SEQ TO "EMG_USER";
GRANT SELECT ON PIPELINE_TOOL_SEQ TO "EMG_READ";

-- triggers the usage of the PIPELINE_RELEASE_SEQ sequence to be used for new entries
create or replace
trigger PIPELINE_TOOL_TRIGGER
BEFORE INSERT ON pipeline_tool
FOR EACH ROW
BEGIN
if :new.tool_id is null then
  select PIPELINE_TOOL_SEQ.nextval into :new.tool_id
  from dual;
END IF;
END;

--Relation table for pipeline_release and pipeline_tool
CREATE TABLE pipeline_release_tool (
         pipeline_id  NUMBER(4) NOT NULL
                      CONSTRAINT pipeline_id_fk REFERENCES pipeline_release (pipeline_id),
         tool_id      NUMBER(7) NOT NULL
                      CONSTRAINT tool_id_fk REFERENCES pipeline_tool (tool_id),
         PRIMARY KEY (pipeline_id, tool_id)
         );

GRANT SELECT ON pipeline_release_tool TO "EMGAPP";
GRANT SELECT ON pipeline_release_tool TO "EMG_USER";
GRANT ALTER ON pipeline_release_tool TO "EMG_USER";
GRANT SELECT ON pipeline_release_tool TO "EMG_READ";


-- example insert statements for table pipeline_release
INSERT INTO pipeline_release (description,changes,release_version,release_date) values ('test2','test2','1.0',to_date('19960725','YYYYMMDD'));

--example insert statements for the pipeline_tool table
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('Trimmomatic','A flexible read trimming tool.','http://www.usadellab.org/cms/?page=trimmomatic','0.32','-o {2}.log ''java -classpath {0}/Trimmomatic-0.32/trimmomatic-0.32.jar org.usadellab.trimmomatic.TrimmomaticSE  -phred33 {1} {2} LEADING:3 TRAILING:3 SLIDINGWINDOW:4:15''','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('FragGeneScan','An application for finding (fragmented) genes in short reads.','http://omics.informatics.indiana.edu/FragGeneScan/','1.15','{0}/FragGeneScan -s {0} -o {0}_CDS -w 0 -t 454_10','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('InterProScan','A sequence analysis application (nucleotide and protein sequences) that combines different protein signature recognition methods into one resource.','https://code.google.com/p/interproscan/wiki/Introduction','5.0 (beta release)','{0}/interproscan-5-dist.dir/interproscan.sh --appl PfamA,TIGRFAM-10.1,PRINTS,PrositePatterns,Gene3d -goterms -o {1}_out.tsv -i {1}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools');

INSERT INTO pipeline_release_tool (pipeline_id,tool_id) values (1,4);


-- example insert statements for table analysis_status
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (1,'scheduled');
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (2,'running');
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (3,'completed');
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (4,'failed');

-- example insert statements for table analysis_run_job
INSERT INTO analysis_run_job (job_operator,result_directory,is_production_run,pipeline_id,analysis_status_id) values ('maxim','/ebi/production/interpro/metagenomics/results/SRS086433_G_FASTQ',1,1,2);