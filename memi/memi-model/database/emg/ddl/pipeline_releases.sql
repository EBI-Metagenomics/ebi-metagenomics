--EMG schema extension to support monitoring of pipeline jobs and pipeline versioning

--Author: Maxim Scheremetjew, EMBL-EBI, InterPro

--Table Drop statements
DROP TABLE analysis_job cascade constraints;
DROP TABLE analysis_status cascade constraints;
DROP TABLE analysis_step cascade constraints;

DROP TABLE pipeline_release cascade constraints;
DROP TABLE pipeline_tool cascade constraints;
DROP TABLE pipeline_release_tool cascade constraints;

--Sequence drop statements
DROP SEQUENCE "EMG"."ANALYSIS_JOB_SEQ";
DROP SEQUENCE "EMG"."ANALYSIS_STEP_SEQ";
DROP SEQUENCE "EMG"."PIPELINE_RELEASE_SEQ";
DROP SEQUENCE "EMG"."PIPELINE_TOOL_SEQ";

--DDL statements for tables analysis_run_job and pipeline release and pipeline tool
--Login as emg schema owner and run the following statements

-- ANALYSIS_STATUS table
CREATE TABLE analysis_status (
         analysis_status_id NUMBER(2)  PRIMARY KEY,
         analysis_status    VARCHAR2(15)  NOT NULL
         );

GRANT SELECT ON analysis_status TO "EMGAPP";
GRANT SELECT ON analysis_status TO "EMG_USER";
GRANT ALTER ON analysis_status TO "EMG_USER";
GRANT SELECT ON analysis_status TO "EMG_READ";
GRANT DELETE ON analysis_status TO "EMG_USER";

-- PIPELINE_RELEASE table (including create table, CREATE SEQUENCE, GRANT statements and triggers)
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
GRANT DELETE ON pipeline_release TO "EMG_USER";

CREATE SEQUENCE  PIPELINE_RELEASE_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON PIPELINE_RELEASE_SEQ TO "EMGAPP";
GRANT SELECT ON PIPELINE_RELEASE_SEQ TO "EMG_USER";
GRANT ALTER ON PIPELINE_RELEASE_SEQ TO "EMG_USER";
GRANT SELECT ON PIPELINE_RELEASE_SEQ TO "EMG_READ";

-- ANALYSIS_JOB table (including create table, CREATE SEQUENCE, GRANT statements and triggers)
CREATE TABLE analysis_job (
         job_id             NUMBER(10)  PRIMARY KEY,
         job_operator       VARCHAR2(15)  NOT NULL,
         pipeline_id        NUMBER(4)  NOT NULL
                            CONSTRAINT pipeline_id_fk2 REFERENCES pipeline_release (pipeline_id),
         submit_time        TIMESTAMP(0)  DEFAULT CURRENT_TIMESTAMP NOT NULL,
         complete_time      TIMESTAMP(0),
         analysis_status_id NUMBER(2)  NOT NULL
                            CONSTRAINT analysis_status_fk REFERENCES analysis_status (analysis_status_id),
         re_run_count       NUMBER(2) DEFAULT 0,
         input_file_name    VARCHAR2(50) NOT NULL,
         result_directory   VARCHAR2(100) NOT NULL,
         external_run_ids   VARCHAR2(100),
         experiment_type    VARCHAR2(30) NOT NULL,
         sample_id          NUMBER(19)
                            CONSTRAINT sample_id_fk REFERENCES sample (sample_id),
         is_production_run  NUMBER(1)
         );

COMMENT ON TABLE analysis_job IS 'Table to track all analysis runs in production.';

GRANT SELECT ON analysis_job TO "EMGAPP";
GRANT SELECT ON analysis_job TO "EMG_USER";
GRANT ALTER ON analysis_job TO "EMG_USER";
GRANT SELECT ON analysis_job TO "EMG_READ";
GRANT DELETE ON analysis_job TO "EMG_USER";

CREATE SEQUENCE  ANALYSIS_JOB_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON ANALYSIS_JOB_SEQ TO "EMGAPP";
GRANT SELECT ON ANALYSIS_JOB_SEQ TO "EMG_USER";
GRANT ALTER ON ANALYSIS_JOB_SEQ TO "EMG_USER";
GRANT SELECT ON ANALYSIS_JOB_SEQ TO "EMG_READ";

--PIPELINE_TOOL table (including create table, CREATE SEQUENCE, GRANT statements and triggers)
CREATE TABLE pipeline_tool (
         tool_id            NUMBER(7)  PRIMARY KEY,
         tool_name          VARCHAR2(30)  NOT NULL,
         description        VARCHAR2(1000)  NOT NULL,
         web_link           VARCHAR2(500),
         version            VARCHAR2(30)  NOT NULL,
         exe_command        VARCHAR2(500)  NOT NULL,
         installation_dir   VARCHAR2(200),
         configuration_file CLOB,
         notes              CLOB
         );

GRANT SELECT ON pipeline_tool TO "EMGAPP";
GRANT SELECT ON pipeline_tool TO "EMG_USER";
GRANT ALTER ON pipeline_tool TO "EMG_USER";
GRANT SELECT ON pipeline_tool TO "EMG_READ";
GRANT DELETE ON pipeline_tool TO "EMG_USER";

CREATE SEQUENCE  PIPELINE_TOOL_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON PIPELINE_TOOL_SEQ TO "EMGAPP";
GRANT SELECT ON PIPELINE_TOOL_SEQ TO "EMG_USER";
GRANT ALTER ON PIPELINE_TOOL_SEQ TO "EMG_USER";
GRANT SELECT ON PIPELINE_TOOL_SEQ TO "EMG_READ";

--ANALYSIS_STEP table (including create table, CREATE SEQUENCE, GRANT statements and triggers)
CREATE TABLE analysis_step (
         step_id            NUMBER(10)  PRIMARY KEY,
         step_name          VARCHAR2(35)  NOT NULL,
         submit_time        TIMESTAMP(0)  DEFAULT CURRENT_TIMESTAMP,
         complete_time      TIMESTAMP(0),
         succeeded          NUMBER(1) DEFAULT 0,
         rerun_count        NUMBER(1)    DEFAULT 0,
         tool_id            NUMBER(7)
                            CONSTRAINT tool_id_fk REFERENCES pipeline_tool (tool_id),
         job_id             NUMBER(7) NOT NULL
                            CONSTRAINT job_id_fk REFERENCES analysis_job (job_id)
         );

GRANT SELECT ON analysis_step TO "EMGAPP";
GRANT SELECT ON analysis_step TO "EMG_USER";
GRANT ALTER ON analysis_step TO "EMG_USER";
GRANT SELECT ON analysis_step TO "EMG_READ";
GRANT DELETE ON analysis_step TO "EMG_USER";

CREATE SEQUENCE  ANALYSIS_STEP_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON ANALYSIS_STEP_SEQ TO "EMGAPP";
GRANT SELECT ON ANALYSIS_STEP_SEQ TO "EMG_USER";
GRANT ALTER ON ANALYSIS_STEP_SEQ TO "EMG_USER";
GRANT SELECT ON ANALYSIS_STEP_SEQ TO "EMG_READ";

--Relation table for pipeline_release and pipeline_tool
CREATE TABLE pipeline_release_tool (
         pipeline_id   NUMBER(4) NOT NULL
                       CONSTRAINT pipeline_id_fk REFERENCES pipeline_release (pipeline_id),
         tool_id       NUMBER(7) NOT NULL
                       CONSTRAINT tool_id_fk2 REFERENCES pipeline_tool (tool_id),
         tool_group_id NUMBER(4) NOT NULL,
         PRIMARY KEY (pipeline_id, tool_id)
         );

COMMENT ON COLUMN pipeline_release_tool.tool_group_id
IS
  'The tool group identifier allows you to group pipeline tools into different categories. It is mainly used to assign pipeline tools to certain steps in the pipeline workflow. For example, tool group identifiers 1, 2, 3 etc. may represent categories such as quality control, taxonomic and function annotation, gene and rRNA predication.';

GRANT SELECT ON pipeline_release_tool TO "EMGAPP";
GRANT SELECT ON pipeline_release_tool TO "EMG_USER";
GRANT ALTER ON pipeline_release_tool TO "EMG_USER";
GRANT SELECT ON pipeline_release_tool TO "EMG_READ";
GRANT DELETE ON pipeline_release_tool TO "EMG_USER";

-- triggers the usage of the PIPELINE_RELEASE_SEQ sequence to be used for new entries
create or replace
trigger "EMG"."ANALYSIS_JOB_TRIGGER"
BEFORE INSERT ON ANALYSIS_JOB
FOR EACH ROW
BEGIN
if :new.JOB_ID is null then
  select ANALYSIS_JOB_SEQ.nextval into :new.JOB_ID
  from dual;
END IF;
END;
/

-- triggers the usage of the ANALYSIS_STEP_SEQ sequence to be used for new entries
create or replace
trigger "EMG"."ANALYSIS_STEP_TRIGGER"
BEFORE INSERT ON ANALYSIS_STEP
FOR EACH ROW
BEGIN
if :new.STEP_ID is null then
  select ANALYSIS_STEP_SEQ.nextval into :new.STEP_ID
  from dual;
END IF;
END;
/

-- triggers the usage of the PIPELINE_TOOL_SEQ sequence to be used for new entries
create or replace
trigger "EMG"."TOOL_TRIGGER"
BEFORE INSERT ON PIPELINE_TOOL
FOR EACH ROW
BEGIN
if :new.TOOL_ID is null then
  select PIPELINE_TOOL_SEQ.nextval into :new.TOOL_ID
  from dual;
END IF;
END;
/

-- triggers the usage of the PIPELINE_RELEASE_SEQ sequence to be used for new entries
create or replace
trigger "EMG"."RELEASE_TRIGGER"
BEFORE INSERT ON PIPELINE_RELEASE
FOR EACH ROW
BEGIN
if :new.PIPELINE_ID is null then
  select PIPELINE_RELEASE_SEQ.nextval into :new.PIPELINE_ID
  from dual;
END IF;
END;
/

-- Populate analysis_status table
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (1,'scheduled');
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (2,'running');
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (3,'completed');
INSERT INTO analysis_status (analysis_status_id,analysis_status) values (4,'failed');

-- Populate pipeline_release table
INSERT INTO pipeline_release (description,changes,release_version,release_date) values ('Initial version','N/A','1.0',to_date('20091209','YYYYMMDD'));
INSERT INTO pipeline_release (changes,release_version,release_date) values ('Major upgrade. Updated the following binaries: InterProScan, FragGeneScan, QIIME','2.0',to_date('20150215','YYYYMMDD'));

-- Populate pipeline_tool table
-- For pipeline version 1.0
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('Trimmomatic','A flexible read trimming tool.','http://www.usadellab.org/cms/?page=trimmomatic','0.32','java -classpath {0}/Trimmomatic-0.32/trimmomatic-0.32.jar org.usadellab.trimmomatic.TrimmomaticSE  -phred33 {1} {2} LEADING:3 TRAILING:3 SLIDINGWINDOW:4:15','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/Trimmomatic-0.32/trimmomatic-0.32.jar');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('FragGeneScan','An application for finding (fragmented) genes in short reads.','http://omics.informatics.indiana.edu/FragGeneScan/','1.15','./FragGeneScan -s {0} -o {0}_CDS -w 0 -t 454_10','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/FragGeneScan1.15/FragGeneScan');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('InterProScan','A sequence analysis application (nucleotide and protein sequences) that combines different protein signature recognition methods into one resource.','https://code.google.com/p/interproscan/wiki/Introduction','5.0 (beta release)','./interproscan.sh --appl PfamA,TIGRFAM-10.1,PRINTS,PrositePatterns,Gene3d -goterms -o {1}_out.tsv -i {1}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/interproscan-5-dist.dir/');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('UCLUST','A high-performance clustering, alignment and search algorithm.','http://www.drive5.com/uclust/downloads1_1_579.html','1.1.579','./uclust1.1.579_i86linux64 --id 0.99 --usersort --nucleo --input {1} --uc {2}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/uclust1.1.579_i86linux64');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('RepeatMasker','A program that screens DNA sequences for interspersed repeats and low complexity DNA sequences.','http://www.repeatmasker.org/','open-3.2.2','./RepeatMasker {0}','/sw/arch/bin/RepeatMasker');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('rRNASelector','A computer program for selecting ribosomal RNA encoding sequences from metagenomic and metatranscriptomic shotgun libraries. EBI metagenomics only uses the hidden Markov models from rRNASelector.','http://www.ezbiocloud.net/sw/rrnaselector','1.0.0','HMMER3.0/hmmsearch --tblout {0} --cpu 4 -E 1.0E-5  {1}/rRNASelector/lib/all.hmm {2} > /dev/null','/ebi/production/interpro/binaries/64_bit_Linux/HMMER3.0/hmmsearch');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('QIIME','An open-source bioinformatics pipeline for performing taxonomic analysis from raw DNA sequencing data.','http://qiime.org/','1.5.0','./qiimeWrapper.sh  {1}  {2}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/qiimeWrapper.sh');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command) values ('Biopython','A set of freely available tools for biological computation written in Python by an international team of developers.','http://www.biopython.org/','1.54','N/A');

-- For pipeline version 2.0
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('rRNASelector','A computer program for selecting ribosomal RNA encoding sequences from metagenomic and metatranscriptomic shotgun libraries. EBI metagenomics only uses the hidden Markov models from rRNASelector.','http://www.ezbiocloud.net/sw/rrnaselector','1.0.1','./pipelineDetectRRNA.sh {1} {2} {3}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/bin/pipelineDetectRRNA.sh');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('QIIME','An open-source bioinformatics pipeline for performing taxonomic analysis from raw DNA sequencing data.','http://qiime.org/','1.9.0','./qiime190Wrapper.sh  {1} {2} {3}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/bin/qiime190Wrapper.sh');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command) values ('Biopython','A set of freely available tools for biological computation written in Python by an international team of developers.','http://www.biopython.org/','1.65','N/A');
INSERT INTO pipeline_tool (tool_name,description,web_link,version,exe_command,installation_dir) values ('InterProScan','A sequence analysis application (nucleotide and protein sequences) that combines different protein signature recognition methods into one resource.','https://code.google.com/p/interproscan/wiki/Introduction','5.9-50.0','./interproscan.sh --appl PfamA,TIGRFAM-10.1,PRINTS,PrositePatterns,Gene3d -goterms -o {1}_out.tsv -i {1}','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/interproscan-5/interproscan-5.9-50.0/');

-- Populate the relation table pipeline_release_tool
-- For pipeline version 1.0
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,1,1);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,2,3);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,3,4);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,4,1);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,5,1);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,6,2);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,7,5);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (1,8,1);

-- For pipeline version 2.0
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (2,1,1);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (2,2,3);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (2,9,2);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (2,10,5);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (2,11,1);
INSERT INTO pipeline_release_tool (pipeline_id,tool_id,tool_group_id) values (2,12,4);