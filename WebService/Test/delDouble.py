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



def uniqueKeyFinder():
	with sql.connect("MM.db") as con:
        	cur = con.cursor()
        	result = cur.execute("select * from Interest")
        	all = result.fetchall()

	UL = []
	UK = []
	for i in range(0,len(all)):
		if(all[i][1:4] not in UL):
			UL.append(all[i][1:4])
			UK.append(all[i][0])		

	return UK

UniqueKeys = uniqueKeyFinder()

def deleteFromTable(input,table,column):
	input = str(input)
	tableName = str(table)
	columnName = str(column)
	buildCommand = "delete from " + tableName + " " + "where " + tableName + "." + columnName + " =="
	toAdd = " \"" + str(input) + "\""
	buildCommand +=  toAdd

	with sql.connect("MM.db") as con:
		cur = con.cursor()
		if(isInTable(input,table,column)):
			cur.execute(buildCommand)
			return "Success"	
		else:
			return "Error: That entry is not within the table "+ tableName

############################################################################
#The following lines of code delete non-unique entries in the interest table
############################################################################
#for i in range(1,14):
	#if(i not in UniqueKeys):
		#deleteFromTable(str(i),"Interest","IDp")
print("")
print("")
print("")
print(UniqueKeys)


