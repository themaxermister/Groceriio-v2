
# Groceriio-v2

A brief description of what this project does and who it's for






## Database

We used Firebase to manage our users' data and retrieve necessary information.
Product images are stored in Firebase Storage. We store our user's name in Firebase as well.
## Login & Register (P0)

Email Verification is implemented to make sure each email can only be registered once.




## Map Function (P0)

Google Cloud Platform is utilised to retrieve user's location and compare it with
multiple store locations to identify the closest location from the user.

We also allow user to manually key in their desired location so that they could
help check stock availability for others such as their parents or friends.

Comparator is used to sort the list of Location object in order of nearest to furtherest.


## Search Function & View Product Stock (P1)

Upon logging into Grocerrio, user can select a category they are interested in and search for the
item they would like to purchase/check.



## Order Function (P1)


This function allow users to place their order to the nearest store from their location.
After placing the order, they should proceed to collect their products from the store.
