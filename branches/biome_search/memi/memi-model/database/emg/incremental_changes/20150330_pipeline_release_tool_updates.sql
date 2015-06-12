-- Additions to the pipeline_release_tool TABLE

ALTER TABLE pipeline_release_tool MODIFY(tool_group_id number(4) null);
UPDATE pipeline_release_tool SET tool_group_id=null;
COMMIT;
ALTER TABLE pipeline_release_tool MODIFY(tool_group_id number(6,3));
ALTER TABLE pipeline_release_tool ADD (how_tool_used_desc varchar2(1000));

COMMENT ON COLUMN pipeline_release_tool.how_tool_used_desc IS
'Text description on how this version of the tool is used in this version of the pipeline.';

-- Repopulate the new/changed columns in the PIPELINE_RELEASE_TOOL table
-- For pipeline version 1.0
--Trimmomatic
UPDATE pipeline_release_tool 
SET tool_group_id = 1.1, how_tool_used_desc = 'Low quality trimming (low quality ends and sequences with > 10% undetermined nucleotides removed). Adapter sequences removed using Biopython SeqIO package.'
WHERE pipeline_id = 1 AND tool_id = 1;

-- FragGeneScan
UPDATE pipeline_release_tool 
SET tool_group_id = 3.0, how_tool_used_desc = 'Reads with predicted coding sequences (pCDS) above 60 nucleotides in length.'
WHERE pipeline_id = 1 AND tool_id = 2;

-- InterProScan
UPDATE pipeline_release_tool 
SET tool_group_id = 4.0, how_tool_used_desc = 'Matches are generated against predicted CDS, using a subSET of databases (Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d) from InterPro release 31.0. A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided. It is generated using a reduced list of GO terms called GO slim (version <a href="http://www.geneontology.org/ontology/subSETs/goslim_metagenomics.obo" class="ext">goslim_goa</a>).'
WHERE pipeline_id = 1 AND tool_id = 3;

-- UCLUST
UPDATE pipeline_release_tool 
SET tool_group_id = 1.3, how_tool_used_desc = 'Duplicate sequences removed - clustered on 99% identity for LS454 or on 50 nucleotides prefix identity (using pick_otus.py script in Qiime v1.15).'
WHERE pipeline_id = 1 AND tool_id = 4;

-- RepeatMasker
UPDATE pipeline_release_tool 
SET tool_group_id = 1.4, how_tool_used_desc = 'Repeat masked - removed reads with 50% or more nucleotides masked.'
WHERE pipeline_id = 1 AND tool_id = 5;

-- rRNASelector
UPDATE pipeline_release_tool 
SET tool_group_id = 2.0, how_tool_used_desc = 'Prokaryotic rRNA reads are filtered. We use the hidden Markov models to identify rRNA sequences.'
WHERE pipeline_id = 1 AND tool_id = 6;

-- QIIME
UPDATE pipeline_release_tool 
SET tool_group_id = 5.0, how_tool_used_desc = '16s rRNA are annotated using the Greengenes reference database (default de novo OTU picking protocol with Greengenes 12.10 reference with reverse strand matching enabled).'
WHERE pipeline_id = 1 AND tool_id = 7;

-- Biopython
UPDATE pipeline_release_tool 
SET tool_group_id = 1.2, how_tool_used_desc = 'Sequences < 100 nucleotides in length removed.'
WHERE pipeline_id = 1 AND tool_id = 8;


-- For pipeline version 2.0
--Trimmomatic
UPDATE pipeline_release_tool 
SET tool_group_id = 1.1, how_tool_used_desc = 'Low quality trimming (low quality ends and sequences with > 10% undetermined nucleotides removed). Adapter sequences removed using Biopython SeqIO package.'
WHERE pipeline_id = 2 AND tool_id = 1;

-- FragGeneScan
UPDATE pipeline_release_tool 
SET tool_group_id = 3.0, how_tool_used_desc = 'Reads with predicted coding sequences (pCDS) above 60 nucleotides in length.'
WHERE pipeline_id = 2 AND tool_id = 2;

-- rRNASelector
UPDATE pipeline_release_tool 
SET tool_group_id = 2.0, how_tool_used_desc = 'Prokaryotic rRNA reads are filtered. We use the hidden Markov models to identify rRNA sequences.'
WHERE pipeline_id = 2 AND tool_id = 9;

-- QIIME
UPDATE pipeline_release_tool 
SET tool_group_id = 5.0, how_tool_used_desc = '16s rRNA are annotated using the Greengenes reference database (default closed-reference OTU picking protocol with Greengenes 13.8 reference with reverse strand matching enabled).'
WHERE pipeline_id = 2 AND tool_id = 10;

-- Biopython
UPDATE pipeline_release_tool 
SET tool_group_id = 1.2, how_tool_used_desc = 'Sequences < 100 nucleotides in length removed.'
WHERE pipeline_id = 2 AND tool_id = 11;

-- InterProScan
UPDATE pipeline_release_tool 
SET tool_group_id = 4.0, how_tool_used_desc = 'Matches are generated against predicted CDS, using a subSET of databases (Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d) from InterPro release 31.0. A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided. It is generated using a reduced list of GO terms called GO slim (version <a href="http://www.geneontology.org/ontology/subSETs/goslim_metagenomics.obo" class="ext">goslim_goa</a>).'
WHERE pipeline_id = 2 AND tool_id = 12;

COMMIT;


ALTER TABLE pipeline_release_tool MODIFY(tool_group_id number(6,3) NOT NULL);
ALTER TABLE pipeline_release_tool MODIFY (how_tool_used_desc varchar2(1000) NOT NULL);
CREATE UNIQUE INDEX "pipeline_tool_group_uqidx" ON pipeline_release_tool (pipeline_id, tool_group_id);