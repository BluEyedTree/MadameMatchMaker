import sys
sys.path.insert(0, '/Users/Tom/desktop/Fflask')

from dbAccess import *


'''
General Unit Test Function
Feed im a function(args) and put in expected output
If the function outputs true then its working
'''

def assertEquals(function,ExpectedOutput):
	if(function == ExpectedOutput):
		return True
	else:
		return False

#Case where activity does not exist but users do
'''
Correct output when a non-existenet function is tried
'''

print(assertEquals(requestNewMatch("Thomas.Bekman12@ncf.edu","Cora.Coleman14@ncf.edu","Fake"),"ERROR: Activity Description not in Activity Table"))


'''
Test to see how it deals with repeat elements being entered
'''



print(assertEquals(requestNewMatch("Thomas.Bekman12@ncf.edu","Cora.Coleman14@ncf.edu","Snuggle"),"Error: Entry already is in Interest Table"))