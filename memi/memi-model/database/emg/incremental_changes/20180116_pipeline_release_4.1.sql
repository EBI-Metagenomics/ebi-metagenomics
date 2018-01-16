-- MySQL insert statements for pipeline release 4.1

-- Author: Maxim Scheremetjew, EMBL-EBI, InterPro

-- Populate pipeline_release table
INSERT INTO emg.PIPELINE_RELEASE (description,changes,release_version,release_date) values ('Release of version 4.1','Minor upgrade. Upgraded SeqPrep to v1.2. Upgraded MAPseq to v1.2.2. Rebuilt taxonomic reference database based on SILVA v132. Taxonomic assignments now also available in HDF5 format.','4.1','2018-01-17');

-- Populate pipeline_tool table
INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('SeqPrep','A program to merge paired end Illumina reads that are overlapping into a single longer read.','https://github.com/jstjohn/SeqPrep','1.2','?','/hps/nobackup/production/metagenomics/pipeline/tools-v4/SeqPrep-1.2/');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('MAPseq','MAPseq is a set of fast and accurate sequence read classification tools designed to assign taxonomy and OTU classifications to ribosomal RNA sequences.','https://github.com/jfmrod/MAPseq/','1.2.2','./mapseq -nthreads 1 -outfmt simple {1} <customref.fasta> <customref.tax>','/hps/nobackup/production/metagenomics/pipeline/tools-v4/mapseq-1.2.2-linux/');

-- Populate pipeline_release_tool table
-- SeqPrep
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,24,0,'Paired-end overlapping reads are merged - if you want your data assembled, email us.');
-- Trimmomatic
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,15,1.1,'Low quality trimming (low quality ends and sequences with > 10% undetermined nucleotides removed). Adapter sequences removed using Biopython SeqIO package.');
-- Biopython
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,11,1.2,'Sequences < 100 nucleotides in length removed.');
-- Infernal
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,21,2.1,'Identification of ncRNAs.');
-- cmsearch deoverlap
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,23,2.2,'Removes lower scoring overlaps from cmsearch --tblout files.');
-- FragGeneScan
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,16,3.1,'Run as a combined gene caller component, giving priority to Prodigal predictions in the case of assembled sequences or FragGeneScan for short reads (all predictions from the higher priority caller are used, supplemented by any non-overlapping regions predicted by the other).');
-- Prodigal
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,20,3.2,'');
-- InterProScan
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,19,4,'Matches are generated against predicted CDS, using a sub set of databases (Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d) from InterPro release 64.0. A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided. It is generated using a reduced list of GO terms called GO slim (version <a href="http://www.geneontology.org/ontology/subsets/goslim_metagenomics.obo" class="ext">goslim_goa</a>).');
-- MAPseq
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (5,25,5,'SSU and LSU rRNA are annotated using SILVAs SSU/LSU version 132 reference database, enabling classification of eukaryotes, remapped to a 7-level taxonomy.');
