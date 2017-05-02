
import sqlite3 as sql
from flask import Flask, request
from dbAccess import *

app = Flask(__name__)



#General format of the API
#/MetodName/User/Pass/Other

@app.route('/')
def hellowWorld():
	return "Hello World"

@app.route('/Users')
def Users():
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		result = cur.execute("select * from Users")
		return str(result.fetchall())

@app.route('/Interest')
def test():
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		result = cur.execute("select * from Interest")
		return str(result.fetchall())

@app.route('/Activity')
def tester():
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		result = cur.execute("select * from Activity")
		return str(result.fetchall())

@app.route('/test/<test>')
def blah(test):
	buildCommand = "select * from Interest where Interest.Emailp =="
	toAdd = " \"" + str(test) + "\""
	buildCommand +=  toAdd
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		result = cur.execute(buildCommand)
		return str(result.fetchall())
		
@app.route('/test1/<test>')
def inUser(test):
	marker = isPresentInUserTable(str(test))
	return str(marker)
	
	
'''
Example Usage
Register/Thomas.Bekman12@ncf.edu/Pass
'''
@app.route('/Register/<Email>/<Password>')
def register(Email,Password):
	eEmail = str(Email)
	pPassword = str(Password)
	return Register(eEmail,pPassword)

'''
Example Usage
/changePassword/Thomas.Bekman12@ncf.edu/newPass
'''
@app.route('/changePassword/<Email>/<newPassword>')
def changePass(Email,newPassword):
	eEmail = str(Email)
	newPass = str(newPassword)
	return changePassword(eEmail,newPass)	


@app.route('/getMatches/<Email>/')
def getMatch(Email):
	eEmail = str(Email)
	return getMatches(eEmail)	

'''
Allows a user to request a new match
It will allow the match to be put in the system even if the person they are requesting is not yet in the system
Example Usage (where Thomas Bekman is trying to match with Cora Coleman on the Snuggle activity):
http://127.0.0.1:5000/requestNewMatch/Thomas.Bekman12@ncf.edu/Cora.Coleman14@ncf.edu/Snuggle
'''
@app.route('/requestNewMatch/<EmailRequester>/<EmailDesired>/<activityDescription>')
def requestMatch(EmailRequester,EmailDesired,activityDescription):
	eRequester = str(EmailRequester)
	eDesired = str(EmailDesired)
	activity = 	str(activityDescription)
	return requestNewMatch(eRequester,eDesired,activity) 
	

@app.route('/addNewActivity/<activityDescription>')
def NewActivity(activityDescription):
	return addNewActivity(str(activityDescription))

@app.route('/getActivities/')
def getActivity():
	return getActivities()

@app.route('/numberUsers')
def numUsers():
	return str(numberUsers())

@app.route('/deleteFromTable/<rowItemToDelete>/<table>/<column>')
def Delete(rowItemToDelete,table,column):
	toDelete = str(rowItemToDelete)
	tableName = str(table)
	colName = str(column)
	return str(deleteFromTable(toDelete,tableName,colName))


		
if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True)










	

	
	