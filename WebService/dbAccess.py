import sqlite3 as sql

'''
Converts the standard format of sqlLite queries into the following
Standard sqlLite format:
[("Email1","item1"),("Email2","item1")]
The format of this web service:
Email1;item1,Email2,item1
The tuples are seperated with a semi colon while the tuples are seperated with a comma

'''

def format(inputList):
	outputList =[]
	counter = 0
	lenList = len(inputList)
	for Tuple in inputList:
		counter+=1
		for tupleItem in Tuple:
			outputList.append(tupleItem)
			if(Tuple.index(tupleItem) != len(Tuple)-1):
				outputList.append(";")
		if(counter<lenList):
			outputList.append(",")
	return "".join(outputList)


'''
Removes repeated elements that may be returned if multiple requests are made to the same person.
'''
def deleteRepeats(inputList):
	userList = []
	userAct = {}
	outputList = []
	outOutList = []# Creates the correct dictionary of values
	for Tuple in inputList:
		if (Tuple[0] not in userList):
			userList.append(Tuple[0])
			userAct[Tuple[0]] = [Tuple[1]]
		elif(Tuple[1] not in userAct[Tuple[0]]):
			userAct[Tuple[0]].append(Tuple[1])

# Converts the dictionary into a list
	for key in userAct:
		outputList.append(key)
	for item in userAct[key]:
		outputList.append(item)
	outOutList.append(tuple(outputList))
	outputList = []

	return outOutList


'''
Checks to see if the given input, exists within that column of a given table.
Example usage:
isInTable("Thomas.Bekman12@ncf.edu","Users","Email")
'''
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

'''
Deletes the row that contains input within the column of table. 
'''
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
			

'''
Adds a new user to the Users table. 
Because of poor databse design the email will also be the username
!!! Add email verifcation !!!
'''
def Register(Email,Pass):
	user = Email
	if(not isInTable(Email,"Users","Email")):
		with sql.connect("MM.db") as con:
			cur = con.cursor()
			cur.execute("INSERT INTO Users (Email,Username,Password) VALUES (?,?,?)", (Email,Email,Pass))
			con.commit()
			return "Success"
	else:
		return "ERROR: User already in the table"

'''
Changes the password of an account
'''		
def changePassword(Email,newPass):
	if(isInTable(Email,"Users","Email")):
		with sql.connect("MM.db") as con:
			cur = con.cursor()
			cur.execute("UPDATE Users SET Password = \'" + str(newPass) + "\' WHERE Email == \'" +str(Email) + "\'")
			con.commit()
			return "Success"
	else:
		return "ERROR: User Not in Table"	
		

'''
Returns the matches for the input eMail
For each match on activity the following is returned:
("MatchedEmail@ncf.edu","Activity")
'''
		
def getMatches(eMail):
	if(isInTable(eMail,"Users","Email")):
		queryString= "select a.InterestEmail,ActivityDescription from Interest a, Interest b, Activity c where a.Emailp == b.InterestEmail and b.Emailp == a.InterestEmail and a.ActivityID == b.ActivityID and a.Emailp == \""+eMail+"\"  and a.ActivityID == c.ID;"	
		with sql.connect("MM.db") as con:
			cur = con.cursor()
			result = cur.execute(queryString)
			return format(deleteRepeats(result.fetchall()))
	else:
		return "ERROR: User Not in Table"
		
	
def requestNewMatch(eMailRequester,eMailDesired,activityDescription):
	if(str(getActivityID(activityDescription))[0] != "E"):#Ensures it didn't return an error
		activityID = getActivityID(activityDescription)
	else:
		return "ERROR: Activity Description not in Activity Table"	
	
	if(isInTable(eMailRequester,"Users","Email")):
		with sql.connect("MM.db") as con:
			cur = con.cursor()
			cur.execute("INSERT INTO Interest (Emailp,InterestEmail,ActivityID) VALUES (?,?,?)", (eMailRequester,eMailDesired,activityID))
			con.commit()
			return "Success"	
	else:
		return "ERROR: User Not in User Table"
		

def getActivityID(ActivityDescription):
	if(isInTable(ActivityDescription,"Activity","ActivityDescription")):
		queryString="select ID from Activity where ActivityDescription == \""+ActivityDescription+"\";"
		with sql.connect("MM.db") as con:
			cur = con.cursor()
			result = cur.execute(queryString)
			return result.fetchall()[0][0]

	else:
		return "ERROR: Activity not in the table"

def addNewActivity(activityName):
	currentMax = ""
	with sql.connect("MM.db") as con:
		queryString = "SELECT MAX(ID) FROM Activity;" #Gets the highest current value for ActivityID
		cur = con.cursor()
		result = cur.execute(queryString)
		currentMax = int(result.fetchall()[0][0]) #The two zeroes take the odd query result format and turn into a string of an integer
		if (not isInTable(activityName, "Activity","ActivityDescription")):
			cur.execute("INSERT INTO Activity (ID,ActivityDescription) VALUES (?,?)", (currentMax+1,activityName))
			con.commit()
			return "Success"	
		else:
			return "Error: " +str(activityName)+" is already in the table"

'''
Returns a string of each activity seperated by a comma
'''


def getActivities():
	with sql.connect("MM.db") as con:
			cur = con.cursor()
			queryString = "select ActivityDescription from Activity;"
			result = cur.execute(queryString)
			return format(result.fetchall())


def numberUsers():
	with sql.connect("MM.db") as con:
			cur = con.cursor()
			queryString = "SELECT COUNT(Email) FROM Users;"
			result = cur.execute(queryString)
			return result.fetchall()[0][0]



