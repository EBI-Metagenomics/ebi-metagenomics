--------------------------------------------------------
--  File created - Monday-October-13-2014
--------------------------------------------------------
DROP TABLE "EMG"."CIH_I5_RESULTS_TMP" cascade constraints;
DROP TABLE "EMG"."COMPARE_SAMPLE_TEMP" cascade constraints;
DROP TABLE "EMG"."CONTIGS" cascade constraints;
DROP TABLE "EMG"."EXPERIMENT" cascade constraints;
DROP TABLE "EMG"."GSC_CV_CV" cascade constraints;
DROP TABLE "EMG"."LOG_ANALYSIS_STATUS" cascade constraints;
DROP TABLE "EMG"."LOG_CDS_RESULTS" cascade constraints;
DROP TABLE "EMG"."LOG_FILE_INFO" cascade constraints;
DROP TABLE "EMG"."LOG_NT_SEQS" cascade constraints;
DROP TABLE "EMG"."LOG_NT_STATUS" cascade constraints;
DROP TABLE "EMG"."PUBLICATION" cascade constraints;
DROP TABLE "EMG"."SAMPLE" cascade constraints;
DROP TABLE "EMG"."SAMPLE_ANN" cascade constraints;
DROP TABLE "EMG"."SAMPLE_ANN_TEMP" cascade constraints;
DROP TABLE "EMG"."SAMPLE_PUBLICATION" cascade constraints;
DROP TABLE "EMG"."STUDY" cascade constraints;
DROP TABLE "EMG"."STUDY_PUBLICATION" cascade constraints;
DROP TABLE "EMG"."VARIABLE_NAMES" cascade constraints;
DROP SYNONYM "PUBLIC"."DUAL";
DROP SYNONYM "EMG"."SUBMITTER";
DROP DATABASE LINK "EMGDEV";
DROP SEQUENCE "EMG"."EXPERIMENT_SEQ";
DROP SEQUENCE "EMG"."PUBLICATION_SEQ";
DROP SEQUENCE "EMG"."SAMPLE_SEQ";
DROP SEQUENCE "EMG"."STUDY_SEQ";
DROP SEQUENCE "EMG"."VARIABLE_NAMES_SEQ";
DROP PACKAGE "EMG"."REFRESH_EMG_PKG";
DROP PACKAGE BODY "EMG"."REFRESH_EMG_PKG";
--------------------------------------------------------
--  DDL for DB Link EMGDEV
--------------------------------------------------------

  CREATE DATABASE LINK "EMGDEV"
   CONNECT TO "EMG" IDENTIFIED BY VALUES '05A02EE9EC03438BBF00BE38DF68AEC1D8B37741A0483C0262'
   USING 'EMGDEV';
--------------------------------------------------------
--  DDL for Sequence EXPERIMENT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EMG"."EXPERIMENT_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 2 NOORDER  NOCYCLE ;
  GRANT SELECT ON "EMG"."EXPERIMENT_SEQ" TO "EMGAPP";
  GRANT SELECT ON "EMG"."EXPERIMENT_SEQ" TO "EMG_USER";
  GRANT ALTER ON "EMG"."EXPERIMENT_SEQ" TO "EMG_USER";
  GRANT SELECT ON "EMG"."EXPERIMENT_SEQ" TO "EMG_READ";
--------------------------------------------------------
--  DDL for Sequence PUBLICATION_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EMG"."PUBLICATION_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 354 CACHE 2 NOORDER  NOCYCLE ;
  GRANT SELECT ON "EMG"."PUBLICATION_SEQ" TO "EMGAPP";
  GRANT SELECT ON "EMG"."PUBLICATION_SEQ" TO "EMG_READ";
  GRANT SELECT ON "EMG"."PUBLICATION_SEQ" TO "EMG_USER";
  GRANT ALTER ON "EMG"."PUBLICATION_SEQ" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Sequence SAMPLE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EMG"."SAMPLE_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 4049 CACHE 2 NOORDER  NOCYCLE ;
  GRANT SELECT ON "EMG"."SAMPLE_SEQ" TO "EMGAPP";
  GRANT SELECT ON "EMG"."SAMPLE_SEQ" TO "EMG_READ";
  GRANT SELECT ON "EMG"."SAMPLE_SEQ" TO "EMG_USER";
  GRANT ALTER ON "EMG"."SAMPLE_SEQ" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Sequence STUDY_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EMG"."STUDY_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 410 CACHE 2 NOORDER  NOCYCLE ;
  GRANT SELECT ON "EMG"."STUDY_SEQ" TO "EMGAPP";
  GRANT SELECT ON "EMG"."STUDY_SEQ" TO "EMG_READ";
  GRANT SELECT ON "EMG"."STUDY_SEQ" TO "EMG_USER";
  GRANT ALTER ON "EMG"."STUDY_SEQ" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Sequence VARIABLE_NAMES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EMG"."VARIABLE_NAMES_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 370 CACHE 2 NOORDER  NOCYCLE ;
  GRANT SELECT ON "EMG"."VARIABLE_NAMES_SEQ" TO "EMGAPP";
  GRANT SELECT ON "EMG"."VARIABLE_NAMES_SEQ" TO "EMG_READ";
  GRANT SELECT ON "EMG"."VARIABLE_NAMES_SEQ" TO "EMG_USER";
  GRANT ALTER ON "EMG"."VARIABLE_NAMES_SEQ" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table CIH_I5_RESULTS_TMP
--------------------------------------------------------

  CREATE TABLE "EMG"."CIH_I5_RESULTS_TMP"
   (	"CDS_NAME" VARCHAR2(200 BYTE),
	"MD5_CHECKSUM" VARCHAR2(40 BYTE),
	"LENGTH" NUMBER,
	"DATABASE" VARCHAR2(20 BYTE),
	"DB_ID" VARCHAR2(20 BYTE),
	"DB_NAME" VARCHAR2(200 BYTE),
	"HIT_START" NUMBER,
	"HIT_END" NUMBER,
	"E-VALUE" VARCHAR2(20 BYTE),
	"RANDOM_LETTER:1" VARCHAR2(20 BYTE),
	"DATE" DATE,
	"IPR_NUMBER" VARCHAR2(20 BYTE),
	"IP_DESCRIPTION" VARCHAR2(200 BYTE),
	"GO_INFO" VARCHAR2(200 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMGAPP";
  GRANT SELECT ON "EMG"."CIH_I5_RESULTS_TMP" TO "HUDENISE";
  GRANT SELECT ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_READ";
  GRANT FLASHBACK ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT DEBUG ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT QUERY REWRITE ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT ON COMMIT REFRESH ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT REFERENCES ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT UPDATE ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT SELECT ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT INSERT ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT INDEX ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT DELETE ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
  GRANT ALTER ON "EMG"."CIH_I5_RESULTS_TMP" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table COMPARE_SAMPLE_TEMP
--------------------------------------------------------

  CREATE TABLE "EMG"."COMPARE_SAMPLE_TEMP"
   (	"SAMPLE_ID" NUMBER(19,0),
	"VAR_VAL_UCV" VARCHAR2(255 CHAR),
	"VAR_ID" NUMBER
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
--------------------------------------------------------
--  DDL for Table CONTIGS
--------------------------------------------------------

  CREATE TABLE "EMG"."CONTIGS"
   (	"CONTIG_ID" VARCHAR2(30 BYTE),
	"SAMPLE_ID" NUMBER(19,0),
	"LENGTH" VARCHAR2(30 BYTE),
	"ASSEMBLY_ALGORITHM" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."CONTIGS" TO "EMGAPP";
  GRANT SELECT ON "EMG"."CONTIGS" TO "HUDENISE";
  GRANT SELECT ON "EMG"."CONTIGS" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."CONTIGS" TO "EMG_USER";
  GRANT SELECT ON "EMG"."CONTIGS" TO "EMG_USER";
  GRANT INSERT ON "EMG"."CONTIGS" TO "EMG_USER";
  GRANT DELETE ON "EMG"."CONTIGS" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table EXPERIMENT
--------------------------------------------------------

  CREATE TABLE "EMG"."EXPERIMENT"
   (	"EXPERIMENT_ID" NUMBER(19,0),
	"EXPERIMENT_TYPE" VARCHAR2(50 BYTE),
	"SAMPLE_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."EXPERIMENT" TO "EMGAPP";
  GRANT SELECT ON "EMG"."EXPERIMENT" TO "HUDENISE";
  GRANT SELECT ON "EMG"."EXPERIMENT" TO "OPS$MAXIM";
  GRANT UPDATE ON "EMG"."EXPERIMENT" TO "EMG_USER";
  GRANT SELECT ON "EMG"."EXPERIMENT" TO "EMG_USER";
  GRANT INSERT ON "EMG"."EXPERIMENT" TO "EMG_USER";
  GRANT SELECT ON "EMG"."EXPERIMENT" TO "EMG_READ" WITH GRANT OPTION;
  GRANT DELETE ON "EMG"."EXPERIMENT" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table GSC_CV_CV
--------------------------------------------------------

  CREATE TABLE "EMG"."GSC_CV_CV"
   (	"VAR_NAME" VARCHAR2(30 CHAR),
	"VAR_VAL_CV" VARCHAR2(60 CHAR)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."GSC_CV_CV" TO "EMGAPP";
  GRANT SELECT ON "EMG"."GSC_CV_CV" TO "HUDENISE";
  GRANT SELECT ON "EMG"."GSC_CV_CV" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."GSC_CV_CV" TO "EMG_USER";
  GRANT SELECT ON "EMG"."GSC_CV_CV" TO "EMG_USER";
  GRANT INSERT ON "EMG"."GSC_CV_CV" TO "EMG_USER";
  GRANT DELETE ON "EMG"."GSC_CV_CV" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table LOG_ANALYSIS_STATUS
--------------------------------------------------------

  CREATE TABLE "EMG"."LOG_ANALYSIS_STATUS"
   (	"ANALYSIS_STATUS_ID" VARCHAR2(4 BYTE),
	"ANALYSIS_STATUS_DESCRIPTION" CHAR(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."LOG_ANALYSIS_STATUS" TO "EMGAPP";
  GRANT SELECT ON "EMG"."LOG_ANALYSIS_STATUS" TO "HUDENISE";
  GRANT SELECT ON "EMG"."LOG_ANALYSIS_STATUS" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."LOG_ANALYSIS_STATUS" TO "EMG_USER";
  GRANT SELECT ON "EMG"."LOG_ANALYSIS_STATUS" TO "EMG_USER";
  GRANT INSERT ON "EMG"."LOG_ANALYSIS_STATUS" TO "EMG_USER";
  GRANT DELETE ON "EMG"."LOG_ANALYSIS_STATUS" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table LOG_CDS_RESULTS
--------------------------------------------------------

  CREATE TABLE "EMG"."LOG_CDS_RESULTS"
   (	"PROTEIN_ID" VARCHAR2(50 BYTE),
	"DERIVED_PROT_SEQ" VARCHAR2(30 BYTE),
	"ORF_RESULT_CREATED" DATE,
	"PFAM_PRESCREEN" DATE,
	"IPRSCAN_V" NUMBER,
	"IPRSCAN_V_DATE" DATE,
	"LOCATION_COORD_START" NUMBER,
	"LOCATION_COORD_END" NUMBER,
	"FRAME" NUMBER,
	"INSERTIONS" VARCHAR2(255 BYTE),
	"DELETEIONS" VARCHAR2(255 BYTE),
	"SAMPLE_ID" NUMBER(19,0),
	"CONTIG_ID" VARCHAR2(30 BYTE),
	"READ_ID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."LOG_CDS_RESULTS" TO "EMGAPP";
  GRANT SELECT ON "EMG"."LOG_CDS_RESULTS" TO "HUDENISE";
  GRANT SELECT ON "EMG"."LOG_CDS_RESULTS" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."LOG_CDS_RESULTS" TO "EMG_USER";
  GRANT SELECT ON "EMG"."LOG_CDS_RESULTS" TO "EMG_USER";
  GRANT INSERT ON "EMG"."LOG_CDS_RESULTS" TO "EMG_USER";
  GRANT DELETE ON "EMG"."LOG_CDS_RESULTS" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table LOG_FILE_INFO
--------------------------------------------------------

  CREATE TABLE "EMG"."LOG_FILE_INFO"
   (	"FILE_ID" VARCHAR2(30 BYTE),
	"FILE_NAME" VARCHAR2(100 BYTE),
	"DATA_TYPE_GTA" VARCHAR2(30 BYTE),
	"FILE_ID_CREATED" DATE,
	"ANALYSIS_START_DATE" DATE,
	"PIPELINE_VERSION_NUMBER" VARCHAR2(6 BYTE),
	"ANALYSIS_STATUS_ID" VARCHAR2(30 BYTE),
	"EXT_SAMPLE_ID" VARCHAR2(15 CHAR),
	"SRA_RUN_IDS" VARCHAR2(100 BYTE),
	"FILE_DESC" VARCHAR2(4000 BYTE),
	"FILE_FORMAT" VARCHAR2(30 BYTE),
	"SAMPLE_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;

   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."FILE_ID" IS 'name of the nt sequence file in the analysis directory';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."FILE_NAME" IS 'The human readable name of file_id';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."DATA_TYPE_GTA" IS 'metaGenomic, metaTranscriptomic or Amplicon study?';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."FILE_ID_CREATED" IS 'the date on which this file_id was added to this table';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."ANALYSIS_START_DATE" IS 'the date on which the analysis was last started';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."PIPELINE_VERSION_NUMBER" IS 'version number of the pipeline that was run on the file, definitions of versions are not stored in schema anywhere yet, Chrish has a changes table in excel for changes to each version.';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."ANALYSIS_STATUS_ID" IS 'status of the analysis, i.e. started, running, failed complete, etc... this is a number from the LOG_ANALYSIS_STATUS';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."EXT_SAMPLE_ID" IS 'The SRA sample accession to which this file is associated';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."SRA_RUN_IDS" IS 'a semi-colon separated list of SRA run IDs used to create the processed file';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."FILE_DESC" IS 'A breif description of how the analysed file was created from the SRA run ID''s, for internal use only.';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."FILE_FORMAT" IS 'The format of the processed file (i.e. the file that was given to the pipeline).';
   COMMENT ON COLUMN "EMG"."LOG_FILE_INFO"."SAMPLE_ID" IS 'the ID number of the sample from the SAMPLE table';
  GRANT SELECT ON "EMG"."LOG_FILE_INFO" TO "EMGAPP";
  GRANT SELECT ON "EMG"."LOG_FILE_INFO" TO "HUDENISE";
  GRANT SELECT ON "EMG"."LOG_FILE_INFO" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."LOG_FILE_INFO" TO "EMG_USER";
  GRANT SELECT ON "EMG"."LOG_FILE_INFO" TO "EMG_USER";
  GRANT INSERT ON "EMG"."LOG_FILE_INFO" TO "EMG_USER";
  GRANT DELETE ON "EMG"."LOG_FILE_INFO" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table LOG_NT_SEQS
--------------------------------------------------------

  CREATE TABLE "EMG"."LOG_NT_SEQS"
   (	"READ_ID" VARCHAR2(20 BYTE),
	"FILE_ID" VARCHAR2(20 BYTE),
	"NT_REJECT_CREATED" DATE,
	"STATUS_CODE" VARCHAR2(3 BYTE),
	"CONTIG_ID" VARCHAR2(30 BYTE),
	"DUP_READ_ID" VARCHAR2(20 CHAR)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."LOG_NT_SEQS" TO "EMGAPP";
  GRANT SELECT ON "EMG"."LOG_NT_SEQS" TO "HUDENISE";
  GRANT SELECT ON "EMG"."LOG_NT_SEQS" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."LOG_NT_SEQS" TO "EMG_USER";
  GRANT SELECT ON "EMG"."LOG_NT_SEQS" TO "EMG_USER";
  GRANT INSERT ON "EMG"."LOG_NT_SEQS" TO "EMG_USER";
  GRANT DELETE ON "EMG"."LOG_NT_SEQS" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table LOG_NT_STATUS
--------------------------------------------------------

  CREATE TABLE "EMG"."LOG_NT_STATUS"
   (	"STATUS_CODE" VARCHAR2(3 BYTE),
	"STATUS_DESC" VARCHAR2(100 BYTE),
	"STATUS_CREATED" DATE,
	"STATUS_DEACTIVATED" DATE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;
  GRANT SELECT ON "EMG"."LOG_NT_STATUS" TO "EMGAPP";
  GRANT SELECT ON "EMG"."LOG_NT_STATUS" TO "HUDENISE";
  GRANT SELECT ON "EMG"."LOG_NT_STATUS" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."LOG_NT_STATUS" TO "EMG_USER";
  GRANT SELECT ON "EMG"."LOG_NT_STATUS" TO "EMG_USER";
  GRANT INSERT ON "EMG"."LOG_NT_STATUS" TO "EMG_USER";
  GRANT DELETE ON "EMG"."LOG_NT_STATUS" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table PUBLICATION
--------------------------------------------------------

  CREATE TABLE "EMG"."PUBLICATION"
   (	"PUB_ID" NUMBER(19,0),
	"AUTHORS" VARCHAR2(4000 CHAR),
	"DOI" VARCHAR2(1500 CHAR),
	"ISBN" VARCHAR2(10 CHAR),
	"ISO_JOURNAL" VARCHAR2(255 CHAR),
	"ISSUE" VARCHAR2(55 CHAR),
	"MEDLINE_JOURNAL" VARCHAR2(255 CHAR),
	"PUB_ABSTRACT" CLOB,
	"PUBMED_CENTRAL_ID" NUMBER(10,0),
	"PUBMED_ID" NUMBER(10,0),
	"PUB_TITLE" VARCHAR2(740 CHAR),
	"RAW_PAGES" VARCHAR2(30 CHAR),
	"URL" VARCHAR2(740 CHAR),
	"VOLUME" VARCHAR2(55 CHAR),
	"PUBLISHED_YEAR" NUMBER(10,0),
	"PUB_TYPE" VARCHAR2(30 CHAR)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB"
 LOB ("PUB_ABSTRACT") STORE AS BASICFILE (
  TABLESPACE "EMG_TAB" ENABLE STORAGE IN ROW CHUNK 16384 RETENTION
  NOCACHE LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
  GRANT SELECT ON "EMG"."PUBLICATION" TO "EMGAPP";
  GRANT SELECT ON "EMG"."PUBLICATION" TO "HUDENISE";
  GRANT SELECT ON "EMG"."PUBLICATION" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."PUBLICATION" TO "EMG_USER";
  GRANT SELECT ON "EMG"."PUBLICATION" TO "EMG_USER";
  GRANT INSERT ON "EMG"."PUBLICATION" TO "EMG_USER";
  GRANT DELETE ON "EMG"."PUBLICATION" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table SAMPLE
--------------------------------------------------------

  CREATE TABLE "EMG"."SAMPLE"
   (	"DTYPE" VARCHAR2(31 CHAR),
	"SAMPLE_ID" NUMBER(19,0),
	"ANALYSIS_COMPLETED" DATE,
	"COLLECTION_DATE" DATE,
	"GEO_LOC_NAME" VARCHAR2(255 CHAR),
	"IS_PUBLIC" NUMBER(1,0),
	"METADATA_RECEIVED" DATE DEFAULT CURRENT_DATE,
	"MISC" CLOB,
	"SAMPLE_CLASSIFICATION" VARCHAR2(255 CHAR),
	"SAMPLE_DESC" CLOB,
	"SEQUENCEDATA_ARCHIVED" DATE,
	"SEQUENCEDATA_RECEIVED" DATE,
	"SUBMITTER_ID" NUMBER(19,0),
	"PHENOTYPE" VARCHAR2(255 CHAR),
	"ENVIRONMENT_BIOME" VARCHAR2(255 CHAR),
	"ENVIRONMENT_FEATURE" VARCHAR2(255 CHAR),
	"ENVIRONMENT_MATERIAL" VARCHAR2(255 CHAR),
	"LAT_LON" VARCHAR2(255 CHAR),
	"STUDY_ID" NUMBER(19,0),
	"SAMPLE_NAME" VARCHAR2(255 CHAR),
	"SAMPLE_ALIAS" VARCHAR2(255 CHAR),
	"HOST_TAX_ID" NUMBER(10,0),
	"HOST_SEX" VARCHAR2(30 CHAR),
	"EXT_SAMPLE_ID" VARCHAR2(15 CHAR),
	"SPECIES" VARCHAR2(255 CHAR),
	"LATITUDE" NUMBER(7,4),
	"LONGITUDE" NUMBER(7,4),
	"LAST_UPDATE" DATE DEFAULT CURRENT_DATE,
	"SUBMISSION_ACCOUNT_ID" VARCHAR2(15 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB"
 LOB ("MISC") STORE AS BASICFILE (
  TABLESPACE "EMG_TAB" ENABLE STORAGE IN ROW CHUNK 16384 RETENTION
  NOCACHE LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT))
 LOB ("SAMPLE_DESC") STORE AS BASICFILE (
  TABLESPACE "EMG_TAB" ENABLE STORAGE IN ROW CHUNK 16384 RETENTION
  NOCACHE LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;

   COMMENT ON COLUMN "EMG"."SAMPLE"."DTYPE" IS 'data type, This is a very coarse grain measure of the evironment from which the sample was taken, host associated, ecological, or man-made. this was originaly thought to be required to enable a differential display for each data type on the browser, it may not be required.';
   COMMENT ON COLUMN "EMG"."SAMPLE"."SAMPLE_ID" IS 'The unique identifier assigned by a trigger on the database that assignes the next number in the series. This is effectively an EMG accession number, but we have no intention of ever discolsing this to external users.';
   COMMENT ON COLUMN "EMG"."SAMPLE"."ANALYSIS_COMPLETED" IS 'This is the date that analysis was (last) completed on. It is the trigger used in the current web-app to display the analysis results page, if this is null there will never be the button on the sample page to be able to show the analsyis results.';
   COMMENT ON COLUMN "EMG"."SAMPLE"."COLLECTION_DATE" IS 'The date the sample was collected, this value is now also present in the sample_ann table, and so can be deleted from this table AFTER the web-app has been changed to get the date from the sample_ann table instead.';
   COMMENT ON COLUMN "EMG"."SAMPLE"."GEO_LOC_NAME" IS 'The (country) name of the location the sample was collected from, this value is now also present in the sample_ann table, and so can be deleted from this table AFTER the web-app has been changed to get the data from the sample_ann table instead.';
   COMMENT ON COLUMN "EMG"."SAMPLE"."SAMPLE_CLASSIFICATION" IS 'The classification of the sample using the GOLD classification system, this value is now also present in the sample_ann table, and so can be deleted from this table AFTER the web-app has been changed to get the data from the sample_ann table instead.';
   COMMENT ON COLUMN "EMG"."SAMPLE"."SUBMITTER_ID" IS 'Deprecated and replaced by column submissionAccountId. Not in use by the web application any more';
   COMMENT ON COLUMN "EMG"."SAMPLE"."SUBMISSION_ACCOUNT_ID" IS 'Defines which users do have permission to access that sample/study. It is a reference to ERAPRO''s submission_account table';
  GRANT SELECT ON "EMG"."SAMPLE" TO "EMGAPP";
  GRANT SELECT ON "EMG"."SAMPLE" TO "HUDENISE";
  GRANT SELECT ON "EMG"."SAMPLE" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."SAMPLE" TO "EMG_USER";
  GRANT SELECT ON "EMG"."SAMPLE" TO "EMG_USER";
  GRANT INSERT ON "EMG"."SAMPLE" TO "EMG_USER";
  GRANT DELETE ON "EMG"."SAMPLE" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table SAMPLE_ANN
--------------------------------------------------------

  CREATE TABLE "EMG"."SAMPLE_ANN"
   (	"SAMPLE_ID" NUMBER(19,0),
	"VAR_VAL_CV" VARCHAR2(60 CHAR),
	"UNITS" VARCHAR2(25 CHAR),
	"VAR_ID" NUMBER(19,0),
	"VAR_VAL_UCV" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;

   COMMENT ON COLUMN "EMG"."SAMPLE_ANN"."SAMPLE_ID" IS 'Internal sample ID from SAMPLE table';
   COMMENT ON COLUMN "EMG"."SAMPLE_ANN"."VAR_VAL_CV" IS 'The value of the variable defined in VAR_ID where that variable must use a controlled vocabulary, this value must be in GSC_CV_CV';
   COMMENT ON COLUMN "EMG"."SAMPLE_ANN"."UNITS" IS 'The UNITS of the value given in VAR_VAL_UCV';
   COMMENT ON COLUMN "EMG"."SAMPLE_ANN"."VAR_ID" IS 'The variable ID from the VARIABLE_NAMES table';
   COMMENT ON COLUMN "EMG"."SAMPLE_ANN"."VAR_VAL_UCV" IS 'The value for the varible defined by VAR_ID';
   COMMENT ON TABLE "EMG"."SAMPLE_ANN"  IS 'Created by Chris 23-5-2011. This table maybe used to contain all additional sample metadata not currently held in HB-SAMPLE.';
  GRANT SELECT ON "EMG"."SAMPLE_ANN" TO "EMGAPP";
  GRANT SELECT ON "EMG"."SAMPLE_ANN" TO "HUDENISE";
  GRANT SELECT ON "EMG"."SAMPLE_ANN" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."SAMPLE_ANN" TO "EMG_USER";
  GRANT SELECT ON "EMG"."SAMPLE_ANN" TO "EMG_USER";
  GRANT INSERT ON "EMG"."SAMPLE_ANN" TO "EMG_USER";
  GRANT DELETE ON "EMG"."SAMPLE_ANN" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table SAMPLE_ANN_TEMP
--------------------------------------------------------

  CREATE TABLE "EMG"."SAMPLE_ANN_TEMP"
   (	"SAMPLE_ID" NUMBER(19,0),
	"VAR_VAL_CV" VARCHAR2(60 CHAR),
	"UNITS" VARCHAR2(25 CHAR),
	"VAR_ID" NUMBER(19,0),
	"VAR_VAL_UCV_OLD" CLOB,
	"VAR_VAL_UCV" VARCHAR2(4000 CHAR)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB"
 LOB ("VAR_VAL_UCV_OLD") STORE AS BASICFILE (
  TABLESPACE "EMG_TAB" ENABLE STORAGE IN ROW CHUNK 16384 PCTVERSION 10
  NOCACHE LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
--------------------------------------------------------
--  DDL for Table SAMPLE_PUBLICATION
--------------------------------------------------------

  CREATE TABLE "EMG"."SAMPLE_PUBLICATION"
   (	"SAMPLE_SAMPLE_ID" NUMBER(19,0),
	"PUBLICATIONS_PUB_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;

   COMMENT ON COLUMN "EMG"."SAMPLE_PUBLICATION"."SAMPLE_SAMPLE_ID" IS 'sample_id from the sample table, of the sample associated with this publication';
   COMMENT ON COLUMN "EMG"."SAMPLE_PUBLICATION"."PUBLICATIONS_PUB_ID" IS 'publication ID from publication table';
  GRANT SELECT ON "EMG"."SAMPLE_PUBLICATION" TO "EMGAPP";
  GRANT SELECT ON "EMG"."SAMPLE_PUBLICATION" TO "HUDENISE";
  GRANT SELECT ON "EMG"."SAMPLE_PUBLICATION" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."SAMPLE_PUBLICATION" TO "EMG_USER";
  GRANT SELECT ON "EMG"."SAMPLE_PUBLICATION" TO "EMG_USER";
  GRANT INSERT ON "EMG"."SAMPLE_PUBLICATION" TO "EMG_USER";
  GRANT DELETE ON "EMG"."SAMPLE_PUBLICATION" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table STUDY
--------------------------------------------------------

  CREATE TABLE "EMG"."STUDY"
   (	"STUDY_ID" NUMBER(19,0),
	"CENTRE_NAME" VARCHAR2(255 CHAR),
	"EXPERIMENTAL_FACTOR" VARCHAR2(255 CHAR),
	"IS_PUBLIC" NUMBER(1,0),
	"NCBI_PROJECT_ID" NUMBER(19,0),
	"PUBLIC_RELEASE_DATE" DATE,
	"STUDY_ABSTRACT" CLOB,
	"EXT_STUDY_ID" VARCHAR2(18 CHAR),
	"STUDY_NAME" VARCHAR2(255 CHAR),
	"STUDY_LINKOUT" VARCHAR2(255 CHAR),
	"STUDY_TYPE" NUMBER(10,0),
	"SUBMITTER_ID" NUMBER(19,0) DEFAULT 0,
	"STUDY_STATUS" VARCHAR2(30 CHAR),
	"DATA_ORIGINATION" VARCHAR2(20 CHAR),
	"AUTHOR_EMAIL" VARCHAR2(100 CHAR),
	"AUTHOR_NAME" VARCHAR2(100 CHAR),
	"LAST_UPDATE" DATE DEFAULT CURRENT_DATE,
	"SUBMISSION_ACCOUNT_ID" VARCHAR2(15 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB"
 LOB ("STUDY_ABSTRACT") STORE AS BASICFILE (
  TABLESPACE "EMG_TAB" ENABLE STORAGE IN ROW CHUNK 16384 RETENTION
  NOCACHE LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;

   COMMENT ON COLUMN "EMG"."STUDY"."CENTRE_NAME" IS 'The center_name used by SRA, it must be in the SRA schema, table CV_CENTER_NAME, which also should contain the description of that acronym (but doesn''t always!)';
   COMMENT ON COLUMN "EMG"."STUDY"."EXPERIMENTAL_FACTOR" IS 'This is metadata about the study, its to give an easy look up for time series studies or things where the study wass designed to test a particular variable, e.g. time, depth, disease etc...';
   COMMENT ON COLUMN "EMG"."STUDY"."IS_PUBLIC" IS '1 for public, 0 for private (As of Aug2012 this is set manually in both Production and Web databases)';
   COMMENT ON COLUMN "EMG"."STUDY"."NCBI_PROJECT_ID" IS 'This is for an X-ref to the NCBI projects ID, which is now BioProjects, not used very much and should be moved to an xref table';
   COMMENT ON COLUMN "EMG"."STUDY"."PUBLIC_RELEASE_DATE" IS 'The date originally specified by the submitter of when their data should be released to public, can be changed by submitter in SRA and we should sync with SRA.';
   COMMENT ON COLUMN "EMG"."STUDY"."STUDY_ABSTRACT" IS 'The submitter provided description of the project/study.';
   COMMENT ON COLUMN "EMG"."STUDY"."EXT_STUDY_ID" IS 'This is the external (non-EMG) ID for the study, i.e. its the SRA study ID which always starts ERP or SRP (or DRP)';
   COMMENT ON COLUMN "EMG"."STUDY"."STUDY_NAME" IS 'The human readable name of the study, as presented on EMG web pages, can be submitter provided or curated from details provided in submission';
   COMMENT ON COLUMN "EMG"."STUDY"."STUDY_LINKOUT" IS 'details of any URL links to study information outside EMG control';
   COMMENT ON COLUMN "EMG"."STUDY"."STUDY_TYPE" IS 'must be from a CV of Environmental, host, man made';
   COMMENT ON COLUMN "EMG"."STUDY"."SUBMITTER_ID" IS 'Deprecated and replaced by column submissionAccountId. Not in use by the web application any more';
   COMMENT ON COLUMN "EMG"."STUDY"."STUDY_STATUS" IS 'not used, should be deprecated';
   COMMENT ON COLUMN "EMG"."STUDY"."DATA_ORIGINATION" IS 'Where did the data come from, this could be HARVESTED for stuff taken from SRA, or SUBMITTED for stuff that is brokered to SRA through EMG';
   COMMENT ON COLUMN "EMG"."STUDY"."AUTHOR_EMAIL" IS 'Email address of contact person for study, WILL be shown publicly on Study page';
   COMMENT ON COLUMN "EMG"."STUDY"."AUTHOR_NAME" IS 'Name of contact person for study, WILL be shown publicly on Study page';
   COMMENT ON COLUMN "EMG"."STUDY"."LAST_UPDATE" IS 'The date any update was made to the row, this is auto-updated in PROD by a trigger, but not in any others (e.g. web, test or dev)';
   COMMENT ON COLUMN "EMG"."STUDY"."SUBMISSION_ACCOUNT_ID" IS 'Defines which users do have permission to access that sample/study. It is a reference to ERAPRO''s submission_account table';
  GRANT SELECT ON "EMG"."STUDY" TO "EMGAPP";
  GRANT SELECT ON "EMG"."STUDY" TO "HUDENISE";
  GRANT SELECT ON "EMG"."STUDY" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."STUDY" TO "EMG_USER";
  GRANT SELECT ON "EMG"."STUDY" TO "EMG_USER";
  GRANT INSERT ON "EMG"."STUDY" TO "EMG_USER";
  GRANT DELETE ON "EMG"."STUDY" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table STUDY_PUBLICATION
--------------------------------------------------------

  CREATE TABLE "EMG"."STUDY_PUBLICATION"
   (	"STUDY_STUDY_ID" NUMBER(19,0),
	"PUBLICATIONS_PUB_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 131072 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB" ;

   COMMENT ON COLUMN "EMG"."STUDY_PUBLICATION"."STUDY_STUDY_ID" IS 'the study id of the study with this publication';
   COMMENT ON COLUMN "EMG"."STUDY_PUBLICATION"."PUBLICATIONS_PUB_ID" IS 'publication ID from the publication table';
  GRANT SELECT ON "EMG"."STUDY_PUBLICATION" TO "EMGAPP";
  GRANT SELECT ON "EMG"."STUDY_PUBLICATION" TO "HUDENISE";
  GRANT SELECT ON "EMG"."STUDY_PUBLICATION" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."STUDY_PUBLICATION" TO "EMG_USER";
  GRANT SELECT ON "EMG"."STUDY_PUBLICATION" TO "EMG_USER";
  GRANT INSERT ON "EMG"."STUDY_PUBLICATION" TO "EMG_USER";
  GRANT DELETE ON "EMG"."STUDY_PUBLICATION" TO "EMG_USER";
--------------------------------------------------------
--  DDL for Table VARIABLE_NAMES
--------------------------------------------------------

  CREATE TABLE "EMG"."VARIABLE_NAMES"
   (	"VAR_NAME" VARCHAR2(50 BYTE),
	"DEFINITION" CLOB,
	"VALUE_SYNTAX" VARCHAR2(250 CHAR),
	"ALIAS" VARCHAR2(30 BYTE),
	"AUTHORITY" VARCHAR2(30 BYTE),
	"SRA_XML_ATTRIBUTE" VARCHAR2(30 BYTE),
	"REQUIRED_FOR_MIMARKS_COMPLIANC" VARCHAR2(1 BYTE),
	"REQUIRED_FOR_MIMS_COMPLIANCE" VARCHAR2(1 BYTE),
	"GSC_ENV_PACKAGES" VARCHAR2(250 BYTE),
	"COMMENTS" VARCHAR2(250 BYTE),
	"VAR_ID" NUMBER(19,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "EMG_TAB"
 LOB ("DEFINITION") STORE AS BASICFILE (
  TABLESPACE "EMG_TAB" ENABLE STORAGE IN ROW CHUNK 16384 RETENTION
  NOCACHE LOGGING
  STORAGE(INITIAL 81920 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;

   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."VAR_NAME" IS 'Unique human readable name as given by GSC (or other authority)';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."DEFINITION" IS 'Definition of variable, as given by GSC (or other authority)';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."VALUE_SYNTAX" IS 'how the GSC (or other authority) has defined the value for the term should be given';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."ALIAS" IS 'Short name, or INSDC name given by GSC, should be less than 20char and contain no spaces';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."AUTHORITY" IS 'person or organisation that created/defined the variable (usualy GSC)';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."SRA_XML_ATTRIBUTE" IS 'Where the ATTRIBUTE should be in the SRA XML schema, (NB currently (Aug2012) almost everything goes in SRA.SAMPLE which is technically wrong!)';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."REQUIRED_FOR_MIMARKS_COMPLIANC" IS 'If a value for the variable is required for GSC MIMARKS compliance (as of Aug2012)';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."REQUIRED_FOR_MIMS_COMPLIANCE" IS 'Is a value required for GSC MIMS compliance (as of Aug 2012)';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."GSC_ENV_PACKAGES" IS 'which (if any) of the GSC environmental packages is this variable part of';
   COMMENT ON COLUMN "EMG"."VARIABLE_NAMES"."VAR_ID" IS ' variable identifier, unique sequenctial number auto generated';
  GRANT SELECT ON "EMG"."VARIABLE_NAMES" TO "EMGAPP";
  GRANT SELECT ON "EMG"."VARIABLE_NAMES" TO "HUDENISE";
  GRANT SELECT ON "EMG"."VARIABLE_NAMES" TO "EMG_READ";
  GRANT UPDATE ON "EMG"."VARIABLE_NAMES" TO "EMG_USER";
  GRANT SELECT ON "EMG"."VARIABLE_NAMES" TO "EMG_USER";
  GRANT INSERT ON "EMG"."VARIABLE_NAMES" TO "EMG_USER";
  GRANT DELETE ON "EMG"."VARIABLE_NAMES" TO "EMG_USER";