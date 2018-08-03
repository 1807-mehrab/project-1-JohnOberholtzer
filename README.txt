Project 1, by John Paul Oberholtzer II
03AUG2016

As a preface, the current configuration of this project uses an absolute path for the 
connection.properties file to be located. The absolute path is contained in ConnectionUtil.java 
and needs to be modified in order to run on another computer. When running this project on an
apache tomcat server, it also currently must be run on port 8080, or else every AJAX call's URL
will need to be modified. In retrospect this could be easily reformatted to configure a URL prefix
for all servlet AJAX calls in one location.





RBSYS is the working title of my Employee Reimbursement System.

This is a web application is utilizing AJAX to communicate with an Apache Tomcat Server, which
contain numerous java servlets. Each java servlet performs a different action, primarily in response to
buttons that activate javascript functions on the front end. These java servlets instantiate one or more DAOs
when necessary, which communicate with an Oracle SQL database.

My SQL Database contains three tables and stored procedures that fulfill necessary CRUD functions on those tables.
My three tables are Employees, Tickets, and Images.
The Employees table contains all users in my database, and managers are identified by a boolean value.
All requests are represented in the Ticket table, and the majority of relevant information (ticket amount,
title, description, status, ID) are contained there. Employees can have many tickets (one-to-many), but each Ticket
can have one owner, and may have one ID for another employee who approved or denied it.

The Images table contains images that relate to an individual ticket. This was kept separate from the ticket table
in order reduce the amount of data being moved around (tickets are loaded far more often than their images are)
as well as to help streamline development by having it as an isolated system. 
Every ticket can have many images (one-to-many).

I have three Data Access Objects, one for each of my tables. Each calls various callable and prepared statements which
perform queries or stored procedures and fulfill CRUD operations on the database.

I have five beans, three of which are directly related to tables and store information pulled via
the DAOs (Employee, Ticket, Image). The other two (Result, Login) are utilized when i needed to pass less 
information via Jackson but am still utilizing JSONs in order to package information.

I have approximately twenty servlets, each of which performs a specific job. All are activated by AJAX calls from
specific buttons that either exist naturally or are created by DOM in javascript functions in my frontend.

My front-end consists of three HTML pages, formatted with Bootstrap and one CSS document. These are Index.html,
ehome.html, and mhome.html. The mhome page is a copy of the ehome page with two additional buttons on its navbar
for management related functions. Index is only used for login, and all remaining functionality is contained
within either ehome or mhome, utilizing DOM to rewrite the body of the page every time a button is pressed.

I have four AJAX documents that divide my AJAX calls into categories based on which data form they are attempting 
to work with.

GenericAJAX controls login functionality and some of the simpler methods like logging out and the navbar home button,
which just displays predefined text currently. I would like to update the actual home display to be showier and perhaps
swap between company news files of some sort.

ProfileAJAX controls functions related to reading and writing Employee information.
TicketAJAX controls functions related to reading and writing Ticket information.
ImageAJAX controls functions related to reading and writing Image information.






The actual website layout begins with the login page at index.html. The user can enter their login information here.
I am utilizing AJAX to send their login information to the LoginServlet, which sends back a response between 0 and 3
depending on a comparison. If the AJAX receives a 0 or 1 back, the employee or manager respectively was able to login.
If it receives a 2 or 3, those are error returns for either the username being invalid or the password being incorrect.
If login succeeds, the window changes. If not, DOM is utilized to insert a warning below the form.

On the homepage (employee or manager), there is a Navigation Bar which allows the user to Logout, view their profile,
see the homepage default text, and view their own tickets. Managers additionally can view all tickets and all employees.

All of these navigation buttons are actually calling javascript functions that use 
DOM to rewrite the main body of the page.


Logging out invalidates your current session, necessitating a new login and redirecting the page.
Most of the application performs a session check via an onload function, and will redirect back to the login
page if the user is not logged in.

On the Profile page, users can view and edit their own profile information.
The home button loads a predefined informational page. I would like to spruce this up and have news here in the future.
On the My Tickets page, users can see their tickets, filter their view, and create new tickets.
Viewed tickets may be edited if accessed through this menu.

For managers, the All Tickets page functions similarly to My Tickets, but also
displays ticket owners. The view ticket function is actually a duplicate of the original that
has been slightly modified to have different buttons generated, so that a manager cannot edit a ticket,
but is able to approve/deny tickets.

Also for managers, the Employees page lists all Employees. Managers count as Employees.
New employees can be registered here, and accounts can be deleted. I need to implement a check to ensure
that the sysadmin account cannot be deleted.
Employee registration takes in a first name and a last name and will create a username that consists
of the first letter of the first name combined with the rest of the last name. If that username already
exists a number will be appended to the end. This number will increment as many times as necessary to find 
a valid username.

From the employees page a manager can view their personal information and can view their tickets.
I added cancel/back buttons to improve the ability of the user to navigate the site by removing the need for the user
to go back to the navigation bar menus in order to reach the original lists they were viewing.

Images I tackled after the other systems for ticket submission were in place. Images are read from a form as a file, and
sent to a servlet via an AJAX call. The file is received and read from an Inputstream. a ByteArrayOutputStream is then
used to write the file data into a Byte Array. This Byte Array is then passed inside the Image Bean to a DAO and is stored
in the Oracle SQL database in Binary Large OBject (BLOB) format. Images are loaded whenever a user attempts to view the
images of a ticket, and are pulled as Byte Arrays from the database, passed back inside a JSON, formatted for base64
encoding, and inserted into an <img> tag in html as the src element.

Given more time I would like to make a window that could be magnified to make it possible to view images closely. Originally my images
were sized based on their actual size, and could be extremely large or very small. To bypass this I set the images to a
fixed width and height so that they are always viewable, but I would like to upgrade to a more elegant solution.





