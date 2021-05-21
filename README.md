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
    <td>The "Properties" button when it is clicked (or when it is clicked over the row of the table) shows the "Properties" and "Update Parent UOI" components</td>  
  </tr>    
  <tr>  
    <td><img src="docs\images-readme\User interface Components 4.png"/></td>  
    <td>"Properties" component with metadata of the created UOI<br />- Add button - A new &ltkey, value&gt pair is prepared for the user to append to the UOI<br />- Update button – Takes all the new additions and updates the UOI (Frontend sends request message to the UOI Backend with the all &ltkey, value&gt pairs)</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 5.png"/></td>  
    <td>"Update Parent UOI" component<br />- For parent UOI must be provided an already existing UOI<br />- Clicking on "Update Parent UOI" button Frontend send request message to the UOI Backend with the valid UOI. Behind the scene relation between Parent UOI and early created UOI is made.<br />- Limitation: currently it cannot be delete relation when once is created</td>  
  </tr>
  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 9.png"/></td>  
    <td>"Hierarchy" <br />The Frontend shows parent and children only on one level up & down.</td>  
  </tr>
    
  <tr>  
    <td><img src="docs\images-readme\User interface Components 10.png"/></td>  
    <td>"Documents" <br />Shows integrated external to UOI services for documents to particular UOI</td>  
  </tr>
  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 6.png"/></td>  
    <td>"Search By UOI" <br />The UOI must be known in advance. I could be taken from "Table of existing UOI nodes"<br />Searching for UOI will give you the full information about the node if it exist.</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 7.png"/></td>  
    <td>"Search By Properties" <br /> The Properties must be known in advance. They could be taken from "Properties" component. The &ltkey, value&gt pair must be matched together with already stored &ltkey, value&gt pairs in Backend database in order to show at least one result.</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 8.png"/></td>  
    <td>"Search Result Table" <br />  is populated when search results are found and it has have same structure as "Table of existing UOI nodes"</td>  
  </tr>  
</table>  
  
## **Use cases**
See use cases [here](https://github.com/ReCheck-io/uoi-frontend/blob/main/README%20-%20Use%20cases.md)