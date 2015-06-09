--EMG schema extension to support biome search (represents an implementation of the preorder tree traversal model)

--Author: Maxim Scheremetjew, EMBL-EBI, InterPro

--Table Drop statements
DROP TABLE biome_hierarchy_tree cascade constraints;
DROP TABLE biome_study cascade constraints;

--Sequence drop statements
DROP SEQUENCE "EMG"."BIOME_STUDY_SEQ";

--DDL statements for tables biome_hierarchy_tree and biome_study
--Login as emg schema owner and run the following statements

-- biome_hierarchy_tree table
CREATE TABLE biome_hierarchy_tree (
         biome_id NUMBER(4)     PRIMARY KEY,
         biome    VARCHAR2(40)  NOT NULL,
         parent   VARCHAR2(40),
         lft      NUMBER(4)     NOT NULL,
         rgt      NUMBER(4)     NOT NULL
         );

GRANT SELECT ON biome_hierarchy_tree TO "EMGAPP";
GRANT SELECT ON biome_hierarchy_tree TO "EMG_USER";
GRANT ALTER ON biome_hierarchy_tree TO "EMG_USER";
GRANT SELECT ON biome_hierarchy_tree TO "EMG_READ";
GRANT DELETE ON biome_hierarchy_tree TO "EMG_USER";


-- biome_study table
CREATE TABLE biome_study (
         study_id NUMBER(10)    PRIMARY KEY,
         accession      VARCHAR2(40)  NOT NULL,
         biome_id NUMBER(4)     NOT NULL
                                CONSTRAINT biome_id_fk REFERENCES biome_hierarchy_tree (biome_id)
         );

GRANT SELECT ON biome_study TO "EMGAPP";
GRANT SELECT ON biome_study TO "EMG_USER";
GRANT ALTER ON biome_study TO "EMG_USER";
GRANT SELECT ON biome_study TO "EMG_READ";
GRANT DELETE ON biome_study TO "EMG_USER";

CREATE SEQUENCE  BIOME_STUDY_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 2 NOORDER  NOCYCLE;

GRANT SELECT ON BIOME_STUDY_SEQ TO "EMGAPP";
GRANT SELECT ON BIOME_STUDY_SEQ TO "EMG_USER";
GRANT ALTER ON BIOME_STUDY_SEQ TO "EMG_USER";
GRANT SELECT ON BIOME_STUDY_SEQ TO "EMG_READ";

create or replace
trigger "EMG"."BIOME_STUDY_TRIGGER"
BEFORE INSERT ON BIOME_STUDY
FOR EACH ROW
BEGIN
if :new.STUDY_ID is null then
  select BIOME_STUDY_SEQ.nextval into :new.STUDY_ID
  from dual;
END IF;
END;
/

INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (1,null,'Root',1,24);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (2,'Root','Environmental',2,7);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (3,'Environmental','Air',3,4);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (4,'Environmental','Terrestrial',5,6);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (5,'Root','Engineered',8,13);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (6,'Engineered','Lab synthesis',9,10);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (7,'Engineered','Wastewater',11,12);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (8,'Root','Host-associated',14,23);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (9,'Host-associated','Human',15,20);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (10,'Human','Skin',16,17);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (11,'Human','Digestive system',18,19);
INSERT INTO biome_hierarchy_tree (biome_id,parent,biome,lft,rgt) values (12,'Host-associated','Fish',21,22);

INSERT INTO biome_study (accession,biome_id)
  SELECT  study.ext_study_id as accession,
          10 as biome_id
  FROM study where study_id<500;