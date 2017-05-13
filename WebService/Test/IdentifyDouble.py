import sqlite3 as sql

def isInTable(input,table,column):

	tableName = str(table)
	columnName = str(column)
	buildCommand = "select * from " + tableName + " " + "where " + tableName + "." + columnName + " =="
	toAdd = " \"" + str(input) + "\""
	buildCommand +=  toAdd
	
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		result = cur.execute(buildCommand)	
		if(len(result.fetchall())>0):
			return True
		else:
			return False


def getActivityID(ActivityDescription):
	if(isInTable(ActivityDescription,"Activity","ActivityDescription")):
		queryString="select ID from Activity where ActivityDescription == \""+ActivityDescription+"\";"
		with sql.connect("MM.db") as con:
			cur = con.cursor()
			result = cur.execute(queryString)
			return result.fetchall()[0][0]

	else:
		return "ERROR: Activity not in the table"
'''
Determines if the row defined by: eMailRequester,eMailDesired,activityDescription
Actually exists within the Interest table
True returned if that row does not exist in the table
False returned if that row is in the Interest Table
'''

def notInInterestTable(eMailRequester,eMailDesired,activityDescription):
	requesterEmail = str(eMailRequester)
	eMailDesire = str(eMailDesired)
	activityID = getActivityID(activityDescription)
	if(activityID == "ERROR: Activity not in the table"): #Ensures the activity exists within the activity table
		return "ERROR: " + str(activityDescription) + " is not in the activity table"
	with sql.connect("MM.db") as con:
        	cur = con.cursor()
        	result = cur.execute("select * from Interest")
        	all = result.fetchall()
	isNotIn = True 
	for item in all:
		if(requesterEmail == item[1] and eMailDesire == item[2] and activityID == item[3]):
			isNotIn = False
	return isNotIn

print(notInInterestTable("Cora.Coleman14@ncf.edu","Cora.Coleman14@ncf.edu","Snuggle"))