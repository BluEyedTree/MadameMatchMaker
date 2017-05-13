import sqlite3 as sql
def test():
	with sql.connect("MM.db") as con:
		cur = con.cursor()
		result = cur.execute("select * from Interest")
		all = result.fetchall()
	return all
print(test())
