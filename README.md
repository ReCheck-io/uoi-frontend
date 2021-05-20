# UOI Frontend

UOI Frontend is a web application accessed via web browser which makes possible the creation, updating and searching of the Unique object identifier (UOI) through service provider called UOI Backend (which implements an API). Frontend and Backend exchange messages in a request–response messaging pattern.

This repository contains the source code of the UOI Frontend (no UOI backend API code here)

UOI Frontend is currently hosted on [https://uoi-frontend.recheck.io/](https://uoi-frontend.recheck.io/)

## Data model of UOI

- Country code: should answer which country is a resident of the building unit. The format must be "ISO 3166-1 alpha-2"
- Level: It is the level in the topology of the UOI system. As of now there are the following levels: _BUILDING\_BLOCK, BUILDING, UNIT, ROOM, WALL, ELEMENT, PRODUCT\_SAMPLE_
- UOI: The UOI is created by having the country&#39;s acronym in capital letters + the uuid4 (universally unique identifier). Ex: NL.dfb2d5f4-89e3-43b0-840d-7c1cc57aa014
- Properties: Contains metadata for concrete UOI. It is list of <key, value> pair (map).
 Example properties:
	* length: 0,
	* height: 0,
	* width: 0,
	* materials: null,
	* tenant: null,
	* address: null,
	* longitude: 0,
	* latitude: 0,
	* resources: null,
	* BAG: "123123213",
	* Company Owner: "Hello Inc",
	* VAT: "12312312321

## User interface Components

<table align="center">
  <tr>
    <td><img src="docs\images-readme\User interface Components 1.png"/></td>
    <td>UOI Creation Form:<br /> - Create button – when it is clicked Frontend send request message to the UOI Backend<br /> - Request message contains the filled UI fields.<br /> - Parent UOI is optional</td>
  </tr>
  <tr>
    <td><img src="docs\images-readme\User interface Components 2.png"/></td>
    <td>Table of existing UOI nodes <br />- The table is populated with the created UOI after receiving response from Backend.</td>
  </tr>
  <tr>
    <td><img src="docs\images-readme\User interface Components 3.png"/></td>
    <td>The "View Properties" button when it is clicked (or when it is clicked over the row of the table) shows the "Properties" and "Update Parent UOI" components</td>
  </tr>  
  <tr>
    <td><img src="docs\images-readme\User interface Components 4.png"/></td>
    <td>"Properties" component with metadata of the created UOI<br />- Add button - A new key/value pair is prepared for the user to append to the UOI<br />- Update button – Takes all the new additions and updates the UOI (Frontend sends request message to the UOI Backend with the all key and value pairs)</td>
  </tr>
  <tr>
    <td><img src="docs\images-readme\User interface Components 5.png"/></td>
    <td>"Update Parent UOI" component<br />- For parent UOI must be provided an already existing UOI<br />- Clicking on "Update Parent UOI" button Frontend send request message to the UOI Backend with the valid UOI. Behind the scene relation between Parent UOI and early created UOI is made.<br />- Limitation: currently it cannot be delete relation when once is created</td>
  </tr>
  <tr>
    <td><img src="docs\images-readme\User interface Components 6.png"/></td>
    <td>"Search By UOI" <br />The UOI must be known in advance. I could be taken from "Table of existing UOI nodes"<br />Searching for UOI will give you the full information about the node if it exist.</td>
  </tr>
  <tr>
    <td><img src="docs\images-readme\User interface Components 7.png"/></td>
    <td>"Search By Properties" <br /> The Properties must be known in advance. They could be taken from "Properties" component. The <key,value> pair must be matched together with already stored <key,value> pairs in Backend database in order to show at least one result.</td>
  </tr>
  <tr>
    <td><img src="docs\images-readme\User interface Components 8.png"/></td>
    <td>"Search Result Table" <br />  is populated when search results are found and it has have same structure as "Table of existing UOI nodes"</td>
  </tr>
</table>

## **Use cases**

For each use case there is shown a one or several screenshots that the user can go through to complete a given scenario.

##### Create UOI
The user should fill the Country Code (with 2 letters as is by the standard) and LEVEL. Parent UOI is optional.
<img src="docs\images-readme\Use cases 1.png"/>

The user will see the result in the table after clicking the "Create" button
<img src="docs\images-readme\Use cases 2.png"/>


##### Add / Update Properties
When the user clicks over the row or "View Properties" button the UI will go to update state (fields country code and level will be disabled and "Properties" component will be shown):
<img src="docs\images-readme\Use cases 3.png"/>

If there is already recorded properties they will be shown. Note that the key (property name) is disabled for editing:
<img src="docs\images-readme\Use cases 4.png"/>

Clicking on "Add" adds new pair of key and value text fields to the UI:
<img src="docs\images-readme\Use cases 5.png"/>

Clicking on "Update Properties" the UI will go to create state
<img src="docs\images-readme\Use cases 6.png"/>


##### Update parent UOI
When the user clicks over the row or "View Properties" button the UI will go to update state (fields country code and level will be disabled and "Properties" component will be shown)
<img src="docs\images-readme\Use cases 7.png"/>

To make relation with another UOI node the user have to know existing UOI node and fill "Parent UOI" text field
<img src="docs\images-readme\Use cases 8.png"/>

When the user clicks over "Update Parent UOI" will see result in the updated table:
<img src="docs\images-readme\Use cases 9.png"/>


##### Search concrete UOI
If result is found it will be shown in the table:
<img src="docs\images-readme\Use cases 10.png"/>

The user can update properties as it is on "Add / Update Properties" case
<img src="docs\images-readme\Use cases 11.png"/>


##### Search by properties
The Properties must be known in advance. They could be taken from "Properties" component. The <key,value> pair must be matched together with already stored <key,value> pairs in Backend database in order to show at least one result.
<img src="docs\images-readme\Use cases 12.png"/>