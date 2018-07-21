# RSSToyProject

Hello and welcome to our project!:)
Before you run the jar file, please make sure you leave your mySQL config information in the SQL-Config file located in target/classes.
The information about our default web agencies can be found at this SQL-Config too!
Afterwards, run the jar file.
As you do this, the scraper will start working in the background. Scarapper will crawl RSS pages and will import their news into a table named "News in a database named "RSSDatabase"
Alongside with the scraper, our console is ready to serve you with your desired queris and commads.
For getting help in the console, enter ?l
The guide for commands is as follows:
con	count-of-news	(webSiteAddress, date) -> this command lets you get the count of news for a RSS address on a specific date
ln	latest-news	(webSiteLink) -> this command will give the the top latest news of the rss link
ans	add-new-site	(rssLink, pattern) -> with this command you can add a new rss link along eith main website's class for the content of the news 
s	search	(type, text) -> this command helps you search text on a specific field which is either title or content

**Important point** the dates you enter in the above queries should be in format : yyyy-mm-dd, for example: 2018-07-21

