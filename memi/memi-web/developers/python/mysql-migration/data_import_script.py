import os
import csv
import argparse

from mysql_db_connection import MySQLDBConnection

from db_access_object import DataAccessObject


def parseColumnNames(row, delimiter):
    return row.split(delimiter)


def createDatabaseEntry(column_names_list, row):
    result = {}
    for x in range(0, len(column_names_list)):
        key = column_names_list[x]
        value = row[x]
        result[key] = value
    return result


def create_data_type_dict(query_result):
    result = {}
    for row in query_result:
        column_name = row['COLUMN_NAME']
        data_type = row['DATA_TYPE']
        result[column_name] = data_type
    return result


if __name__ == "__main__":
    # Default list of available file types
    default_file_type_list = ["InterProScan", "GOAnnotations", "GOSlimAnnotations", "ProcessedReads",
                              "ReadsWithPredictedCDS", "ReadsWithMatches", "ReadsWithoutMatches", "PredictedCDS",
                              "PredictedCDSWithoutAnnotation", "PredictedORFWithoutAnnotation"]

    # Parse script parameters
    parser = argparse.ArgumentParser(description="MGPortal bulk download tool.")
    parser.add_argument("-i", "--input_file",
                        help="Tab-separated file, which contains the mapping between project, sample and run.",
                        required=True)
    args = vars(parser.parse_args())

    # Set database connection details
    config = {
        'user': 'admin',
        'password': 'b0SB8dgW',
        'host': 'mysql-vm-076.ebi.ac.uk',
        'port': '4499',
        'database': 'emg',
        'raise_on_warnings': True,
    }

    # Parse the input file path
    input_file = args['input_file']
    # Parse the table name
    input_file_basename = os.path.basename(input_file)
    list_of_words = input_file_basename.split('_')
    table_name = list_of_words[0]
    print "Reading in data export dump for table " + table_name

    db_connection = MySQLDBConnection(**config)
    data_access_object = DataAccessObject(db_connection)

    query_result = data_access_object._runQuery(
            "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + table_name + "'")

    # add_analysis_status = ("INSERT INTO ANALYSIS_STATUS "
    #                 "(ANALYSIS_STATUS_ID, ANALYSIS_STATUS) "
    #                 "VALUES (%s, %s)")
    # data_analysis_status = (7, 'test')
    #
    # data_access_object._run_insert_stmt(add_entry_stmt=add_analysis_status,entry_data=data_analysis_status)

    column_name_to_data_type_map = create_data_type_dict(query_result)

    delimiter = ','
    with open(input_file, 'r') as csv_file:
        reader = csv.reader(csv_file, delimiter=delimiter)
        row_counter = 1
        # List of strings
        column_names_list = []
        # List of dictionaries
        entry_list = []
        for row in reader:
            if row_counter == 1:
                # Parsing column names
                column_names_list = row
            else:
                entryDictionary = createDatabaseEntry(column_names_list, row)
                entry_list.append(entryDictionary)
            row_counter += 1

    print "Found " + str(len(entry_list)) + " entries."

    print "Creating insert statement..."
    # insert_stmt_prefix = str(
    #         "INSERT INTO " + table_name + " (" + ",".join(attribute_data_type_dict.keys()) + ") VALUES (")

    repeat_factor = len(column_name_to_data_type_map.keys())
    placeholders = ['%s'] * repeat_factor
    add_entry_stmt = ("INSERT INTO " + table_name +
                      " (" + ",".join(column_name_to_data_type_map.keys()) + ") VALUES (" + ",".join(
            placeholders) + ")")

    add_analysis_status = ("INSERT INTO ANALYSIS_STATUS "
                           "(ANALYSIS_STATUS_ID, ANALYSIS_STATUS) "
                           "VALUES (%s, %s)")

    add_entry_stmt = ("INSERT INTO " + table_name + " "
                      "(" + ",".join(column_name_to_data_type_map.keys()) + ") "
                      "VALUES (" + ",".join(placeholders) + ")")

    entry_data_list = []
    for entry in entry_list:
        entry_data = "("
        for key in column_name_to_data_type_map.keys():
            data_type = column_name_to_data_type_map[key]
            try:
                value = entry[key]
                if len(entry_data) > 1:
                    entry_data += ","
                # Set value to null if empty
                if len(value) == 0:
                    entry_data += "null"
                elif data_type == 'tinyint' or data_type == 'smallint' or data_type == 'int' or data_type == 'bigint':
                    entry_data += value
                elif data_type == 'varchar' or data_type == 'text' or data_type == 'longtext':
                    entry_data += "'" + value + "'"
                elif data_type == 'date' or data_type == 'datetime':
                    entry_data += "'" + value + "'"
                elif data_type == 'decimal':
                    entry_data += ")"
                else:
                    print "Could NOT find an option for the following data type: " + data_type
            except KeyError:
                print "The following column has been renamed/removed from the MySQL instance: " + key

        entry_data += ")"
        data_access_object._run_insert_stmt(add_entry_stmt=add_entry_stmt, entry_data=entry_data)
        entry_data_list.append(entry_data)

    print str(len(entry_data_list)) + " insert statements created."

    # data_access_object._run_insert_stmt(add_entry_stmt=add_entry_stmt,entry_data=entry_data_list[0])
    # data_access_object._run_batch_of_inserts(add_entry_stmt=add_entry_stmt, entry_data_list=entry_data_list)
