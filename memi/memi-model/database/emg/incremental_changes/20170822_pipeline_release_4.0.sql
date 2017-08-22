-- MySQL insert statements for pipeline release 4.0

-- Author: Maxim Scheremetjew, EMBL-EBI, InterPro

-- Populate pipeline_release table
INSERT INTO emg.PIPELINE_RELEASE (description,changes,release_version,release_date) values ('Release of version 4.0','Major upgrade. rRNASelector, which was previously used to identify 16S rRNA genes by the pipeline, was replaced with Infernal using a library of competing ribosomal RNA hidden Markov models. This allows accurate identification of both large and small subunit ribosomal ribonucleic acid genes, including the eukaryotic 18S rRNA gene. The QIIME taxonomic classification component was replaced with MapSeq. , which provides fast and accurate classification of reads, and provides corresponding confidence scores for assignment at each taxonomic level. The Greengenes reference database was replaced with SILVA SSU / LSU version 128, enabling classification of eukaryotes, remapped to a 7-level taxonomy. Prodigal version 2.6.3 was added to run alongside FragGeneScan as part of a combined gene caller when processing assembled sequences. InterProScan was updated to version 5.25 (based on InterPro release 64.0).','4.0','2017-08-28');

-- Populate pipeline_tool table
INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('InterProScan','A sequence analysis application (nucleotide and protein sequences) that combines different protein signature recognition methods into one resource.','https://github.com/ebi-pf-team/interproscan/wiki','5.25-64.0','./interproscan.sh -dp --appl PfamA,TIGRFAM,PRINTS,PrositePatterns,Gene3d --goterms --pathways -f tsv -o {1}_out.tsv -i {1}','/hps/nobackup/production/metagenomics/pipeline/tools-v4/interproscan-5.25-64.0/');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('Prodigal','Prodigal (Prokaryotic Dynamic Programming Genefinding Algorithm) is a microbial (bacterial and archaeal) gene finding program.','https://github.com/hyattpd/prodigal/wiki','2.6.3','./prodigal -i {0} -o {1} -f sco -d {1}.ffn -a {1}.faa','/hps/nobackup/production/metagenomics/pipeline/tools-v4/Prodigal-2.6.3/');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('Infernal','Infernal ("INFERence of RNA ALignment") is for searching DNA sequence databases for RNA structure and sequence similarities. It is an implementation of a special case of profile stochastic context-free grammars called covariance models (CMs). A CM is like a sequence profile, but it scores a combination of sequence consensus and RNA secondary structure consensus, so in many cases, it is more capable of identifying RNA homologs that conserve their secondary structure more than their primary sequence.','http://eddylab.org/infernal/','1.1.2','../infernal-1.1.2/src/cmsearch --hmmonly --noali --cut_ga --cpu 4 --tblout {1} -Z 1000 -o {2} {3} {4}','/hps/nobackup/production/metagenomics/pipeline/tools-v4/infernal-1.1.2/');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('MAPseq','MAPseq is a set of fast and accurate sequence read classification tools designed to assign taxonomy and OTU classifications to ribosomal RNA sequences.','https://github.com/jfmrod/MAPseq/','1.2','./mapseq -nthreads 8 -outfmt simple {1} <customref.fasta> <customref.tax>','/hps/nobackup/production/metagenomics/pipeline/tools-v4/mapseq-1.2-linux/');

INSERT INTO emg.PIPELINE_TOOL (tool_name,description,web_link,version,exe_command,installation_dir) values ('cmsearch deoverlap script','A tool, which removes lower scoring overlaps from cmsearch --tblout files.','https://raw.githubusercontent.com/nawrockie/cmsearch_tblout_deoverlap/master/cmsearch-deoverlap.pl','X.X','./cmsearch_deoverlap.pl --clanin <claninfo-file> <matches-file>','/hps/nobackup/production/metagenomics/production-scripts/current/mgportal/analysis-pipeline/python/tools/RNASelection/scripts/');

-- Populate pipeline_release_tool table
-- SeqPrep
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,17,0,'Paired-end overlapping reads are merged - we do not perform assembly.');
-- Trimmomatic
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,15,1.1,'Low quality trimming (low quality ends and sequences with > 10% undetermined nucleotides removed). Adapter sequences removed using Biopython SeqIO package.');
-- Biopython
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,11,1.2,'Sequences < 100 nucleotides in length removed.');
-- Infernal
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,21,2,'Identification of ncRNAs.');
-- cmsearch deoverlap
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,23,2,'Removes lower scoring overlaps from cmsearch --tblout files.');
-- FragGeneScan
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,16,3,'Reads with predicted coding sequences (pCDS) above 60 nucleotides in length.');
-- Prodigal
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,20,3,'??');
-- InterProScan
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,19,4,'Matches are generated against predicted CDS, using a sub set of databases (Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d) from InterPro release 64.0. A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided. It is generated using a reduced list of GO terms called GO slim (version <a href="http://www.geneontology.org/ontology/subsets/goslim_metagenomics.obo" class="ext">goslim_goa</a>).');
-- MAPseq
INSERT INTO emg.PIPELINE_RELEASE_TOOL (pipeline_id, tool_id, tool_group_id, how_tool_used_desc) values (4,22,5,'SSU and LSU rRNA are annotated using SILVAs SSU/LSU version 128 reference database, enabling classification of eukaryotes, remapped to a 7-level taxonomy.');


update emg.PIPELINE_RELEASE_TOOL set HOW_TOOL_USED_DESC='Matches are generated against predicted CDS, using a sub set of databases (Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d) from InterPro release 58.0. A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided. It is generated using a reduced list of GO terms called GO slim (version <a href="http://www.geneontology.org/ontology/subsets/goslim_metagenomics.obo" class="ext">goslim_goa</a>).' where PIPELINE_ID=3 and TOOL_ID=14;

update emg.PIPELINE_TOOL set WEB_LINK='https://sourceforge.net/projects/fraggenescan/' where TOOL_ID=16;