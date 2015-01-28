--DDL statements

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

CREATE TABLE pipeline_release (
         pipeline_id      NUMBER(4)  PRIMARY KEY,
         description      CLOB,
         changes          CLOB  NOT NULL,
         release_version  VARCHAR2(20)  NOT NULL,
         release_date     DATE  NOT NULL
         );

GRANT SELECT ON pipeline_release TO "EMGAPP";


CREATE SEQUENCE  PIPELINE_RELEASE_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;
GRANT SELECT ON PIPELINE_RELEASE_SEQ TO "EMGAPP";

CREATE TABLE pipeline_tool (
         tool_id            NUMBER(7)  PRIMARY KEY,
         tool_name          VARCHAR2(20),
         version            VARCHAR2(20)  NOT NULL,
         exe_command        VARCHAR2(20)  NOT NULL,
         installation_dir   VARCHAR2(60)  NOT NULL,
         configuration_file CLOB,
         notes              CLOB
         );

CREATE TABLE pipeline_release_tool (
         pipeline_id  NUMBER(4) PRIMARY KEY,
         tool_id      NUMBER(7) PRIMARY KEY
         );


-- insert statements
Insert into pipeline_release (pipeline_id,description,changes,release_version,release_date) values (1,'test','test','v2.1',to_date('19960725','YYYYMMDD'));

