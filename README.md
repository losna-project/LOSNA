# LOSNA
Online Auctions Website - Team Project (part of ENI Web Developer Course)

## Contributors:</br>
Kateryna Bohringer (user management)</br>
Maxime Baillemont (product management)</br>
Adrien Ponassie (visual/design)</br>

## Development </br>
Back-End Development:</br>
Java/Sql

Front-End Development:</br>
Html/Css/Javascript/Bootstrap

## Website functionality - User management:</br>
-Users can create account or log-in (using user name or email), save their log-in info, modify account information, and view profiles of other users.</br>
-Registration only accepts unique user name and email values.</br>
-User are automatically disconnected after 5 min of inactivity.</br>
-User can delete their account as long as they have no active bids.</br>
-Users can buy credits (with card number verification and user verification, but no real payment functionality).</br>
-Users can upload photos for their auctions (the link to the photos is stored in the database).</br>
-Users can request to reset their email, in which case a password reset token is created and a link is sent to their registered email address that is only valid for 24 hours (fully fnctional).</br>
-Administrators can edit item categories (delete or add a new category) - category menus are dynamically updated from the database.</br>
-Administrators can delete a user account or suspend an account (in which case a user can no longer make new auctions or bid on items and is notified of that with a pop-up window).</br>
-Every day a background process processes all sales that end on that day, updates the final sale price in the database and sends an email to the highest bidder who won the sale (fully functional).

## Website functionality - Product management:</br>
-Users can view details of all current auctions.</br>
-Users can create new auctions or bid on auctions.</br>
-Users can't bid more credits than they currently have in their account.</br>
-Users can't bid on their own auctions.</br>
-Users can edit or delete their listing as long as it is before the start time of the sale.</br>
-Once the sale has terminated, users are only credited the final sale price once the item has been picked up by the buyer.</br>
-When users are outbid their credits are refunded to them.</br>
-Users can sort the auctions by multiple criteria: all auctions that are open, auctions based on category or name of item, auctions they currently have bids on, auctions that are finished that the user won.</br>
-Users can sort their own sales by multiple criteria: all their current sales, their sales that have not started yet, their sales that are finished.</br>
-Users can view a list of all thier sales with the current top bid and the name of the top bidder.</br>
-Fully functional pagination of main sale page, with maximum of 6 articles per page.</br>

## Website design:</br>
-Responsive web design, adapted to desktop or mobile phone use.</br>
-Multi language functionality (English/French).</br>

## Notes:</br>
To create the trial database you can use the sql files provided: Create DB/Data for DB.</br>
You will need to modify ForgotPassword.java with your own admin email/password details (must be gmail), and NewAuction.java with a path to your images folder.
