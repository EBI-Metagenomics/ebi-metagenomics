import argparse
import csv
import os
import urllib
from urllib2 import URLError

if __name__ == "__main__":
    #    Parse script parameters
    parser = argparse.ArgumentParser(description="MGPortal bulk download tool.")
    parser.add_argument("-i", "--input_file",
        help="Tab-separated file, which contains the mapping between project, sample and run.",
        required=True)
    parser.add_argument("-o", "--output_path", help="Location where the download files are stored.",
        required=True)
    parser.add_argument("-v", "--version", help="Version of the pipeline used to generate the results.",
        required=True)
    parser.add_argument("-t", "--file_type",
        help="Possible file types are: InterProScan, ProcessedReads, ReadsWithPredictedCDS, ReadsWithMatches, "\
             "ReadsWithoutMatches, PredictedCDS, PredicatedCDSWithoutAnnotation, PredictedORFWithoutAnnotation",
        required=True)
    args = vars(parser.parse_args())

    input_file = args['input_file']
    file_type = args['file_type']
    output_path = args['output_path'] + "/" + file_type
    version = args['version']
    domain = None
    fileExtension = None

    # Set the result file domain (sequences or function) dependent on the file type
    # Set output file extension (tsv, faa or fasta) dependent on the file type
    if file_type == 'InterProScan':
        domain = "function"
        fileExtension = ".tsv.gz"
    elif file_type == 'PredictedCDS' or file_type == 'PredicatedCDSWithoutAnnotation':
        domain = "sequences"
        fileExtension = ".faa.gz"
    else:
        domain = "sequences"
        fileExtension = ".fasta.gz"

    # Parse the input file and iterate over each line (each run) and build the download link using the variables from above
    with open(input_file, 'r') as f:
        reader = csv.reader(f, delimiter='\t')
        for study_id, sample_id, run_id in reader:
            print study_id + ", " + sample_id + ", " + run_id

            if not os.path.exists(output_path):
                os.makedirs(output_path)

            output_file_name = output_path + "/" + run_id + "_" + file_type + fileExtension
            rootUrl = "https://wwwdev.ebi.ac.uk/metagenomics/projects/%s/samples/%s/runs/%s/results/versions/%s/%s/%s/chunks/1" % (
                study_id, sample_id, run_id, version, domain, file_type)

            print "Starting the download of the following file..."
            print rootUrl

            try: urllib.urlretrieve(rootUrl, output_file_name)
            except URLError as e:
                print e.reason
            print "Download finished."
    print "Program finished."