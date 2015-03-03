#!/usr/bin/python

import zipfile
import sys
import os
import shutil
from optparse import OptionParser
import re

def replaceValue(regex, newValue, contents):
    # check there is actually a match of the expected type
    match = regex.search(contents)
    if (not match) or len(match.groups()) > 2:        
        print "The following regex did not properly match with any property:", regex.pattern
        sys.exit(1)
    # The expression below puts the new value we want in between groups 1 and 2 of the match
    # when doing the substitution
    return regex.sub('\\1' + newValue + '\\2', contents)

parser = OptionParser()
parser.add_option("-w", "--warfile", help="the warfile you want to alter (compulsory option) NB this war will not be altered and will be copied to a backup file as a safety measure", dest='war')
parser.add_option("-d", "--dburl", help="the database url to be written to the war file", dest='dbUrl')
parser.add_option("-u", "--username", help="the username to be written to the war file", dest='username')
parser.add_option("-p", "--password", help="the password to be written to the war file", dest='password')
(options, args) = parser.parse_args()

if not options.war:
    parser.print_help()
    sys.exit(1)

war = options.war
tempWar = options.war + '.tmp'
backupWar = war + '.bak'

# Because of how regex substitution works in python
# we have to make sure that all parts of the regex that stay the same
# are captured in groups (parentheses)
# This allows the replaceValue function above to do the substitution quite simply
basicPropertyString = '(property\s+name="{0}"\s+value=").*(")'
dbUrlRegex = re.compile(basicPropertyString.format('url'))
usernameRegex = re.compile(basicPropertyString.format('username'))
passwordRegex = re.compile(basicPropertyString.format('password'))


if not os.path.exists(war):
    print "Could not find WAR file:", war
    sys.exit(1)

if not zipfile.is_zipfile(war):
    print "Not a valid WAR file:", war
    sys.exit(1)

zipInFile = zipfile.ZipFile(war, 'r')
zipOutFile = zipfile.ZipFile(tempWar, 'w')
for item in zipInFile.infolist():
    contents = zipInFile.read(item.filename)
    if item.filename == 'WEB-INF/spring/datasource-beans-context.xml':       
                             
        if options.dbUrl:            
            contents = replaceValue(dbUrlRegex, options.dbUrl, contents)            
        if options.username:
            contents = replaceValue(usernameRegex, options.username , contents)
        if options.password:
            contents = replaceValue(passwordRegex, options.password, contents)      
    
    zipOutFile.writestr(item, contents)
zipInFile.close()
zipOutFile.close()

# make backup of original war
shutil.copyfile(war, backupWar)
shutil.move(tempWar, war)