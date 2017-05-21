import sqlite3 as sql

'''
Converts the standard format of sqlLite queries into the following
Standard sqlLite format:
[("Email1","item1"),("Email2","item1")]
The format of this web service:
Email1;item1,Email2,item1
The tuples are seperated with a semi colon while the tuples are seperated with a comma
Mainly used for getActivities Function
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
Checks to see if a row already exists that contains: eMailRequester,eMailDesired,activityDescription 

select *
from Interest
where Interest.Emailp = "Thomas.Bekman12@ncf.edu" && Interest.InterestEmail = "Cora.Coleman14@ncf.edu" && Interest.ActivityID=2;

FINISH ME ADD Proper SQL query, see if fetchall greater than one.
Then do correct query for match
'''
def IsInInsertTable(eMailRequester,eMailDesired,activityDescription):
	requesterEmail = str(eMailRequester)
	eMailDesire = str(eMailDesired)
	activityID = getActivityID(activityDescription)
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		buildCommand = "select * from Interest where Interest.Emailp == \""+ str(eMailRequester)+ "\""+" and Interest.InterestEmail == \"" + str(eMailDesire) + "\" and Interest.ActivityID="+ "\""+ activityID+ "\"" + ";"
		result = cur.execute(buildCommand)	
		
		if(len(result.fetchall())>0):
			return True
		else:
			return False

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


def login(Email,Password):


	buildCommand = "select " +"Email" +" from " + "Users" + " " + "where " + "Users" + "." + "Password" + " =="
	toAdd = " \"" + str(Password) + "\""
	buildCommand +=  toAdd
	toAdd = " and " + "Users.Email ==" + " \"" + str(Email) + "\""
	buildCommand +=  toAdd
	
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		result = cur.execute(buildCommand)	
		if(len(result.fetchall())>0):
			return str("True")
		else:
			return str("False")
	
'''
TEST SHIT
'''
print(login('Cora.Coleman14@ncf.edu','Cora')) #Should return TRUE
print(login('sadas','ssadas')) #Should return False



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
			return formatterForGetMatches(result.fetchall())
	else:
		return "ERROR: User Not in Table"
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


'''
-Add verifcation that emailRequester is in the table
-Use notInInterestTable to ensure your not adding repeated elements

'''
	
def requestNewMatch(eMailRequester,eMailDesired,activityDescription):
	if(str(getActivityID(activityDescription))[0] != "E"):#Ensures it didn't return an error
		activityID = getActivityID(activityDescription)
	else:
		return "ERROR: Activity Description not in Activity Table"	
	
	if(not notInInterestTable(eMailRequester,eMailDesired,activityDescription)):
		return "Error: Entry already is in Interest Table"
	
	
	if(isInTable(eMailRequester,"Users","Email")):
		
		with sql.connect("MM.db") as con:
			cur = con.cursor()
			cur.execute("INSERT INTO Interest (Emailp,InterestEmail,ActivityID) VALUES (?,?,?)", (eMailRequester,eMailDesired,activityID))
			con.commit()
			return "Success"	
	else:
		return "ERROR: User Not in User Table"
'''
Converts the output query into an easier to understand format
Example:

Email;Act1;Act2&Email2;Act1;Act2;Act3...

'''

		
def formatterForGetMatches(inputQuery):
  
  def dictionaryFromatter(inputQuery):
    adic ={}
    for tup in inputQuery:
      if(tup not in adic):
        adic[tup[0]]=[]
  
    for tup in inputQuery:
      for k in adic:
        if(tup[0]==k):
          adic[k].append(tup[1])
    return adic
  
  inDic = dictionaryFromatter(inputQuery)
  
  formatList = []
  KCounter = 0
  for k in inDic:
      formatList.append(k)
      formatList.append(";")
      lLength = len(inDic[k])
      for i in range(0,lLength):
        formatList.append(inDic[k][i])
        if(i==lLength-1):
          formatList.append("&")
        else:
          formatList.append(";")
  formatList = "".join(formatList)    
  formatList = formatList[:-1]
  return(formatList)  
  



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

