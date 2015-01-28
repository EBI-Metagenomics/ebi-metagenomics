--DDL statements for analysis run and pipeline release tables
--Login as emg schema owner and run the following statements

CREATE TABLE analysis_run (
         analysis_id        NUMBER(10)  PRIMARY KEY,
         submit_time        DATE  DEFAULT (sysdate),
         completed_time     DATE,
         analysis_operator  VARCHAR2(10),
         is_production_run  NUMBER(1, 0),
         run_id             NUMBER(19)  NOT NULL
                            CONSTRAINT run_id_fk REFERENCES sample (sample_id),
         pipeline_id        NUMBER(4)  NOT NULL
                            CONSTRAINT pipeline_id_fk REFERENCES pipeline_release (pipeline_id),
         analysis_status_id NUMBER(2)  NOT NULL
                            CONSTRAINT analysis_status_fk REFERENCES analysis_status (analysis_status_id)
         );

COMMENT ON TABLE analysis_run IS 'Table to track all analysis runs in production.';

CREATE TABLE analysis_status (
         analysis_status_id NUMBER(2)  PRIMARY KEY,
         analysis_status    VARCHAR2(15)  NOT NULL
         );

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
INSERT INTO pipeline_release (pipeline_id,description,changes,release_version,release_date) values (1,'test','test','1.0',to_date('19960725','YYYYMMDD'));
INSERT INTO pipeline_release (description,changes,release_version,release_date) values ('test2','test2','1.0',to_date('19960725','YYYYMMDD'));

--example insert statements for the pipeline_tool table
INSERT INTO pipeline_tool (tool_name,version,exe_command,installation_dir) values ('Trimmomatic','0.32','-o {2}.log ''java -classpath {0}/Trimmomatic-0.32/trimmomatic-0.32.jar org.usadellab.trimmomatic.TrimmomaticSE  -phred33 {1} {2} LEADING:3 TRAILING:3 SLIDINGWINDOW:4:15''','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools');
INSERT INTO pipeline_tool (tool_name,version,exe_command,installation_dir) values ('FragGeneScan','1.15','{0}/FragGeneScan -s {0} -o {0}_CDS -w 0 -t 454_10','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools');
INSERT INTO pipeline_tool (tool_name,version,exe_command,installation_dir) values ('InterProScan','5.0 (beta release)','{0}/interproscan-5-dist.dir/interproscan.sh --appl PfamA,TIGRFAM-10.1,PRINTS,PrositePatterns,Gene3d -goterms -o {1}_out.tsv -i {1}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools');

INSERT INTO pipeline_release_tool (pipeline_id,tool_id) values (1,4);