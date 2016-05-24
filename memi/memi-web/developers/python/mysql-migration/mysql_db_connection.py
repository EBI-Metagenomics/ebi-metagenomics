'''
Created on 18 Feb 2016

@author: Maxim Scheremetjew
'''

import mysql.connector
from mysql.connector import errorcode


class MySQLDBConnection:
    """Context manager class for oracle DB connection"""

    def __init__(self, **config):
        self.config = config

    def __enter__(self):
        try:
            print 'Connecting to MySQL datavbase...'
            self.connection = mysql.connector.connect(**self.config)
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print("Something is wrong with your user name or password")
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exist")
            else:
                print(err)
        else:
            print "Connection successfully established."
        return self.connection

    def __exit__(self, ext_type, exc_value, traceback):
        self.connection.close()
        self.connection = None
        print "Connection closed and cleaned up."


if __name__ == '__main__':
    pass
