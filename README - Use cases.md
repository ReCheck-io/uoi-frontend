# UOI Frontend - Use cases
For each use case there is shown a one or several screenshots that the user can go through to complete a given scenario.  

Opening the application for first time it will show “Create/ Update” screen without existing nodes in user session.
<img src="docs\images-readme\Use cases 1.png"/>
  
##### Create UOI  
Clicking the “New UOI” button shows the create form to the right. The user should fill the Country Code (with 2 letters as is by the standard) and level. Parent UOI is optional.
<img src="docs\images-readme\Use cases 2.png"/>  
  
Clicking on “Create” triggers Backend validation. If data input is incorrect/incomplete appropriate message is shown.
<img src="docs\images-readme\Use cases 3.png"/>

<img src="docs\images-readme\Use cases 4.png"/>  

After successful validation of input data new UOI is created and it is shown on the table.
<img src="docs\images-readme\Use cases 5.png"/>  

##### Update parent UOI  
When the user clicks on the "Data" button the UI will show “Create new” form with UOI data (fields country code and level will be disabled). To make child/parent relation with another UOI node the user have to know existing UOI node and fill "Parent UOI" text field.
<img src="docs\images-readme\Use cases 6.png"/>  

##### Add / Update Properties
When the user clicks on the "Properties" button the UI will show form on the right side.
<img src="docs\images-readme\Use cases 7.png"/>  

Clicking on "Add" adds new <key, value> pair text fields to the UI:
<img src="docs\images-readme\Use cases 8.png"/>

<img src="docs\images-readme\Use cases 9.png"/>

If there is already recorded properties they will be shown. Note that the key (property name) is disabled for editing:
<img src="docs\images-readme\Use cases 10.png"/>  

Clicking on "Update" the Frontend sends all user entered properties to the Backend to store it in the database.

##### Documents
When the user clicks on the "Documents" button the UI will show integrated external to UOI services for documents to particular UOI
<img src="docs\images-readme\Use cases 11.png"/> 

Some of the external UOI services need user related data to access documents. UOI Backend sends the user related data for verification to the external system. If the verification require administrators of the external UOI service to take decision for permitting or denying access then appropriate message will be shown.
<img src="docs\images-readme\Use cases 12.png"/>

If the user already have permissions for that type of documents for particular system then Frontend will list them.
<img src="docs\images-readme\Use cases 13.png"/>

##### Hierarchy
The Frontend shows parent and children only on one level up & down.
<img src="docs\images-readme\Use cases 15.png"/>

##### Search concrete UOI
UOI must be known in advance. If result is found it will be shown in the table:
<img src="docs\images-readme\Use cases 16.png"/>

##### Search by properties  
The Properties must be known in advance. They could be taken from "Properties" component. The <key, value> pair must be matched together with already stored <key, value> pairs in Backend database in order to show at least one. 
<img src="docs\images-readme\Use cases 17.png"/>