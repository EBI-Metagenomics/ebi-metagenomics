--Added additional analysis status
--Updated pipeline tool versions numbers and descriptions

--Author: Maxim Scheremetjew, EMBL-EBI, InterPro

INSERT INTO analysis_status (analysis_status_id,analysis_status) values (5,'suppressed');

UPDATE pipeline_tool SET version='3.2.2' WHERE version='open-3.2.2';
UPDATE pipeline_tool SET version='5.0-beta' WHERE version='5.0 (beta release)';
UPDATE pipeline_tool SET description='An application for finding fragmented genes in short reads.' WHERE tool_name='FragGeneScan';
UPDATE pipeline_tool SET description='A set of freely available tools for biological computation written in Python.' WHERE tool_name='Biopython';
UPDATE pipeline_tool SET description='A computer program for selecting ribosomal RNA encoding sequences from metagenomic and metatranscriptomic shotgun libraries.' WHERE tool_name='rRNASelector';