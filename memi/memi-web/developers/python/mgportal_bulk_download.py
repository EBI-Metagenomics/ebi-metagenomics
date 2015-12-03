'''
Created on 27/10/2015
@author: Maxim Scheremetjew
amended 4/11/2015 by Hubert DENISE
version: 1.0-rc1
'''

import argparse
import csv
import os
import urllib
from urllib2 import URLError

def _download_resource_by_url(url, output_file_name):
    """Kicks off a download and stores the file at the given path.
    Arguments:
    'url' -- Resource location.
    'output_file_name' -- Path of the output file.
    """
    print "Starting the download of the following file..."
    print url
    print "Saving file in:\n" + output_file_name

    try:
        urllib.urlretrieve(url, output_file_name)
    except URLError as url_error:
        print(url_error)
        raise
    except  IOError as io_error:
        print(io_error)
        raise
    print "Download finished."


def _get_number_of_chunks(study_id, sample_id, run_id, version, domain, file_type):
    """
    Returns the number of chunks for the given set of parameters (study, sample and run identifier).
    """
    print "Getting the number of chunks from the following URL..."
    url_get_number_of_chunks = "https://www.ebi.ac.uk/metagenomics/projects/%s/samples/%s/runs/%s/results/versions/%s/%s/%s/chunks" % (
        study_id, sample_id, run_id, version, domain, file_type)
    print url_get_number_of_chunks
    try:
        file_stream_handler = urllib.urlopen(url_get_number_of_chunks)
        result = int(file_stream_handler.read())
        print "Retrieved " + str(result) + " chunks."
        return result
    except URLError as url_error:
        print(url_error)
        raise
    except  IOError as io_error:
        print(io_error)
        raise
    except ValueError as e:
        print(e)
        print "Could not retrieve the number of chunks. Check the version number in the URL. Program will exit now."
        raise


def _print_program_settings(input_file, output_path, version, file_type):
    print "Running the program with the following setting...\n"
    print "Input file: " + input_file
    print "Output directory: " + output_path
    print "Analysis version: " + version
    print "Selected file types: " + file_type


if __name__ == "__main__":
    # Default list of available file types
    default_file_type_list = ["InterProScan", "GOAnnotations", "GOSlimAnnotations", "ProcessedReads",
                              "ReadsWithPredictedCDS", "ReadsWithMatches", "ReadsWithoutMatches", "PredictedCDS",
                              "PredictedCDSWithoutAnnotation", "PredictedORFWithoutAnnotation"]

    #Parse script parameters
    parser = argparse.ArgumentParser(description="MGPortal bulk download tool.")
    parser.add_argument("-i", "--input_file",
        help="Tab-separated file, which contains the mapping between project, sample and run.",
        required=True)
    parser.add_argument("-o", "--output_path", help="Location where the download files are stored.",
        required=True)
    parser.add_argument("-v", "--version", help="Version of the pipeline used to generate the results.",
        required=True)
    parser.add_argument("-t", "--file_type",
        help="Possible file types are: AllFunctional OR comma-separated list of supported file types: " + ', '.join(
            default_file_type_list) + " OR single file type.", required=False)
    args = vars(parser.parse_args())

    # Parse the path to the input file
    input_file = args['input_file']

    # Parse the values for the file type parameter
    selected_file_types_list = []
    if not args['file_type']:
        # If not specified use the default set of file types
        selected_file_types_list = default_file_type_list
    else:
        # Remove whitespaces
        selected_file_types_str = args['file_type'].replace(" ", "")
        # Set all functional result file types
        if selected_file_types_str == "AllFunctional":
            selected_file_types_list = default_file_type_list
        # Set defined file types
        elif len(selected_file_types_str.split(",")) > 1:
            selected_file_types_list = selected_file_types_str.split(",")
        # Set single file type
        else:
            selected_file_types_list.append(selected_file_types_str)

    # Parse the analysis version
    version = args['version']

    # Print out the program settings
    domain = None
    fileExtension = None
    for file_type in selected_file_types_list:
        # Boolean flag to indicate if a file type is chunked or not
        is_chunked = True
        # Set the result file domain (sequences or function) dependent on the file type
        # Set output file extension (tsv, faa or fasta) dependent on the file type
        if file_type == 'InterProScan':
            domain = "function"
            fileExtension = ".tsv.gz"
        elif file_type == 'GOSlimAnnotations' or file_type == 'GOAnnotations':
            domain = "function"
            fileExtension = ".csv"
            is_chunked = False
        elif file_type == 'PredictedCDS' or file_type == 'PredicatedCDSWithoutAnnotation':
            domain = "sequences"
            fileExtension = ".faa.gz"
        else:
            domain = "sequences"
            fileExtension = ".fasta.gz"
            # Parse the input file and iterate over each line (each run) and build the download link using the variables from above
        with open(input_file, 'r') as tsv_file:
            reader = csv.reader(tsv_file, delimiter='\t')
            for study_id, sample_id, run_id in reader:
                print study_id + ", " + sample_id + ", " + run_id

                output_path = args['output_path'] + "/" + study_id + "/" + file_type
                if not os.path.exists(output_path):
                    os.makedirs(output_path)

                if is_chunked:
                    number_of_chunks = _get_number_of_chunks(study_id, sample_id, run_id, version, domain, file_type)

                    for chunk in range(1, number_of_chunks + 1):
                        output_file_name = output_path + "/" + run_id.replace(" ", "").replace(",",
                            "-") + "_" + file_type + "_" + str(chunk) + fileExtension
                        rootUrl = "https://www.ebi.ac.uk/metagenomics/projects/%s/samples/%s/runs/%s/results/versions/%s/%s/%s/chunks/%s" % (
                            study_id, sample_id, run_id, version, domain, file_type, chunk)
                        _download_resource_by_url(rootUrl, output_file_name)
                else:
                    output_file_name = output_path + "/" + run_id.replace(" ", "").replace(",",
                        "-") + "_" + file_type + fileExtension
                    rootUrl = "https://www.ebi.ac.uk/metagenomics/projects/%s/samples/%s/runs/%s/results/versions/%s/%s/%s" % (
                        study_id, sample_id, run_id, version, domain, file_type)
                    _download_resource_by_url(rootUrl, output_file_name)

    print "Program finished."