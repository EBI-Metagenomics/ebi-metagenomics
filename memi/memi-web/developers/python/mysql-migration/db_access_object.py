__author__ = 'maxim'

"""
Created on 07/12/2015

@author: Maxim Scheremetjew
"""


class DataAccessObject:
    """Database access object"""

    def __init__(self, dbConnection):
        """
        Constructor
        """
        self.dbConnection = dbConnection

    def _run_insert_stmt(self, add_entry_stmt, entry_data):
        """Runs the insert statement"""
        print "Running the following insert statement:\n" + add_entry_stmt
        # print "with the following entry data:\n" + entry_data
        with self.dbConnection as c:
            cursor = c.cursor()
            # cursor.execute("INSERT INTO PUBLICATION (PUB_TITLE,PUB_ID,DOI,ISBN,PUBMED_ID,URL,RAW_PAGES,VOLUME,PUB_TYPE,MEDLINE_JOURNAL,PUBMED_CENTRAL_ID,PUB_ABSTRACT,AUTHORS,ISO_JOURNAL,ISSUE,PUBLISHED_YEAR) VALUES ('Type 2 Diabetes gut metagenome (microbiome) data from 368 Chinese samples and updated metagenome gene catalog.',348,'doi:10.5524/100036',null,null,'http://gigadb.org/t2d-gut-metagenomes/',null,'GigaScience 2012','PUBLICATION',null,null,null,'Li, S; Guan, Y; Zhang, W; Zhang, F; Cai, Z; Wu, W; Zhang, D; Jie, Z; Liang, S; Shen, D; Qin, Y; Xu, R; Wang, M; Gong, M; Yu, J; Zhang, Y; Han, L; Lu, D; Wu, P; Dai, Y; Sun, X; Li, Z; Tang, A; Zhong, S; Li, X; Chen, W; Zhang, M; Zhang, Z; Chen, H; Qin, J; Li, Y; Wang, J',null,null,2012)")
            cursor.execute(add_entry_stmt, entry_data)
            c.commit()

    def _run_batch_of_inserts(self, add_entry_stmt, entry_data_list):
        """Runs the insert statement"""
        print "Running a batch of insert statements...\n" + add_entry_stmt
        batch_size = 50
        start_index = 0
        with self.dbConnection as c:
            cursor = c.cursor()
            cursor.executemany(add_entry_stmt, entry_data_list)
            c.commit()

    def _runQuery(self, query):
        """Runs the query"""
        print "Running the following database query:\n" + query
        with self.dbConnection as c:
            cursor = c.cursor()
            cursor.execute(query)
            results = []
            result = cursor.fetchone()
            while result is not None:
                numCols = len(result)
                item = {}
                for col in range(0, numCols):
                    key = cursor.description[col][0]
                    value = result[col]
                    try:
                        # handle oracle clob datatypes
                        value = result[col].read()
                    except AttributeError:
                        pass
                    item[key] = value
                    # item = {cursor.description[col][0]: result[col] for col in range(0, numCols)}
                results.append(item)
                result = cursor.fetchone()
            return results


if __name__ == '__main__':
    pass
