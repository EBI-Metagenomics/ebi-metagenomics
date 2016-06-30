-- MySQL insert statements for pipeline release 3.0

-- Author: Maxim Scheremetjew, EMBL-EBI, InterPro

-- Populate pipeline_release table
INSERT INTO emg.PIPELINE_RELEASE (description,changes,release_version,release_date) values ('Release of version 3.0','Major upgrade. Updated the following binaries: InterProScan, FragGeneScan, QIIME, Trimmomatic. Added new steps for producing quality control statistics and for tRNA selection.','3.0','2016-06-30');

-- Populate pipeline_tool table
INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('QIIME','An open-source bioinformatics pipeline for performing taxonomic analysis from raw DNA sequencing data.','http://qiime.org/','1.9.1','./qiime-1.9.1-wrapper.sh {1} {2} {3} {4}','/panfs/nobackup/production/metagenomics/pipeline/tools/pipeline-version-3/qiime-1.9.1/qiime-1.9.1-wrapper.sh');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('InterProScan','A sequence analysis application (nucleotide and protein sequences) that combines different protein signature recognition methods into one resource.','https://github.com/ebi-pf-team/interproscan/wiki','5.19-58.0','./interproscan.sh --appl PfamA,TIGRFAM,PRINTS,PrositePatterns,Gene3d --goterms --pathways -f tsv -o {1}_out.tsv -i {1}','/panfs/nobackup/production/metagenomics/pipeline/tools/interproscan-5/interproscan-5.19-58.0');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('Trimmomatic','A flexible read trimming tool.','http://www.usadellab.org/cms/?page=trimmomatic','0.35','java -classpath {0}/Trimmomatic-0.35/trimmomatic-0.35.jar org.usadellab.trimmomatic.TrimmomaticSE -threads 8 -phred33 {1} {2} LEADING:3 TRAILING:3 SLIDINGWINDOW:4:15','/panfs/nobackup/production/metagenomics/pipeline/tools/pipeline-version-3/Trimmomatic-0.35/trimmomatic-0.35.jar');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('FragGeneScan','An application for finding (fragmented) genes in short reads.','http://omics.informatics.indiana.edu/FragGeneScan/','1.20','./FragGeneScan -s {1} -o {1}_CDS -w 0 -t illumina_5 -p 8','/panfs/nobackup/production/metagenomics/pipeline/tools/FragGeneScan1.20/FragGeneScan');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('SeqPrep','A program to merge paired end Illumina reads that are overlapping into a single longer read.','https://github.com/jstjohn/SeqPrep','1.1','?','/nfs/seqdb/production/interpro/development/metagenomics/pipeline/tools/bin/SeqPrep-1.1');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('HMMER','A computer program for biosequence analysis using profile hidden Markov models.','http://hmmer.org','v3.1b1','./nhmmer --tblout $outpath/${file}_tRNAselect.txt --cpu 4 -T 20 tRNA.hmm $outpath/${file}.fna > /dev/null','/panfs/nobackup/production/metagenomics/pipeline/releases/mgpipeline-v3.0-rc1/analysis-pipeline/python/tools/RNASelector-1.0/binaries/64_bit_Linux/HMMER3.1b1');

-- Populate pipeline_release_tool table
-- SeqPrep
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (3,17,0,'Paired-end overlapping reads are merged - we do not perform assembly.');
-- Trimmomatic
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (3,15,1.1,'Low quality trimming (low quality ends and sequences with > 10% undetermined nucleotides removed). Adapter sequences removed using Biopython SeqIO package.');
-- Biopython
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (3,11,1.2,'Sequences < 100 nucleotides in length removed.');
-- HMMER
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (3,18,2,'Identification and masking of ncRNAs.');
-- FragGeneScan
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (3,16,3,'Reads with predicted coding sequences (pCDS) above 60 nucleotides in length.');
-- InterProScan
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (3,14,4,'Matches are generated against predicted CDS, using a sub set of databases (Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d) from InterPro release 57.0. A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided. It is generated using a reduced list of GO terms called GO slim (version <a href="http://www.geneontology.org/ontology/subsets/goslim_metagenomics.obo" class="ext">goslim_goa</a>).');
-- QIIME
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (3,13,5,'16s rRNA are annotated using the Greengenes reference database (default closed-reference OTU picking protocol with Greengenes 13.8 reference with reverse strand matching enabled).');